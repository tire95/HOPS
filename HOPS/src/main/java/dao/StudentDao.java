/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Student;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author timo
 */
public interface StudentDao {

//    T findById(K key) throws SQLException;
    Student save(Student student) throws SQLException;

    Student findByUsername(String string) throws SQLException;

    void delete(Integer key) throws SQLException;

    List<Student> findAll() throws SQLException;

}
