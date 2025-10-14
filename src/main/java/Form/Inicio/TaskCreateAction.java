package Form.Inicio;

import com.controller.CategoryController;
import com.controller.TaskController;
import com.controller.util.ScannerUtil;
import com.model.Category;
import com.model.enums.TaskState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class TaskCreateAction {

    private static final TaskController taskController = new TaskController();
    // 1. La Vista necesita acceso al CategoryController
    private static final CategoryController categoryController = new CategoryController();

    public static void addTask() {
        Scanner sc = ScannerUtil.getInstance();

        String[] state = {"Pending", "In Progress", "Done", "Canceled"};

        System.out.println("\n=== CREAR NUEVA TAREA ===");
        System.out.print("Nombre de la tarea: ");
        String nombre = sc.nextLine();

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        List<Category> categories = categoryController.getAllCategories();
        if (categories == null || categories.isEmpty()) {
            System.out.println("ERROR: No hay categorías disponibles en la base de datos.");
            return;
        }

        System.out.println("Seleccione una categoría:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.print("Opción: ");

        int categoryChoice;
        try {
            categoryChoice = Integer.parseInt(sc.nextLine());
            if (categoryChoice < 1 || categoryChoice > categories.size()) {
                System.out.println("Selección inválida. Operación cancelada.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida. Operación cancelada.");
            return;
        }

        Category categoriaSeleccionada = categories.get(categoryChoice - 1);

        System.out.println("Elige un estado: ");
        int sum = 1;
        for(String s : state){
            System.out.println(sum + ". " + s);
            sum+=1;
        }
        System.out.print("Opción: ");
        int stateChoice = sc.nextInt();
        sc.nextLine();
        String estadoInput = state[stateChoice - 1];
        TaskState estado = null;
            try {
                estado = TaskState.fromDbValue(estadoInput);
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

        taskController.agregarTarea(nombre, descripcion, estado, categoriaSeleccionada, endDate);

        System.out.println("¡Tarea agregada exitosamente!");
    }
}


