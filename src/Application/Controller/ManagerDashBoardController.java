package Application.Controller;

import Application.Main;
import Application.Model.ManagerDashBoardModel;
import Application.Resources.MyMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerDashBoardController implements Initializable {

    @FXML
    private Button btEditM;

    @FXML
    private Button btReportM;

    @FXML
    private Label lblUsername;

    //    FXML NODES

    Scene scene;
    Parent root;
    Stage window;
    private ManagerDashBoardModel managerDashBoardModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        managerDashBoardModel = new ManagerDashBoardModel();
        managerDashBoardModel.setUsername(lblUsername);
        managerDashBoardModel.setBT (btEditM);
        managerDashBoardModel.setBT (btReportM);

    }

    @FXML
    private void Logout_Action(ActionEvent event) {
        managerDashBoardModel.Logout_Action((Stage) ((Node) event.getSource()).getScene().getWindow(), this);
//        Exit_Action (event);
//        try {
//            Parent parentRoot = FXMLLoader.load(getClass().getResource("/Application/View/Login.fxml"));
//            Stage primaryStage = new Stage();
//            primaryStage.setTitle("Login");
//            primaryStage.setResizable(false);
//            primaryStage.setIconified(false);
//            primaryStage.initStyle(StageStyle.UNDECORATED);
//            primaryStage.setScene(new Scene(parentRoot));
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    private void Exit_Action(ActionEvent event) {
        managerDashBoardModel.Exit_Action((Stage) ((Node) event.getSource()).getScene().getWindow(), this);
//        MyMethods.addtoManagerLog("LOGOUT.");
//        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void Manage_Menu(ActionEvent event) {
        try {
            openNewWindow("/Application/View/ManageMenu.fxml", "Manage Menu");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Manage_Waiters(ActionEvent event) {
        try {
            openNewWindow("/Application/View/ManageWaiters.fxml", "Manage Waiters");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Waiters_Report(ActionEvent event) {
        try {
            ReportController.detector = 0;
            openNewWindow("/Application/View/Report.fxml", "Waiters Report");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Sales_Report(ActionEvent event) {
        try {
            ReportController.detector = 2;
            openNewWindow("/Application/View/Report.fxml", "Waiters Report");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Edit_Manager(ActionEvent event) {
        try {
            openNewWindow("/Application/View/ManageManagers.fxml", "Manage Managers");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Manager_Report(ActionEvent event) {
        try {
            ReportController.detector = 1;
            openNewWindow("/Application/View/Report.fxml", "Waiters Report");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openNewWindow (String resource, String Title) throws IOException {
        root = FXMLLoader.load(getClass().getResource(resource));
        scene = new Scene(root);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setAlwaysOnTop(true);
        window.setIconified(false);
        window.setTitle(Title);
        window.showAndWait();
        this.initialize(null, null);
    }
}
