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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AddStudentController implements Initializable {

    @FXML
    private Label titlelabel;
    @FXML
    private TextField fnametf;
    @FXML
    private TextField lnametf;
    @FXML
    private TextField emailtf;
    @FXML
    private TextField passtf;
    @FXML
    private TextField leveltf;
    @FXML
    private TextField gpatf;
    @FXML
    private TextField thourstf;
    @FXML
    private TextField citytf;
    @FXML
    private TextField streettf;
    @FXML
    private TextField buildingtf;
    @FXML
    private ChoiceBox<String> majorcb;
    @FXML
    private ChoiceBox<String> minorcb;
    @FXML
    private Button addbtn;
    @FXML
    private TextArea numbersta;
    private boolean toggleflag = false;

    private Student student;
    private ObservableList<String> majorObservableList;
    private ObservableList<String> minorObservableList;

    ArrayList<String> deptids;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            loadDepartments();
            if (student != null) {
                titlelabel.setText("Update Student");
                fnametf.setText(student.getFname());
                lnametf.setText(student.getLname());
                emailtf.setText(student.getEmail());
                passtf.setText(student.getPassword());
                leveltf.setText(Integer.toString(student.getLevel()));
                gpatf.setText(Float.toString(student.getGpa()));
                thourstf.setText(Integer.toString(student.getTotal_hours()));
                citytf.setText(student.getCity());
                streettf.setText(student.getStreet());
                buildingtf.setText(Integer.toString(student.getBuilding_no()));
                setMajor(true);
                addbtn.setText("Update");
                numbersta.setText(concatNumbers(student.getPhonenumbers()));
            }else
                setMajor(false);
            majorcb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!toggleflag) {
                        toggle(true);
                    } else {
                        toggleflag = false;
                    }
                }
            });
            minorcb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!toggleflag) {
                        toggle(false);
                    } else {
                        toggleflag = false;
                    }
                }
            });
            addbtn.setOnAction(event -> {
                if (checkData()) {
                    try {
                        Student newstudent = prepareData();
                        if (student != null) {
                            DataModification.modifyStudent(newstudent, true);
                        } else {
                            DataModification.modifyStudent(newstudent, false);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please Enter All Data");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.showAndWait();
                }
            });
        });
    }

    private void loadDepartments() {
        try {
            ArrayList<Department> dlist = DataRetrieval.getDepartments();
            deptids = new ArrayList<String>();
            for (Department d : dlist) {
                deptids.add(d.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void toggle(boolean major) {
        if (minorcb.getSelectionModel().getSelectedItem() == majorcb.getSelectionModel().getSelectedItem()) {
            if (major) {
                toggleflag = true;
                minorcb.setValue(null);//.setItems(majorObservableList);

            } else {
                toggleflag = true;
                majorcb.setValue(null);

            }
        }
    }

    private void setMajor(boolean update) {
        majorObservableList = FXCollections.observableArrayList(deptids);
        minorObservableList = FXCollections.observableArrayList(deptids);

        majorcb.setItems(majorObservableList);
        minorcb.setItems(minorObservableList);
        if(update){
            majorcb.setValue(student.getMajor());
            minorcb.setValue(student.getMinor());
        }
    }

    private String concatNumbers(ArrayList<Integer> numbers) {
        String concat = "";
        for (int num : numbers) {
            concat += num + "\n";
        }
        return concat;
    }

    private boolean checkData() {
        if (fnametf.getText().isEmpty() || passtf.getText().isEmpty() || emailtf.getText().isEmpty() 
                /*|| thourstf.getText().isEmpty()
                || leveltf.getText().isEmpty() || gpatf.getText().isEmpty()*/) {
            return false;
        } else {
            return true;
        }
    }

    private Student prepareData() {
         float gpa = gpatf.getText().isEmpty() ? 0.0f : Float.parseFloat(gpatf.getText());
         int totalHours = thourstf.getText().isEmpty() ? 0 : Integer.parseInt(thourstf.getText());
         int buildingno = buildingtf.getText().isEmpty() ? 0 : Integer.parseInt(buildingtf.getText());
         int level = leveltf.getText().isEmpty() ? 1 : Integer.parseInt(leveltf.getText());
    Student temp = new Student(
        fnametf.getText(), lnametf.getText(), citytf.getText(),
        streettf.getText(), buildingno, emailtf.getText(),
        passtf.getText(), level , gpa,
        totalHours, majorcb.getSelectionModel().getSelectedItem(),
        minorcb.getSelectionModel().getSelectedItem(), getNumbers()
    );
        if(student != null)
            temp.setStudent_id(student.getStudent_id());
        return temp;
    }

    public ArrayList<Integer> getNumbers() {
    ArrayList<Integer> phones = new ArrayList<Integer>();
    String[] temp = numbersta.getText().split("\n");
    if (!numbersta.getText().isEmpty()) {
        for (int i = 0; i < temp.length; i++) {
            if (!temp[i].isEmpty()) {
                phones.add(Integer.parseInt(temp[i]));
            }
        }
    }
    return phones;
}


    public void setStudent(Student student) {
        this.student = student;
    }

}
