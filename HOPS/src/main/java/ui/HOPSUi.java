
package ui;

import dao.CourseDao;
import dao.SQLCourseDao;
import dao.SQLStudentDao;
import dao.StudentDao;
import database.Database;
import domain.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
    public void init() {
        try {
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
            this.students.setSpacing(5);
            this.loggedInWelcome = new Label();
            this.totalPointsLabel = new Label();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void start(Stage stage) {

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

        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.setPadding(new Insets(20));

        toAdminSceneButton.setOnAction(e -> {
            String adminInputText = adminInput.getText();
            if (adminInputText.equals(adminPassword)) {
                adminErrorMsg.setText("");
                logInError.setText("");
                getStudents(stage);
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
                    totalPointsLabel.setText("Opintopisteesi: " + getCoursePoints(stage) + "/" + coursePointsMax);
                    userNameInput.clear();
                    getCourses(stage);
                    stage.setScene(loggedInScene);
                } else {
                    logInError.setText("Tunnusta ei löydy");
                    logInError.setTextFill(Color.RED);
                }
            } catch (SQLException ex) {
                stage.setScene(getErrorScene(ex));
            }
        });

        toNewUserScene.setOnAction(e -> {
            userNameInput.setText("");
            logInError.setText("");
            adminErrorMsg.setText("");
            stage.setScene(newUserScene);
        });

        logInScene = new Scene(loginPane);

//        admin scene
        Label adminLabel = new Label("Tervetuloa admin-näkymään. Täällä voit poistaa opiskelijan ja tähän liittyvät kurssit järjestelmästä.");
        Button toLogInSceneButton = new Button("Takaisin");

        toLogInSceneButton.setOnAction(e -> {
            adminInput.clear();
            stage.setScene(logInScene);
        });

        ScrollPane adminScroll = new ScrollPane(students);
        adminScroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        adminScroll.setFitToHeight(true);
        GridPane adminPane = new GridPane();
        adminPane.add(adminLabel, 0, 0);
        adminPane.add(toLogInSceneButton, 0, 1);

        adminPane.setVgap(10);
        adminPane.setHgap(10);

        BorderPane adminMainPane = new BorderPane();
        adminMainPane.setPadding(new Insets(20));
        adminMainPane.setCenter(adminScroll);
        adminMainPane.setTop(adminPane);
        adminMainPane.setPrefHeight(400);

        adminScene = new Scene(adminMainPane);

//        new user creation screen
        GridPane newUserPane = new GridPane();
        newUserPane.setVgap(10);
        newUserPane.setHgap(10);

        Label newUserNameLabel = new Label("Nimi: ");
        TextField newUserNameInput = new TextField();
        Label newUserUsernameLabel = new Label("Käyttäjätunnus: ");
        TextField newUserUsernameInput = new TextField();
        Button createUserButton = new Button("Luo uusi käyttäjä");
        Button backToLogIn = new Button("Takaisin sisäänkirjautumiseen");
        Label newUserErrorMessage = new Label("");

        VBox newUserVBox = new VBox();
        newUserPane.add(newUserNameLabel, 0, 0);
        newUserPane.add(newUserNameInput, 1, 0);
        newUserPane.add(newUserUsernameLabel, 0, 1);
        newUserPane.add(newUserUsernameInput, 1, 1);
        newUserPane.add(createUserButton, 0, 2);
        newUserPane.add(backToLogIn, 1, 2);
        newUserVBox.getChildren().addAll(newUserPane, newUserErrorMessage);
        newUserVBox.setSpacing(5);
        newUserVBox.setPadding(new Insets(20));

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
                stage.setScene(getErrorScene(ex));
            }
        });

        backToLogIn.setOnAction(e -> {
            newUserNameInput.setText("");
            newUserErrorMessage.setText("");
            newUserUsernameInput.setText("");
            stage.setScene(logInScene);
        });

        newUserScene = new Scene(newUserVBox);

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

        ScrollPane loggedInScroll = new ScrollPane(courses);
        loggedInScroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        loggedInScroll.setFitToHeight(true);
        GridPane loggedInPane = new GridPane();
        loggedInPane.add(loggedInWelcome, 0, 0);
        loggedInPane.add(totalPointsLabel, 0, 1);
        loggedInPane.add(logOutButton, 1, 0);
        loggedInPane.add(toCreateNewCourseButton, 0, 2);

        loggedInPane.setVgap(10);
        loggedInPane.setHgap(10);

        BorderPane loggedInMainPane = new BorderPane();
        loggedInMainPane.setPadding(new Insets(20));
        loggedInMainPane.setCenter(loggedInScroll);
        loggedInMainPane.setTop(loggedInPane);
        loggedInMainPane.setPrefHeight(400);

        loggedInScene = new Scene(loggedInMainPane);

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
        courseBox.setSpacing(5);
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
                    getCourses(stage);
                    totalPointsLabel.setText("Opintopisteesi: " + getCoursePoints(stage) + "/" + coursePointsMax);
                    stage.setScene(loggedInScene);
                } else {
                    courseCreationFailMsg.setText("Kyseisellä koodilla tai nimellä on jo kurssi");
                    courseCreationFailMsg.setTextFill(Color.RED);
                }
            } catch (SQLException ex) {
                stage.setScene(getErrorScene(ex));
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

    private void getCourses(Stage stage) {
        try {
            courses.getChildren().clear();
            List<Course> coursesList = HOPSService.getAllCourses();
            if (!coursesList.isEmpty()) {
                coursesList.forEach(course -> {
                    courses.getChildren().add(createCourseNode(course, stage));
                });
            }
        } catch (SQLException ex) {
            stage.setScene(getErrorScene(ex));
        }

    }

    private Node createCourseNode(Course c, Stage stage) {
        HBox box = new HBox();
        box.setSpacing(10);
        Label courseLabel = new Label(c.toString());
        Button courseButton = new Button("Poista kurssi");

        courseButton.setOnAction(e -> {
            try {
                HOPSService.removeCourse(c.getId());
                getCourses(stage);
                totalPointsLabel.setText("Opintopisteesi: " + getCoursePoints(stage) + "/" + coursePointsMax);
            } catch (SQLException ex) {
                stage.setScene(getErrorScene(ex));
            }
        });
        box.getChildren().addAll(courseLabel, courseButton);
        return box;
    }

    private void getStudents(Stage stage) {
        try {
            students.getChildren().clear();
            List<Student> studentsList = HOPSService.getAllStudents();
            if (!studentsList.isEmpty()) {
                studentsList.forEach(student -> {
                    students.getChildren().add(createStudentNode(student, stage));
                });
            }
        } catch (SQLException ex) {
            stage.setScene(getErrorScene(ex));
        }
    }

    private Node createStudentNode(Student s, Stage stage) {
        HBox box = new HBox();
        box.setSpacing(10);
        Label studentLabel = new Label(s.getName());
        Button studentButton = new Button("Poista opiskelija");

        studentButton.setOnAction(e -> {
            try {
                HOPSService.removeStudentAndCourses(s.getId());
                getStudents(stage);
            } catch (SQLException ex) {
                stage.setScene(getErrorScene(ex));
            }
        });
        box.getChildren().addAll(studentLabel, studentButton);
        return box;
    }

    private int getCoursePoints(Stage stage) {
        int i = 0;
        try {
            List<Course> coursesList = HOPSService.getAllCourses();
            if (!coursesList.isEmpty()) {
                i = coursesList.stream().map((c) -> c.getPoints()).reduce(i, Integer::sum);
            }
        } catch (SQLException ex) {
            stage.setScene(getErrorScene(ex));
        }
        return i;
    }

    private Scene getErrorScene(Exception e) {
        Label errorMessage1 = new Label("An exception was encountered: ");
        Label errorMessage2 = new Label(e.getMessage());
        Label errorMessage3 = new Label("Did you move/delete the database?");
        Label errorMessage4 = new Label("Please close this window to terminate the application.");
        errorMessage2.setTextFill(Color.RED);
        VBox box = new VBox();
        box.setSpacing(5);
        box.setPadding(new Insets(20));
        box.getChildren().addAll(errorMessage1, errorMessage2, errorMessage3, errorMessage4);
        return new Scene(box);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(HOPSUi.class);
    }

}
