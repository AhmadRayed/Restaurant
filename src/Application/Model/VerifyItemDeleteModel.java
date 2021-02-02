package Application.Model;

import Application.Resources.MyMethods;
import Application.Resources.MySqlConnection;
import Application.Resources.Observable;
import Application.Resources.Observer;

public class VerifyItemDeleteModel implements Observable {

    private Observer observer;
    public VerifyItemDeleteModel(Object t) {
        observer = (Observer) t;
    }

    public void Delete(String username, String password, int id) {
        boolean flag = LoginModel.create_Login().validate_inside (username, password);
        if (flag) {
            String DELETE_QUERY = "DELETE FROM `order_item_table` WHERE `order_item_table`.`id` = '" + id + "'";
            MySqlConnection.MakeConnection().executeQuery(DELETE_QUERY, null);
            notifyObserver();
            MyMethods.addINtoManagerLog("DELETE ITEMS UNDER ID = " + id);
        }
    }

    @Override
    public void notifyObserver() {
        observer.updateTable();
    }
}
