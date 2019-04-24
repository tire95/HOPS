package domain;

/**
 * Yksittäistä kurssisuoritusta kuvaava luokka
 */

public class Course {

    private final int id;
    private final int studentId;
    private final String code;
    private final String name;
    private final int points;

    public Course(int i, int si, String c, String n, int p) {
        this.id = i;
        this.studentId = si;
        this.code = c;
        this.name = n;
        this.points = p;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
    
    @Override
    public String toString() {
        return "Kurssikoodi: " + code + ", kurssinimi: " + name + ", opintopisteitä: " + points;
    }

}
