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

    List<Course> findAllForStudent(Integer key) throws SQLException;

    Course save(Course course) throws SQLException;

    void delete(Integer key) throws SQLException;

    Course findByName(Integer key, String string) throws SQLException;

    Course findByCode(Integer key, String string) throws SQLException;

    void deleteForStudent(Integer key) throws SQLException;
}
