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
    

//    @Test
//    public void equalWhenSameUsername() {
//        Student arto2 = new Student(2, "Arto", "akke");
//        assertTrue(arto.equals(arto2));
//    }
//
//    @Test
//    public void notEqualWhenDifferentUsername() {
//        Student timo = new Student(3, "Timo", "tirppa");
//        assertFalse(arto.equals(timo));
//    }
//
//    @Test
//    public void notEqualWhenDifferentType() {
//        Object o = new Object();
//        assertFalse(arto.equals(o));
//    }
//    
//    @Test
//    public void equalWhenSameId() {
//        Student arto2 = new Student(1, "sadadsa", "asddsadsa");
//        assertTrue(arto.equals(arto2));
//    }

}
