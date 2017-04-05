package com.haulmont.testtask;

import com.haulmont.testtask.entities.Group;
import com.haulmont.testtask.entities.Student;

import javax.persistence.*;

/**
 * Created by Alexey on 26.03.2017.
 */
public class Main {

    public static void main(String[] args) {}
  /*      EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("com.haulmont");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Group group = new Group("P1", "Physics");
        Student student = new Student("1", "2", "3", "14.11.89", group);

        tx.begin();
        em.persist(student);
        em.persist(group);
        tx.commit();

     //   tx.begin();
        TypedQuery<Student> query = em.createNamedQuery("findAllStudents", Student.class);
        System.out.println("args = [" + query.getSingleResult().getFirstName() + "]");
        query.getSingleResult().setFirstName("11");
     //   tx.commit();

    //    tx.begin();
        System.out.println("args = [" + em.find(Student.class, query.getSingleResult().getId()).getFirstName() + "]");
     //   tx.commit();

        em.close();
        emf.close();

        System.out.println("args = [" + em + " " + emf + "]");
    }
*/
}
