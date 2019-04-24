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
    SQLStudentDao sd;

    public HOPSServiceTest() throws SQLException {
        testDatabase = new Database("jdbc:sqlite:testDatabase.db");
        sd = new SQLStudentDao(testDatabase);
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
            PreparedStatement st = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Student (id integer PRIMARY KEY, name varchar(50), username varchar(20))");
            PreparedStatement st2 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Course (id integer PRIMARY KEY, studentId integer, code varchar(10), name varchar(50), points integer, FOREIGN KEY (studentId) REFERENCES Student(id))");
            st.executeUpdate();
            st2.executeUpdate();
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
        assertFalse(service.logIn("non-existing"));
    }

    @Test
    public void existingUserCanLogIn() throws SQLException {
        assertTrue(service.logIn("testi"));
    }

    @Test
    public void creatingUserFailsIfUsernameUsed() throws SQLException {
        assertFalse(service.createNewUser("testiNimi", "testi"));
    }

    @Test
    public void creatingUserSucceedsIfUsernameNotUsed() throws SQLException {
        assertTrue(service.createNewUser("testiNimi", "morso"));
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
        assertTrue(service.createNewCourse("TEST2", "Test Course", 5));
    }

    @Test
    public void courseListSizeIsCorrect() throws SQLException {
        service.logIn("testi");
        service.createNewCourse("TEST2", "Test Course", 5);
        List<Course> courses = service.getAllCourses();
        assertEquals(1, courses.size());
    }

    @Test
    public void creatingExistingCourseFails() throws SQLException {
        service.logIn("testi");
        service.createNewCourse("TEST2", "Test Course", 5);
        assertFalse(service.createNewCourse("TEST2", "Test Course", 5));
    }

    @Test
    public void getLoggedInNameWorks() throws SQLException {
        service.logIn("testi");
        assertEquals("testiNimi", service.getLoggedInName());
    }

    @Test
    public void getAllStudentsWorks() throws SQLException {
        assertEquals(1, service.getAllStudents().size());
    }

    @Test
    public void removingStudentActuallyRemovesStudent() throws SQLException {
        service.createNewUser("testi", "testi2");
        Student justCreated = sd.findByUsername("testi2");
        service.removeStudentAndCourses(justCreated.getId());
        assertNull(sd.findByUsername("testi2"));
    }

}
