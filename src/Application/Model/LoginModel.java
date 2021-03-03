package Application.Model;

import Application.Resources.*;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.sql.*;

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
        String CALL_PROCEDURE_1 = "{CALL sp_W_Login(?,?,?)}";
        String CALL_PROCEDURE_2 = "{CALL sp_get_Waiter(?)}";

        try {
            CallableStatement callableStatement = mySqlConnection.getConnection().prepareCall(CALL_PROCEDURE_1);
            callableStatement.setString(1, username);
            callableStatement.setString(2, password);
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.execute();

            int ID = callableStatement.getInt(3);
            callableStatement.close();
            System.out.println(ID);
            if (ID != - 1) {
                callableStatement = mySqlConnection.getConnection().prepareCall(CALL_PROCEDURE_2);
                callableStatement.setInt(1, ID);
                callableStatement.execute();
                ResultSet resultSet = callableStatement.getResultSet();
                resultSet.next();
                employee = new Waiter(
                                        resultSet.getInt("ID"),
                                        resultSet.getString("First_Name"),
                                        resultSet.getString("Last_Name"),
                                        resultSet.getString("Username"),
                                        resultSet.getInt("Age"),
                                        resultSet.getDate("Birthdate"),
                                        resultSet.getString("Password"),
                                        resultSet.getString("Mobile_Number"),
                                        resultSet.getDouble("Salary"),
                                        resultSet.getInt("Manager_ID"),
                                        resultSet.getBlob("Profile_Image"));
                WaiterDashBoardModel.waiter = (Waiter) employee;
                WaiterDashBoardModel.table = null;
                WaiterDashBoardModel.order = null;
                callableStatement.close();
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean validate_Manager (String username, String password) {
        String CALL_PROCEDURE_1 = "{CALL sp_M_Login(?,?,?)}";
        String CALL_PROCEDURE_2 = "{CALL sp_get_Manager(?)}";

        try {
            CallableStatement callableStatement = mySqlConnection.getConnection().prepareCall(CALL_PROCEDURE_1);
            callableStatement.setString(1, username);
            callableStatement.setString(2, password);
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.execute();

            int ID = callableStatement.getInt(3);
            callableStatement.close();

            if (ID != - 1) {
                callableStatement = mySqlConnection.getConnection().prepareCall(CALL_PROCEDURE_2);
                callableStatement.setInt(1, ID);
                callableStatement.execute();
                ResultSet resultSet = callableStatement.getResultSet();
                resultSet.next();
                employee = new Manager (
                        resultSet.getInt("ID"),
                        resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"),
                        resultSet.getString("Username"),
                        resultSet.getInt("Age"),
                        resultSet.getDate("Birthdate"),
                        resultSet.getString("Password"),
                        resultSet.getString("Mobile_Number"),
                        resultSet.getDouble("Salary"),
                        resultSet.getInt("Admin"),
                        resultSet.getBlob("Profile_Image"));
                ManagerDashBoardModel.manager = (Manager) employee;
                callableStatement.close();
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean validate_inside (String username, String password) {
        String CALL_PROCEDURE = "{CALL sp_M_Login(?,?,?)}";

        try {
            CallableStatement callableStatement = mySqlConnection.getConnection().prepareCall(CALL_PROCEDURE);
            callableStatement.setString(1, username);
            callableStatement.setString(2, password);
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.execute();

            int ID = callableStatement.getInt(3);
            callableStatement.close();
            if (ID != -1)
                return true;
            else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
