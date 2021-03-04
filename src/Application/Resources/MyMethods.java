package Application.Resources;

import Application.Model.LoginModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class MyMethods {

    public static void showAlert (String infoMessage, String title, Alert.AlertType alertType, Window owner) {
        Alert alert = new Alert (alertType);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initOwner(owner);
        alert.show();
    }

    public static void addtoWaiterLog (String Activity) {
//        String DT = LoginModel.employee.FullName() + " IN " + new java.sql.Date(new java.util.Date().getTime()) + " AT " +
//                    new java.sql.Time(new java.util.Date().getTime()) + " DO ";
//        String INSERT_QUERY = "INSERT INTO `waiter_Log` (`id`,`Activity`) VALUES ('"+ LoginModel.employee.getId() +"','"+ DT + Activity +"')";
//        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);
    }

    public static void addINtoManagerLog (String Activity) {
//        String DT = LoginModel.create_Login().VI + " IN " + new java.sql.Date(new java.util.Date().getTime()) + " AT " +
//                new java.sql.Time(new java.util.Date().getTime()) + " DO ";
//        String INSERT_QUERY = "INSERT INTO `manager_Log` (`id`,`Activity`) VALUES ('"+ LoginModel.create_Login().VII +"','"+ DT + Activity +"')";
//        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);
    }

    public static void addtoManagerLog (String Activity) {
//        String DT = LoginModel.employee.FullName() + " IN " + new java.sql.Date(new java.util.Date().getTime()) + " AT " +
//                new java.sql.Time(new java.util.Date().getTime()) + " DO ";
//        String INSERT_QUERY = "INSERT INTO `manager_Log` (`id`,`Activity`) VALUES ('"+ LoginModel.employee.getId() +"','"+ DT + Activity +"')";
//        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);

    }
}
