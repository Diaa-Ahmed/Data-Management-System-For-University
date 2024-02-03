/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Helper.AddStudentController;
import Server.DAO.DataModification;
import Server.DAO.DataRetrieval;
import Server.DTO.Student;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class StudentViewController implements Initializable {

    @FXML
    private TableView<Student> studentlist;
    @FXML
    private TableColumn<Student, String> fnamecol;
    @FXML
    private TableColumn<Student, String> lnamecol;
    @FXML
    private TableColumn<Student, String> emailcol;
    @FXML
    private TableColumn<Student, Integer> levelcol;
    @FXML
    private TableColumn<Student, Float> gpacol;
    @FXML
    private TableColumn<Student, String> majorcol;
    @FXML
    private TableColumn<Student, String> minorcol;
    @FXML
    private Button addbtn;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deletebtn;

    @FXML
    private TableColumn<Student, String> citycol;
    @FXML
    private TableColumn<Student, String> streetcol;
    @FXML
    private TableColumn<Student, Integer> buildingcol;
    @FXML
    private TableColumn<Student, String> passcol;
    @FXML
    private TableColumn<Student, Integer> thourscol;

    private Student selected_student;
    private ArrayList<Student> receivedlist;
    private ObservableList<Student> studentObservableList;
    @FXML
    private TableColumn<Student, String> phonecol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addbtn.setOnAction(event -> {
             try {
                    Stage popup = new Stage();
                    popup.initModality(Modality.APPLICATION_MODAL);
                    Parent root = FXMLLoader.load(getClass().getResource("/View/Helper/AddStudent.fxml"));
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
                 if (!studentlist.getSelectionModel().isEmpty()) {
                    selected_student = studentlist.getSelectionModel().getSelectedItems().get(0);
                    Stage popup = new Stage();
                    popup.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Helper/AddStudent.fxml"));
                    Parent temp = loader.load();
                    AddStudentController ctrl = loader.getController();
                    ctrl.setStudent(selected_student);
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
                    alert.setContentText("Please Choose Student");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.setTitle("Updating Student !!");
                    alert.showAndWait();
                 }
             } catch (IOException ex) {
                 Logger.getLogger(CourseViewController.class.getName()).log(Level.SEVERE, null, ex);
             }
         });
        
        deletebtn.setOnAction(event -> {
            if (!studentlist.getSelectionModel().isEmpty()) {
                selected_student = studentlist.getSelectionModel().getSelectedItems().get(0);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("You're about to remove " + selected_student.getFname());
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.setTitle("Remove Student");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            if(DataModification.deletion("student", selected_student))
                                loadData();
                        } catch (SQLException ex) {
                            Logger.getLogger(StudentViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Choose Student");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.setTitle("Removing Student");
                alert.showAndWait();
            }
        });
        fnamecol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getFname()));

        lnamecol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getLname()));

        emailcol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getEmail()));

        levelcol.setCellValueFactory(cellData
                -> new SimpleIntegerProperty((cellData.getValue().getLevel())).asObject());

        gpacol.setCellValueFactory(cellData
                -> new SimpleFloatProperty((cellData.getValue().getGpa())).asObject());

        majorcol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getMajor()));

        minorcol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getMinor()));
        citycol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getCity()));
        streetcol.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getStreet()));
        buildingcol.setCellValueFactory(cellData
                -> new SimpleIntegerProperty(cellData.getValue().getBuilding_no()).asObject());
        passcol.setCellValueFactory(cellData
                -> new SimpleStringProperty(hidePassword(cellData.getValue().getPassword())));
        thourscol.setCellValueFactory(cellData
                -> new SimpleIntegerProperty(cellData.getValue().getTotal_hours()).asObject());

       phonecol.setCellValueFactory(cellData
                -> new SimpleStringProperty(concatNumbers(cellData.getValue().getPhonenumbers())));
        /*fnamecol.setEditable(true);
        fnamecol.setCellFactory( tc -> {
        EditingCell cell = new EditingCell();
        cell.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
         });
            return cell;
        });*/

        //  fnamecol.setOnEditCommit(event -> updateDatabase(event));
        loadData();
    }

    private void loadData() {
        try {
            receivedlist = DataRetrieval.getStudents();
            studentObservableList = FXCollections.observableArrayList(receivedlist);
            studentlist.setItems(studentObservableList);
        } catch (SQLException ex) {
            Logger.getLogger(StudentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String hidePassword(String pass){
        String hidden ="";
        for(int i = 0 ; i <pass.length() ; i++)
            hidden+="*";
        return hidden;
    }
    private String concatNumbers(ArrayList<Integer> numbers){
        String concat = "";
        for(int num : numbers)
            concat+= num +"\n";
        return concat;
    }
    /*public static class EditingCell extends javafx.scene.control.cell.TextFieldTableCell<Student, String> {

    public EditingCell() {
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getString());
            setGraphic(null);
        }
    }

    @Override
    public void commitEdit(String newValue) {
        
        System.out.println("inside commit");
        if (getTableRow() != null && getTableRow().getItem() != null) {
            Student curr = (Student) getTableRow().getItem();
            System.out.println("name befor edit -- " + curr.getFname() );
         //   curr.setFname(newValue);
            super.commitEdit("");
         System.out.println("name after edit -- " + curr.getFname() );
        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
     */
}
