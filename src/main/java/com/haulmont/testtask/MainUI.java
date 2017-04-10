package com.haulmont.testtask;

import com.haulmont.testtask.dao.JpaCrud;
import com.haulmont.testtask.view.ComponentContainer;
import com.haulmont.testtask.utils.DbInicializer;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private JpaCrud jpaCrud = JpaCrud.getInstance();

    @Override
    protected void init(VaadinRequest request) {
        jpaCrud.connect();
    //    DbInicializer.initDb();

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