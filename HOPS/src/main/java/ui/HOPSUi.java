/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dao.*;
import database.Database;
import domain.*;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author timo
 */
public class HOPSUi {

    private HOPSService HOPSService;
    private Scanner reader;

    public HOPSUi() {
        Database database = new Database("jdbc:sqlite:HOPSDatabase.db");
        SQLStudentDao sDao = new SQLStudentDao(database);
        SQLCourseDao cDao = new SQLCourseDao(database);
        this.HOPSService = new HOPSService(sDao, cDao);
        this.reader = new Scanner(System.in);
    }

    public void start() throws SQLException {
        System.out.println("Keskeneräinen versio HOPS-ohjelmasta.");
        System.out.println("Ainoat tuetut ominaisuudet ovat uuden opiskelijan luonti ja opiskelijoiden tulostus.");
        OUTER:
        while (true) {
            printOptions();
            System.out.print("Mitä haluat tehdä? ");
            int order = readUserInput();
            System.out.println("");
            switch (order) {
                case 1:
                    printStudents();
                    break;
                case 2:
                    saveNewStudent();
                    break;
                case 3:
                    System.out.println("Hyvää päivänjatkoa!");
                    break OUTER;
                default:
                    System.out.println("Ei kelpo komento.");
                    break;
            }
            System.out.println("");
        }
    }

    public void printStudents() throws SQLException {
        System.out.println("Järjestelmässä olevat opiskelijat: ");
        HOPSService.printAllStudents();
    }

    public void saveNewStudent() throws SQLException {
        System.out.println("Lisätään uusi opiskelija: ");
        System.out.print("Nimi: ");
        String name = this.reader.nextLine();
        System.out.print("Käyttäjänimi: ");
        String uName = reader.nextLine();
        boolean created = HOPSService.createNewUser(name, uName);
        if (!created) {
            System.out.println("Käyttäjätunnuksen täytyy olla uniikki.");
        } else {
            System.out.println("Uusi opiskelija lisätty");
        }
    }

    public void printOptions() {
        System.out.println("1: tulosta opiskelijat.");
        System.out.println("2: lisää uusi opiskelija.");
        System.out.println("3: poistu ohjelmasta.");
    }

    public int readUserInput() {
        int order = Integer.parseInt(this.reader.nextLine());
        return order;
    }

    public static void main(String[] args) throws SQLException {
        HOPSUi ui = new HOPSUi();
        ui.start();
    }
}
