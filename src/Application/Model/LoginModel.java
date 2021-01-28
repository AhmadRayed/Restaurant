package Application.Model;

import Application.Resources.*;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    private static LoginModel loginModel;
    private MySqlConnection mySqlConnection;

    public static Employee employee;

    private LoginModel () {
        mySqlConnection = MySqlConnection.MakeConnection();
    }

    public static LoginModel create_Login () {
        if (loginModel == null)
            loginModel = new LoginModel();
        return loginModel;
    }


    public int LoginFunction (String username, String password, Window window) {
        if (username.isEmpty()) {
            MyMethods.showAlert("Please Enter a Username", "Form error!", Alert.AlertType.ERROR, window);
            return -1;
        }

        if (password.isEmpty()) {
            MyMethods.showAlert("Please Enter a Password", "Form error!", Alert.AlertType.ERROR, window);
            return -1;
        }

        if (!validate_Waiter(username, password))
            if (validate_Manager(username, password))
                return 1;
            else MyMethods.showAlert("Please enter a correct USERNAME and PASSWORD", null, Alert.AlertType.CONFIRMATION, null);
        else
            return 0;

        return -1;
    }

    private boolean validate_Waiter (String username, String password) {
        String SELECT_QUERY = "SELECT * FROM waiter_Table WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = mySqlConnection.getConnection().prepareStatement(SELECT_QUERY);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employee = new Waiter(  resultSet.getInt("id"),               resultSet.getString("first_name"),
                                        resultSet.getString("last_name"),     resultSet.getString("username"),
                                        resultSet.getString("password"),      resultSet.getString("email"),
                                        resultSet.getString("mobile_number"), resultSet.getDouble("salary"),
                                        resultSet.getInt("manager_id"));
                MyMethods.addtoWaiterLog("LOGIN.");
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean validate_Manager (String username, String password) {
        String SELECT_QUERY = "SELECT * FROM manager_Table WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = mySqlConnection.getConnection().prepareStatement(SELECT_QUERY);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employee = new Manager (resultSet.getInt("id"),                 resultSet.getString("first_name"),
                                        resultSet.getString("last_name"),       resultSet.getString("username"),
                                        resultSet.getString("password"),        resultSet.getString("email"),
                                        resultSet.getString("mobile_number"),   resultSet.getDouble("salary"),
                                        resultSet.getInt("admin"));
                MyMethods.addtoManagerLog("LOGIN.");
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean validate_inside (String username, String password) {
        String SELECT_QUERY = "SELECT * FROM manager_Table WHERE username = ? AND password = ?";

        try {

            PreparedStatement preparedStatement = mySqlConnection.getConnection().prepareStatement(SELECT_QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                VI = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                VII = resultSet.getInt("id");
                return true;
            }
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String VI;
    public int VII;

}
