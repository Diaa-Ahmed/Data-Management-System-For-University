/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controller.CourseViewController;
import Controller.DepartmentViewController;
import Controller.ReportController;
import Controller.StudentViewController;
import Server.DAO.DataRetrieval;
import Server.DTO.Student;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import com.jfoenix.controls.JFXTabPane;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class HomeController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private Tab studentstab;
    @FXML
    private Tab departmentstab;
    @FXML
    private Tab coursestab;
    @FXML
    private Tab reportstab;
    @FXML
    private JFXTabPane container;
    EventHandler<Event> tabFocusHandler;
    private static ReportController rc ;
    private static DepartmentViewController dc ;
    private static StudentViewController sc ;
    private static CourseViewController cc ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            studentstab.setContent(FXMLLoader.load(getClass().getResource("/View/StudentView.fxml")));
            departmentstab.setContent(FXMLLoader.load(getClass().getResource("/View/DepartmentView.fxml")));
            coursestab.setContent(FXMLLoader.load(getClass().getResource("/View/CourseView.fxml")));
            reportstab.setContent(FXMLLoader.load(getClass().getResource("/View/ReportView.fxml")));
            configureTab(studentstab, "Students", "/resources/student.png");
            studentstab.setStyle("-fx-background-color:  derive(#00BCD4,-20%);");
            configureTab(departmentstab, "Departments", "/resources/department.png");
            configureTab(coursestab, "Courses", "/resources/course.png");
            configureTab(reportstab, "Reports", "/resources/report.png");

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configureTab(Tab tab, String title, String iconPath) {
        double imageWidth = 60.0;
        tab.setOnSelectionChanged((event) -> {
            try {
                Tab currentTab = (Tab) event.getTarget();
                if (currentTab.isSelected()) {
                    currentTab.setStyle("-fx-background-color:  derive(#00BCD4,-20%);");
                } else {
                    currentTab.setStyle("-fx-background-color: #00BCD4;");
                }
                rc.loadData();
                dc.loadData() ;
                sc.loadData();
                cc.loadData();
            } catch (SQLException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ImageView imageView = new ImageView(new Image(iconPath));
        imageView.setFitHeight(imageWidth);
        imageView.setFitWidth(imageWidth);

        Label label = new Label(title);
        label.setMaxWidth(222 - 20);
        label.setPadding(new Insets(5, 0, 0, 0));
        label.setStyle("-fx-text-fill: white; -fx-font-size: 18pt; -fx-font-weight: bold;");
        label.setTextAlignment(TextAlignment.CENTER);

        BorderPane tabPane = new BorderPane();
        // tabPane.setRotate(90.0);
        tabPane.setMaxWidth(222);
        tabPane.setCenter(imageView);
        tabPane.setBottom(label);

        tab.setText("");
        tab.setGraphic(tabPane);
    }

    public static void setRc(ReportController rc) {
        HomeController.rc = rc;
    }

    public static void setDc(DepartmentViewController dc) {
        HomeController.dc = dc;
    }

    public static void setSc(StudentViewController sc) {
        HomeController.sc = sc;
    }

    public static void setCc(CourseViewController cc) {
        HomeController.cc = cc;
    }
    
}
