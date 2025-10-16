package com.DAO;

import com.model.Category;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import static com.controller.util.HibernateUtil.getSessionFactory;

public class CategoryDAO {
    public List<Category> getAllCategories() {
        try (Session session = getSessionFactory().openSession()) {
            return (List<Category>) session.createQuery("from Category").list();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public Category findCategoryById(int id) {
        try (Session session = getSessionFactory().openSession()) {
            return session.find(Category.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCategory(Category category) {
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(category);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category) {
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(category);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}