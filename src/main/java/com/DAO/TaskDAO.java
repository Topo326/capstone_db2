package com.DAO;

import com.model.Task;
import com.model.enums.TaskState;
import org.hibernate.Session;
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
}
