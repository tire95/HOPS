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
public class StudentTest {

    Student arto;

    public StudentTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        arto = new Student(1, "Arto", "akke");
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void getIdWorks() {
        assertEquals(1, arto.getId());
    }
    
    @Test
    public void getNameWorks() {
        assertEquals("Arto", arto.getName());
    }
    
    @Test
    public void getUsernameWorks() {
        assertEquals("akke", arto.getUsername());
    }

}
