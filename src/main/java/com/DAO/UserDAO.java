package com.dao;

import com.model.User;
import com.controller.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDAO {

    public User loginUser(String username, String password) {
        Transaction transaction = null;
        User user = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<User> query = session.createQuery(
                    "FROM User WHERE username = :username AND password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            user = query.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return user;
    }

    public boolean registerUser(User user) {
        Transaction transaction = null;
        boolean isSaved = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(user); // Guarda el nuevo usuario
            transaction.commit();
            isSaved = true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return isSaved;
    }

    public boolean userExists(String username) {
        Transaction transaction = null;
        Long count = 0L;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<Long> query = session.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class);
            query.setParameter("username", username);

            count = query.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return count > 0;
    }
}
