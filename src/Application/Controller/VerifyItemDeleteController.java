package Application.Controller;

import Application.Model.VerifyItemDeleteModel;
import Application.Resources.IndividualOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class VerifyItemDeleteController implements Initializable {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    public static IndividualOrder individualOrder;
    private VerifyItemDeleteModel verifyItemDeleteModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        verifyItemDeleteModel = new VerifyItemDeleteModel();
    }
    @FXML
    public void Cancel_Action(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void verify_Action(ActionEvent event) {
        verifyItemDeleteModel.Delete (txtUsername.getText(), txtPassword.getText(), individualOrder.getId());
        Cancel_Action(event);
    }

}
