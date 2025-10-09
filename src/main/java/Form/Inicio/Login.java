package Form.Inicio;

import com.controller.LoginController;
import com.model.User;

import java.util.Scanner;

public class Login {

    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==============================");
        System.out.println("         LOGIN SYSTEM         ");
        System.out.println("==============================");

        System.out.print("Ingrese su usuario: ");
        String username = scanner.nextLine().trim();

        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Por favor llene todos los campos.");
            return;
        }

        // Lógica de autenticación
        LoginController loginController = new LoginController();
        User user = loginController.authenticate(username, password);

        if (user != null) {
            com.controller.util.SessionData.setLoggedUser(user);
            System.out.println("\nBienvenido, " + user.getUsername() + "!");
            mostrarMenuPrincipal();
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    public static void mostrarMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = true;

        while (exit) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Ver tareas");
            System.out.println("2. Agregar tareas");
            System.out.println("3. Modificar tareas");
            System.out.println("4. Eliminar tareas");
            System.out.println("5. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    TaskMenu.show();
                    break;
                case "2":
                case "3":
                case "4":
                    System.out.println("En proceso..");
                    break;
                case "5":
                    System.out.println("Sesión cerrada.");
                    exit = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
