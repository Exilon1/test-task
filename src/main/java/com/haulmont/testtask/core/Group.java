package com.haulmont.testtask.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Alexey on 25.03.2017.
 */
@Entity
public class Group {

    @Id @GeneratedValue
    private Long id;
    private String number;
    private String faculty;

    public Group() {
    }

    public Group(String number, String faculty) {
        this.number = number;
        this.faculty = faculty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
