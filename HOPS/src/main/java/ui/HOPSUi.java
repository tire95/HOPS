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

    @Override
    public void init() throws Exception {
        Database database = new Database("jdbc:sqlite:HOPSDatabase.db");
        SQLStudentDao sDao = new SQLStudentDao(database);
        SQLCourseDao cDao = new SQLCourseDao(database);
        this.HOPSService = new HOPSService(sDao, cDao);
    }

    @Override
    public void start(Stage stage) throws SQLException {

        Label loggedInWelcome = new Label();
        VBox courses = new VBox();

//        login screen
        Label guideForLogIn = new Label("Käyttäjätunnus (käytä 'testi' testataksesi)");
        TextField userNameField = new TextField();
        Button logInButton = new Button("Kirjaudu");
        Label logInError = new Label("");
        Button toNewUserScene = new Button("Luo uusi käyttäjä");

        GridPane pane = new GridPane();
        pane.add(guideForLogIn, 0, 0);
        pane.add(userNameField, 0, 1);
        pane.add(logInButton, 1, 1);
        pane.add(logInError, 0, 3);
        pane.add(toNewUserScene, 0, 2);

        pane.setPrefSize(400, 180);
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(20));

        logInButton.setOnAction(e -> {
            try {
                if (HOPSService.logIn(userNameField.getText())) {
                    logInError.setText("");
//                    logInError.setTextFill(Color.GREEN);
                    loggedInWelcome.setText("Tervetuloa, " + HOPSService.getLoggedInName());
                    List<Course> coursesList = HOPSService.getAllCourses();
                    if (!coursesList.isEmpty()) {
                        coursesList.forEach(course -> {
                            courses.getChildren().add(new Label(course.toString()));
                        });
                    }
                    stage.setScene(loggedInScene);
                } else {
                    logInError.setText("Tunnusta ei löydy.");
                    logInError.setTextFill(Color.RED);
                }
            } catch (SQLException ex) {
                Logger.getLogger(HOPSUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        toNewUserScene.setOnAction(e -> {
            stage.setScene(newUserScene);
        });

        logInScene = new Scene(pane);

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
                    newUserErrorMessage.setText("Nimi tai käyttäjätunnus liian lyhyt.");
                    newUserErrorMessage.setTextFill(Color.RED);
                } else if (HOPSService.createNewUser(name, username)) {
                    newUserErrorMessage.setText("");
                    logInError.setText("Uusi käyttäjä luotu.");
                    logInError.setTextFill(Color.GREEN);
                    stage.setScene(logInScene);
                } else {
                    newUserErrorMessage.setText("Käyttäjätunnus käytössä.");
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

        GridPane loggedInPane = new GridPane();
        loggedInPane.add(loggedInWelcome, 0, 0);
        loggedInPane.add(logOut, 1, 0);
        loggedInPane.add(courses, 0, 1);

        loggedInPane.setPrefSize(600, 400);
        loggedInPane.setVgap(10);
        loggedInPane.setHgap(10);
        loggedInPane.setPadding(new Insets(20));

        loggedInScene = new Scene(loggedInPane);

        stage.setScene(logInScene);
        stage.show();

    }

    public static void main(String[] args) throws SQLException {
        launch(HOPSUi.class);
    }

}
