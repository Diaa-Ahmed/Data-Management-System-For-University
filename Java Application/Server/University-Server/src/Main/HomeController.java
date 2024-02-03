/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

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
            
            /*button.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                Student student = new Student("mego@gmail.com", "123");
                try {
                    DataRetrieval.login(student);
                } catch (SQLException ex) {
                    System.out.println("Can't Connect");
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });*/
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void configureTab(Tab tab, String title, String iconPath) {
        double imageWidth = 60.0;
        tab.setOnSelectionChanged((event) -> { 
            Tab currentTab = (Tab) event.getTarget();
            if (currentTab.isSelected()) {
                currentTab.setStyle("-fx-background-color:  derive(#00BCD4,-20%);");
            } else {
                currentTab.setStyle("-fx-background-color: #00BCD4;");
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
}
