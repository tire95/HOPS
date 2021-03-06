package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Tietokantaan yhdistämisestä vastaava luokka
 */
public final class Database {
    
    private final String databaseAddress;

    /**
     * Konstruktori (ja mahdollinen tietokannan alustus)
     * @param databaseAddress tietokannan osoite
     * @throws SQLException
     */
    public Database(String databaseAddress) throws SQLException {
        this.databaseAddress = databaseAddress;
        try (Connection conn = getConnection()) {
            PreparedStatement st = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Student (id integer PRIMARY KEY, name varchar(50), username varchar(20))");
            PreparedStatement st2 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Course (id integer PRIMARY KEY, studentId integer, code varchar(10), name varchar(50), points integer, FOREIGN KEY (studentId) REFERENCES Student(id))");
            st.executeUpdate();
            st2.executeUpdate();
        }
    }

    /**
     * Yhteyden luonti tietokantaan
     * @return yhteys tietokantaan
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
}

