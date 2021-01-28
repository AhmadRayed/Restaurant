package Application.Controller;

import Application.Model.VerifyItemEditModel;
import Application.Resources.IndividualOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class VerifyItemEditController implements Initializable {

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    public static IndividualOrder individualOrder;
    private VerifyItemEditModel verifyItemEditModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        verifyItemEditModel = new VerifyItemEditModel();
    }

    @FXML
    private void Cancel_Action(ActionEvent event) { ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void Verify_Action(ActionEvent event) {
        verifyItemEditModel.Edit (txtUsername.getText(), txtPassword.getText(),
                                    txtQuantity.getText(), individualOrder.getId(),
                                        ((Node) event.getSource()).getScene().getWindow());
        Cancel_Action(event);
    }

}
