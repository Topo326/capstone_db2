package Form.Inicio;

import com.controller.TaskController;
import com.controller.util.ScannerUtil;
import com.model.Task;
import java.util.List;
import java.util.Scanner;

public class TaskDeleteAction {

    private static final TaskController controller = new TaskController();

    public static void deleteTask() {
        Scanner sc = ScannerUtil.getInstance();
        List<Task> tasks = controller.getMyTasks(null);

        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No tienes tareas para eliminar.");
            return;
        }

        System.out.println("\n--- SELECCIONA LA TAREA A ELIMINAR ---");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getTaskName());
        }
        System.out.print("Elige una opción o presiona 0 para cancelar: ");

        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return;
        }

        if (choice == 0 || choice > tasks.size()) {
            System.out.println("Operación cancelada.");
            return;
        }

        Task selectedTask = tasks.get(choice - 1);

        System.out.print("¿Estás seguro de que quieres eliminar la tarea '" + selectedTask.getTaskName() + "'? (s/n): ");
        String confirmation = sc.nextLine();

        if (confirmation.equalsIgnoreCase("s")) {
            controller.deleteTask(selectedTask.getId());
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }
}
