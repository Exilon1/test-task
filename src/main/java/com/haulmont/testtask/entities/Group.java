package com.haulmont.testtask.entities;

import javax.persistence.*;

/**
 * Created by Alexey on 25.03.2017.
 */
@NamedQueries({
        @NamedQuery(name = "findAllGroups", query = "select g from Group g")
})

@Entity
@Table(name="EDU_GROUP")
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

    @Override
    public String toString() {
        return faculty + ": " + number;
    }
}
