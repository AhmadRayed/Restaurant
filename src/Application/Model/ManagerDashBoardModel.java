package Application.Model;

import Application.Resources.Manager;
import Application.Resources.MyMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ManagerDashBoardModel {
    public static Manager manager = (Manager) LoginModel.employee;

    public void setUsername (Label label) {
        label.setText(manager.FullName());
    }

    public void setBT(Button button) {
        if (manager.getAdmin() == 1)
                button.setDisable(false);
    }

    public void Logout_Action(Stage stage, Object object) {
        Exit_Action (stage, object);
        try {
            Parent parentRoot = FXMLLoader.load(getClass().getResource("/Application/View/Login.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.setIconified(false);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(parentRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Exit_Action(Stage stage, Object object) {
        MyMethods.addtoManagerLog("LOGOUT.");
        stage.close();
        object = null;
    }
}
