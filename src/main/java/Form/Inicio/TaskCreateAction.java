package Form.Inicio;

import com.controller.CategoryController;
import com.controller.TaskController;
import com.controller.TeamController;
import com.controller.util.ScannerUtil;
import com.model.Category;
import com.model.Team;
import com.model.enums.TaskState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class TaskCreateAction {

    private static final TaskController taskController = new TaskController();
    private static final CategoryController categoryController = new CategoryController();
    private static final TeamController teamController = new TeamController();

    public static void addTask() {
        Scanner sc = ScannerUtil.getInstance();

        String[] state = {"Pending", "InProgress", "Done", "Canceled"};

        System.out.println("\n=== CREAR NUEVA TAREA ===");
        System.out.print("Nombre de la tarea: ");
        String nombre = sc.nextLine();

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        List<Category> categories = categoryController.getAllCategories();
        if (categories == null || categories.isEmpty()) {
            System.out.println("No hay categorías registradas. Se creará la categoría 'General' por defecto...");
            Category general = categoryController.findOrCreateDefaultCategory();
            categories = List.of(general);
        }


        System.out.println("Seleccione una categoría: ");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.print("Opción: ");

        String categoryChoiceStr = sc.nextLine().trim();
        Category categoriaSeleccionada = null;

        if (categoryChoiceStr.isEmpty()) {
            // El usuario presionó Enter sin elegir
            categoriaSeleccionada = categoryController.findOrCreateDefaultCategory();
        } else {
            try {
                int categoryChoice = Integer.parseInt(categoryChoiceStr);
                if (categoryChoice >= 1 && categoryChoice <= categories.size()) {
                    categoriaSeleccionada = categories.get(categoryChoice - 1);
                } else {
                    System.out.println("Selección inválida. Se usará la categoría 'General'.");
                    categoriaSeleccionada = categoryController.findOrCreateDefaultCategory();
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Se usará la categoría 'General'.");
                categoriaSeleccionada = categoryController.findOrCreateDefaultCategory();
            }
        }

        List<Team> teams = teamController.getAllTeams();
        Integer teamId = null;
        if (teams != null && !teams.isEmpty()) {
            System.out.println("Seleccione un equipo (opcional):");
            for (int i = 0; i < teams.size(); i++) {
                System.out.println((i + 1) + ". " + teams.get(i).getName());
            }
            System.out.print("Opción (presione Enter para no asignar a un equipo): ");
            String teamChoiceStr = sc.nextLine();
            if (!teamChoiceStr.isEmpty()) {
                try {
                    int teamChoice = Integer.parseInt(teamChoiceStr);
                    if (teamChoice >= 1 && teamChoice <= teams.size()) {
                        teamId = teams.get(teamChoice - 1).getId();
                    }
                } catch (NumberFormatException e) {}
            }
        }

        System.out.println("Elige un estado: ");
        int sum = 1;
        for(String s : state){
            System.out.println(sum + ". " + s);
            sum+=1;
        }
        System.out.print("Opción: ");
        int stateChoice = Integer.parseInt(sc.nextLine());
        String estadoInput = state[stateChoice - 1];
        TaskState estado = null;
            try {
                estado = TaskState.valueOf(estadoInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Estado inválido. Se usará Pending.");
            }

        System.out.print("Fecha de vencimiento (yyyy-MM-dd HH:mm) o presione Enter para no asignar: ");
        String fechaInput = sc.nextLine().trim();
        LocalDateTime endDate = null;
        if (!fechaInput.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                endDate = LocalDateTime.parse(fechaInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. No se asignará fecha de vencimiento.");
            }
        }

        taskController.agregarTarea(nombre, descripcion, estado, categoriaSeleccionada, endDate, teamId);

        System.out.println("¡Tarea agregada exitosamente!");
    }
}


