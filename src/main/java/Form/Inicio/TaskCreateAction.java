package Form.Inicio;

import com.controller.TaskController;
import com.model.Category;
import com.model.enums.TaskState;

import java.time.LocalDateTime;
import java.util.Scanner;

public class TaskCreateAction {

    private static final TaskController controller = new TaskController();

    public static void addTask() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== CREAR NUEVA TAREA ===");
        System.out.print("Nombre de la tarea: ");
        String nombre = sc.nextLine();

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        System.out.print("Estado (Pending, InProgress, Done, Canceled) o presione Enter para Pending: ");
        String estadoInput = sc.nextLine().trim();

        TaskState estado = TaskState.Pending; // default
        if (!estadoInput.isEmpty()) {
            try {
                estado = TaskState.valueOf(estadoInput);
            } catch (IllegalArgumentException e) {
                System.out.println("⚠ Estado inválido. Se usará Pending.");
            }
        }

        // 👇 por ahora usamos una categoría con ID 1 (por ejemplo, "General")
        Category categoria = new Category();
        categoria.setId(1);

        System.out.print("Fecha de vencimiento (yyyy-MM-dd HH:mm) o presione Enter para no asignar: ");
        String fechaInput = sc.nextLine().trim();

        LocalDateTime endDate = null;
        if (!fechaInput.isEmpty()) {
            try {
                endDate = LocalDateTime.parse(fechaInput.replace(" ", "T"));
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido. No se asignará fecha de vencimiento.");
            }
        }

        controller.agregarTarea(nombre, descripcion, estado, categoria, endDate);

        System.out.println("Tarea agregada exitosamente.");

        Login.mostrarMenuPrincipal();
    }
}

