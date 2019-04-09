/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author timo
 */
public interface CourseDao<T, K, S> {

    T findOne(K key) throws SQLException;

    List<T> findAllForStudent(K key) throws SQLException;

    T save(T object) throws SQLException;

    void delete(K key) throws SQLException;

    T findByName(K key, S string) throws SQLException;

    T findByCode(K key, S string) throws SQLException;

}
