/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DAO;

import Server.DTO.*;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;

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

    public static float getGPA(Student student) throws SQLException {
        Connection con = DBConnection.getConnection();
        ResultSet result;
        PreparedStatement statement = con.prepareCall("SELECT student_gpa FROM Students where student_id = ?");
        statement.setInt(1, student.getStudent_id());
        result = statement.executeQuery();
        if (result.next()) {
            student.setGpa(result.getFloat(1));
            statement.close();
            return student.getGpa();
        } else {
            statement.close();
            return 0.0f;
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
                students.add(new Student(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getInt(6), result.getString(7), result.getString(8), result.getInt(9), result.getFloat(10), result.getInt(11), result.getString(12), result.getString(13), getPhoneNumbers(result.getInt(1))));
            }
            return students;
        }
        statement.close();
        return students;
    }

    private static ArrayList<Integer> getPhoneNumbers(int studentid) throws SQLException {
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
        statement.close();
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

    public static ArrayList<Course> getCourses() throws SQLException {
        ArrayList<Course> courses = new ArrayList<Course>();
        ResultSet result;
        Connection con = DBConnection.getConnection();
        PreparedStatement statement = con.prepareCall("select * from courses", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        result = statement.executeQuery();
        if (result.next()) {
            result.previous();
            while (result.next()) {
                courses.add(new Course(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4), new Department(result.getString(5))));
            }
            return courses;
        }
        statement.close();
        return courses;
    }

    public static ArrayList<String> getGrades() throws SQLException {
        ArrayList<String> grades = new ArrayList<String>();
        ResultSet result;
        Connection con = DBConnection.getConnection();
        PreparedStatement statement = con.prepareCall("select GRADE_LETTER from grades");
        result = statement.executeQuery();
        if (result.next()) {
            grades.add(result.getString(1));
            while (result.next()) {
                grades.add(result.getString(1));
            }
            return grades;
        }
        statement.close();
        return grades;
    }

    public static ArrayList<Enrollment> getEnrollment(Student student) throws SQLException {
        ArrayList<Enrollment> enrollments = new ArrayList<Enrollment>();
        ResultSet result;
        Connection con = DBConnection.getConnection();
        PreparedStatement statement = con.prepareCall("select * from enrollment where student_id = ?");
        statement.setInt(1, student.getStudent_id());
        result = statement.executeQuery();
        if (result.next()) {
            enrollments.add(new Enrollment(student, getCourseData(result.getInt(2)), result.getString(3), result.getString(4)));
            while (result.next()) {
                enrollments.add(new Enrollment(student, getCourseData(result.getInt(2)), result.getString(3), result.getString(4)));
            }
            return enrollments;
        }
        statement.close();
        return enrollments;
    }

    private static Course getCourseData(int course_id) throws SQLException {
        Course course = null;
        ResultSet result;
        Connection con = DBConnection.getConnection();
        PreparedStatement statement = con.prepareCall("select * from courses where course_id = ? ");
        statement.setInt(1, course_id);

        result = statement.executeQuery();
        if (result.next()) {
            course = new Course(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4), new Department(result.getString(5)));
        }
        statement.close();
        return course;
    }

    public static ArrayList<Course> getAvailableCourses(Student student) throws SQLException {
        ArrayList<Course> courses = new ArrayList<Course>();
        Connection con = DBConnection.getConnection();
        CallableStatement cstmt = con.prepareCall("{ call get_available_courses(?,?) }");
        cstmt.setInt(1, student.getStudent_id());
        cstmt.registerOutParameter(2, OracleTypes.CURSOR);
        cstmt.execute();

        ResultSet result = (ResultSet) cstmt.getObject(2);
        while (result.next()) {
            courses.add(new Course(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4), new Department(result.getString(5))));
        }
        cstmt.close();
        return courses;
    }

    public static ArrayList<Prerequisite> getPrerequisite(Course course) throws SQLException {
        ArrayList<Prerequisite> prerequisites = new ArrayList<Prerequisite>();
        Course selected;
        Connection con = DBConnection.getConnection();
        ResultSet result;
        PreparedStatement statement = con.prepareCall("select c.* , 'false' from courses c where course_id not in ( select CASE WHEN course_id = ? THEN PREREQUISITE_ID WHEN PREREQUISITE_ID = ? THEN course_id  else 0 END AS ids from COURSE_PREREQUISITES ) and course_id != ?\n"
                + "union all\n"
                + "select c.* , 'true' from courses c where course_id in ( select PREREQUISITE_ID from  COURSE_PREREQUISITES where course_id = ?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
        statement.setInt(1, course.getId());
        statement.setInt(2, course.getId());
        statement.setInt(3, course.getId());
        statement.setInt(4, course.getId());
        
        result = statement.executeQuery();
        if (result.next()) {
            result.previous();
            while (result.next()) {
                selected = new Course(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4), new Department(result.getString(5)));
                prerequisites.add(new Prerequisite(selected, result.getBoolean(6)));
            }
            
        }
         statement.close();
        return prerequisites;
    }
}
