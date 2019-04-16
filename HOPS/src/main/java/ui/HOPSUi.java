/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dao.SQLCourseDao;
import dao.SQLStudentDao;
import database.Database;
import domain.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author timo
 */
public class HOPSUi extends Application {

    private HOPSService HOPSService;
    private Scene logInScene;
    private Scene newUserScene;
    private Scene loggedInScene;
    private Scene newCourseScene;
    private Scene adminScene;
    private VBox courses;
    private VBox students;

    @Override
    public void init() throws Exception {
        Database database = new Database("jdbc:sqlite:HOPSDatabase.db");
        SQLStudentDao sDao = new SQLStudentDao(database);
        SQLCourseDao cDao = new SQLCourseDao(database);
        this.HOPSService = new HOPSService(sDao, cDao);
        this.courses = new VBox();
        this.students = new VBox();
    }

    @Override
    public void start(Stage stage) throws SQLException {

        Label loggedInWelcome = new Label();

//        login screen
        Label guideForLogIn = new Label("Käyttäjätunnus");
        TextField userNameField = new TextField();
        Button logInButton = new Button("Kirjaudu");
        Label logInError = new Label("");
        Button toNewUserScene = new Button("Luo uusi käyttäjä");
        Label adminText = new Label("Kirjoita admin-salasana");
        PasswordField adminInput = new PasswordField();
        Button toAdminSceneButton = new Button("Admin");
        Label adminErrorMsg = new Label("");

        GridPane loginPane = new GridPane();
        loginPane.add(guideForLogIn, 0, 0);
        loginPane.add(userNameField, 0, 1);
        loginPane.add(logInButton, 1, 1);
        loginPane.add(logInError, 0, 3);
        loginPane.add(toNewUserScene, 0, 2);
        loginPane.add(adminText, 0, 4);
        loginPane.add(adminInput, 0, 5);
        loginPane.add(toAdminSceneButton, 1, 5);
        loginPane.add(adminErrorMsg, 0, 6);

//        loginPane.setPrefSize(400, 180);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.setPadding(new Insets(20));

        toAdminSceneButton.setOnAction(e -> {
            String adminInputText = adminInput.getText();
            if (adminInputText.equals("salasana1234")) {
                adminErrorMsg.setText("");
                logInError.setText("");
                getStudents();
                stage.setScene(adminScene);
            } else {
                adminErrorMsg.setText("Väärä salasana");
                adminErrorMsg.setTextFill(Color.RED);
            }
        });

        logInButton.setOnAction(e -> {
            try {
                if (HOPSService.logIn(userNameField.getText())) {
                    logInError.setText("");
                    adminErrorMsg.setText("");
                    loggedInWelcome.setText("Tervetuloa, " + HOPSService.getLoggedInName());
                    userNameField.clear();
                    getCourses();
                    stage.setScene(loggedInScene);
                } else {
                    logInError.setText("Tunnusta ei löydy");
                    logInError.setTextFill(Color.RED);
                }
            } catch (SQLException ex) {
                Logger.getLogger(HOPSUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        toNewUserScene.setOnAction(e -> {
            stage.setScene(newUserScene);
        });

        logInScene = new Scene(loginPane);

//        admin scene
        GridPane adminPane = new GridPane();
        adminPane.setVgap(10);
        adminPane.setHgap(10);
        adminPane.setPadding(new Insets(20));
        Label adminLabel = new Label("Tervetuloa admin-näkymään. Täällä voit poistaa opiskelijan ja tähän liittyvät kurssit järjestelmästä.");
        Button toLogInScene = new Button("Takaisin");

        toLogInScene.setOnAction(e -> {
            adminInput.clear();
            stage.setScene(logInScene);
        });

        adminPane.add(adminLabel, 0, 0);
        adminPane.add(students, 0, 1);
        adminPane.add(toLogInScene, 0, 2);

        adminScene = new Scene(adminPane);

//        new user creation screen
        GridPane newUserPane = new GridPane();
        newUserPane.setPrefSize(400, 180);
        newUserPane.setVgap(10);
        newUserPane.setHgap(10);
        newUserPane.setPadding(new Insets(20));

        Label newUserName = new Label("Nimi: ");
        TextField nameInput = new TextField();
        Label newUserUsername = new Label("Käyttäjätunnus: ");
        TextField usernameInput = new TextField();
        Button createUser = new Button("Luo uusi käyttäjä");
        Label newUserErrorMessage = new Label("");

        newUserPane.add(newUserName, 0, 0);
        newUserPane.add(nameInput, 1, 0);
        newUserPane.add(newUserUsername, 0, 1);
        newUserPane.add(usernameInput, 1, 1);
        newUserPane.add(createUser, 0, 2);
        newUserPane.add(newUserErrorMessage, 1, 2);

        createUser.setOnAction(e -> {
            try {
                String name = nameInput.getText();
                String username = usernameInput.getText();

                if (name.length() < 3 || username.length() < 3) {
                    newUserErrorMessage.setText("Nimi tai käyttäjätunnus liian lyhyt");
                    newUserErrorMessage.setTextFill(Color.RED);
                } else if (HOPSService.createNewUser(name, username)) {
                    newUserErrorMessage.setText("");
                    nameInput.clear();
                    usernameInput.clear();
                    logInError.setText("Uusi käyttäjä luotu");
                    logInError.setTextFill(Color.GREEN);
                    stage.setScene(logInScene);
                } else {
                    newUserErrorMessage.setText("Käyttäjätunnus käytössä");
                    newUserErrorMessage.setTextFill(Color.RED);
                }
            } catch (SQLException ex) {
                Logger.getLogger(HOPSUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        newUserScene = new Scene(newUserPane);

//        loggedIn screen
        Button logOut = new Button("Kirjaudu ulos");

        logOut.setOnAction(e -> {
            HOPSService.logOut();
            courses.getChildren().clear();
            userNameField.clear();
            stage.setScene(logInScene);
        });
        Button toCreateNewCourse = new Button("Luo uusi kurssi");

        toCreateNewCourse.setOnAction(e -> {
            stage.setScene(newCourseScene);
        });

        GridPane loggedInPane = new GridPane();
        loggedInPane.add(loggedInWelcome, 0, 0);
        loggedInPane.add(logOut, 1, 0);
        loggedInPane.add(courses, 0, 1);
        loggedInPane.add(toCreateNewCourse, 0, 2);

//        loggedInPane.setPrefSize(1000, 1000);
        loggedInPane.setVgap(10);
        loggedInPane.setHgap(10);
        loggedInPane.setPadding(new Insets(20));

        loggedInScene = new Scene(loggedInPane);

//        new course creation scene
        Label courseCode = new Label("Kurssikoodi: ");
        Label courseName = new Label("Kurssin nimi: ");
        Label coursePoints = new Label("Kurssin opintopisteet: ");
        TextField courseCodeInput = new TextField();
        TextField courseNameInput = new TextField();
        TextField coursePointsInput = new TextField();
        Button createNewCourseButton = new Button("Luo uusi kurssi");
        Button backToLoggedInButton = new Button("Takaisin");
        Label courseCreationFailMsg = new Label("");
        GridPane courseCreationPane = new GridPane();
//        courseCreationPane.setPrefSize(600, 400);
        courseCreationPane.setVgap(10);
        courseCreationPane.setHgap(10);
        courseCreationPane.setPadding(new Insets(20));
        courseCreationPane.add(courseCode, 0, 0);
        courseCreationPane.add(courseCodeInput, 1, 0);
        courseCreationPane.add(courseName, 0, 1);
        courseCreationPane.add(courseNameInput, 1, 1);
        courseCreationPane.add(coursePoints, 0, 2);
        courseCreationPane.add(coursePointsInput, 1, 2);
        courseCreationPane.add(createNewCourseButton, 0, 3);
        courseCreationPane.add(courseCreationFailMsg, 0, 4);
        courseCreationPane.add(backToLoggedInButton, 1, 3);

        createNewCourseButton.setOnAction(e -> {
            try {
                String courseCodeText = courseCodeInput.getText();
                String courseNameText = courseNameInput.getText();
                String coursePointText = coursePointsInput.getText();
                boolean coursePointsAreInt = true;
                int num = 0;
                try {
                    num = Integer.parseInt(coursePointText);
                } catch (NumberFormatException nfe) {
                    coursePointsAreInt = false;
                }
                if (courseCodeText.length() > 10) {
                    courseCreationFailMsg.setText("Kurssikoodi liian pitkä");
                    courseCreationFailMsg.setTextFill(Color.RED);
                } else if (courseNameText.length() > 51) {
                    courseCreationFailMsg.setText("Kurssinimi liian pitkä");
                    courseCreationFailMsg.setTextFill(Color.RED);
                } else if (courseNameText.length() < 3) {
                    courseCreationFailMsg.setText("Kurssinimi liian lyhyt");
                    courseCreationFailMsg.setTextFill(Color.RED);
                } else if (!coursePointsAreInt) {
                    courseCreationFailMsg.setText("Kurssipisteet täytyy olla kokonaisluku");
                    courseCreationFailMsg.setTextFill(Color.RED);
                } else if (HOPSService.createNewCourse(courseCodeText, courseNameText, num)) {
                    courseCreationFailMsg.setText("");
                    courseCodeInput.clear();
                    courseNameInput.clear();
                    coursePointsInput.clear();
                    getCourses();
                    stage.setScene(loggedInScene);
                } else {
                    courseCreationFailMsg.setText("Kyseisellä koodilla tai nimellä on jo kurssi");
                    courseCreationFailMsg.setTextFill(Color.RED);
                }
            } catch (SQLException ex) {
                Logger.getLogger(HOPSUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        backToLoggedInButton.setOnAction(e -> {
            courseCreationFailMsg.setText("");
            stage.setScene(loggedInScene);
        });

        newCourseScene = new Scene(courseCreationPane);

        stage.setTitle("HOPS");
        stage.setScene(logInScene);
        stage.show();

    }

    private void getCourses() {
        try {
            courses.getChildren().clear();
            List<Course> coursesList = HOPSService.getAllCourses();
            if (!coursesList.isEmpty()) {
                coursesList.forEach(course -> {
                    courses.getChildren().add(new Label(course.toString()));
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(HOPSUi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void getStudents() {
        try {
            students.getChildren().clear();
            List<Student> studentsList = HOPSService.getAllStudents();
            if (!studentsList.isEmpty()) {
                studentsList.forEach(student -> {
                    students.getChildren().add(createStudentNode(student));
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(HOPSUi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Node createStudentNode(Student s) {
        HBox box = new HBox();
        Label studentLabel = new Label(s.getName());
        Button studentButton = new Button("Poista opiskelija");

        studentButton.setOnAction(e -> {
            try {
                HOPSService.removeStudentAndCourses(s.getId());
                getStudents();
            } catch (SQLException ex) {
                Logger.getLogger(HOPSUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        box.getChildren().addAll(studentLabel, studentButton);
        return box;
    }

    public static void main(String[] args) throws SQLException {
        launch(HOPSUi.class);
    }

}
