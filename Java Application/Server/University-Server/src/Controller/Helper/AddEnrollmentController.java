/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Helper;

import Server.DAO.DataModification;
import Server.DAO.DataRetrieval;
import Server.DTO.Course;
import Server.DTO.Enrollment;
import Server.DTO.Student;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AddEnrollmentController implements Initializable {

    @FXML
    private Label titlelabel;
    @FXML
    private TextField yeattf;
    @FXML
    private ChoiceBox<String> gradecb;
    @FXML
    private ChoiceBox<Course> coursescb;
    @FXML
    private Button addbtn;
    private Student clicked;
     private ArrayList<Course> receivedlist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            try {
                gradecb.getItems().addAll(DataRetrieval.getGrades());
                loadData();
                
                addbtn.setOnAction(event -> 
                {
                    if(checkData())
                    {
                        try {
                            Enrollment enrollment = new Enrollment(clicked , coursescb.getValue() , gradecb.getSelectionModel().getSelectedItem(),yeattf.getText() );
                            DataModification.modifyEnrollment(enrollment, false);
                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
                        } catch (SQLException ex) {
                            Logger.getLogger(AddEnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     }
                    else
                    {
                        //alert
                    }
                });
                
                yeattf.setOnKeyTyped(new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    if(yeattf.getText().length() > 4 || !Character.isDigit(event.getCharacter().charAt(0)))
                        event.consume();
                }});
                
            } catch (SQLException ex) {
                Logger.getLogger(AddEnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    private void loadData(){
         try {
            receivedlist = DataRetrieval.getAvailableCourses(clicked);
            System.out.println(receivedlist.size());
            ObservableList<Course> ava_courses = FXCollections.observableArrayList(receivedlist);
            coursescb.setItems(ava_courses);
        } catch (SQLException ex) {
            Logger.getLogger(AddEnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private boolean checkData(){
         if(coursescb.getSelectionModel().getSelectedItem() != null && yeattf.getText() != null)
            return true;
         else
            return false;
    }
     public void setClicked(Student clicked) {
        this.clicked = clicked;
    }
}
