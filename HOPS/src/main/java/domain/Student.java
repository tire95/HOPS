package domain;


/**
 * Käyttäjää/opiskelijaa kuvaava luokka
 */

public class Student {
    private final int id;
    private final String name;
    private final String username;
    
    /**
     * Konstruktori
     * @param i id
     * @param n nimi
     * @param u käyttäjänimi
     */
    public Student(int i, String n, String u) {
        this.id = i;
        this.name = n;
        this.username = u;
    }
    
    /**
     * id:n haku
     * @return id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Nimen haku
     * @return nimi
     */
    public String getName() {
        return name;
    }
    
    /**
     * Käyttäjätunnuksen haku
     * @return käyttäjätunnus
     */
    public String getUsername() {
        return username;
    }
}
