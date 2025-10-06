package com.controller.util;

import org.hibernate.Session;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class ConnectionTester {

    public static boolean checkConnection() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.doWork(connection -> {
                try {
                    DatabaseMetaData metaData = connection.getMetaData();
                    System.out.println("--- Metadatos de la Base de Datos ---");
                    System.out.println("Nombre del Producto: " + metaData.getDatabaseProductName());
                    System.out.println("Versión del Producto: " + metaData.getDatabaseProductVersion());
                    System.out.println("Nombre del Driver: " + metaData.getDriverName());
                    System.out.println("Versión del Driver: " + metaData.getDriverVersion());
                    System.out.println("URL de Conexión: " + metaData.getURL());
                    System.out.println("Usuario: " + metaData.getUserName());
                    System.out.println("-------------------------------------");
                } catch (SQLException e) {
                    throw new RuntimeException("No se pudieron obtener los metadatos de la DB", e);
                }
            });

            System.out.println("¡La conexión con la base de datos funciona correctamente!");
            return true;

        } catch (Exception e) {
            System.err.println("-------------------------------------------------");
            System.err.println("ERROR: Fallo en la prueba de conexión.");
            System.err.println("Verifica la configuración en hibernate.cfg.xml y que la base de datos esté activa.");
            System.err.println("Detalles del error: " + e.getMessage());
            System.err.println("-------------------------------------------------");
            e.printStackTrace();
            return false;
        }
    }
    public static void testConnection() {
        boolean check = checkConnection();
        if (check) {
            System.out.println("\nResultado: Éxito. La aplicación puede continuar.");
        } else {
            System.out.println("\nResultado: Fallo. La aplicación no puede iniciar.");
        }

        HibernateUtil.shutdown();
        System.out.println("== PRUEBA FINALIZADA ==");
    }
}
