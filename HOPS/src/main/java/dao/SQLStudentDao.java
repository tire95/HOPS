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
public class SQLStudentDao implements StudentDao<Student, Integer, String> {

    private final Database database;

    public SQLStudentDao(Database database) {
        this.database = database;
    }

//    @Override
//    public Student findById(Integer key) throws SQLException {
//        Connection conn = database.getConnection();
//        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Student WHERE id = ?");
//        stmt.setInt(1, key);
//
//        ResultSet rs = stmt.executeQuery();
//        if (!rs.next()) {
//            return null;
//        }
//        Student s = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("username"));
//
//        stmt.close();
//        rs.close();
//        conn.close();
//        return s;
//    }

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

}
