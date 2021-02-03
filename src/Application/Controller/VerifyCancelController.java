package Application.Controller;

import Application.Model.VerifyCancelModel;
import Application.Model.WaiterDashBoardModel;
import Application.Resources.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class VerifyCancelController implements Initializable {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    public static Order order;
    private VerifyCancelModel verifyCancelModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        verifyCancelModel = new VerifyCancelModel(WaiterDashBoardModel.T);
    }
    @FXML
    public void Cancel_Action(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void verify_Action(ActionEvent event) {
        verifyCancelModel.Cancel(txtUsername.getText(), txtPassword.getText(), order.getOrder_ID());

        Cancel_Action(event);

    }
}
