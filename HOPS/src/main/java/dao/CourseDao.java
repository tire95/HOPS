
package dao;

import domain.Course;
import java.sql.SQLException;
import java.util.List;

/**
 * dao-rajapinta kurssien käsittelyä varten
 */
public interface CourseDao {

    /**
     * Kurssien etsiminen tietylle opiskelijalle
     * @param studentId opiskelijan id
     * @return lista kursseista
     * @throws SQLException 
     */
    List<Course> findAllForStudent(Integer studentId) throws SQLException;

    /**
     * Kurssin tallennus
     * @param course tallennettava kurssi
     * @return null jos koodilla tai nimellä löytyy kurssi kyseisellä opiskelijalla, tai luotu kurssi
     * @throws SQLException 
     */
    Course save(Course course) throws SQLException;

    /**
     * Kurssin poisto
     * @param courseId poistettavan kurssin id
     * @throws SQLException 
     */
    void delete(Integer courseId) throws SQLException;

    /**
     * Kurssin etsiminen nimen ja opiskelijan id:n perusteella
     * @param studentId opiskelijan id
     * @param courseName kurssin nimi
     * @return null jos kurssia ei löydy, tai kurssi
     * @throws SQLException 
     */
    Course findByName(Integer studentId, String courseName) throws SQLException;

    /**
     * Kurssin etsiminen koodin ja opiskelijan id:n perusteella
     * @param studentId opiskelijan id
     * @param courseCode kurssikoodi
     * @return null jos kurssia ei löydy, tai kurssi
     * @throws SQLException 
     */
    Course findByCode(Integer studentId, String courseCode) throws SQLException;

    /**
     * Opiskelijan kurssien poisto
     * @param studentId opiskelijan id
     * @throws SQLException 
     */
    void deleteForStudent(Integer studentId) throws SQLException;
}
