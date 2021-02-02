package Application.Controller;

import Application.Model.PaymentModel;
import Application.Model.WaiterDashBoardModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtCardNumber;

    private PaymentModel paymentModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paymentModel = new PaymentModel(WaiterDashBoardModel.T);
    }

    @FXML
    private void ByCard(ActionEvent event) {
        paymentModel.ByCard (txtDiscount.getText(), txtCardNumber.getText());
//        ((WaiterDashBoardController) WaiterDashBoardModel.getT()).initialize(null, null);
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void ByCash(ActionEvent event) {
        paymentModel.ByCash (txtDiscount.getText());
//        ((WaiterDashBoardController) WaiterDashBoardModel.getT()).initialize(null, null);
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
