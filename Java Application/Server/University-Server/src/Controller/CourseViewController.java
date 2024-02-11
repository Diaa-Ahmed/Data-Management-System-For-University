/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Helper.AddCourseController;
import Controller.Helper.PrerequisiteController;
import Server.DAO.DataModification;
import Server.DAO.DataRetrieval;
import Server.DTO.Course;
import Server.DTO.Department;
import Server.DTO.Student;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class CourseViewController implements Initializable {

    @FXML
    private TableView<Course> courselist;
    @FXML
    private TableColumn<Course, String> cnamecol;
    @FXML
    private TableColumn<Course, Integer> credithourscol;
    @FXML
    private TableColumn<Course, Integer> mlevelcol;
    @FXML
    private TableColumn<Course, String> dnamecol;
    @FXML
    private Button addbtn;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deletebtn;
    
    private Course selected_Course;
    private ArrayList<Course> receivedlist;
    private ObservableList<Course> CourseObservableList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
         addbtn.setOnAction(event -> {
             try {
                    Stage popup = new Stage();
                    popup.initModality(Modality.APPLICATION_MODAL);
                    Parent root = FXMLLoader.load(getClass().getResource("/View/Helper/AddCourse.fxml"));
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
                 if (!courselist.getSelectionModel().isEmpty()) {
                    selected_Course = courselist.getSelectionModel().getSelectedItems().get(0);
                    Stage popup = new Stage();
                    popup.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Helper/AddCourse.fxml"));
                    Parent temp = loader.load();
                    AddCourseController ctrl = loader.getController();
                    ctrl.setCourse(selected_Course);
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
                    alert.setContentText("Please Choose Course");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.setTitle("Updating Course !!");
                    alert.showAndWait();
                 }
             } catch (IOException ex) {
                 Logger.getLogger(CourseViewController.class.getName()).log(Level.SEVERE, null, ex);
             }
         });
         
         deletebtn.setOnAction(event -> {
            if (!courselist.getSelectionModel().isEmpty()) {
                selected_Course = courselist.getSelectionModel().getSelectedItems().get(0);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("You're about to remove " + selected_Course.getName());
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.setTitle("Remove Course");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            if(DataModification.deletion("course", selected_Course))
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
                alert.setContentText("Please Choose Course");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.setTitle("Removing Course !!");
                alert.showAndWait();
            }
        });
         
        cnamecol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getName()));

        credithourscol.setCellValueFactory(cellData
                -> new SimpleIntegerProperty(cellData.getValue().getCredit_hours()).asObject());
        
        mlevelcol.setCellValueFactory(cellData
                -> new SimpleIntegerProperty(cellData.getValue().getMinimum_level()).asObject());

        dnamecol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getDepartment().getId()));
        loadData();
        
        courselist.setRowFactory(new Callback<TableView<Course>, TableRow<Course>>() {
        @Override
        public TableRow<Course> call(TableView<Course> st) {
            TableRow<Course> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        try {
                            selected_Course = courselist.getSelectionModel().getSelectedItems().get(0);
                            Stage popup = new Stage();
                            popup.initModality(Modality.APPLICATION_MODAL);
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Helper/Prerequisite.fxml"));
                            Parent temp = loader.load();
                            PrerequisiteController ctrl = loader.getController();
                            ctrl.setClicked(selected_Course);
                            Scene scene = new Scene(temp);
                            popup.setResizable(false);
                            popup.setScene(scene);
                            popup.setOnCloseRequest(evt-> {
                                loadData();
                            });
                            popup.show();
                        } catch (IOException ex) {
                            Logger.getLogger(CourseViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                }
            });
            return row;
        }
    });


    }    
     private void loadData(){
        try {
            receivedlist = DataRetrieval.getCourses();
            CourseObservableList = FXCollections.observableArrayList(receivedlist);
            courselist.setItems(CourseObservableList);
        } catch (SQLException ex) {
            Logger.getLogger(StudentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
