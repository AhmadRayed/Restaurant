package Application.Model;

import Application.Resources.MyMethods;
import Application.Resources.MySqlConnection;
import Application.Resources.Observable;
import Application.Resources.Observer;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.sql.CallableStatement;
import java.sql.SQLException;

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

            String CALL_PROCEDURE = "{CALL sp_edit_Order(?,?)}";
            try {
                CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
                callableStatement.setInt(1, q);
                callableStatement.setInt(2, id);
                callableStatement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
            notifyObserver();
//            MyMethods.addINtoManagerLog("EDIT ITEMS UNDER ID = " + id);
        }
    }

    @Override
    public void notifyObserver() {
        observer.updateTable();
    }
}
