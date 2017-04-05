package com.haulmont.testtask.entities;

import javax.persistence.*;
import java.time.LocalDate;


/**
 * Created by Alexey on 25.03.2017.
 */
@NamedQuery(name = "findAllStudents", query = "select s from Student s")
@Entity
@Table(name="STUDENT")
public class Student {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate dob;
    @ManyToOne()
    @JoinColumn(name = "group_fk")
    private Group group;


    public Student() {
    }

    public Student(String firstName, String lastName, String middleName, LocalDate dob, Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dob = dob;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
