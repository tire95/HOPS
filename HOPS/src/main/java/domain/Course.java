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

    /**
     * Konstruktori
     * @param i id
     * @param si opiskelijan id
     * @param c kurssikoodi
     * @param n kurssinimi
     * @param p opintopisteet
     */
    public Course(int i, int si, String c, String n, int p) {
        this.id = i;
        this.studentId = si;
        this.code = c;
        this.name = n;
        this.points = p;
    }

    /**
     * id:n haku
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Opiskelijan id:n haku
     * @return opiskelijan id
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Kurssikoodin haku
     * @return kurssikoodi
     */
    public String getCode() {
        return code;
    }

    /**
     * Nimen haku
     * @return nimi
     */
    public String getName() {
        return name;
    }

    /**
     * Opintopisteiden haku
     * @return opintopisteet
     */
    public int getPoints() {
        return points;
    }
    
    @Override
    public String toString() {
        return "Kurssikoodi: " + code + ", kurssinimi: " + name + ", opintopisteitä: " + points;
    }

}
