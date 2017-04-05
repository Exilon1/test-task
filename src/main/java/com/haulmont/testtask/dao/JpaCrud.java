package com.haulmont.testtask.dao;

import com.haulmont.testtask.entities.Group;
import com.haulmont.testtask.entities.Student;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alexey on 31.03.2017.
 */
public class JpaCrud {

    private JpaCrud() {
    }

    private static class SingletonHelper {
        private static final JpaCrud SINGLETON = new JpaCrud();
    }

    public static JpaCrud getInstance() {
        return JpaCrud.SingletonHelper.SINGLETON;
    }

    private final String PERSISTENCE_UNIT = "com.haulmont";

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;

    public void connect() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    public void close() {
        em.close();
        emf.close();
    }



    public List<Group> findAllGroups() {
        TypedQuery<Group> query = em.createNamedQuery("findAllGroups", Group.class);
        return query.getResultList();
    }

    public List<Student> findAllStudents() {
        TypedQuery<Student> query = em.createNamedQuery("findAllStudents", Student.class);
        return query.getResultList();
    }

    public void removeEntity(Object entity) throws IllegalStateException {
        tx.begin();
        em.remove(entity);
     //   tx.commit();
        try {
            tx.commit();
        } catch (Exception e) {
            throw new IllegalStateException("This group contains one ore many students");
        }
    }

    public void createOrUpdate(Object entity) {
        tx.begin();
        if(em.contains(entity))
            em.merge(entity);
        else em.persist(entity);
        tx.commit();
    }

}
