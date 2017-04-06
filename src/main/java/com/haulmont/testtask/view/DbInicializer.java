package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.JpaCrud;
import com.haulmont.testtask.entities.Group;
import com.haulmont.testtask.entities.Student;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by Nikotin on 06.04.2017.
 */
public class DbInicializer {

    private static JpaCrud jpaCrud = JpaCrud.getInstance();

    public static void initDb() {
        Group group1 = new Group("P1", "Physics");
        Group group2 = new Group("M2", "Math");
        Group group3 = new Group("S3", "Science");
        Group group4 = new Group("G4", "Genetic");
        Group group5 = new Group("C5", "Chemical");

        Student student1 = new Student("A", "B", "C", LocalDate.of(2009, Month.NOVEMBER, 30), group1);
        Student student2 = new Student("D", "E", "F", LocalDate.of(2009, Month.NOVEMBER, 25), group1);
        Student student3 = new Student("J", "H", "I", LocalDate.of(2009, Month.NOVEMBER, 20), group2);
        Student student4 = new Student("G", "K", "L", LocalDate.of(2009, Month.NOVEMBER, 15), group2);

        jpaCrud.createOrUpdate(group1);
        jpaCrud.createOrUpdate(group2);
        jpaCrud.createOrUpdate(group3);
        jpaCrud.createOrUpdate(group4);
        jpaCrud.createOrUpdate(group5);

        jpaCrud.createOrUpdate(student1);
        jpaCrud.createOrUpdate(student2);
        jpaCrud.createOrUpdate(student3);
        jpaCrud.createOrUpdate(student4);
    }
}
