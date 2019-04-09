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
        
//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof Student)) {
//            return false;
//        }
//        
//        Student other = (Student) obj;
//        if (this.id == other.getId()) {
//            return true;
//        }
//        return this.username.equals(other.getUsername());
//    }

    
    
}
