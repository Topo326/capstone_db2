package com.DAO;

import com.model.*;
import com.model.enums.TaskState;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;

import static com.controller.util.HibernateUtil.getSessionFactory;

public class TaskDAO {

    // A) TODAS LAS TAREAS DEL USUARIO
    public java.util.List<Task> listByUser(int userId) {
        try (Session s = getSessionFactory().openSession()) {
            return s.createQuery(
                    "select distinct t "
                            + "from TaskUser a "
                            + "join a.task t "
                            + "join fetch t.category " // Category inicializada
                            + "left join fetch t.assignments ta " // Trae todos los assignments de la tarea
                            + "left join fetch ta.team " // Team inicializado
                            + "where a.user.id = :uid "
                            + "order by t.createDate desc",
                    com.model.Task.class
            ).setParameter("uid", userId).list();
        }
    }

    // B) MIS TAREAS FILTRADAS POR ESTADO ACTUAL
    public java.util.List<Task> listByUserAndCurrentState(int userId, TaskState state) {
        try (Session s = getSessionFactory().openSession()) {
            return s.createQuery(
                            "select distinct t "
                                    + "from TaskUser a "
                                    + "join a.task t "
                                    + "join fetch t.category "
                                    + "left join fetch t.assignments ta "
                                    + "left join fetch ta.team "
                                    + "join t.details td "
                                    + "where a.user.id = :uid "
                                    + "  and td.initDate = (select max(td2.initDate) from TaskDetail td2 where td2.task = t) "
                                    + "  and td.state = :st "
                                    + "order by t.createDate desc",
                            com.model.Task.class
                    )
                    .setParameter("uid", userId)
                    .setParameter("st", state)
                    .list();
        }
    }

    public void addTask(String name, String description, TaskState state, Category category, int userId, LocalDateTime endDate) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Task task = new Task();
            task.setTaskName(name);
            task.setCategory(category);
            task.setCreateDate(LocalDateTime.now());
            session.persist(task);

            TaskDetail detail = new TaskDetail();
            detail.setTask(task);
            detail.setState(state);
            detail.setDescription(description);
            detail.setInitDate(LocalDateTime.now());
            detail.setEndDate(endDate);
            session.persist(detail);

            TaskUser tu = new TaskUser();
            tu.setTask(task);
            tu.setUser(session.getReference(User.class, userId));
            session.persist(tu);

            tx.commit();
        }
    }

    public void updateTask(Task task) {
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(task);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Task task = session.find(Task.class, taskId);
            if (task != null) {
                session.remove(task);
                System.out.println("Tarea eliminada correctamente.");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
