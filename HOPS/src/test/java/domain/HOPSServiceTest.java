/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.SQLCourseDao;
import dao.SQLStudentDao;
import database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author timo
 */
public class HOPSServiceTest {

    HOPSService service;
    Database testDatabase;

    public HOPSServiceTest() {
        testDatabase = new Database("jdbc:sqlite:testDatabase.db");
        SQLStudentDao sd = new SQLStudentDao(testDatabase);
        SQLCourseDao cd = new SQLCourseDao(testDatabase);
        this.service = new HOPSService(sd, cd);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {
        try (Connection conn = testDatabase.getConnection()) {
            PreparedStatement emptyStudents = conn.prepareStatement("DELETE FROM Student");
            PreparedStatement emptyCourses = conn.prepareStatement("DELETE FROM Course");
            emptyStudents.executeUpdate();
            emptyCourses.executeUpdate();
            PreparedStatement insertUserTest = conn.prepareStatement("INSERT INTO Student (name, username) VALUES ('testiNimi', 'testi')");
            insertUserTest.executeUpdate();
        }
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void notExistingUserCannotLogIn() throws SQLException {
        boolean loggedIn = service.logIn("non-existing");
        assertTrue(loggedIn == false);
    }
    
    @Test
    public void existingUserCanLogIn() throws SQLException {
        boolean loggedIn = service.logIn("testi");
        assertTrue(loggedIn == true);
    }
    
    @Test
    public void creatingUserFailsIfUsernameUsed() throws SQLException {
        boolean userCreated = service.createNewUser("testiNimi", "testi");
        assertTrue(userCreated == false);
    }
    
    @Test
    public void creatingUserSucceedsIfUsernameNotUsed() throws SQLException {
        boolean userCreated = service.createNewUser("testiNimi", "morso");
        assertTrue(userCreated == true);
    }
    
    @Test
    public void courseListIsEmpty() throws SQLException {
        service.logIn("testi");
        List<Course> courses = service.getAllCourses();
        assertTrue(courses.isEmpty());
    }
    
    @Test
    public void creatingNewCourseSucceeds() throws SQLException {
        service.logIn("testi");
        boolean creatingCourseSucceeded = service.createNewCourse("TEST2", "Test Course", 5);
        assertTrue(creatingCourseSucceeded == true);
        List<Course> courses = service.getAllCourses();
        assertTrue(courses.size() == 1);
    }
    
    

}
