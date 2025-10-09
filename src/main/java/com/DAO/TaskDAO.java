package com.DAO;

import com.model.Task;
import com.controller.util.HibernateUtil;
import com.model.enums.TaskState;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class TaskDAO {

    // A) TODAS LAS TAREAS DEL USUARIO (anclado en TaskUser)
    public java.util.List<com.model.Task> listByUser(int userId) {
        try (org.hibernate.Session s = com.controller.util.HibernateUtil.getSessionFactory().openSession()) {
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
    public java.util.List<com.model.Task> listByUserAndCurrentState(int userId, com.model.enums.TaskState state) {
        try (org.hibernate.Session s = com.controller.util.HibernateUtil.getSessionFactory().openSession()) {
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
}
