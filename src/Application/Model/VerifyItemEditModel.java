package Application.Model;

import Application.Resources.MyMethods;
import Application.Resources.MySqlConnection;
import Application.Resources.Observable;
import Application.Resources.Observer;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class VerifyItemEditModel implements Observable {

    private Observer observer;

    public VerifyItemEditModel(Object t) {
        observer = (Observer) t;
    }

    public void Edit(String username, String password, String quantity, int id, Window window) {
        boolean flag = LoginModel.create_Login().validate_inside (username, password);
        int q = Integer.parseInt(quantity);
        String alert = "!!Please Enter valid number!!";
        if (quantity.isEmpty() || q == 0)
            MyMethods.showAlert(alert, "ERROR", Alert.AlertType.ERROR, window);
        if (flag) {
            String UPDATE_QUERY = "UPDATE `order_item_table` SET `quantity` = '"+ q +
                    "' WHERE `order_item_table`.`id` ='" + id + "'";
            MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
            notifyObserver();
            MyMethods.addINtoManagerLog("EDIT ITEMS UNDER ID = " + id);
        }
    }

    @Override
    public void notifyObserver() {
        observer.updateTable();
    }
}
