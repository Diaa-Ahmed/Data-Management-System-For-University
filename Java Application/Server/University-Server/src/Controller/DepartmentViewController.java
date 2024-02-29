/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Helper.AddDepartmentController;
import Main.HomeController;
import Server.DAO.DataModification;
import Server.DAO.DataRetrieval;
import Server.DTO.Department;
import Server.DTO.Student;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class DepartmentViewController implements Initializable {
    
    @FXML
    private TableView<Department> departmentlist;
    @FXML
    private TableColumn<Department, String> didcol;
    @FXML
    private TableColumn<Department, String> dnamecol;
    @FXML
    private Button addbtn;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deletebtn;
    
    private Department selected_Department;
    private ArrayList<Department> receivedlist;
    private ObservableList<Department> DepartmentObservableList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
        HomeController.setDc(this);
          addbtn.setOnAction(event -> {
             try {
                    Stage popup = new Stage();
                    popup.initModality(Modality.APPLICATION_MODAL);
                    Parent root = FXMLLoader.load(getClass().getResource("/View/Helper/AddDepartment.fxml"));
                    Scene scene = new Scene(root);
                    popup.setResizable(false);
                    popup.setScene(scene);
                    popup.setOnCloseRequest(evt-> loadData());
                    popup.show();
             } catch (IOException ex) {
                 Logger.getLogger(CourseViewController.class.getName()).log(Level.SEVERE, null, ex);
             }
         });
         updatebtn.setOnAction(event -> {
             try {
                 if (!departmentlist.getSelectionModel().isEmpty()) {
                    selected_Department = departmentlist.getSelectionModel().getSelectedItems().get(0);
                    Stage popup = new Stage();
                    popup.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Helper/AddDepartment.fxml"));
                    Parent temp = loader.load();
                    AddDepartmentController ctrl = loader.getController();
                    ctrl.setDepartment(selected_Department);
                    Scene scene = new Scene(temp);
                    popup.setResizable(false);
                    popup.setScene(scene);
                    popup.setOnCloseRequest(evt-> {
                        loadData();
                    });
                    popup.show();
                 }
                 else
                 {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please Choose Department");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.setTitle("Updating Department !!");
                    alert.showAndWait();
                 }
             } catch (IOException ex) {
                 Logger.getLogger(CourseViewController.class.getName()).log(Level.SEVERE, null, ex);
             }
         });
         deletebtn.setOnAction(event -> {
            if (!departmentlist.getSelectionModel().isEmpty()) {
                selected_Department = departmentlist.getSelectionModel().getSelectedItems().get(0);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("You're about to remove " + selected_Department.getName());
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.setTitle("Remove Department");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            if(DataModification.deletion("department", selected_Department))
                                loadData();
                        } catch (SQLException ex) {
                            Logger.getLogger(StudentViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Choose Department");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.setTitle("Removing Department !!");
                alert.showAndWait();
            }
        });
        didcol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getId()));

        dnamecol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getName()));
        loadData();
        });
    }    
    public void loadData(){
        try {
            receivedlist = DataRetrieval.getDepartments();
            DepartmentObservableList = FXCollections.observableArrayList(receivedlist);
            departmentlist.setItems(DepartmentObservableList);
        } catch (SQLException ex) {
            Logger.getLogger(StudentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
