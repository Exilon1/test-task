package com.haulmont.testtask;

import com.haulmont.testtask.dao.JpaCrud;
import com.haulmont.testtask.entities.Group;
import com.haulmont.testtask.entities.Student;
import com.haulmont.testtask.view.ComponentContainer;
import com.haulmont.testtask.view.DbInicializer;
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
        jpaCrud.connect();
        DbInicializer.initDb();

        ComponentContainer componentContainer = new ComponentContainer();

        TabSheet mainTabSheet = componentContainer.createMainTabSheet();

        addWindow(componentContainer.createAddEditStudentWindow());
        addWindow(componentContainer.createAddEditGroupWindow());

        componentContainer.refreshStudentGrig(jpaCrud.findAllStudents());
        componentContainer.refreshGroupGrig(jpaCrud.findAllGroups());

        setContent(mainTabSheet);
    }

    @Override
    public void detach() {
        jpaCrud.close();
        super.detach();
    }



}