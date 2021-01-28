package Application.Controller;

import Application.Model.ManageWaitersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ADDWaiterController implements Initializable {

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

    @FXML
    private ComboBox <String> cbManager;

    ManageWaitersModel manageWaitersModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manageWaitersModel = new ManageWaitersModel();
        manageWaitersModel.ShowManager (cbManager);
        if (manageWaitersModel.flag()) {
            cbManager.setDisable(false);
        }
    }

    @FXML
    private void Add_Action(ActionEvent event) {
        manageWaitersModel.AddWaiter (txtFirstname.getText(), txtLastname.getText(),
                                        txtUsername.getText(), txtPassword.getText(),
                                        txtEmail.getText(), txtMobile.getText(), txtSalary.getText(), cbManager,
                                        ((Node) event.getSource()).getScene().getWindow());
        Cancel_Action(event);
    }


    @FXML
    private void Cancel_Action(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
