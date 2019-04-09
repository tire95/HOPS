/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;


/**
 *
 * @author timo
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

//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof Course)) {
//            return false;
//        }
//        Course other = (Course) obj;
//        
//        if (this.id == other.getId()) {
//            return true;
//        }
//        if (this.code.equals(other.getCode())) {
//            return true;
//        }
//        return this.name.equals(other.getName());
//    }

}
