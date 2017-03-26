package com.haulmont.testtask;

import com.haulmont.testtask.core.Group;
import com.haulmont.testtask.core.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by Alexey on 26.03.2017.
 */
public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("com.haulmont");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Group group = new Group("P1", "Physics");
        Student student = new Student("1", "2", "3", "14.11.89", group);

        tx.begin();
        em.persist(student);
        em.persist(group);
        tx.commit();
    }

}
