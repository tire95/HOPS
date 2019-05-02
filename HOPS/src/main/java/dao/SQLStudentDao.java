package dao;

import database.Database;
import domain.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * dao-rajapinnan toteuttava luokka opiskelijoiden käsittelyyn SQL-tietokannassa
 */
public class SQLStudentDao implements StudentDao {

    private final Database database;

    /**
     * Konstruktori
     * @param database tietokanta
     */
    public SQLStudentDao(Database database) {
        this.database = database;
    }

    /**
     * Opiskelijan tallennus tietokantaan
     * @param student opiskelija
     * @see dao.SQLStudentDao#findByUsername(java.lang.String) 
     * @return null, jos opiskelija löytyy jo, tai juuri luotu opiskelija
     * @throws SQLException 
     */
    @Override
    public Student save(Student student) throws SQLException {
        if (findByUsername(student.getUsername()) != null) {
            return null;
        }
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Student (name, username) VALUES (?,?)");
            st.setString(1, student.getName());
            st.setString(2, student.getUsername());
            st.executeUpdate();
        }
        return findByUsername(student.getUsername());
    }

    /**
     * Opiskelijan etsiminen käyttäjätunnuksen perusteella
     * @param username opiskelijan käyttäjätunnus
     * @return null jos opiskelijaa ei löydy, tai opiskelija
     * @throws SQLException 
     */
    @Override
    public Student findByUsername(String username) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Student WHERE username = ?");
            st.setString(1, username);

            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Student(rs.getInt("id"), rs.getString("name"), rs.getString("username"));
        }
    }

    /**
     * Opiskelijan poisto
     * @param id opiskelijan id
     * @throws SQLException 
     */
    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Student WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    /**
     * Kaikkien opiskelijoiden etsiminen
     * @return lista opiskelijoista
     * @throws SQLException 
     */
    @Override
    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Student");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                students.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("username")));
            }
        }
        return students;
    }

}
