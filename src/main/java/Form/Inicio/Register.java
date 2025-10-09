package Form.Inicio;

import com.dao.UserDAO;
import com.model.User;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;

public class Register {

    public void register() {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        System.out.println("=== Registro de Usuario ===");

        System.out.print("Ingrese su nombre de usuario: ");
        String username = sc.nextLine().trim();

        System.out.print("Ingrese su contraseña: ");
        String password = sc.nextLine();

        System.out.print("Confirme su contraseña: ");
        String confirmPassword = sc.nextLine();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            System.out.println("Por favor, completa todos los campos.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            System.out.println("Las contraseñas no coinciden.");
            return;
        }

        if (userDAO.userExists(username)) {
            System.out.println("El nombre de usuario ya está en uso. Elige otro.");
            return;
        }

        User newUser = new User(username, password, LocalDate.now(), new HashSet<>());

        boolean success = userDAO.registerUser(newUser);

        if (success) {
            System.out.println("Usuario registrado correctamente.");

            System.out.println("\nRedirigiendo al inicio de sesión...");
            Login login = new Login();
            login.login();
        } else {
            System.out.println("Error al registrar el usuario.");
        }

        sc.close();
    }
}
