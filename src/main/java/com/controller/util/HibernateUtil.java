package com.controller.util;

import com.model.*;
import com.model.enums.TaskStateConverter;
import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Dotenv dotenv = Dotenv.load();

            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
            settings.put(Environment.URL, dotenv.get("DB_URL"));
            settings.put(Environment.USER, dotenv.get("DB_USER"));
            settings.put(Environment.PASS, dotenv.get("DB_PASS"));
            settings.put(Environment.DIALECT, "org.hibernate.dialect.SQLServerDialect");

            settings.put(Environment.SHOW_SQL, "false");
            settings.put(Environment.FORMAT_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "update");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Task.class);
            configuration.addAnnotatedClass(Category.class);
            configuration.addAnnotatedClass(TaskDetail.class);
            configuration.addAnnotatedClass(Team.class);
            configuration.addAnnotatedClass(TaskUser.class);
            configuration.addAnnotatedClass(TaskStateConverter.class);

            return configuration.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void shutdown() {
        getSessionFactory().close();
    }
}
