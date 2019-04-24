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
 *
 * @author timo
 */
public class SQLCourseDao implements CourseDao {

    private final Database database;

    public SQLCourseDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Course> findAllForStudent(Integer k) throws SQLException {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Course WHERE studentId = ?");
            st.setInt(1, k);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                courses.add(new Course(rs.getInt("id"), rs.getInt("studentId"), rs.getString("code"), rs.getString("name"), rs.getInt("points")));
            }
        }
        return courses;
    }

    @Override
    public Course save(Course object) throws SQLException {
        Course byName = findByName(object.getStudentId(), object.getName());
        Course byCode = findByCode(object.getStudentId(), object.getCode());

        if (byName != null || byCode != null) {
            return null;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Course (studentId, code, name, points) VALUES (?,?,?,?)");
            st.setInt(1, object.getStudentId());
            st.setString(2, object.getCode());
            st.setString(3, object.getName());
            st.setInt(4, object.getPoints());
            st.executeUpdate();
        }
        return object;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Course WHERE id = ?");
            st.setInt(1, key);
            st.executeUpdate();
        }
    }

    @Override
    public Course findByName(Integer key, String string) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Course WHERE studentId = ? AND name = ?");
            st.setInt(1, key);
            st.setString(2, string);

            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }
            return new Course(rs.getInt("id"), rs.getInt("studentId"), rs.getString("code"), rs.getString("name"), rs.getInt("points"));
        }
    }

    @Override
    public Course findByCode(Integer key, String string) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Course WHERE studentId = ? AND code = ?");
            st.setInt(1, key);
            st.setString(2, string);

            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }
            return new Course(rs.getInt("id"), rs.getInt("studentId"), rs.getString("code"), rs.getString("name"), rs.getInt("points"));
        }
    }

    @Override
    public void deleteForStudent(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Course WHERE studentId = ?");
            st.setInt(1, key);
            st.executeUpdate();
        }
    }

}
