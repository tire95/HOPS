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
public interface StudentDao<T, K, S> {

//    T findById(K key) throws SQLException;
    T save(T object) throws SQLException;

    T findByUsername(S string) throws SQLException;

    void delete(K key) throws SQLException;

    List<T> findAll() throws SQLException;

}
