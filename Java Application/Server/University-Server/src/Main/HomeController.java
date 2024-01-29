/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Server.DAO.DataRetrieval;
import Server.DTO.Student;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class HomeController implements Initializable {

    @FXML
    private Button button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        button.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            Student student = new Student("mego@gmail.com", "123");
            try {
                DataRetrieval.login(student);
            } catch (SQLException ex) {
                System.out.println("Can't Connect");
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
