package domain;


/**
 * Käyttäjää/opiskelijaa kuvaava luokka
 */

public class Student {
    private final int id;
    private final String name;
    private final String username;
    
    public Student(int i, String n, String u) {
        this.id = i;
        this.name = n;
        this.username = u;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getUsername() {
        return username;
    }
}
