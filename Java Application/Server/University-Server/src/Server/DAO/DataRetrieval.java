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
        Connection con = DBConnection.getConnection();
        ResultSet result;
        PreparedStatement statement = con.prepareCall("select * from students where email = ? and account_password = ? ");
        statement.setString(1, student.getEmail());
        statement.setString(2, student.getPassword());

        result = statement.executeQuery();
        if (result.next()) {
           /* user.setUserid(result.getInt(1));
            user.setFullname(result.getString(3));
            user.setUserphoto(result.getString(4));
            user.setDateOfBirth(result.getDate(6));*/
            System.out.println("login Successfully !!\n" + "First name : " + result.getString(2)
            + " .. Last name : " + result.getString(3));
            statement.close();
            return true;
        } else {
            statement.close();
            return false;
        }
    }

}
