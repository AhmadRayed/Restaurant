package Application.Model;

import Application.Resources.MyMethods;
import Application.Resources.MySqlConnection;

public class VerifyCancelModel {

    public void Cancel(String username, String password, int id) {
        boolean flag = LoginModel.create_Login().validate_inside (username, password);
        if (flag) {
            String Update_QUERY = "UPDATE `orders_table` SET `current` = '0' WHERE `orders_table`.`order_id` = '" + id + "'";
            MySqlConnection.MakeConnection().executeQuery(Update_QUERY, null);
            MyMethods.addINtoManagerLog("CANCEL ORDER WITH ID = " + id);
        }
    }
}
