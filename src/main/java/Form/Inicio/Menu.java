package Form.Inicio;

import com.controller.util.ScannerUtil;
import java.util.Scanner;


public class Menu {
    private final static Scanner scanner = ScannerUtil.getInstance();
    public static void mainMenu(){
        boolean running = true;
        while (running) {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    Login.login();
                    break;
                case "2":
                    Register.register();
                    break;
                case "3":
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    running = false;
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }
        }
    }
    public static void loginMenu(){
        boolean exit = true;

        while (exit) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Ver tareas");
            System.out.println("2. Agregar tarea");
            System.out.println("3. Modificar tarea");
            System.out.println("4. Eliminar tarea");
            System.out.println("5. Gestionar Categorías");
            System.out.println("6. Gestionar Equipos");
            System.out.println("7. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    TaskMenu.show();
                    break;
                case "2":
                    TaskCreateAction.addTask();
                    break;
                case "3":
                    TaskModifyAction.modifyTask();
                    break;
                case "4":
                    TaskDeleteAction.deleteTask();
                    break;
                case "5":
                    CategoryMenu.show();
                    break;
                case "6":
                    TeamMenu.show();
                    break;
                case "7":
                    System.out.println("Sesión cerrada.");
                    exit = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
