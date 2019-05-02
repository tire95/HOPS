
package dao;

import domain.Student;
import java.sql.SQLException;
import java.util.List;

/**
 * dao-rajapinta opiskelijoiden käsittelyä varten
 */
public interface StudentDao {

    /**
     * Opiskelijan tallennus tietokantaan
     * @param student opiskelija
     * @return null, jos opiskelija löytyy jo, tai juuri luotu opiskelija
     * @throws SQLException 
     */
    Student save(Student student) throws SQLException;

    /**
     * Opiskelijan etsiminen käyttäjätunnuksen perusteella
     * @param username opiskelijan käyttäjätunnus
     * @return null jos opiskelijaa ei löydy, tai opiskelija
     * @throws SQLException 
     */
    Student findByUsername(String username) throws SQLException;

    /**
     * Opiskelijan poisto
     * @param id opiskelijan id
     * @throws SQLException 
     */
    void delete(Integer id) throws SQLException;

    /**
     * Kaikkien opiskelijoiden etsiminen
     * @return lista opiskelijoista
     * @throws SQLException 
     */
    List<Student> findAll() throws SQLException;

}
