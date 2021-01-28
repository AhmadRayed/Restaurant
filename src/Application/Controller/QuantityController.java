package Application.Controller;

import Application.Model.QuantityModel;
import Application.Model.WaiterDashBoardModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class QuantityController implements Initializable {

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextArea txtAComment;

    private QuantityModel quantityModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quantityModel = new QuantityModel();
    }

    @FXML
    private void Cancel_Action(ActionEvent event) {
        txtAComment.setText("");
        txtQuantity.setText("1");
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void Confirm_Action(ActionEvent event) {
        quantityModel.AddOrder(txtQuantity.getText(), txtAComment.getText(), ((Node) event.getSource()).getScene().getWindow());
        txtAComment.setText("");
        txtQuantity.setText("1");
        ((WaiterDashBoardController) WaiterDashBoardModel.getT()).initialize(null, null);
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

}
