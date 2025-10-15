package com.DAO;

import com.model.Team;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import java.util.List;
import static com.controller.util.HibernateUtil.getSessionFactory;

public class TeamDAO {
    public List<Team> gatAllTeams(){
        try(Session session = getSessionFactory().openSession()){
            return session.createQuery("from Team", Team.class).list();
        }catch(HibernateException ex){
            throw new HibernateException(ex);
        }
    }
    public void addTeam(Team team){
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(team);
            transaction.commit();
        }catch(Exception ex){
            if(transaction != null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }

    }
}
