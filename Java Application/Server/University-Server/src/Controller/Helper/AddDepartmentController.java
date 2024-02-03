/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Helper;

import Server.DAO.DataModification;
import Server.DTO.Course;
import Server.DTO.Department;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class AddDepartmentController implements Initializable {

    @FXML
    private Label titlelabel;
    @FXML
    private TextField dnametf;
    @FXML
    private Button addbtn;
    @FXML
    private TextField did;
    private Department department;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->{
            if(department != null)
            {
                titlelabel.setText("Update Department");
                addbtn.setText("Update");
                dnametf.setText(department.getName());
                did.setText(department.getId());
                did.setDisable(true);
            }
            // Only two chars
            did.setOnKeyTyped(new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    if(did.getText().length() >=2 || !Character.isLetter(event.getCharacter().charAt(0)))
                        event.consume();
                }});
            // Make ID upper case
            did.textProperty().addListener(new ChangeListener(){
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    String temp =(String)newValue;
                    did.setText(temp.toUpperCase());
                }
               });
             addbtn.setOnAction(event -> {
                if(dnametf.getText().equals(null) || did.getText().length() < 2 ) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please Enter Valid Data");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.showAndWait();
                }
                else
                {
                    if(department != null){
                        if(!dnametf.getText().equals(department.getName()) ) 
                        {
                            try {
                                Department newdepartment = new Department( did.getText() , dnametf.getText() );
                                DataModification.modifyDepartment(newdepartment, true);
                            } catch (SQLException ex) {
                                Logger.getLogger(AddCourseController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        // else and put alert to inform user that no changes happened
                    }
                    else{
                        try {
                               Department newdepartment = new Department( did.getText() , dnametf.getText() );
                                DataModification.modifyDepartment(newdepartment, false);
                                
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

    public void setDepartment(Department department) {
        this.department = department;
    }
    
}
