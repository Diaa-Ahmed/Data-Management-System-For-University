/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Helper;

import Controller.StudentViewController;
import Server.DAO.DataModification;
import Server.DAO.DataRetrieval;
import Server.DTO.Course;
import Server.DTO.Department;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AddCourseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Course course ;
    @FXML
    private Label titlelabel;
    @FXML
    private TextField cnametf;
    @FXML
    private ChoiceBox<String> department;
    @FXML
    private ChoiceBox<Integer> credithours;
    @FXML
    private ChoiceBox<Integer> level;
    @FXML
    private Button addbtn;
    
    private ObservableList<String> deptObservableList;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->{
            loadDepartments();
            loadLevels();
            creditHours();
            if(course != null)
            {
                titlelabel.setText("Update Course"); 
                addbtn.setText("Update");
                cnametf.setText(course.getName());
                department.setValue(course.getDepartment().getId());
                credithours.setValue(course.getCredit_hours());
                level.setValue(course.getMinimum_level());
            }
            else
            {
               
            }
            addbtn.setOnAction(event -> {
                if(cnametf.getText().equals(null) || department.getSelectionModel().isEmpty() 
                   || credithours.getSelectionModel().isEmpty() || level.getSelectionModel().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please Enter All Data");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.showAndWait();
                }
                else
                {
                    if(course!= null){
                        if(checkChanged()) 
                        {
                            try {
                                Course newcourse = new Course(course.getId(),cnametf.getText().trim() , credithours.getSelectionModel().getSelectedItem() ,
                                level.getSelectionModel().getSelectedItem() , new Department(department.getSelectionModel().getSelectedItem()));
                                // Update Item
                                DataModification.modifyCourse(newcourse, true);
                            } catch (SQLException ex) {
                                Logger.getLogger(AddCourseController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    else{
                        try {
                                Course newcourse = new Course(cnametf.getText().trim() , credithours.getSelectionModel().getSelectedItem() ,
                                level.getSelectionModel().getSelectedItem() , new Department(department.getSelectionModel().getSelectedItem()));
                                // Add Item
                                DataModification.modifyCourse(newcourse, false);
                            } catch (SQLException ex) {
                                Logger.getLogger(AddCourseController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    }
                   Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                   window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
                }
            });
        });
    }    

    public void setCourse(Course course) {
        this.course = course;
    }
    private void loadDepartments(){
        try {
            ArrayList<Department> dlist = DataRetrieval.getDepartments();
            ArrayList<String> deptids = new ArrayList<String>();
            for(Department d : dlist)
                deptids.add(d.getId());
            deptObservableList = FXCollections.observableArrayList(deptids);
            department.setItems(deptObservableList);
        } catch (SQLException ex) {
            Logger.getLogger(StudentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadLevels(){
        ArrayList<Integer> levelsdata = new ArrayList<Integer>();
        levelsdata.add(1);
        levelsdata.add(2);
        levelsdata.add(3);
        levelsdata.add(4);
        ObservableList<Integer> lev = FXCollections.observableArrayList(levelsdata);
        level.setItems(lev);     
    }
    private void creditHours(){
        ArrayList<Integer> cHours = new ArrayList<Integer>();
        cHours.add(2);
        cHours.add(3);
        ObservableList<Integer> chs = FXCollections.observableArrayList(cHours);
        credithours.setItems(chs);     
    }
    private boolean checkChanged(){     
        if(cnametf.getText().equals(course.getName()) && department.getSelectionModel().getSelectedItem().equals(course.getDepartment().getId())
          &&credithours.getSelectionModel().getSelectedItem().equals(course.getCredit_hours())
          &&level.getSelectionModel().getSelectedItem().equals(course.getMinimum_level()))
            return false;
         else
            return true;
    } 
}
