package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.JpaCrud;
import com.haulmont.testtask.entities.Group;
import com.haulmont.testtask.entities.Student;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

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
    private HeaderRow studentGridTopHeader;

    public VerticalLayout createGroupTableLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button add = new Button("Добавить", clickEvent -> {
            if(!addEditGroupWindow.isAttached())
                UI.getCurrent().addWindow(addEditGroupWindow);
            groupBinder.setBean(new Group());
            addEditGroupWindow.setVisible(true);
        });
        Button edit = new Button("Редактировать", clickEvent -> {
            Group g = getSelectedGroup();
            if (g == null)
                Notification.show("No selected group!");
            else {
                if(!addEditGroupWindow.isAttached())
                    UI.getCurrent().addWindow(addEditGroupWindow);
                groupBinder.setBean(g);
                addEditGroupWindow.setVisible(true);
            }
        });
        Button remove = new Button("Удалить", clickEvent -> {
            Group g = getSelectedGroup();
            if (g == null)
                Notification.show("No selected group!");
            else {
                try {
                    jpaCrud.removeEntity(g);
                } catch (IllegalStateException e) {
                    Notification.show(e.getMessage());
                }
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

    public VerticalLayout createStudentTableLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button add = new Button("Добавить", clickEvent -> {
            if(!addEditStudentWindow.isAttached())
                UI.getCurrent().addWindow(addEditStudentWindow);
            studentBinder.setBean(new Student());
            addEditStudentWindow.setVisible(true);
        });
        Button edit = new Button("Редактировать", clickEvent -> {
            Student s = getSelectedStudent();
            if (s == null)
                Notification.show("No selected student!");
            else {
                if(!addEditStudentWindow.isAttached())
                    UI.getCurrent().addWindow(addEditStudentWindow);
                studentBinder.setBean(s);
                addEditStudentWindow.setVisible(true);
            }
        });
        Button remove = new Button("Удалить", clickEvent -> {
            Student s = getSelectedStudent();
            if (s == null)
                Notification.show("No selected student!");
            else {
                try {
                    jpaCrud.removeEntity(s);
                } catch (IllegalStateException e) {
                    Notification.show(e.getMessage());
                }
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
        studentGridTopHeader = studentGrid.appendHeaderRow();

        verticalLayout.addComponent(studentGrid);
        return verticalLayout;
    }

    public Window createAddEditGroupWindow() {
        addEditGroupWindow = new Window("Edit group");

        FormLayout formLayout = new FormLayout();
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
            if(groupBinder.validate().isOk()) {
                jpaCrud.createOrUpdate(groupBinder.getBean());
                refreshGroupGrig(jpaCrud.findAllGroups());
                addEditGroupWindow.setVisible(false);
            }
        });
        Button cancelButton = new Button("Отменить", clickEvent -> addEditGroupWindow.setVisible(false));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(okButton);
        horizontalLayout.addComponent(cancelButton);

        formLayout.addComponent(horizontalLayout);

        addEditGroupWindow.setContent(formLayout);
        addEditGroupWindow.setModal(true);
        addEditGroupWindow.setVisible(false);
        return addEditGroupWindow;
    }

    public Window createAddEditStudentWindow() {
        addEditStudentWindow = new Window("Edit student");

        FormLayout formLayout = new FormLayout();
        TextField firstName = new TextField("First Name:");
        TextField lastName = new TextField("Last Name:");
        TextField middleName = new TextField("Middle Name:");
        DateField dob = new DateField("Date of Birth:");
        NativeSelect<Group> group = new NativeSelect("Group:", jpaCrud.findAllGroups());

        Label firstNameValidation = new Label();
        Label lastNameValidation = new Label();
        Label middleNameValidation = new Label();
        Label dobValidation = new Label();
        Label groupValidation = new Label();

        formLayout.addComponent(firstName);
        formLayout.addComponent(firstNameValidation);
        formLayout.addComponent(lastName);
        formLayout.addComponent(lastNameValidation);
        formLayout.addComponent(middleName);
        formLayout.addComponent(middleNameValidation);
        formLayout.addComponent(dob);
        formLayout.addComponent(dobValidation);
        formLayout.addComponent(group);
        formLayout.addComponent(groupValidation);

        studentBinder.forField(firstName)
                .asRequired("First Name may not be empty")
                .withValidator(
                        fName -> Pattern.matches("\\D+", fName),
                        "Name must not contain numerical")
                .withStatusLabel(firstNameValidation)
                .bind(Student::getFirstName, Student::setFirstName);
        studentBinder.forField(lastName)
                .asRequired("Last Name may not be empty")
                .withValidator(
                        lName -> Pattern.matches("\\D+", lName),
                        "Name must not contain numerical")
                .withStatusLabel(lastNameValidation)
                .bind(Student::getLastName, Student::setLastName);
        studentBinder.forField(middleName)
                .asRequired("Middle Name may not be empty")
                .withValidator(
                        mName -> Pattern.matches("\\D+", mName),
                        "Name must not contain numerical")
                .withStatusLabel(middleNameValidation)
                .bind(Student::getMiddleName, Student::setMiddleName);
        studentBinder.forField(dob)
                .asRequired("Date of Birth may not be empty")
                .withValidator(
                        localDate -> localDate.isBefore(LocalDate.now()),
                        "Date of Birth must not be in future")
                .withStatusLabel(dobValidation)
                .bind(Student::getDob, Student::setDob);
        studentBinder.forField(group)
                .asRequired("Group may not be empty")
                .withStatusLabel(groupValidation)
                .bind(Student::getGroup, Student::setGroup);

        Button okButton = new Button("Ok", clickEvent -> {
            if(studentBinder.validate().isOk()) {
                jpaCrud.createOrUpdate(studentBinder.getBean());
                refreshStudentGrig(jpaCrud.findAllStudents());
                addEditStudentWindow.setVisible(false);
            }
        });
        Button cancelButton = new Button("Отменить", clickEvent -> addEditStudentWindow.setVisible(false));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(okButton);
        horizontalLayout.addComponent(cancelButton);

        formLayout.addComponent(horizontalLayout);

        addEditStudentWindow.setContent(formLayout);
        addEditStudentWindow.setModal(true);
        addEditStudentWindow.setVisible(false);
        return addEditStudentWindow;
    }

    public void refreshGroupGrig(List<Group> groupList) {
        groupGrid.setDataProvider(DataProvider.ofCollection(groupList));
    }

    public void refreshStudentGrig(List<Student> studentList) {
        ListDataProvider<Student> dataProvider = DataProvider.ofCollection(studentList);
        studentGridTopHeader.getCell("LastNameColumn").setComponent(createLastNameFilter(dataProvider));
        studentGridTopHeader.getCell("GroupColumn").setComponent(createGroupNumberFilter(dataProvider));
        studentGrid.setDataProvider(dataProvider);
    }

    private Group getSelectedGroup() {
        for(Group g: groupGrid.getSelectedItems())
            return g;
        return null;
    }

    private Student getSelectedStudent() {
        for(Student s: studentGrid.getSelectedItems())
            return s;
        return null;
    }

    private TextField createLastNameFilter(ListDataProvider<Student> dataProvider) {
        TextField filter = getTextField();
        filter.addValueChangeListener(event -> {
            dataProvider.setFilter(Student::getLastName, lastName -> {
                if (lastName == null) {
                    return false;
                }
                String lastNameLower = lastName.toLowerCase();
                String filterLower = event.getValue().toLowerCase();
                return lastNameLower.contains(filterLower);
            });
        });
        return filter;
    }

    private TextField createGroupNumberFilter(ListDataProvider<Student> dataProvider) {
        TextField filter = getTextField();
        filter.addValueChangeListener(event -> {
            dataProvider.setFilter(Student::getGroup, group -> {
                if (group == null) {
                    return false;
                }
                String numberLower = group.getNumber().toLowerCase();
                String filterLower = event.getValue().toLowerCase();
                return numberLower.contains(filterLower);
            });
        });
        return filter;
    }

    private TextField getTextField() {
        TextField filter = new TextField();
        filter.setWidth("100%");
        filter.addStyleName(ValoTheme.TEXTFIELD_TINY);
        filter.setPlaceholder("Filter");
        return filter;
    }
}
