package Application.Model;

import Application.Resources.*;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class QuantityModel implements Observable {

    static Product product;
    private Observer observer;

    public QuantityModel(Object t) {
        observer = (Observer) t;
    }

    public void AddOrder (String quantity, String comment, Window window) {
        int q = Integer.parseInt(quantity);
        String error = "!!ERROR!! Insertion not completed";
        String alert = "Please Enter a correct quantity.";
        if (quantity.isEmpty() || q == 0) {
            MyMethods.showAlert(alert, "ERROR", Alert.AlertType.ERROR, window);
        }
        else {
            String CALL_PROCEDURE = "{CALL sp_Add_To_Order(?,?,?,?,?)}";
            try {

                    CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
                    callableStatement.setInt(1, q);
                    callableStatement.setInt(2, WaiterDashBoardModel.order.getOrder_ID());
                    callableStatement.setInt(3, product.getId());
                    callableStatement.setString(4, comment);

                    callableStatement.registerOutParameter(5, Types.INTEGER);
                    callableStatement.execute();

                    int result = callableStatement.getInt(5);
                    callableStatement.close();
                    if (result == 0)
                        MyMethods.showAlert(error, "ERROR", Alert.AlertType.ERROR, window);

                notifyObserver();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public void notifyObserver() {
        observer.updateTable();
    }
}
