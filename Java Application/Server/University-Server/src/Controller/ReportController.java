/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Helper.PrerequisiteController;
import Main.HomeController;
import Server.DAO.DataRetrieval;
import Server.DAO.ReportDAO;
import Server.DTO.Course;
import Server.DTO.CourseStat;
import Server.DTO.Prerequisite;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    @FXML
    private PieChart piechart;
    private BarChart<String, Number> barchart;
    @FXML
    private AnchorPane barchartpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            HomeController.setRc(this);
            cnamecol.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().getName()));

            dnamecol.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().getDepartment().getId()));
            courselist.setRowFactory(new Callback<TableView<Course>, TableRow<Course>>() {
                @Override
                public TableRow<Course> call(TableView<Course> st) {
                    TableRow<Course> row = new TableRow<>();
                    row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if ((!row.isEmpty())) {
                                try {
                                    Course pressed = row.getItem();
                                    totalstud.setText(Integer.toString(ReportDAO.numberOfStudents(pressed)));
                                    avggpa.setText(Float.toString(ReportDAO.averageGPA(pressed)));
                                    loadStat(pressed);
                                } catch (SQLException ex) {
                                    Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    });
                    return row;
                }
            });

            try {
                loadData();
                setupBarChart();
            } catch (SQLException ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void loadData() throws SQLException {
        receivedlist = DataRetrieval.getCourses();
        CourseObservableList = FXCollections.observableArrayList(receivedlist);
        courselist.setItems(CourseObservableList);
    }

    private void setupBarChart() throws SQLException {
    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setLabel("Grade");
    xAxis.setCategories(FXCollections.observableArrayList(DataRetrieval.getGrades()));

    NumberAxis yAxis = new NumberAxis(0, 20,1);
    yAxis.setTickUnit(1);
    yAxis.setLabel("Number of Students");

    barchart = new BarChart<>(xAxis, yAxis);
    barchart.setAnimated(false);
    barchart.setTitle("Student Grades");
    barchartpane.getChildren().add(barchart);
    
}

    private void loadStat(Course course) {
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Grade Distribution");
            
            barchart.getData().clear();
            ArrayList<CourseStat> stat = ReportDAO.getStat(course);
            if (stat.size() > 0) {
                for (int i = 0; i < stat.size(); i++) {
                    
                    series.getData().add(new XYChart.Data<>(stat.get(i).getGrade(), stat.get(i).getCount()));
                }
                barchart.getData().add(series);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
