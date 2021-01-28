package Application.Controller;

import Application.Model.ManageWaitersModel;
import Application.Resources.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageWaitersController implements Initializable {

    @FXML
    private ComboBox <String> cbOption;

    @FXML
    private TextField txtChange;

    @FXML
    private Button btUpdate;

    @FXML
    private Button btDelete;

    @FXML
    private TableView <Employee> tableView;

    @FXML
    private TableColumn <Employee, Integer> colID;

    @FXML
    private TableColumn <Employee, String> colFName;

    @FXML
    private TableColumn <Employee, String> colLName;

    @FXML
    private TableColumn <Employee, String> colUsername;

    @FXML
    private TableColumn <Employee, String> colPassword;

    @FXML
    private TableColumn <Employee, String> colEmail;

    @FXML
    private TableColumn <Employee, String> colMobile;

    @FXML
    private TableColumn <Employee, Double> colSalary;

//    FXML NODES

    Scene scene;
    Parent root;
    Stage window;
    private ManageWaitersModel manageWaitersModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manageWaitersModel = new ManageWaitersModel();
        manageWaitersModel.ShowTable (tableView, colID, colFName, colLName, colUsername, colPassword, colEmail, colMobile, colSalary);
        manageWaitersModel.ShowOption (cbOption);
        addListenerForTable ();
    }

    @FXML
    private void Add_Action(ActionEvent event) {
        try {
            openNewWindow("/Application/View/ADDWaiter.fxml", "ADD WAITER");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Update_Action(ActionEvent event) {
        manageWaitersModel.UpdateWaiter (tableView, colID, colFName, colLName, colUsername,
                                        colPassword, colEmail, colMobile, colSalary, txtChange.getText(), cbOption.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void Delete_Action(ActionEvent event) {
        manageWaitersModel.DeleteWaiter (tableView, colID, colFName, colLName, colUsername,
                colPassword, colEmail, colMobile, colSalary, ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    private void Cancel_Action(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void addListenerForTable () {
        tableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btDelete.setDisable(false);
                btUpdate.setDisable(false);
            }
            else {
                btDelete.setDisable(true);
                btUpdate.setDisable(true);
            }
        }));
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
