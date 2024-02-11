/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Server.DAO.DataRetrieval;
import Server.DTO.Course;
import Server.DTO.Prerequisite;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class ReportController implements Initializable {

    @FXML
    private TableView<Course> courselist;
    @FXML
    private TableColumn<Course, String> cnamecol;
    @FXML
    private TableColumn<Course, String> dnamecol;
    @FXML
    private Label totalstud;
    @FXML
    private Label avggpa;
    private ArrayList<Course> receivedlist;
    private ObservableList<Course> CourseObservableList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->{
           cnamecol.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().getName()));

            dnamecol.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().getDepartment().getId()));

            loadData();
        });
    }
    
    private void loadData() {
        // receivedlist = DataRetrieval.getPrerequisite(clicked);
//        CourseObservableList = FXCollections.observableArrayList(receivedlist);
  //      courselist.setItems(CourseObservableList);
    } 
    
}
