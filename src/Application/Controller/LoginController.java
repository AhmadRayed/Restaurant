package Application.Controller;

import Application.Model.LoginModel;
import Application.Resources.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private LoginModel loginModel;


    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginModel = LoginModel.create_Login();
    }

    @FXML
    private void Exit_Action(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void Login_Action(ActionEvent event) {

        FXMLLoader fxmlLoader = null;

        int flag = loginModel.LoginFunction (txtUsername.getText(), txtPassword.getText(),
                                            ((Node)event.getSource()).getScene().getWindow());

        if (flag == -1)
            return;

        if (flag == 0)
            fxmlLoader = new FXMLLoader(getClass().getResource("/Application/View/WaiterDashBoard.fxml"));

        if (flag == 1)
            fxmlLoader = new FXMLLoader(getClass().getResource("/Application/View/ManagerDashBoard.fxml"));

        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("POS | DASHBOARD");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
