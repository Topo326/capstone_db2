package Form.Inicio;

import com.controller.CategoryController;
import com.controller.util.ScannerUtil;
import com.model.Category;

import java.util.List;
import java.util.Scanner;

public class CategoryMenu {

    private static final CategoryController controller = new CategoryController();

    public static void show() {
        Scanner sc = ScannerUtil.getInstance();
        boolean running = true;

        while (running) {
            System.out.println("\n=== GESTIÓN DE CATEGORÍAS ===");
            System.out.println("1. Ver todas las categorías");
            System.out.println("2. Crear nueva categoría");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    listCategories();
                    break;
                case "2":
                    createCategory();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void listCategories() {
        List<Category> categories = controller.getAllCategories();
        if (categories == null || categories.isEmpty()) {
            System.out.println("\nNo hay categorías registradas.");
            return;
        }

        System.out.println("\n--- LISTA DE CATEGORÍAS ---");
        for (Category category : categories) {
            System.out.println("• ID: " + category.getId() + " | Nombre: " + category.getCategoryName() + " | Descripción: " + category.getDescription());
        }
    }

    private static void createCategory() {
        Scanner sc = ScannerUtil.getInstance();

        System.out.println("\n--- CREAR NUEVA CATEGORÍA ---");
        System.out.print("Nombre de la categoría: ");
        String name = sc.nextLine();

        System.out.print("Descripción: ");
        String description = sc.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("El nombre de la categoría no puede estar vacío.");
            return;
        }

        controller.addCategory(name, description);
        System.out.println("¡Categoría creada exitosamente!");
    }
}
