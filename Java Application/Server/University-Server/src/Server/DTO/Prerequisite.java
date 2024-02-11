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
public class Prerequisite {
    private Course course;
    private boolean status;

    public Prerequisite(Course course, boolean status) {
        this.course = course;
        this.status = status;
    }
    
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
