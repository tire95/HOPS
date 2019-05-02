/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

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
public class CourseTest {

    Course testCourse;

    public CourseTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        testCourse = new Course(1, 2, "TKT20002", "Ohjelmistotekniikka", 5);
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void getIdWorks() {
        assertEquals(1, testCourse.getId());
    }
    
    @Test
    public void getStudentIdWorks() {
        assertEquals(2, testCourse.getStudentId());
    }
    
    @Test
    public void getNameWorks() {
        assertEquals("Ohjelmistotekniikka", testCourse.getName());
    }
    
    @Test
    public void getCodeWorks() {
        assertEquals("TKT20002", testCourse.getCode());
    }
    
    @Test
    public void getPointsWorks() {
        assertEquals(5, testCourse.getPoints());
    }
    
    @Test
    public void toStringWorks() {
        assertEquals("Kurssikoodi: TKT20002, kurssinimi: Ohjelmistotekniikka, opintopisteitä: 5", testCourse.toString());
    }
 

}
