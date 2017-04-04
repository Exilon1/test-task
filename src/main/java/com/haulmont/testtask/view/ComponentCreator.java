package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.JpaCrud;
import com.haulmont.testtask.entities.Group;
import com.haulmont.testtask.entities.Student;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by Alexey on 01.04.2017.
 */
public class ComponentCreator {

    private static JpaCrud jpaCrud = JpaCrud.getInstance();

    private Grid<Group> groupGrid;
    private Grid<Student> studentGrid;
    private static Binder<Group> groupBinder = new Binder<>();
    private static Binder<Student> studentBinder = new Binder<>();
    private static Window addEditGroupWindow;
    private static Window addEditStudentWindow;


    public VerticalLayout createGroupTable() {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button add = new Button("Добавить", clickEvent -> {
            groupBinder.setBean(new Group());
            addEditGroupWindow.setVisible(true);
        });
        Button edit = new Button("Редактировать", clickEvent -> {
            Group g = getSelectedGroup();
            if (g == null)
                Notification.show("No selected group!");
            else {
                groupBinder.setBean(g);
                addEditGroupWindow.setVisible(true);
            }
        });
        Button remove = new Button("Удалить", clickEvent -> {
            Group g = getSelectedGroup();
            if (g == null)
                Notification.show("No selected group!");
            else {
                jpaCrud.removeEntity(g);
                refreshGroupGrig(jpaCrud.findAllGroups());
            }
        });
        horizontalLayout.addComponent(add);
        horizontalLayout.addComponent(edit);
        horizontalLayout.addComponent(remove);
        verticalLayout.addComponent(horizontalLayout);

        groupGrid = new Grid<>("Groups");
        groupGrid.setSizeFull();
        groupGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        //   HeaderRow topHeader = grid.prependHeaderRow();
        groupGrid.addColumn(Group::getFaculty)
                .setId("FacultyColumn")
                .setCaption("Faculty");
        groupGrid.addColumn(Group::getNumber)
                .setId("NumberColumn")
                .setCaption("Number");
        verticalLayout.addComponent(groupGrid);
        return verticalLayout;
    }

    public VerticalLayout createStudentTable() {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button add = new Button("Добавить", clickEvent -> {
            studentBinder.setBean(new Student());
            addEditStudentWindow.setVisible(true);
        });
        Button edit = new Button("Редактировать", clickEvent -> {
            Student s = getSelectedStudent();
            if (s == null)
                Notification.show("No selected student!");
            else {
                studentBinder.setBean(s);
                addEditStudentWindow.setVisible(true);
            }
        });
        Button remove = new Button("Удалить", clickEvent -> {
            Student s = getSelectedStudent();
            if (s == null)
                Notification.show("No selected student!");
            else {
                jpaCrud.removeEntity(s);
                refreshStudentGrig(jpaCrud.findAllStudents());
             }
        });
        horizontalLayout.addComponent(add);
        horizontalLayout.addComponent(edit);
        horizontalLayout.addComponent(remove);
        verticalLayout.addComponent(horizontalLayout);

        studentGrid = new Grid<Student>("Students");
        studentGrid.setSizeFull();
        studentGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        //   HeaderRow topHeader = grid.prependHeaderRow();
        studentGrid.addColumn(Student::getFirstName)
                .setId("FirstNameColumn")
                .setCaption("First Name");
        studentGrid.addColumn(Student::getLastName)
                .setId("LastNameColumn")
                .setCaption("Last Name");
        studentGrid.addColumn(Student::getMiddleName)
                .setId("MiddleNameColumn")
                .setCaption("Middle Name");
        studentGrid.addColumn(Student::getDob)
                .setId("DobColumn")
                .setCaption("Date of Birth");
        studentGrid.addColumn(Student::getGroup)
                .setId("GroupColumn")
                .setCaption("Group");
        verticalLayout.addComponent(studentGrid);
        return verticalLayout;
    }

