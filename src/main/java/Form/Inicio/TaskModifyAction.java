package Form.Inicio;

import com.controller.TaskController;
import com.controller.util.ScannerUtil;
import com.model.Task;
import com.model.TaskDetail;
import com.model.enums.TaskState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TaskModifyAction {

    private static final TaskController controller = new TaskController();

    public static void modifyTask() {
        Scanner sc = ScannerUtil.getInstance();
        List<Task> tasks = controller.getMyTasks(null); // Obtenemos todas las tareas

        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No tienes tareas para modificar.");
            return;
        }

        System.out.println("\n--- SELECCIONA LA TAREA A MODIFICAR ---");
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

        System.out.println("--- MODIFICANDO TAREA: " + selectedTask.getTaskName() + " ---");
        System.out.print("Nuevo nombre (deja en blanco para no cambiar): ");
        String newName = sc.nextLine();
        if (!newName.isEmpty()) {
            selectedTask.setTaskName(newName);
        }

        // Para modificar el detalle, creamos uno nuevo que será el más reciente
        TaskDetail latestDetail = selectedTask.getDetails().stream()
                .max(Comparator.comparing(TaskDetail::getInitDate))
                .orElse(new TaskDetail());

        System.out.print("Nueva descripción (deja en blanco para no cambiar): ");
        String newDescription = sc.nextLine();
        if (!newDescription.isEmpty()) {
            latestDetail.setDescription(newDescription);
        }

        System.out.print("Nuevo estado (Pending, InProgress, Done, Canceled) (deja en blanco para no cambiar): ");

        String newStateStr = sc.nextLine();
        if (!newStateStr.isEmpty()) {
            try {
                latestDetail.setState(TaskState.valueOf(newStateStr));
            } catch (IllegalArgumentException e) {
                System.out.println("Estado inválido. No se ha modificado.");
            }
        }
        System.out.println("Nueva hora [yyyy-mm-dd HH:MM] (deja en blanco para no cambiar): )");
        String newDate = sc.nextLine();
        LocalDateTime endDate = null;
        if (!newDate.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                endDate = LocalDateTime.parse(newDate, formatter);
                latestDetail.setInitDate(endDate);
            }catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. No se asignará fecha de vencimiento.");
            }
        }

        controller.updateTask(selectedTask);
        System.out.println("¡Tarea actualizada correctamente!");
    }
}
