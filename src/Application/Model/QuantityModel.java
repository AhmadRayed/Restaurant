package Application.Model;

import Application.Resources.*;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class QuantityModel implements Observable {

    static Product product;
    private Observer observer;

    public QuantityModel(Object t) {
        observer = (Observer) t;
    }

    public void AddOrder (String quantity, String comment, Window window) {
        int q = Integer.parseInt(quantity);
        String error = "!!ERROR!! Invalid Number";
        String alert = "Please Enter a correct quantity.";
        if (quantity.isEmpty() || q == 0) {
            MyMethods.showAlert(alert, "ERROR", Alert.AlertType.ERROR, window);
        }
        else {
            String INSERT_QUERY = "INSERT INTO `order_item_table` (order_id,product_id,quantity,comment)" +
                    " VALUES (" + WaiterDashBoardModel.order.getOrder_ID() + "," + product.getId() +
                                "," + q + ",'" + comment + "')";
            MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, error);
            notifyObserver();
            MyMethods.addtoWaiterLog("ADD PRODUCT WITH ID = " + product.getId() + " ON ORDER WITH ID = " + WaiterDashBoardModel.order.getOrder_ID());
        }
    }

    @Override
    public void notifyObserver() {
        observer.updateTable();
    }
}
