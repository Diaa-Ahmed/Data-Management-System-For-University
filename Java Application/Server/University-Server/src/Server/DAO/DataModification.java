/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DAO;

import Server.DTO.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class DataModification {

    public static boolean modifyStudent(Student student, boolean update) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement statement;

        if (update) {
            statement = con.prepareStatement("UPDATE students SET first_name = ?, last_name = ?, city = ?, street = ?, building_no = ?, email = ?, account_password = ?, student_level = ?, student_gpa = ?, total_hours = ?, major = ?, minor = ? WHERE student_id = ?");
        } else {
            statement = con.prepareStatement("INSERT INTO students (first_name, last_name, city, street, building_no, email, account_password, student_level, student_gpa, total_hours, major, minor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }

        statement.setString(1, student.getFname());
        statement.setString(2, student.getLname());
        statement.setString(3, student.getCity());
        statement.setString(4, student.getStreet());
        statement.setInt(5, student.getBuilding_no());
        statement.setString(6, student.getEmail());
        statement.setString(7, student.getPassword());
        statement.setInt(8, student.getLevel());
        statement.setFloat(9, student.getGpa());
        statement.setInt(10, student.getTotal_hours());
        statement.setString(11, student.getMajor());
        statement.setString(12, student.getMinor());

        // For UPDATE operation, set student_id parameter
        if (update) {
            statement.setInt(13, student.getStudent_id());
        }

        int out = statement.executeUpdate();
        statement.close();

        if (out == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean modifyCourse(Course course, boolean update) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement statement;
        if (update) {
            statement = con.prepareCall("update Courses set course_name = ? , credit_hours = ? , minimum_level = ? , department_id = ? where course_id = ?");
            statement.setInt(5, course.getId());
        } else {
            statement = con.prepareCall("INSERT INTO Courses (course_name, credit_hours, minimum_level, department_id) VALUES (?,?,?,?)");
        }

        statement.setString(1, course.getName());
        statement.setInt(2, course.getCredit_hours());
        statement.setInt(3, course.getMinimum_level());
        statement.setString(4, course.getDepartment().getId());
        //System.out.println(c);
        int out = statement.executeUpdate();
        statement.close();
        if (out == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean modifyDepartment(Department department, boolean update) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement statement;
        if (update) {
            statement = con.prepareCall("update departments set department_name = ? where department_id = ? ");
        } else {
            statement = con.prepareCall("INSERT INTO departments (department_name ,department_id) VALUES (?,?)");
        }
        statement.setString(2, department.getId());
        statement.setString(1, department.getName());
        int out = statement.executeUpdate();
        statement.close();
        if (out == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean modifyEnrollment(Enrollment enrollment, boolean update) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement statement;
        if (update) {
            statement = con.prepareCall("update enrollment set grade_letter = ? where student_id = ? and course_id = ? and academic_year = ?");
            statement.setString(1, enrollment.getGrade());
            statement.setInt(2, enrollment.getStudent().getStudent_id());
            statement.setInt(3, enrollment.getCourse().getId());
        } else {
            statement = con.prepareCall("INSERT INTO enrollment VALUES (?,?,?,?)");
            statement.setInt(1, enrollment.getStudent().getStudent_id());
            statement.setInt(2, enrollment.getCourse().getId());
            statement.setString(3, enrollment.getGrade());
        }
        statement.setString(4, enrollment.getYear());
        int out = statement.executeUpdate();
        statement.close();
        if (out == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean addPrerequisite(Course course, ArrayList<Prerequisite> prerequisites) throws SQLException {
        Connection con = DBConnection.getConnection();
        ResultSet result;
        PreparedStatement statement;
        statement = con.prepareCall("INSERT INTO COURSE_PREREQUISITES VALUES(?,?)");
        for (Prerequisite p : prerequisites) {
            if(p.getStatus())
            {
            statement.setInt(1, course.getId());
            statement.setInt(2, p.getCourse().getId());
            int out = statement.executeUpdate();
        
            }
        }
        statement.close();
        return true;
    }

    public static boolean deletion(String type, Object data) throws SQLException {
        Connection con = DBConnection.getConnection();
        PreparedStatement statement;
        int out;
        switch (type) {
            case "student":
                Student student = (Student) data;
                statement = con.prepareCall("delete from students where student_id = ?");
                statement.setInt(1, student.getStudent_id());
                break;
            case "course":
                Course course = (Course) data;
                statement = con.prepareCall("delete from courses where course_id = ?");
                statement.setInt(1, course.getId());
                break;
            case "department":
                Department department = (Department) data;
                statement = con.prepareCall("delete from departments where department_id = ? ");
                statement.setString(1, department.getId());
                break;
            case "enrollment":
                Enrollment enrollment = (Enrollment) data;
                statement = con.prepareCall("delete from Enrollment where student_id = ? and course_id = ? and academic_year = ?");
                statement.setInt(1, enrollment.getStudent().getStudent_id());
                statement.setInt(2, enrollment.getCourse().getId());
                statement.setString(3, enrollment.getYear());
                break;
            case "prerequisite":
                Course prerequisite = (Course) data;
                statement = con.prepareCall("delete from COURSE_PREREQUISITES where course_id = ?");
                statement.setInt(1, prerequisite.getId());
                break;
            default:
                return false;
        }
        out = statement.executeUpdate();
        statement.close();
        if (out == 1) {
            return true;
        } else {
            return false;
        }
    }
}
