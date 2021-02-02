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
    public static Manager manager;

    public void setUsername (Label label) {
        label.setText(manager.FullName());
    }

    public void setBT(Button button) {
        if (manager.getAdmin() == 1)
                button.setDisable(false);
    }
}
