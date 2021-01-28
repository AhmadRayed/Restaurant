package Application.Controller;

import Application.Model.ManageTablesModel;
import Application.Resources.MyMethods;
import Application.Resources.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageTablesController implements Initializable {

    @FXML
    private TextField txtTableName;

    @FXML
    private Button btDelete;

    @FXML
    private TableView <Table> tableView;

    @FXML
    private TableColumn <Table, Integer> colID;

    @FXML
    private TableColumn <Table, String> colName;

    private ManageTablesModel manageTablesModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manageTablesModel = new ManageTablesModel();
        manageTablesModel.ShowTable (tableView, colID, colName);
        addListenerForTable ();
    }

    @FXML
    private void Add_Action(ActionEvent event) {
        manageTablesModel.AddTable (((Node) event.getSource()).getScene().getWindow(), tableView, colName, colID, txtTableName.getText());
        txtTableName.setText("");
    }

    @FXML
    private void Delete_Action(ActionEvent event) {
        manageTablesModel.DeleteTable (((Node) event.getSource()).getScene().getWindow(), tableView, colName, colID, txtTableName.getText());
        txtTableName.setText("");
    }

    private void addListenerForTable () {
        tableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btDelete.setDisable(false);
                txtTableName.setText(newSelection.getName());
            }
            else {
                txtTableName.setText("");
                btDelete.setDisable(true);
            }
        }));
    }
}
