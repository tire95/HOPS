/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Course;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author timo
 */
public interface CourseDao {

    List<Course> findAllForStudent(Integer studentId) throws SQLException;

    Course save(Course course) throws SQLException;

    void delete(Integer courseId) throws SQLException;

    Course findByName(Integer studentId, String courseName) throws SQLException;

    Course findByCode(Integer studentId, String courseCode) throws SQLException;

    void deleteForStudent(Integer studentId) throws SQLException;
}
