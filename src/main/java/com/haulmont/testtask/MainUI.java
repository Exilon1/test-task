package com.haulmont.testtask;

import com.haulmont.testtask.dao.JpaCrud;
import com.haulmont.testtask.entities.Group;
import com.haulmont.testtask.entities.Student;
import com.haulmont.testtask.view.ComponentCreator;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.time.Month;


@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private JpaCrud jpaCrud = JpaCrud.getInstance();

    @Override
    protected void init(VaadinRequest request) {

        LocalDate specificDate = LocalDate.of(2017, Month.NOVEMBER, 30);

        jpaCrud.connect();
        Group group1 = new Group("P1", "Physics");
        Group group2 = new Group("M2", "Math");
        Student student = new Student("1", "2", "3", specificDate, group1);
        jpaCrud.createOrUpdate(group1);
        jpaCrud.createOrUpdate(group2);
        jpaCrud.createOrUpdate(student);
        student.setFirstName("12");
        jpaCrud.createOrUpdate(student);
    //    ListDataProvider<Group> dataProvider = DataProvider.ofCollection(jpaCrud.findAllGroups());

        TabSheet mainTabSheet = new TabSheet();
        mainTabSheet.setSizeFull();

        ComponentCreator componentCreator = new ComponentCreator();

        VerticalLayout layoutOne = componentCreator.createStudentTableLayout();
        VerticalLayout layoutTwo = componentCreator.createGroupTableLayout();

        addWindow(componentCreator.createAddEditStudentWindow());
        addWindow(componentCreator.createAddEditGroupWindow());

        componentCreator.refreshStudentGrig(jpaCrud.findAllStudents());
        componentCreator.refreshGroupGrig(jpaCrud.findAllGroups());

        mainTabSheet.addTab(layoutOne, "Студенты");
        mainTabSheet.addTab(layoutTwo, "Группы");

        setContent(mainTabSheet);
    }
/*
    @Override
    public void detach() {
        jpaCrud.close();
        super.detach();
    }
*/


}