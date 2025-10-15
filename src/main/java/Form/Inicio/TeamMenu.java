package Form.Inicio;

import com.controller.TeamController;
import com.controller.util.ScannerUtil;
import com.model.Task;
import com.model.TaskDetail;
import com.model.Team;
import com.controller.TaskController;
import com.model.enums.TaskState;

import java.util.List;
import java.util.Scanner;

public class TeamMenu {

    private static final TeamController controller = new TeamController();
    private static final TaskController taskController = new TaskController();

    public static void show() {
        Scanner sc = ScannerUtil.getInstance();
        boolean running = true;

        while (running) {
            System.out.println("\n=== GESTIÓN DE EQUIPOS ===");
            System.out.println("1. Ver todos los equipos");
            System.out.println("2. Crear nuevo equipo");
            System.out.println("3. ver tareas por equipos");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    listTeams();
                    break;
                case "2":
                    createTeam();
                    break;
                case "3":
                    listTasksByTeam();
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void listTeams() {
        List<Team> teams = controller.getAllTeams();
        if (teams == null || teams.isEmpty()) {
            System.out.println("\nNo hay equipos registrados.");
            return;
        }

        System.out.println("\n--- LISTA DE EQUIPOS ---");
        for (Team team : teams) {
            System.out.println("• ID: " + team.getId() + " | Nombre: " + team.getName() + " | Descripción: " + team.getDescription());
        }
    }

    private static void createTeam() {
        Scanner sc = ScannerUtil.getInstance();

        System.out.println("\n--- CREAR NUEVO EQUIPO ---");
        System.out.print("Nombre del equipo: ");
        String name = sc.nextLine();

        System.out.print("Descripción: ");
        String description = sc.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("El nombre del equipo no puede estar vacío.");
            return;
        }

        controller.addTeam(name, description);
        System.out.println("¡Equipo creado exitosamente!");
    }
    private static void listTasksByTeam() {
        Scanner sc = ScannerUtil.getInstance();
        List<Team> teams = controller.getAllTeams();

        if (teams == null || teams.isEmpty()) {
            System.out.println("No hay equipos registrados para poder listar sus tareas.");
            return;
        }

        System.out.println("Seleccione un equipo para ver sus tareas:");
        for (int i = 0; i < teams.size(); i++) {
            System.out.println((i + 1) + ". " + teams.get(i).getName());
        }
        System.out.print("Opción: ");

        int teamChoice;
        try {
            teamChoice = Integer.parseInt(sc.nextLine());
            if (teamChoice < 1 || teamChoice > teams.size()) {
                System.out.println("Selección inválida.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida.");
            return;
        }

        Team selectedTeam = teams.get(teamChoice - 1);
        List<Task> tasks = taskController.getTasksByTeam(selectedTeam.getId());

        if (tasks == null || tasks.isEmpty()) {
            System.out.println("\nEl equipo '" + selectedTeam.getName() + "' no tiene tareas asignadas.");
            return;
        }

        System.out.println("\n--- LISTA DE TAREAS DEL EQUIPO: " + selectedTeam.getName() + " ---");
        for (Task t : tasks) {
            TaskDetail lastDetail = null;
            for (TaskDetail detail : t.getDetails()) {
                if (lastDetail == null || (detail.getInitDate() != null && detail.getInitDate().isAfter(lastDetail.getInitDate()))) {
                    lastDetail = detail;
                }
            }

            String estado = (lastDetail != null && lastDetail.getState() != null)
                    ? lastDetail.getState().name()
                    : TaskState.Pending.name();

            String descripcion = (lastDetail != null && lastDetail.getDescription() != null)
                    ? lastDetail.getDescription()
                    : "(Sin descripción)";

            String creationDate = (t.getCreateDate() != null) ? t.getCreateDate().toString() : "No registrada";
            String endDate = (lastDetail != null && lastDetail.getEndDate() != null)
                    ? lastDetail.getEndDate().toString()
                    : "No asignada";

            System.out.println("• " + t.getTaskName()
                    + " | Estado: " + estado
                    + " | Descripción: " + descripcion
                    + " | Fecha de creación: " + creationDate
                    + " | Fecha de vencimiento: " + endDate);
        }
    }
}
