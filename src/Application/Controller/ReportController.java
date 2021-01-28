package Application.Controller;

import Application.Model.ReportModel;
import Application.Resources.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML
    private TableView <Report> tableView;

    @FXML
    private TableColumn <Report, Integer> colId;

    @FXML
    private TableColumn <Report, String> colActivity;

    private ReportModel reportModel;

    public static int detector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportModel = new ReportModel(detector);
        if (detector == 2) {
            colId.setText("order ID");
            colActivity.setText("Details");
        } else {
            colId.setText("ID");
            colActivity.setText("Activity");
        }
        reportModel.ShowTable(tableView, colId, colActivity);
    }

    @FXML
    private void Cancel_Action(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
