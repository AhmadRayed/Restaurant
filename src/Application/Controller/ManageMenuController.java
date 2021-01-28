package Application.Controller;

import Application.Model.ManageMenuModel;
import Application.Resources.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageMenuController implements Initializable {

    @FXML
    private Button btDelete;

    @FXML
    private TableView <Product> tableView;

    @FXML
    private TableColumn <Product, Integer> colID;

    @FXML
    private TableColumn <Product, String> colName;

    @FXML
    private TableColumn <Product, String> colDescription;

    @FXML
    private TableColumn <Product, Double> colPrice;

    @FXML
    private TableColumn <Product, String> colCategory;

    @FXML
    private TableColumn <Product, String> colStatus;

    @FXML
    private ImageView imProduct;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtPrice;

    @FXML
    private ComboBox <String> cbCategory;

    @FXML
    private ComboBox <String> cbStatus;

    @FXML
    private TextField txtBrowser;

    @FXML
    private Button btBrowser;
//    FXML NODES

    Scene scene;
    Parent root;
    Stage window;

    private ManageMenuModel manageMenuModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manageMenuModel = new ManageMenuModel();
        manageMenuModel.ShowTable(tableView, colID, colName, colDescription, colPrice, colCategory, colStatus);
        manageMenuModel.ShowCategory (cbCategory);
        manageMenuModel.ShowStatus (cbStatus);
        addListenerForTable ();
    }


    @FXML
    private void Option_Action(ActionEvent event) {
        try {
            openNewWindow("/Application/View/CategoryMenu.fxml", "Category Menu");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AddProduct_Action(ActionEvent event) {
        manageMenuModel.AddProduct (txtName.getText(), txtDescription.getText(), txtPrice.getText(),
                                    cbCategory.getSelectionModel().getSelectedItem(),
                                    cbStatus.getSelectionModel().getSelectedItem(),
                                    ((Node) event.getSource()).getScene().getWindow());
        txtBrowser.setText("");
        txtDescription.setText("");
        txtName.setText("");
        txtPrice.setText("");
    }

    @FXML
    private void Browser_Action(ActionEvent event) throws MalformedURLException, FileNotFoundException {
        manageMenuModel.Browser (txtBrowser, imProduct, ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    private void Delete_Action(ActionEvent event) {
        manageMenuModel.DeleteProduct (tableView, colID, colName, colDescription, colPrice, colCategory, colStatus,
                                        ((Node)event.getSource()).getScene().getWindow());
    }

    @FXML
    private void Cancel_Action(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    private void addListenerForTable (){
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null){
                try {
                    btDelete.setDisable(false);
                    imProduct.setImage(new Image(newSelection.getImage().getBinaryStream()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
                btDelete.setDisable(true);
        });
    }

    private void openNewWindow (String resource, String Title) throws IOException {
        root = FXMLLoader.load(getClass().getResource(resource));
        scene = new Scene(root);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setAlwaysOnTop(true);
        window.setIconified(false);
        window.setTitle(Title);
        window.showAndWait();
        this.initialize(null, null);
    }
}
