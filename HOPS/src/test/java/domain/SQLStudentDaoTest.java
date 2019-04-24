/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.SQLStudentDao;
import database.Database;
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
public class SQLStudentDaoTest {

    private final SQLStudentDao studentDao;
    private final Database testDatabase;

    public SQLStudentDaoTest() throws SQLException {
        this.testDatabase = new Database("jdbc:sqlite:testDatabase.db");
        this.studentDao = new SQLStudentDao(testDatabase);
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
            emptyStudents.executeUpdate();
            PreparedStatement insertUserTest = conn.prepareStatement("INSERT INTO Student (name, username) VALUES ('testiNimi', 'testi')");
            insertUserTest.executeUpdate();
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void savingNewStudentWorks() throws SQLException {
        assertNotNull(studentDao.save(new Student(-1, "testiNimi", "testi2")));
    }
    
    @Test
    public void savingExistingStudentFails() throws SQLException {
        assertNull(studentDao.save(new Student(-1, "testiNimi", "testi")));
    }
    
    @Test
    public void findingExistingStudentByUsernameWorks() throws SQLException {
        Student found = studentDao.findByUsername("testi");
        assertNotNull(found);
        assertEquals("testi", found.getUsername());
    }
    
    @Test
    public void findingNonExistingStudentByUsernameFails() throws SQLException {
        assertNull(studentDao.findByUsername("testi2"));
    }
    
    @Test
    public void deletingUserActuallyDeletes() throws SQLException {
        Student test = studentDao.findByUsername("testi");
        studentDao.delete(test.getId());
        assertNull(studentDao.findByUsername("testi"));
    }
    
    @Test
    public void findAllReturnsListWithRightSize() throws SQLException {
        assertEquals(1, studentDao.findAll().size());
    }
}
