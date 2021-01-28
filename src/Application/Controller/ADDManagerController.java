package Application.Controller;

import Application.Model.ManageManagersModel;
import Application.Model.ManageWaitersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ADDManagerController implements Initializable {

    @FXML
    private TextField txtFirstname;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtSalary;

    private ManageManagersModel manageManagersModel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manageManagersModel = new ManageManagersModel();
    }

    @FXML
    private void Add_Action(ActionEvent event) {
        manageManagersModel.AddManager(txtFirstname.getText(), txtLastname.getText(),
                txtUsername.getText(), txtPassword.getText(),
                txtEmail.getText(), txtMobile.getText(), txtSalary.getText());
        Cancel_Action(event);
    }


    @FXML
    private void Cancel_Action(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
