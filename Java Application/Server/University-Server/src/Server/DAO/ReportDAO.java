/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DAO;

import Server.DTO.Course;
import Server.DTO.CourseStat;
import Server.DTO.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class ReportDAO {
     public static int numberOfStudents(Course course) throws SQLException {
        int num =0;
        ResultSet result;
        Connection con = DBConnection.getConnection();
        PreparedStatement statement = con.prepareCall("select count( distinct student_id) from enrollment where course_id = ? ");
        statement.setInt(1, course.getId());

        result = statement.executeQuery();
        if (result.next()) 
            num = result.getInt(1);
        statement.close();
        return num;
    }
    public static float averageGPA(Course course) throws SQLException {
        float avg =0;
        ResultSet result;
        Connection con = DBConnection.getConnection();
        PreparedStatement statement = con.prepareCall("select Average_GPA_Course(?)from dual ");
        statement.setInt(1, course.getId());

        result = statement.executeQuery();
        if (result.next()) 
            avg = result.getFloat(1);
        statement.close();
        return avg;
    }
    
    public static ArrayList<CourseStat> getStat(Course course) throws SQLException{
        ArrayList<CourseStat> stat = new ArrayList<CourseStat>() ;
        ResultSet result;
        Connection con = DBConnection.getConnection();
        PreparedStatement statement = con.prepareCall("select gs.grade_letter , nvl(num , 0)  from grades gs left join (\n" +
" select grade_letter , count(*) as num from enrollment where grade_letter is not null and course_id = ? group by grade_letter ) inn on gs.grade_letter = inn.grade_letter order by gs.grade_letter" ,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        statement.setInt(1, course.getId());

        result = statement.executeQuery();
        if (result.next()) {
            result.previous();
            while (result.next()) {
                stat.add(new CourseStat(result.getString(1),result.getInt(2)));
            }
        }
        statement.close();
        return stat;
    }
}
