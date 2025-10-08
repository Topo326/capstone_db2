package com;

import com.controller.util.HibernateUtil;
import com.controller.util.ConnectionTester;
import Form.Inicio.Home; // 👈 Asegúrate de importar la clase correcta

public class Main {

    public static void main(String[] args) {
        //ConnectionTester.testConnection();

        java.awt.EventQueue.invokeLater(() -> new Home().setVisible(true));
    }
}
