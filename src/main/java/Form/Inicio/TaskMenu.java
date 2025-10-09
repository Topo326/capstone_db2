package Form.Inicio;

import com.controller.TaskController;
import com.model.Task;
import com.model.TaskDetail;
import com.model.enums.TaskState;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TaskMenu {

    private static final TaskController controller = new TaskController();

    public static void show() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== MENÚ DE TAREAS ===");
            System.out.println("1. Ver todas mis tareas");
            System.out.println("2. Ver tareas PENDIENTES");
            System.out.println("3. Ver tareas EN PROCESO");
            System.out.println("4. Ver tareas COMPLETADAS");
            System.out.println("5. Ver tareas CANCELADAS");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opción: ");
            String opt = sc.nextLine();

            switch (opt) {
                case "1" -> listar(null);
                case "2" -> listar(TaskState.Pending);
                case "3" -> listar(TaskState.InProgress);
                case "4" -> listar(TaskState.Done);
                case "5" -> listar(TaskState.Canceled);
                case "6" -> {
                    return;
                }
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void listar(TaskState filter) {
        List<Task> tasks = controller.getMyTasks(filter);

        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No tienes tareas " + (filter == null ? "" : "con estado " + filter.name().toLowerCase()));
            return;
        }

        System.out.println("\n--- LISTA DE TAREAS ---");
        for (Task t : tasks) {
            Optional<TaskDetail> lastDetail = t.getDetails().stream()
                    .max(Comparator.comparing(TaskDetail::getInitDate,
                            Comparator.nullsFirst(Comparator.naturalOrder())));

            String estado = lastDetail.map(TaskDetail::getState)
                    .map(Enum::name)
                    .orElse("Sin estado");

            String descripcion = lastDetail.map(TaskDetail::getDescription)
                    .orElse("(Sin descripción)");

            System.out.println("• " + t.getTaskName()
                    + " | Estado: " + estado
                    + " | Descripción: " + descripcion);
        }
    }
}
