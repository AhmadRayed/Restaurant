package Application.Model;

import Application.Resources.MyMethods;
import Application.Resources.MySqlConnection;

public class VerifyItemDeleteModel {

    public void Delete(String username, String password, int id) {
        boolean flag = LoginModel.create_Login().validate_inside (username, password);
        if (flag) {
            String DELETE_QUERY = "DELETE FROM `order_item_table` WHERE `order_item_table`.`id` = '" + id + "'";
            MySqlConnection.MakeConnection().executeQuery(DELETE_QUERY, null);
            MyMethods.addINtoManagerLog("DELETE ITEMS UNDER ID = " + id);
        }
    }
}
