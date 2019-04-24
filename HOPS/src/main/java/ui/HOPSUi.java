/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dao.CourseDao;
import dao.SQLCourseDao;
import dao.SQLStudentDao;
import dao.StudentDao;
import database.Database;
import domain.*;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
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
    private int coursePointsMax;
    private String adminPassword;
    private Label loggedInWelcome;
    private Label totalPointsLabel;

    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));

        String databaseAddress = properties.getProperty("database");
        coursePointsMax = Integer.parseInt(properties.getProperty("coursePointsMax"));
        adminPassword = properties.getProperty("adminPassword");

        Database database = new Database("jdbc:sqlite:" + databaseAddress);
        StudentDao sDao = new SQLStudentDao(database);
        CourseDao cDao = new SQLCourseDao(database);
        this.HOPSService = new HOPSService(sDao, cDao);
        this.courses = new VBox();
        this.students = new VBox();
        this.loggedInWelcome = new Label();
        this.totalPointsLabel = new Label();
    }

    @Override
    public void start(Stage stage) throws SQLException {

//        login screen
        Label guideForLogIn = new Label("Käyttäjätunnus");
        TextField userNameInput = new TextField();
        Button logInButton = new Button("Kirjaudu");
        Label logInError = new Label("");
        Button toNewUserScene = new Button("Luo uusi käyttäjä");
        Label adminText = new Label("Kirjoita admin-salasana");
        PasswordField adminInput = new PasswordField();
        Button toAdminSceneButton = new Button("Admin");
        Label adminErrorMsg = new Label("");

        GridPane loginPane = new GridPane();
        loginPane.add(guideForLogIn, 0, 0);
        loginPane.add(userNameInput, 0, 1);
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
            if (adminInputText.equals(adminPassword)) {
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
                if (HOPSService.logIn(userNameInput.getText())) {
                    logInError.setText("");
                    adminErrorMsg.setText("");
                    loggedInWelcome.setText("Tervetuloa, " + HOPSService.getLoggedInName());
                    totalPointsLabel.setText("Opintopisteesi: " + getCoursePoints() + "/" + coursePointsMax);
                    userNameInput.clear();
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
        Button toLogInSceneButton = new Button("Takaisin");

        toLogInSceneButton.setOnAction(e -> {
            adminInput.clear();
            stage.setScene(logInScene);
        });

        adminPane.add(adminLabel, 0, 0);
        adminPane.add(students, 0, 1);
        adminPane.add(toLogInSceneButton, 0, 2);

        adminScene = new Scene(adminPane);

//        new user creation screen
        GridPane newUserPane = new GridPane();
        newUserPane.setPrefSize(400, 180);
        newUserPane.setVgap(10);
        newUserPane.setHgap(10);
        newUserPane.setPadding(new Insets(20));

        Label newUserNameLabel = new Label("Nimi: ");
        TextField newUserNameInput = new TextField();
        Label newUserUsernameLabel = new Label("Käyttäjätunnus: ");
        TextField newUserUsernameInput = new TextField();
        Button createUserButton = new Button("Luo uusi käyttäjä");
        Label newUserErrorMessage = new Label("");

        newUserPane.add(newUserNameLabel, 0, 0);
        newUserPane.add(newUserNameInput, 1, 0);
        newUserPane.add(newUserUsernameLabel, 0, 1);
        newUserPane.add(newUserUsernameInput, 1, 1);
        newUserPane.add(createUserButton, 0, 2);
        newUserPane.add(newUserErrorMessage, 1, 2);

        createUserButton.setOnAction(e -> {
            try {
                String name = newUserNameInput.getText();
                String username = newUserUsernameInput.getText();

                if (name.length() < 3 || username.length() < 3) {
                    newUserErrorMessage.setText("Nimi tai käyttäjätunnus liian lyhyt");
                    newUserErrorMessage.setTextFill(Color.RED);
                } else if (HOPSService.createNewUser(name, username)) {
                    newUserErrorMessage.setText("");
                    newUserNameInput.clear();
                    newUserUsernameInput.clear();
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
        Button logOutButton = new Button("Kirjaudu ulos");

        logOutButton.setOnAction(e -> {
            HOPSService.logOut();
            courses.getChildren().clear();
            userNameInput.clear();
            stage.setScene(logInScene);
        });
        Button toCreateNewCourseButton = new Button("Luo uusi kurssi");

        toCreateNewCourseButton.setOnAction(e -> {
            stage.setScene(newCourseScene);
        });

        GridPane loggedInPane = new GridPane();
        loggedInPane.add(loggedInWelcome, 0, 0);
        loggedInPane.add(totalPointsLabel, 0, 1);
        loggedInPane.add(logOutButton, 1, 0);
        loggedInPane.add(courses, 0, 2);
        loggedInPane.add(toCreateNewCourseButton, 0, 3);

        loggedInPane.setVgap(10);
        loggedInPane.setHgap(10);
        loggedInPane.setPadding(new Insets(20));

        loggedInScene = new Scene(loggedInPane);

//        new course creation scene
        Label courseCodeLabel = new Label("Kurssikoodi: ");
        TextField courseCodeInput = new TextField();
        HBox courseCodeBox = new HBox();
        courseCodeBox.getChildren().addAll(courseCodeLabel, courseCodeInput);

        Label courseNameLabel = new Label("Kurssin nimi: ");
        TextField courseNameInput = new TextField();
        HBox courseNameBox = new HBox();
        courseNameBox.getChildren().addAll(courseNameLabel, courseNameInput);

        Label coursePointsLabel = new Label("Kurssin opintopisteet: ");
        TextField coursePointsInput = new TextField();
        HBox coursePointsBox = new HBox();
        coursePointsBox.getChildren().addAll(coursePointsLabel, coursePointsInput);

        Button createNewCourseButton = new Button("Luo uusi kurssi");
        Button backToLoggedInButton = new Button("Takaisin");
        HBox courseButtonBox = new HBox();
        courseButtonBox.getChildren().addAll(createNewCourseButton, backToLoggedInButton);

        Label courseCreationFailMsg = new Label("");

        VBox courseBox = new VBox();
        courseBox.getChildren().addAll(courseCodeBox, courseNameBox, coursePointsBox, courseButtonBox, courseCreationFailMsg);
        courseBox.setPadding(new Insets(20));

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
                    totalPointsLabel.setText("Opintopisteesi: " + getCoursePoints() + "/" + coursePointsMax);
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

        newCourseScene = new Scene(courseBox);

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
                    courses.getChildren().add(createCourseNode(course));
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(HOPSUi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Node createCourseNode(Course c) {
        HBox box = new HBox();
        box.setSpacing(10);
        Label courseLabel = new Label(c.toString());
        Button courseButton = new Button("Poista kurssi");

        courseButton.setOnAction(e -> {
            try {
                HOPSService.removeCourse(c.getId());
                getCourses();
                totalPointsLabel.setText("Opintopisteesi: " + getCoursePoints() + "/" + coursePointsMax);
            } catch (SQLException ex) {
                Logger.getLogger(HOPSUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        box.getChildren().addAll(courseLabel, courseButton);
        return box;
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
        box.setSpacing(10);
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

    private int getCoursePoints() {
        int i = 0;
        try {
            List<Course> coursesList = HOPSService.getAllCourses();
            if (!coursesList.isEmpty()) {
                i = coursesList.stream().map((c) -> c.getPoints()).reduce(i, Integer::sum);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HOPSUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public static void main(String[] args) throws SQLException {
        launch(HOPSUi.class);
    }

}
