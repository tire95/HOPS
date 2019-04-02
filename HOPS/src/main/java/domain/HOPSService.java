/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.*;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author timo
 */
public class HOPSService {
    private SQLStudentDao studentDao;
    private SQLCourseDao courseDao;
    
    
    public HOPSService(SQLStudentDao sd, SQLCourseDao cd) {
        this.studentDao = sd;
        this.courseDao = cd;
    }
    
    public boolean createNewUser(String name, String username) throws SQLException {
        Student newStudent = studentDao.save(new Student(-1, name, username));
        if (newStudent == null) {
            return false;
        }
        return true;
    }
    
    public void printAllStudents() throws SQLException {
        List<Student> students = studentDao.findAll();
        for (Student s : students) {
            System.out.println(s.toString());
        }
    }
    
}
