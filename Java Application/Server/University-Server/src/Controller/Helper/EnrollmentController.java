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
import Server.DTO.Enrollment;
import Server.DTO.Student;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class EnrollmentController implements Initializable {

    @FXML
    private TableView<Enrollment> enrollmentlist;
    @FXML
    private TableColumn<Enrollment, String> cnamecol;
    @FXML
    private TableColumn<Enrollment, Integer> credithourscol;
    @FXML
    private TableColumn<Enrollment, String> grade_col;
    @FXML
    private TableColumn<Enrollment, String> year;
    @FXML
    private Button addbtn;
    @FXML
    private ChoiceBox<String> yearcb;
    @FXML
    private Label student_name;
    @FXML
    private Label gpalabel;
    @FXML
    private Button deletebtn;

    private ArrayList<Enrollment> receivedlist;
    private ObservableList<Enrollment> EnrollmentObservableList;
    private ArrayList<String> years = new ArrayList<String>();
    private Enrollment selected_enrollment;
    private Student clicked;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            student_name.setText(clicked.getFname() + " " + clicked.getLname());
            gpalabel.setText(Float.toString(clicked.getGpa()));
            addbtn.setOnAction(event -> {
                try {
                    Stage popup = new Stage();
                    popup.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Helper/AddEnrollment.fxml"));
                    Parent root = loader.load();
                    AddEnrollmentController controller = loader.getController();
                    controller.setClicked(clicked);
                    Scene scene = new Scene(root);
                    popup.setResizable(false);
                    popup.setScene(scene);
                    popup.setOnCloseRequest(evt -> loadData());
                    popup.show();
                } catch (IOException ex) {
                    Logger.getLogger(CourseViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            deletebtn.setOnAction(event -> {
                if (!enrollmentlist.getSelectionModel().isEmpty()) {
                    selected_enrollment = enrollmentlist.getSelectionModel().getSelectedItems().get(0);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("You're about to remove " + selected_enrollment.getCourse().getName());
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.setTitle("Remove Course");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                if (DataModification.deletion("enrollment", selected_enrollment)) {
                                    loadData();

                                    gpalabel.setText(Float.toString(DataRetrieval.getGPA(selected_enrollment.getStudent())));
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(StudentViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please Choose Course");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.setTitle("Removing Course !!");
                    alert.showAndWait();
                }
            });

            cnamecol.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().getCourse().getName()));

            credithourscol.setCellValueFactory(cellData
                    -> new SimpleIntegerProperty(cellData.getValue().getCourse().getCredit_hours()).asObject());

            grade_col.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().getGrade()));

            year.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().getYear()));
            
            loadData();
            years.add(0, "All");
        });
    }

    private void loadData() {
        try {
            receivedlist = DataRetrieval.getEnrollment(clicked);
            EnrollmentObservableList = FXCollections.observableArrayList(receivedlist);
            enrollmentlist.setItems(EnrollmentObservableList);
            gpalabel.setText(Float.toString(DataRetrieval.getGPA(clicked)));
            setYearMenu();
            interactiveGradeEdit();
        } catch (SQLException ex) {
            Logger.getLogger(StudentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setClicked(Student clicked) {
        this.clicked = clicked;
    }

    private void setYearMenu() {
        for (Enrollment enr : receivedlist) {
            if (years.contains(enr.getYear())) {
                continue;
            } else {
                years.add(enr.getYear());
            }
        }
        choiceBoxListener();
    }

    private void choiceBoxListener() {
        ObservableList<String> prev_years = FXCollections.observableArrayList(years);
        yearcb.setItems(prev_years);
        yearcb.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("All")) {
                    EnrollmentObservableList = FXCollections.observableArrayList(receivedlist);
                    enrollmentlist.setItems(EnrollmentObservableList);
                } else {
                    changeView(newValue);
                }
            }
        });
    }

    private void changeView(String newvalue) {
        ArrayList<Enrollment> subset = new ArrayList<Enrollment>();
        for (Enrollment enr : receivedlist) {
            if (enr.getYear().toString().equals(newvalue)) {
                subset.add(enr);
            }
        }
        EnrollmentObservableList = FXCollections.observableArrayList(subset);
        enrollmentlist.setItems(EnrollmentObservableList);
    }

    private void interactiveGradeEdit() {
        grade_col.setEditable(true);
        grade_col.setCellFactory(tc -> {
            EditingCell cell = new EditingCell();
            return cell;
        });

    }

    public class EditingCell extends TableCell<Enrollment, String> {

        private ChoiceBox<String> grades;

        public EditingCell() {
            try {
                grades = new ChoiceBox<>();
                grades.getItems().addAll(DataRetrieval.getGrades());
                grades.setOnAction(e -> {
                    String newValue = grades.getValue();
                    if (!newValue.equals(getItem())) {
                        commitEdit(newValue);
                    }
                });

                setOnMouseClicked(event -> {
                    if (!isEmpty()) {
                        startEdit();
                    }
                });
            } catch (SQLException ex) {
                Logger.getLogger(EnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(grades);
                grades.setValue(item);
            }
        }

        @Override
        public void startEdit() {
            super.startEdit();
            grades.setValue(getItem());
            setGraphic(grades);
        }

        @Override
        public void commitEdit(String newValue) {
            if (getTableRow() != null && getTableRow().getItem() != null) {
                try {
                    Enrollment curr = (Enrollment) getTableRow().getItem();
                    String old = curr.getGrade();
                    if (old != newValue) {
                        curr.setGrade(newValue);
                        if (DataModification.modifyEnrollment(curr, true)) {
                            super.commitEdit(newValue);
                            loadData();
                        } else {
                            super.commitEdit("");
                            curr.setGrade(old);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(EnrollmentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
