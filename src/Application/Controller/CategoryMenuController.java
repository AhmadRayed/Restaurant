package Application.Controller;

import Application.Model.CategoryMenuModel;
import Application.Resources.Category;
import Application.Resources.Observer;
import Application.Resources.Order;
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

public class CategoryMenuController implements Initializable, Observer {
    public Button btUpdate;
    @FXML
    private TextField txtCategoryName;

    @FXML
    private Button btDelete;

    @FXML
    private TableView <Category> tableView;

    @FXML
    private TableColumn <Category, Integer> colID;

    @FXML
    private TableColumn <Category, String> colName;

    private CategoryMenuModel categoryMenuModel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryMenuModel = new CategoryMenuModel(this);
        categoryMenuModel.ShowTable (tableView, colID, colName);
        addListenerForTable();
    }

    @FXML
    private void Add_Action(ActionEvent event) {
        categoryMenuModel.AddCategory (((Node) event.getSource()).getScene().getWindow(), txtCategoryName.getText());
        txtCategoryName.setText("");
    }

    @FXML
    private void Delete_Action(ActionEvent event) {
        categoryMenuModel.DeleteCategory (((Node) event.getSource()).getScene().getWindow(), tableView);
        txtCategoryName.setText("");
    }

    @FXML
    public void Update_Action(ActionEvent event) {
        categoryMenuModel.UpdateCategory (tableView, txtCategoryName.getText());
    }

    private void addListenerForTable () {
        tableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btDelete.setDisable(false);
                btUpdate.setDisable(false);
                txtCategoryName.setText(newSelection.getName());
            }
            else {
                txtCategoryName.setText("");
                btDelete.setDisable(true);
                btUpdate.setDisable(true);
            }
        }));
    }

    @Override
    public void updateOrder(Table table, Order order) {

    }

    @Override
    public void updateTable() {
        categoryMenuModel.ShowTable (tableView, colID, colName);
    }
}
