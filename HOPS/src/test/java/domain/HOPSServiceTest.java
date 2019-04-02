/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.SQLStudentDao;
import database.Database;
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
public class HOPSServiceTest {
    
    SQLStudentDao studentDao;
    
    public HOPSServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        studentDao = new SQLStudentDao(new Database("jdbc:sqlite:testDatabase.db"));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createNewUser method, of class HOPSService.
     */
    
    @Test
    public void createUserWithExistingUsernameFails() throws SQLException {
        Student created = studentDao.save(new Student(-1, "Timo Reijonen", "tire"));
        assertEquals(null, created);
    }

    
}
