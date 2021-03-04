package Application.Model;

import Application.Resources.MyMethods;
import Application.Resources.MySqlConnection;
import Application.Resources.Observable;
import Application.Resources.Observer;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class VerifyCancelModel implements Observable {

    private Observer observer;

    public VerifyCancelModel(Object t) {
        observer = (Observer) t;
    }

    public void Cancel(String username, String password, int id) {
        boolean flag = LoginModel.create_Login().validate_inside (username, password);
        if (flag) {
            String CALL_PROCEDURE = "{CALL sp_Cancel_Order(?,?)}";
//            String Update_QUERY = "UPDATE `orders_table` SET `current` = '0' WHERE `orders_table`.`order_id` = '" + id + "'";
//            MySqlConnection.MakeConnection().executeQuery(Update_QUERY, null);
            try {
                CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
                callableStatement.setInt(1, id);
                callableStatement.registerOutParameter(2, Types.INTEGER);
                callableStatement.execute();
                if (callableStatement.getInt(2) == 0)
                    System.out.println("Error in cancellation");

                notifyObserver();
//            MyMethods.addINtoManagerLog("CANCEL ORDER WITH ID = " + id);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void notifyObserver() {
        observer.updateOrder(null, null);
    }
}
