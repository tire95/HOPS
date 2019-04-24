package domain;

import dao.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Sovelluslogiikasta vastaava luokka
 */
public class HOPSService {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private Student loggedIn;

    public HOPSService(StudentDao sd, CourseDao cd) {
        this.studentDao = sd;
        this.courseDao = cd;
        this.loggedIn = null;
    }

    /**
     * Uuden opiskelijan luonti
     * 
     * @param name luotavan opiskelijan nimi
     * @param username luotavan opiskelijan käyttäjänimi
     * 
     * @see dao.SQLStudentDao#save(domain.Student) 
     * 
     * @return totuusarvo, onnistuiko luotavan käyttäjän luonti
     * @throws SQLException 
     */
    public boolean createNewUser(String name, String username) throws SQLException {
        Student newStudent = studentDao.save(new Student(-1, name, username));
        return newStudent != null;
    }

    /**
     * Sisäänkirjautuminen
     * 
     * @param username sisäänkirjautuvan opiskelijan käyttäjätunnus
     * 
     * @see dao.SQLStudentDao#findByUsername(String) 
     * 
     * @return totuusarvo, onnistuiko sisäänkirjautuminen
     * @throws SQLException 
     */
    public boolean logIn(String username) throws SQLException {
        loggedIn = studentDao.findByUsername(username);
        return loggedIn != null;
    }

    /**
     * Kirjautuneen opiskelija kurssisuorituksien haku
     * 
     * @see dao.SQLCourseDao#findAllForStudent(java.lang.Integer)  
     * 
     * @return listan opiskelijan kursseista
     * @throws SQLException 
     */
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = courseDao.findAllForStudent(loggedIn.getId());
        return courses;
    }

    /**
     * Uuden kurssisuorituksen lisääminen sisäänkirjautuneelle opiskelijalle
     * 
     * @param code kurssisuorituksen kurssikoodi
     * @param name kurssisuorituksen nimi
     * @param points kurssisuorituksen opintopisteet
     * 
     * @see dao.SQLCourseDao#save(domain.Course) 
     * 
     * @return totuusarvo, onnistuiko uuden kurssisuorituksen luonti
     * @throws SQLException 
     */
    public boolean createNewCourse(String code, String name, int points) throws SQLException {
        Course newCourse = courseDao.save(new Course(-1, loggedIn.getId(), code, name, points));
        return newCourse != null;
    }
    
    /**
     * Sisäänkirjautuneen opiskelijan nimen haku
     * 
     * @see domain.Student#getName() 
     * 
     * @return sisäänkirjautuneen opiskelijan nimi
     */
    public String getLoggedInName() {
        return loggedIn.getName();
    }
    
    /**
     * Sisäänkirjautuneen opiskelijan uloskirjaus
     */
    public void logOut() {
        loggedIn = null;
    }
    
    /**
     * Tietyn opiskelijan ja tämän kurssisuoritusten poisto järjestelmästä
     * 
     * @param i opiskelijan id
     * 
     * @see dao.SQLCourseDao#deleteForStudent(java.lang.Integer) 
     * @see dao.SQLStudentDao#delete(int) 
     * 
     * @throws SQLException 
     */
    public void removeStudentAndCourses(Integer i) throws SQLException {
        courseDao.deleteForStudent(i);
        studentDao.delete(i);
    }
    
    
    /**
     * Kaikkien järjestelmässä olevien opiskelijoiden etsiminen
     * 
     * @see dao.SQLStudentDao#findAll() 
     * 
     * @return lista opiskelijoista
     * @throws SQLException 
     */
    public List<Student> getAllStudents() throws SQLException {
        return studentDao.findAll();
    }
    
    /**
     * Kurssin poistaminen
     * 
     * @param i kurssin id
     * 
     * @see dao.SQLCourseDao#delete(java.lang.Integer) 
     * 
     * @throws SQLException 
     */
    public void removeCourse(Integer i) throws SQLException {
        courseDao.delete(i);
    }

}
