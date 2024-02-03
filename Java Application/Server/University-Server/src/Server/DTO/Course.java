/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DTO;

/**
 *
 * @author DELL
 */
public class Course {
    private int id;
    private String name;
    private int credit_hours;
    private int minimum_level;
    private Department department;

    public Course(int id, String name, int credit_hours, int minimum_level, Department department) {
        this.id = id;
        this.name = name;
        this.credit_hours = credit_hours;
        this.minimum_level = minimum_level;
        this.department = department;
    }

    public Course(String name, int credit_hours, int minimum_level, Department department) {
        this.name = name;
        this.credit_hours = credit_hours;
        this.minimum_level = minimum_level;
        this.department = department;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit_hours() {
        return credit_hours;
    }

    public void setCredit_hours(int credit_hours) {
        this.credit_hours = credit_hours;
    }

    public int getMinimum_level() {
        return minimum_level;
    }

    public void setMinimum_level(int minimum_level) {
        this.minimum_level = minimum_level;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

   
    
}