/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.SQLCourseDao;
import database.Database;
import domain.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class SQLCourseDaoTest {

    private final SQLCourseDao courseDao;
    private final Database testDatabase;

    public SQLCourseDaoTest() throws SQLException {
        this.testDatabase = new Database("jdbc:sqlite:testDatabase.db");
        this.courseDao = new SQLCourseDao(testDatabase);
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
            PreparedStatement insertUserTest2 = conn.prepareStatement("INSERT INTO Student (name, username) VALUES ('testiNimi2', 'testi2')");
            insertUserTest2.executeUpdate();
            PreparedStatement insertCourseTest = conn.prepareStatement("INSERT INTO Course (studentId, code, name, points) VALUES (1, 'TEST1', 'test course', 5)");
            insertCourseTest.executeUpdate();
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void findingAllCoursesForStudentReturnsListWithRightSize() throws SQLException {
        assertEquals(1, courseDao.findAllForStudent(1).size());
    }

    @Test
    public void savingNewCourseSucceeds() throws SQLException {
        assertNotNull(courseDao.save(new Course(-1, 1, "TEST2", "test course 2", 5)));
    }

    @Test
    public void savingCourseWithSameNameFails() throws SQLException {
        assertNull(courseDao.save(new Course(-1, 1, "TEST2", "test course", 5)));
    }

    @Test
    public void savingCourseWithSameCodeFails() throws SQLException {
        assertNull(courseDao.save(new Course(-1, 1, "TEST1", "test course 2", 5)));
    }

    @Test
    public void savingCourseWithSameCodeToAnotherStudentSucceeds() throws SQLException {
        assertNotNull(courseDao.save(new Course(-1, 2, "TEST1", "test course 2", 5)));
    }
    
    @Test
    public void savingCourseWithSameNameToAnotherStudentSucceeds() throws SQLException {
        assertNotNull(courseDao.save(new Course(-1, 2, "TEST2", "test course", 5)));
    }
    
    @Test
    public void deletingCourseActuallyDeletes() throws SQLException {
        Course found = courseDao.findByCode(1, "TEST1");
        courseDao.delete(found.getId());
        assertNull(courseDao.findByCode(1, "TEST1"));
    }

    @Test
    public void findByNameFindsExistingCourse() throws SQLException {
        assertNotNull(courseDao.findByName(1, "test course"));
    }
    
    @Test
    public void findByNameDoesNotFindNonexistingCourse() throws SQLException {
        assertNull(courseDao.findByName(1, "non existing"));
        assertNull(courseDao.findByName(2, "test course"));
    }
    
    @Test
    public void findByCodeFindsExistingCourse() throws SQLException {
        assertNotNull(courseDao.findByCode(1, "TEST1"));
    }
    
    @Test
    public void findByCodeDoesNotFindNonexistingCourse() throws SQLException {
        assertNull(courseDao.findByCode(1, "NO"));
        assertNull(courseDao.findByCode(2, "TEST1"));
    }
    
    @Test
    public void deleteForStudentDeletesAllCoursesForStudent() throws SQLException {
        courseDao.save(new Course(-1, 1, "TEST2", "test course 2", 5));
        courseDao.deleteForStudent(1);
        assertEquals(0, courseDao.findAllForStudent(1).size());
    }
}
