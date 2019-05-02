package dao;

import database.Database;
import domain.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * dao-rajapinnan toteuttava luokka kurssien käsittelyyn SQL-tietokannassa
 */
public class SQLCourseDao implements CourseDao {

    private final Database database;

    /**
     * Konstruktori
     * @param database käytettävä tietokanta
     */
    public SQLCourseDao(Database database) {
        this.database = database;
    }

    /**
     * Kurssien etsiminen tietylle opiskelijalle
     * @param studentId opiskelijan id
     * @return lista kursseista
     * @throws SQLException 
     */
    @Override
    public List<Course> findAllForStudent(Integer studentId) throws SQLException {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Course WHERE studentId = ?");
            st.setInt(1, studentId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                courses.add(new Course(rs.getInt("id"), rs.getInt("studentId"), rs.getString("code"), rs.getString("name"), rs.getInt("points")));
            }
        }
        return courses;
    }

    /**
     * Kurssin tallennus
     * @param course tallennettava kurssi
     * @see dao.SQLCourseDao#findByName(java.lang.Integer, java.lang.String) 
     * @see dao.SQLCourseDao#findByCode(java.lang.Integer, java.lang.String) 
     * @return null jos koodilla tai nimellä löytyy kurssi kyseisellä opiskelijalla, tai luotu kurssi
     * @throws SQLException 
     */
    @Override
    public Course save(Course course) throws SQLException {
        Course byName = findByName(course.getStudentId(), course.getName());
        Course byCode = findByCode(course.getStudentId(), course.getCode());

        if (byName != null || byCode != null) {
            return null;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Course (studentId, code, name, points) VALUES (?,?,?,?)");
            st.setInt(1, course.getStudentId());
            st.setString(2, course.getCode());
            st.setString(3, course.getName());
            st.setInt(4, course.getPoints());
            st.executeUpdate();
        }
        return course;
    }

    /**
     * Kurssin poisto
     * @param courseId poistettavan kurssin id
     * @throws SQLException 
     */
    @Override
    public void delete(Integer courseId) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Course WHERE id = ?");
            st.setInt(1, courseId);
            st.executeUpdate();
        }
    }

    /**
     * Kurssin etsiminen nimen ja opiskelijan id:n perusteella
     * @param studentId opiskelijan id
     * @param courseName kurssin nimi
     * @return null jos kurssia ei löydy, tai kurssi
     * @throws SQLException 
     */
    @Override
    public Course findByName(Integer studentId, String courseName) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Course WHERE studentId = ? AND name = ?");
            st.setInt(1, studentId);
            st.setString(2, courseName);

            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }
            return new Course(rs.getInt("id"), rs.getInt("studentId"), rs.getString("code"), rs.getString("name"), rs.getInt("points"));
        }
    }

    /**
     * Kurssin etsiminen koodin ja opiskelijan id:n perusteella
     * @param studentId opiskelijan id
     * @param courseCode kurssikoodi
     * @return null jos kurssia ei löydy, tai kurssi
     * @throws SQLException 
     */
    @Override
    public Course findByCode(Integer studentId, String courseCode) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Course WHERE studentId = ? AND code = ?");
            st.setInt(1, studentId);
            st.setString(2, courseCode);

            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }
            return new Course(rs.getInt("id"), rs.getInt("studentId"), rs.getString("code"), rs.getString("name"), rs.getInt("points"));
        }
    }

    /**
     * Opiskelijan kurssien poisto
     * @param studentId opiskelijan id
     * @throws SQLException 
     */
    @Override
    public void deleteForStudent(Integer studentId) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Course WHERE studentId = ?");
            st.setInt(1, studentId);
            st.executeUpdate();
        }
    }

}
