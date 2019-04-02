/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dao.*;
import database.Database;
import domain.*;

/**
 *
 * @author timo
 */
public class HOPSUi {

    private HOPSService HOPSService;
    
    public HOPSUi() {
        Database database = new Database("jdbc:sqlite:tietokanta.db");
        SQLStudentDao sDao = new SQLStudentDao(database);
        SQLCourseDao cDao = new SQLCourseDao(database);
        this.HOPSService = new HOPSService(sDao, cDao);
    }

    public static void main(String[] args) {
        HOPSUi ui = new HOPSUi();
        System.out.println("Keskeneräinen versio HOPS-ohjelmasta.");
        System.out.println("Ainoa tuettu ominaisuus on uuden opiskelijan luonti.");
        System.out.print("Järjestelmässä olevat opiskelijat: ");
        
    }
}
