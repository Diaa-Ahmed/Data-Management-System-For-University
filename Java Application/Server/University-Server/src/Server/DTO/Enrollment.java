/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DTO;

import java.sql.Date;

/**
 *
 * @author DELL
 */
public class Enrollment {
    private Student student ;
    private Course course;
    private String grade;
    private String year ;

    public Enrollment(Student student, Course course, String grade, String year) {
        this.student = student;
        this.course = course;
        this.grade = grade;
        this.year = year;
    }

    public Enrollment(Student student, Course course, String year) {
        this.student = student;
        this.course = course;
        this.year = year;
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
}
