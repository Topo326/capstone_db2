package Form.Inicio;

import com.controller.LoginController;
import com.controller.util.ScannerUtil;
import com.model.User;

import java.util.Scanner;

public class Login {

    public static void login() {
        Scanner scanner = ScannerUtil.getInstance();

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
            Menu.loginMenu();
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }


}
