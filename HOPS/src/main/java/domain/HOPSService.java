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

    private final SQLStudentDao studentDao;
    private final SQLCourseDao courseDao;
    private Student loggedIn;

    public HOPSService(SQLStudentDao sd, SQLCourseDao cd) {
        this.studentDao = sd;
        this.courseDao = cd;
        this.loggedIn = null;
    }

    public boolean createNewUser(String name, String username) throws SQLException {
        Student newStudent = studentDao.save(new Student(-1, name, username));
        return newStudent != null;
    }

    public boolean logIn(String username) throws SQLException {
        loggedIn = studentDao.findByUsername(username);
        return loggedIn != null;
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = courseDao.findAllForStudent(loggedIn.getId());
        return courses;
    }

    public boolean createNewCourse(String code, String name, int points) throws SQLException {
        Course newCourse = courseDao.save(new Course(-1, loggedIn.getId(), code, name, points));
        return newCourse != null;
    }
    
    public String getLoggedInName() {
        return loggedIn.getName();
    }
    
    public void logOut() {
        loggedIn = null;
    }
    
    public void removeStudentAndCourses(Integer i) throws SQLException {
        courseDao.deleteForStudent(i);
        studentDao.delete(i);
    }
    
    public List<Student> getAllStudents() throws SQLException {
        return studentDao.findAll();
    }

}
