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
            System.out.println("3. Modificar categoría");
            System.out.println("4. Volver al menú principal");
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
                    editCategory();
                    break;
                case "4":
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

    private static void editCategory() {
        Scanner sc = ScannerUtil.getInstance();
        List<Category> categories = controller.getAllCategories();

        if (categories == null || categories.isEmpty()) {
            System.out.println("No hay categorías para editar.");
            return;
        }

        System.out.println("\n--- SELECCIONA LA CATEGORÍA A MODIFICAR ---");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.print("Elige una opción o presiona 0 para cancelar: ");

        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return;
        }

        if (choice == 0 || choice > categories.size()) {
            System.out.println("Operación cancelada.");
            return;
        }

        Category selectedCategory = categories.get(choice - 1);

        System.out.println("--- MODIFICANDO CATEGORÍA: " + selectedCategory.getCategoryName() + " ---");
        System.out.print("Nuevo nombre (deja en blanco para no cambiar): ");
        String newName = sc.nextLine();
        System.out.print("Nueva descripción (deja en blanco para no cambiar): ");
        String newDescription = sc.nextLine();

        controller.editCategory(selectedCategory.getId(), newName.isEmpty() ? null : newName,
                newDescription.isEmpty() ? null : newDescription
        );

        System.out.println("¡Categoría modificada correctamente!");
    }
}
