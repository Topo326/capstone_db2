package Form.Inicio;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

/**
 * Vista para ver tareas del usuario logueado con filtro por estado y lista tipo
 * "hoja". NO importamos com.model.Task ni com.model.TaskDetail para evitar
 * conflicto con este JFrame llamado Task.
 */
public class Task extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(Task.class.getName());

    // ---- Controlador y modelo de lista (usa nombres totalmente calificados) ----
    private final com.controller.TaskController taskController = new com.controller.TaskController();
    private final DefaultListModel<com.model.Task> taskListModel = new DefaultListModel<>();
    private JList<com.model.Task> taskList;
    private JComboBox<Object> cbState;

    // ---- Componentes de tu diseño (se mantienen tus colores/estilo) ----
    private javax.swing.JPanel BackGround;
    private javax.swing.JPanel BackGround2;
    private javax.swing.JLabel Logo;
    private javax.swing.JButton TeamButton;
    private javax.swing.JLabel Username;
    private javax.swing.JLabel Titulo;
    private javax.swing.JLabel LogOut;
    private javax.swing.JButton AddButton;

    public Task() {
        initComponents();  // tu layout base y colores
        setResizable(false);
        buildTaskArea();       // agrega barra de filtros + lista “hoja”
        loadTasks(null);       // carga inicial (Todos)
        setLoggedUsername();   // pinta username desde SessionData si existe
    }

    // ===================== Helpers de cabecera =====================
    private void setLoggedUsername() {
        try {
            if (com.controller.util.SessionData.getLoggedUser() != null) {
                Username.setText(com.controller.util.SessionData.getLoggedUser().getUsername());
            }
        } catch (Exception ignored) {
        }
    }

    // ===================== UI BASE (sidebar + contenedor) =====================
    @SuppressWarnings("unchecked")
    private void initComponents() {
        BackGround = new javax.swing.JPanel();
        BackGround2 = new javax.swing.JPanel();
        Logo = new javax.swing.JLabel();
        TeamButton = new javax.swing.JButton();
        Username = new javax.swing.JLabel();
        Titulo = new javax.swing.JLabel();
        LogOut = new javax.swing.JLabel();
        AddButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 600));

        BackGround.setBackground(new java.awt.Color(31, 164, 206));
        BackGround.setPreferredSize(new java.awt.Dimension(900, 600));
        BackGround.setLayout(new BorderLayout()); // BorderLayout para panel derecho

        // ---- Sidebar ----
        BackGround2.setBackground(new java.awt.Color(18, 54, 75));
        BackGround2.setPreferredSize(new Dimension(300, 600));

        TeamButton.setBackground(new java.awt.Color(20, 105, 136));
        TeamButton.setFont(new java.awt.Font("SansSerif", 1, 20));
        TeamButton.setForeground(Color.WHITE);
        TeamButton.setText("Team");
        TeamButton.setBorder(null);
        TeamButton.setFocusPainted(false);
        TeamButton.addActionListener(this::TeamButtonActionPerformed);

        Username.setFont(new java.awt.Font("SansSerif", 1, 32));
        Username.setForeground(Color.WHITE);
        Username.setHorizontalAlignment(SwingConstants.CENTER);
        Username.setText("Username");

        JPanel sideLayout = new JPanel();
        sideLayout.setOpaque(false);
        sideLayout.setLayout(new BoxLayout(sideLayout, BoxLayout.Y_AXIS));
        sideLayout.add(Box.createVerticalStrut(30));
        sideLayout.add(Logo);
        sideLayout.add(Box.createVerticalGlue());
        sideLayout.add(Username);
        sideLayout.add(Box.createVerticalStrut(40));
        TeamButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sideLayout.add(TeamButton);
        sideLayout.add(Box.createVerticalStrut(40));

        BackGround2.setLayout(new BorderLayout());
        BackGround2.add(sideLayout, BorderLayout.CENTER);

        // ---- Top bar derecha ----
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        Titulo.setFont(new java.awt.Font("SansSerif", 1, 36));
        Titulo.setHorizontalAlignment(SwingConstants.CENTER);
        Titulo.setText("Tasks");
        topBar.add(LogOut, BorderLayout.EAST);
        topBar.add(Titulo, BorderLayout.CENTER);

        // ---- Botón Add (abajo derecha) ----
        JPanel bottomBar = new JPanel();
        bottomBar.setOpaque(false);
        AddButton.setBackground(new java.awt.Color(20, 105, 136));
        AddButton.setFont(new java.awt.Font("SansSerif", 1, 20));
        AddButton.setForeground(Color.WHITE);
        AddButton.setText("Add");
        AddButton.setBorder(null);
        AddButton.setFocusPainted(false);
        AddButton.addActionListener(this::AddButtonActionPerformed);
        bottomBar.add(AddButton);

        // ---- Contenedor derecho (filtros arriba, lista centro) ----
        JPanel rightRoot = new JPanel(new BorderLayout());
        rightRoot.setOpaque(false);
        rightRoot.add(topBar, BorderLayout.NORTH);
        // CENTER y SOUTH se completan luego
        rightRoot.add(bottomBar, BorderLayout.SOUTH);

        BackGround.add(BackGround2, BorderLayout.WEST);
        BackGround.add(rightRoot, BorderLayout.CENTER);

        getContentPane().add(BackGround);
        pack();
        setLocationRelativeTo(null);
    }

    // ===================== ÁREA DE TAREAS (filtros + lista) =====================
    private void buildTaskArea() {
        // Barra de filtros
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterBar.setOpaque(false);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 16));

        cbState = new JComboBox<>(new Object[]{
            "Todos",
            com.model.enums.TaskState.Pending,
            com.model.enums.TaskState.InProgress,
            com.model.enums.TaskState.Done,
            com.model.enums.TaskState.Canceled
        });
        cbState.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbState.addActionListener(e -> reloadTasks());

        filterBar.add(lblEstado);
        filterBar.add(cbState);

        // Lista de tareas con renderer tipo “hoja”
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFixedCellHeight(120);
        taskList.setCellRenderer(new TaskCardRenderer());

        JScrollPane scroll = new JScrollPane(taskList);
        scroll.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Agregar al panel derecho (centro del BorderLayout de BackGround)
        JPanel rightRoot = (JPanel) ((BorderLayout) BackGround.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        rightRoot.add(filterBar, BorderLayout.NORTH);
        rightRoot.add(scroll, BorderLayout.CENTER);
    }

    // ===================== LÓGICA: CARGA Y FILTRO =====================
    private void reloadTasks() {
        Object sel = cbState.getSelectedItem();
        com.model.enums.TaskState st = (sel instanceof com.model.enums.TaskState)
                ? (com.model.enums.TaskState) sel
                : null;
        loadTasks(st);
    }

    private void loadTasks(com.model.enums.TaskState filter) {
        try {
            System.out.println("[DEBUG] Usuario sesión: "
                    + (com.controller.util.SessionData.getLoggedUser() != null
                    ? com.controller.util.SessionData.getLoggedUser().getId()
                    : "null"));
        } catch (Exception e) {
            System.out.println("[DEBUG] No pude obtener DB/UID: " + e.getMessage());
        }

        new javax.swing.SwingWorker<java.util.List<com.model.Task>, Void>() {
            @Override
            protected java.util.List<com.model.Task> doInBackground() {
                return taskController.getMyTasks(filter);
            }

            @Override
            protected void done() {
                try {
                    taskListModel.clear();
                    for (com.model.Task t : get()) {
                        taskListModel.addElement(t);
                    }
                    System.out.println("[DEBUG] Tareas cargadas: " + taskListModel.size());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Task.this,
                            "Error cargando tareas: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    // ===================== ACCIONES =====================
    private void TeamButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Abre pantalla del equipo si la tienes
        JOptionPane.showMessageDialog(this, "Team (pendiente)");
    }

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Diálogo mínimo para crear tarea (puedes abrir un JFrame propio)
        String title = JOptionPane.showInputDialog(this, "Título de la tarea");
        if (title == null || title.isBlank()) {
            return;
        }

        try {
            // Crea la entidad y llama a tu lógica de guardado
            // Ejemplo (si implementaste createTaskForUser en tu controller):
            // com.model.Task t = new com.model.Task();
            // t.setTaskName(title);
            // taskController.createTaskForUser(t, com.controller.util.SessionData.getLoggedUser().getId());
            JOptionPane.showMessageDialog(this, "Implementa la creación en tu Controller/DAO 😉");
            reloadTasks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo crear la tarea: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== RENDERER: Descripción / Equipo / Estado / Creada =====================
    private static class TaskCardRenderer extends JPanel implements ListCellRenderer<com.model.Task> {

        private final JLabel lblTitle = new JLabel();
        private final JLabel lblDesc = new JLabel();
        private final JLabel lblTeam = new JLabel();
        private final JLabel lblState = new JLabel();
        private final JLabel lblCreated = new JLabel();

        private final JPanel infoCol = new JPanel();
        private static final Color BRAND = new Color(20, 105, 136);

        TaskCardRenderer() {
            setLayout(new BorderLayout(8, 6));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(8, 10, 8, 10),
                    BorderFactory.createLineBorder(BRAND, 2, true)
            ));
            setOpaque(true);

            lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 16f));
            Font small = lblTitle.getFont().deriveFont(Font.PLAIN, 12f);
            for (JLabel l : new JLabel[]{lblDesc, lblTeam, lblState, lblCreated}) {
                l.setFont(small);
            }

            infoCol.setOpaque(false);
            infoCol.setLayout(new BoxLayout(infoCol, BoxLayout.Y_AXIS));
            infoCol.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

            infoCol.add(lblDesc);
            infoCol.add(Box.createVerticalStrut(2));
            infoCol.add(lblTeam);
            infoCol.add(Box.createVerticalStrut(2));
            infoCol.add(lblState);
            infoCol.add(Box.createVerticalStrut(2));
            infoCol.add(lblCreated);

            add(lblTitle, BorderLayout.NORTH);
            add(infoCol, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends com.model.Task> list,
                com.model.Task value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            // Título
            lblTitle.setText(value.getTaskName());

            // Último detalle → descripción + estado
            java.util.Optional<com.model.TaskDetail> last = value.getDetails().stream()
                    .max(java.util.Comparator.comparing(
                            com.model.TaskDetail::getInitDate,
                            java.util.Comparator.nullsFirst(java.util.Comparator.naturalOrder())
                    ));

            String description = last.map(com.model.TaskDetail::getDescription)
                    .filter(d -> d != null && !d.isBlank())
                    .orElse("(Sin descripción)");

            String stateEn = last.map(com.model.TaskDetail::getState)
                    .map(Enum::name)
                    .orElse(com.model.enums.TaskState.Pending.name());

            String statePretty = switch (stateEn) {
                case "Pending" ->
                    "Pending";
                case "InProgress" ->
                    "In Progress";
                case "Done" ->
                    "Done";
                case "Canceled" ->
                    "Canceled";
                default ->
                    stateEn;
            };

            // Fecha creada
            String created = (value.getCreateDate() != null)
                    ? value.getCreateDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    : "—";

            // Equipo del assignment del usuario en sesión (requiere fetch de assignments+team en el DAO)
            String teamName = "Sin equipo";
            Integer uid = (com.controller.util.SessionData.getLoggedUser() != null)
                    ? com.controller.util.SessionData.getLoggedUser().getId()
                    : null;

            if (uid != null && value.getAssignments() != null) {
                com.model.Team teamEntity = null;
                for (com.model.TaskUser tu : value.getAssignments()) {
                    if (tu.getUser() != null && tu.getUser().getId() == uid) {
                        teamEntity = tu.getTeam();
                        break;
                    }
                }
                if (teamEntity != null && org.hibernate.Hibernate.isInitialized(teamEntity)) {
                    // intenta getTeamName(); si no, getName()
                    try {
                        String name = null;
                        try {
                            name = (String) teamEntity.getClass().getMethod("getTeamName").invoke(teamEntity);
                        } catch (NoSuchMethodException e1) {
                            try {
                                name = (String) teamEntity.getClass().getMethod("getName").invoke(teamEntity);
                            } catch (NoSuchMethodException ignore) {
                            }
                        }
                        if (name != null && !name.isBlank()) {
                            teamName = name.trim();
                        }
                    } catch (Exception ignore) {
                        /* deja "Sin equipo" */ }
                }
            }

            int wrap = Math.max(100, list.getVisibleRect().width - 28);
            // Pintar líneas (Descripción con wrap)
            String safeDesc = escapeHtml(description).replace("\n", "<br/>");
            lblDesc.setText("<html><div style='width:" + wrap + "px;'><b>Descripcion:</b> "
                    + (safeDesc.isBlank() ? "(Sin descripción)" : safeDesc) + "</div></html>");
            lblTeam.setText("Equipo: " + teamName);
            lblState.setText("Estado: " + statePretty);
            lblCreated.setText("Creada: " + created);

            // Colores por selección
            setBackground(isSelected ? BRAND : Color.WHITE);
            lblTitle.setForeground(isSelected ? Color.WHITE : BRAND);
            Color fg = isSelected ? Color.WHITE : Color.DARK_GRAY;
            for (JLabel l : new JLabel[]{lblDesc, lblTeam, lblState, lblCreated}) {
                l.setForeground(fg);
            }

            return this;
        }

        private static String escapeHtml(String s) {
            if (s == null) {
                return "";
            }
            return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
        }
    }
}
