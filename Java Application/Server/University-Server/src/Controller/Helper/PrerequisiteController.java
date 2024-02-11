/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Helper;

import Controller.CourseViewController;
import Controller.StudentViewController;
import Server.DAO.DataModification;
import Server.DAO.DataRetrieval;
import Server.DTO.Course;
import Server.DTO.Prerequisite;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class PrerequisiteController implements Initializable {

    @FXML
    private TableView<Prerequisite> courselist;
    @FXML
    private TableColumn<Prerequisite, String> cnamecol;
    @FXML
    private TableColumn<Prerequisite, Integer> credithourscol;
    @FXML
    private TableColumn<Prerequisite, String> dnamecol;
    @FXML
    private TableColumn<Prerequisite, Void> prereqcol;
    @FXML
    private Button savebtn;
    @FXML
    private TableColumn<Prerequisite, Integer> mlevelcol;
    private Course clicked;
    private ArrayList<Prerequisite> receivedlist;
    private ObservableList<Prerequisite> CourseObservableList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(() -> {
            savebtn.setOnAction(event -> {
                submitData();
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));

            });

            cnamecol.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().getCourse().getName()));

            credithourscol.setCellValueFactory(cellData
                    -> new SimpleIntegerProperty(cellData.getValue().getCourse().getCredit_hours()).asObject());

            mlevelcol.setCellValueFactory(cellData
                    -> new SimpleIntegerProperty(cellData.getValue().getCourse().getMinimum_level()).asObject());

            dnamecol.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().getCourse().getDepartment().getId()));

            loadData();
            setCheckBoxCol();
        });
    }

    private void loadData() {
        try {
            receivedlist = DataRetrieval.getPrerequisite(clicked);
            CourseObservableList = FXCollections.observableArrayList(receivedlist);
            courselist.setItems(CourseObservableList);
        } catch (SQLException ex) {
            Logger.getLogger(StudentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setClicked(Course clicked) {
        this.clicked = clicked;
    }

    private void setCheckBoxCol() {
        prereqcol.setCellFactory(new Callback<TableColumn<Prerequisite, Void>, TableCell<Prerequisite, Void>>() {
            @Override
            public TableCell<Prerequisite, Void> call(TableColumn<Prerequisite, Void> param) {
                return new TableCell<Prerequisite, Void>() {
                    private final CheckBox statuscheck = new CheckBox();

                    {
                        statuscheck.setOnAction(event -> {
                            Prerequisite prerequisite = (Prerequisite) getTableRow().getItem();
                            if (prerequisite != null) {
                                prerequisite.setStatus(statuscheck.isSelected());
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Prerequisite prerequisite = (Prerequisite) getTableRow().getItem();
                            if (prerequisite != null) {
                                statuscheck.setSelected(prerequisite.getStatus());
                            }
                            setGraphic(statuscheck);
                        }
                    }

                };
            }
        });
    }
    private void submitData(){
    
        try {
            CourseObservableList = FXCollections.observableArrayList(courselist.getItems());
            ArrayList<Prerequisite> arrayList = new ArrayList<>(CourseObservableList);
            DataModification.deletion("prerequisite", clicked);
            DataModification.addPrerequisite(clicked, arrayList);
        } catch (SQLException ex) {
            Logger.getLogger(PrerequisiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
