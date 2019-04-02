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
    private String code;
    private String name;
    private int points;
    
    public Course(String c, String n, int p) {
        this.code = c;
        this.name = n;
        this.points = p;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getPoints() {
        return this.points;
    }
    
}
