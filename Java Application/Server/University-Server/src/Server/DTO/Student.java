/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DTO;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class Student {
    
    private int student_id;
    private String fname;
    private String lname;
    private String city ;
    private String street;
    private int building_no;
    private String email;
    private String password;
    private int level; 
    private float gpa;
    private int total_hours;
    private String major;
    private String minor;
    
    
    private ArrayList<Integer> phonenumbers;

    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Student(int student_id, String fname, String lname, String city, String street, int building_no, String email, String password, int level, float gpa, int total_hours, String major, String minor, ArrayList<Integer> phonenumbers) {
        this.student_id = student_id;
        this.fname = fname;
        this.lname = lname;
        this.city = city;
        this.street = street;
        this.building_no = building_no;
        this.email = email;
        this.password = password;
        this.level = level;
        this.gpa = gpa;
        this.total_hours = total_hours;
        this.major = major;
        this.minor = minor;
        this.phonenumbers = phonenumbers;
    }

    public Student(String fname, String lname, String city, String street, int building_no, String email, String password, int level, float gpa, int total_hours, String major, String minor, ArrayList<Integer> phonenumbers) {
        this.fname = fname;
        this.lname = lname;
        this.city = city;
        this.street = street;
        this.building_no = building_no;
        this.email = email;
        this.password = password;
        this.level = level;
        this.gpa = gpa;
        this.total_hours = total_hours;
        this.major = major;
        this.minor = minor;
        this.phonenumbers = phonenumbers;
    }

   

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuilding_no() {
        return building_no;
    }

    public void setBuilding_no(int building_no) {
        this.building_no = building_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public int getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(int total_hours) {
        this.total_hours = total_hours;
    }

    public ArrayList<Integer> getPhonenumbers() {
        return phonenumbers;
    }

    public void setPhonenumbers(ArrayList<Integer> phonenumbers) {
        this.phonenumbers = phonenumbers;
    }  
}