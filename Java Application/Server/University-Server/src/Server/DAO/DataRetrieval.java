/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DAO;

import Server.DTO.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class DataRetrieval {

    public static boolean login(Student student) throws SQLException {
        ResultSet result;
        Connection con = DBConnection.getConnection();
        PreparedStatement statement = con.prepareCall("select * from students where email = ? and account_password = ? ");
        statement.setString(1, student.getEmail());
        statement.setString(2, student.getPassword());

        result = statement.executeQuery();
        if (result.next()) {
            System.out.println("login Successfully !!\n" + "First name : " + result.getString(2)
            + " .. Last name : " + result.getString(3));
            statement.close();
            return true;
        } else {
            statement.close();
            return false;
        }
    }
    
    public static ArrayList<Student> getStudents() throws SQLException {
        ArrayList<Student> students = new ArrayList<Student>();
        Connection con = DBConnection.getConnection();
        ResultSet result;
        PreparedStatement statement = con.prepareCall("SELECT * FROM Students", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        result = statement.executeQuery();
        if (result.next()) {
            result.previous();
            while (result.next()) {
              students.add(new Student(result.getInt(1), result.getString(2), result.getString(3), result.getString(4) , result.getString(5),result.getInt(6),result.getString(7),result.getString(8), result.getInt(9) , result.getFloat(10), result.getInt(11) , result.getString(12) , result.getString(13) , getPhoneNumbers(result.getInt(1)) ));
            }
            return students;
        }
        statement.close();
        return students;
    }
    private static ArrayList<Integer> getPhoneNumbers(int studentid) throws SQLException{
        ResultSet result;
        ArrayList<Integer> phones = new ArrayList<Integer>();
        Connection con = DBConnection.getConnection();
        PreparedStatement statement = con.prepareCall("SELECT * FROM student_phone where student_id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, studentid);
        result = statement.executeQuery();
        if (result.next()) {
            result.previous();
            while (result.next()) {
              phones.add(result.getInt(2));
            }
            return phones;
        }
        return phones;     
    }
    
    public static ArrayList<Department> getDepartments() throws SQLException {
        ArrayList<Department> departments = new ArrayList<Department>();
        Connection con = DBConnection.getConnection();
        ResultSet result;
        PreparedStatement statement = con.prepareCall("SELECT * FROM departments", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        result = statement.executeQuery();
        if (result.next()) {
            result.previous();
            while (result.next()) {
              departments.add(new Department(result.getString(1), result.getString(2)));
            }
            return departments;
        }
        statement.close();
        return departments;
    }
    public static ArrayList<Course> getCourses() throws SQLException{
        ArrayList<Course> courses = new  ArrayList<Course>();
        ResultSet result;
        Connection con = DBConnection.getConnection();
        PreparedStatement statement = con.prepareCall("select * from courses" , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        result = statement.executeQuery();
        if (result.next()) {
            result.previous();
            while (result.next()) {
              courses.add(new Course(result.getInt(1), result.getString(2) , result.getInt(3),result.getInt(4), new Department(result.getString(5))));
            }
            return courses;
        }
        statement.close();
        return courses; 
    }
}
