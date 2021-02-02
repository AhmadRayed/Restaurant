package Application.Controller;

import Application.Model.ManageOrdersModel;
import Application.Model.WaiterDashBoardModel;
import Application.Resources.Observer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageOrdersController implements Initializable {


    @FXML
    private GridPane Gpane;

    private ManageOrdersModel manageOrdersModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manageOrdersModel = new ManageOrdersModel();
        manageOrdersModel.setObserver((Observer) WaiterDashBoardModel.T);
        manageOrdersModel.ShowButtons(Gpane);
    }

}
