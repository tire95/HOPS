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
 *
 * @author timo
 */
public class SQLStudentDao implements StudentDao {

    private final Database database;

    public SQLStudentDao(Database database) {
        this.database = database;
    }

    @Override
    public Student save(Student object) throws SQLException {
        if (findByUsername(object.getUsername()) != null) {
            return null;
        }
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Student (name, username) VALUES (?,?)");
            st.setString(1, object.getName());
            st.setString(2, object.getUsername());
            st.executeUpdate();
        }
        return findByUsername(object.getUsername());
    }

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

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Student WHERE id = ?");
            st.setInt(1, key);
            st.executeUpdate();
        }
    }

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
