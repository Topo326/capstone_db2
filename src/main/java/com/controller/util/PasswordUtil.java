package com.controller.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // Aqui se hashea la contrasenia que esta en texto plano
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    // verifica si la contrasenia es igual devuelve un boolean true si es verdadero, de lo contrario false
    public static boolean checkPassword(String plainPassword, String hashPassword) {
        return BCrypt.checkpw(plainPassword, hashPassword);
    }
}