    public Window createAddEditGroupLayout() {
        addEditGroupWindow = new Window("Edit group");

        VerticalLayout formLayout = new VerticalLayout ();
        TextField faculty = new TextField("Faculty:");
        TextField number = new TextField("Number:");

        Label facultyValidation = new Label();
        Label numberValidation = new Label();

        formLayout.addComponent(faculty);
        formLayout.addComponent(facultyValidation);
        formLayout.addComponent(number);
        formLayout.addComponent(numberValidation);

        groupBinder.forField(faculty)
                .asRequired("Faculty may not be empty")
                .withStatusLabel(facultyValidation)
                .bind(Group::getFaculty, Group::setFaculty);
        groupBinder.forField(number)
                .asRequired("Number may not be empty")
                .withStatusLabel(numberValidation)
                .bind(Group::getNumber, Group::setNumber);

        Button okButton = new Button("Ok", clickEvent -> {
            jpaCrud.createOrUpdate(groupBinder.getBean());
            refreshGroupGrig(jpaCrud.findAllGroups());
            addEditGroupWindow.setVisible(false);
        });
        Button cancelButton = new Button("Отменить", clickEvent -> addEditGroupWindow.setVisible(false));

        groupBinder.addStatusChangeListener(
                event -> okButton.setEnabled(groupBinder.isValid()));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(okButton);
        horizontalLayout.addComponent(cancelButton);

        formLayout.addComponent(horizontalLayout);

     //   formLayout.setSizeUndefined();
    //    formLayout.setMargin(true);
        addEditGroupWindow.setContent(formLayout);
        addEditGroupWindow.setModal(true);
        addEditGroupWindow.setVisible(false);
        return addEditGroupWindow;
    }

    public Window createAddEditStudentLayout() {
        addEditStudentWindow = new Window("Edit student");
        VerticalLayout verticalLayout = new VerticalLayout();

        TextField firstName = new TextField("First Name:");
        TextField lastName = new TextField("Last Name:");
        TextField middleName = new TextField("Middle Name:");
        DateField dob = new DateField("Date of Birth:");
        NativeSelect<Group> group = new NativeSelect("Group:", jpaCrud.findAllGroups());

        studentBinder.forField(firstName)
                .asRequired("First Name may not be empty")
                .bind(Student::getFirstName, Student::setFirstName);
        studentBinder.forField(lastName)
                .asRequired("Last Name may not be empty")
                .bind(Student::getLastName, Student::setLastName);
        studentBinder.forField(middleName)
                .asRequired("Middle Name may not be empty")
                .bind(Student::getMiddleName, Student::setMiddleName);
        studentBinder.forField(dob)
                .asRequired("Date of Birth may not be empty")
                .bind(Student::getDob, Student::setDob);
        studentBinder.forField(group)
                .asRequired("Group may not be empty")
                .bind(Student::getGroup, Student::setGroup);

        Button ok = new Button("Ok", clickEvent -> {
            jpaCrud.createOrUpdate(studentBinder.getBean());
            refreshStudentGrig(jpaCrud.findAllStudents());
            addEditStudentWindow.setVisible(false);
        });
        Button cancel = new Button("Отменить", clickEvent -> addEditStudentWindow.setVisible(false));

        verticalLayout.addComponent(firstName);
        verticalLayout.addComponent(lastName);
        verticalLayout.addComponent(middleName);
        verticalLayout.addComponent(dob);
        verticalLayout.addComponent(group);
        verticalLayout.addComponent(ok);

        addEditStudentWindow.setContent(verticalLayout);
        addEditStudentWindow.setModal(true);
        addEditStudentWindow.setVisible(false);
        return addEditStudentWindow;
    }

    public void refreshGroupGrig(List<Group> groupList) {
        groupGrid.setDataProvider(DataProvider.ofCollection(groupList));
    }

    public void refreshStudentGrig(List<Student> studentList) {
        studentGrid.setDataProvider(DataProvider.ofCollection(studentList));
    }

    public Group getSelectedGroup() {
        for(Group g: groupGrid.getSelectedItems())
            return g;
        return null;
    }

    public Student getSelectedStudent() {
        for(Student s: studentGrid.getSelectedItems())
            return s;
        return null;
    }
}
