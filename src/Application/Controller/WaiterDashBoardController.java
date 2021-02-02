package Application.Controller;

import Application.Model.WaiterDashBoardModel;
import Application.Resources.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WaiterDashBoardController implements Initializable, Observer {

    public Button btNewOrder;
    @FXML
    private TableView <IndividualOrder> tableview;

    @FXML
    private TableColumn <IndividualOrder, String> ColName;

    @FXML
    private TableColumn <IndividualOrder, Double> ColPrice;

    @FXML
    private TableColumn <IndividualOrder, Integer> ColQuantity;

    @FXML
    private TableColumn <IndividualOrder, Double> ColTotal;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btEdit;

    @FXML
    private Button btDelete;

    @FXML
    private VBox vbCategory;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblTableName;

    @FXML
    private GridPane GPane;

//    FXML NODES

    Scene scene;
    Parent root;
    Stage window;

    private WaiterDashBoardModel waiterDashBoardModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WaiterDashBoardModel.setT(this);
        waiterDashBoardModel = new WaiterDashBoardModel(vbCategory, GPane);
        waiterDashBoardModel.setWaiterName(lblUsername);
//        if (WaiterDashBoardModel.order != null) {
//            waiterDashBoardModel.ShowTable(tableview, ColName, ColPrice, ColQuantity, ColTotal);
//            waiterDashBoardModel.setTableName(lblTableName);
//            lblTotal.setText("0.0");
//            waiterDashBoardModel.setTotal(lblTotal);
//        }
//        else {
                if (WaiterDashBoardModel.order == null) {

        lblTableName.setText("TableName");
            lblTotal.setText("0.0");
            waiterDashBoardModel.ShowEmpty (tableview, ColName, ColPrice, ColQuantity, ColTotal);
        }
        addListenerForTable ();
//        setGlobalEventHandler ();
    }

    @FXML
    private void EditItem(ActionEvent event) {
        try {
            VerifyItemEditController.individualOrder = tableview.getSelectionModel().selectedItemProperty().get();
            openNewWindow("/Application/View/VerifyItemEdit.fxml", "Verify Edit");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void DeleteItem(ActionEvent event) {
        try {
            VerifyItemDeleteController.individualOrder = tableview.getSelectionModel().selectedItemProperty().get();
            openNewWindow("/Application/View/VerifyItemDelete.fxml", "Verify Delete");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void NewOrder_Action(ActionEvent event) {
        try {
            openNewWindow("/Application/View/ManageOrders.fxml", "Manage Orders");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Payment_Action(ActionEvent event) {
        if (WaiterDashBoardModel.order != null)
            try{
                waiterDashBoardModel.setPrice();
                openNewWindow("/Application/View/rPayment.fxml", "Payment");
            }catch (Exception e) {
                e.printStackTrace();
            }
    }

    @FXML
    private void Cancel_Action(ActionEvent event) {
        if (WaiterDashBoardModel.order != null)
            try {
                VerifyCancelController.order = WaiterDashBoardModel.order;
                openNewWindow("/Application/View/VerifyCancel.fxml", "Verify Cancel");

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @FXML
    private void ManageTable(ActionEvent event) {
        try {
            openNewWindow("/Application/View/ManageTables.fxml", "Manage Tables");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Logout_Action(ActionEvent event) {
        Exit_Action (event);
        try {
            Parent parentRoot = FXMLLoader.load(getClass().getResource("/Application/View/Login.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.setIconified(false);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(parentRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Exit_Action(ActionEvent event) {
        MyMethods.addtoWaiterLog("LOGOUT.");
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
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
//        this.initialize(null, null);
    }

    private void addListenerForTable () {
        tableview.getSelectionModel().selectedItemProperty().addListener(((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btDelete.setDisable(false);
                btEdit.setDisable(false);
            }
            else {
                btDelete.setDisable(true);
                btEdit.setDisable(true);
            }
        }));
    }

    @Override
    public void updateOrder(Table table, Order order) {
        WaiterDashBoardModel.order = order;
        WaiterDashBoardModel.table = table;
        if (order != null) {
            waiterDashBoardModel.ShowTable(tableview, ColName, ColPrice, ColQuantity, ColTotal);
            waiterDashBoardModel.setTableName(lblTableName);
            lblTotal.setText("0.0");
            waiterDashBoardModel.setTotal(lblTotal);
        } else {
            lblTableName.setText("TableName");
            lblTotal.setText("0.0");
            waiterDashBoardModel.ShowEmpty(tableview, ColName, ColPrice, ColQuantity, ColTotal);
        }
    }

    @Override
    public void updateTable() {
        waiterDashBoardModel.ShowTable(tableview, ColName, ColPrice, ColQuantity, ColTotal);
        waiterDashBoardModel.setTotal(lblTotal);
    }

//    private void setGlobalEventHandler (Button node) {
//        node.addEventHandler (KeyEvent.KEY_PRESSED, ev -> {
//            if (ev.getCode() == KeyCode.F1) {
//                node.fire();
//                ev.consume();
//            }
//        });
//    }

}
