package com;

import Form.Inicio.Login;
import Form.Inicio.Register;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
                    Login login = new Login();
                    login.login();
                    break;

                case "2":
                    Register register = new Register();
                    register.register();
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
        scanner.close();
    }
}
