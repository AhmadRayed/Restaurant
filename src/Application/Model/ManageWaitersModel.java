package Application.Model;

import Application.Controller.ManageWaitersController;
import Application.Resources.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ManageWaitersModel implements Observable{

    public static Object object;

    private Manager manager = ManagerDashBoardModel.manager;
    private int flag = manager.getAdmin();

    public void ShowTable(TableView<Employee> tableView, TableColumn<Employee, Integer> colID,
                          TableColumn<Employee, String> colFName, TableColumn<Employee, String> colLName,
                          TableColumn<Employee, String> colUsername, TableColumn<Employee, String> colPassword,
                          TableColumn<Employee, String> colEmail, TableColumn<Employee, String> colMobile,
                          TableColumn<Employee, Double> colSalary) {

        ObservableList <Employee> list = getEmployeeList ();
        colID.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        colFName.setCellValueFactory(new PropertyValueFactory <Employee, String>("First_Name"));
        colLName.setCellValueFactory(new PropertyValueFactory <Employee, String>("Last_Name"));
        colUsername.setCellValueFactory(new PropertyValueFactory <Employee, String>("UserName"));
        colPassword.setCellValueFactory(new PropertyValueFactory <Employee, String>("Password"));
        colEmail.setCellValueFactory(new PropertyValueFactory <Employee, String>("Email"));
        colMobile.setCellValueFactory(new PropertyValueFactory <Employee, String>("Mobile_Number"));
        colSalary.setCellValueFactory(new PropertyValueFactory <Employee, Double>("Salary"));
        tableView.setItems(list);
    }

    public void initializeEmployees () {
        manager.getEmployees().clear();
        String SELECT_QUERY;
        if (flag == 0)
            SELECT_QUERY = "SELECT * FROM `Waiter` WHERE `Waiter`.Manager_ID = '" + manager.getID() + "'";
        else
            SELECT_QUERY = "SELECT * FROM `Waiter`";

        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                manager.addEmployee(new Waiter(   resultSet.getInt("ID"),
                        resultSet.getString("First_Name"),
                        resultSet.getString("Last_Name"),
                        resultSet.getString("Username"),
                        resultSet.getInt("Age"),
                        resultSet.getDate("Birthdate"),
                        resultSet.getString("Password"),
                        resultSet.getString("Mobile_Number"),
                        resultSet.getDouble("Salary"),
                        resultSet.getInt("Manager_ID"),
                        resultSet.getBlob("Profile_Image")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Employee> getEmployeeList() {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        initializeEmployees();
        List <Employee> employees = manager.getEmployees();
        int i = 0;
        while (i < employees.size()) {
            list.add(employees.get(i));
            i ++;
        }
        return list;
    }

    public void ShowOption(ComboBox<String> cbOption) {
        ObservableList <String> Options = FXCollections.observableArrayList();
        Options.add("First_Name");
        Options.add("Last_Name");
        Options.add("Password");
        Options.add("email");
        Options.add("Mobile_Number");
        Options.add("Salary");
        cbOption.setItems(Options);
    }

    public void UpdateWaiter(TableView<Employee> tableView, String text, String option) {
        Waiter waiter = (Waiter) tableView.getSelectionModel().getSelectedItem();
        if (text.isEmpty() || option == null)     return;
        manager.updateEmployee(waiter, option, text, true);
//        MyMethods.addtoManagerLog("UPDATE WAITER WITH ID = " + waiter.getID());
        notifyObserver();

    }

    public void DeleteWaiter(TableView<Employee> tableView, Window window) {
        Waiter waiter = (Waiter) tableView.getSelectionModel().getSelectedItem();

        String alert = "Close all Tables of this waiter!!";

//        String DELETE_QUERY = "DELETE FROM `waiter_Table` WHERE `waiter_Table`.id = '"+ waiter.getID() +"'";
        String SELECT_QUERY = "SELECT * FROM `Open_Order` WHERE Waiter_ID = '"+ waiter.getID()+"'";
        String CALL_PROCEDURE = "{CALL sp_Remove_Waiter(?)}";

        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            if (resultSet.next()) {
                MyMethods.showAlert(alert, "ERROR", Alert.AlertType.ERROR, window);
            }
            else {
                CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
                callableStatement.setInt(1, waiter.getID());
                callableStatement.execute();
//                MySqlConnection.MakeConnection().executeQuery(DELETE_QUERY, "error in delete Query");
                notifyObserver();
//                MyMethods.addtoManagerLog("DELETE WAITER WITH ID = " + waiter.getID());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean flag () {
        return (flag == 1);
    }

    public void AddWaiter(String firstname, String lastname, String username, String password,
                          String email, String mobile, String salary, ComboBox <String> cbManager, Window window) {
        int id = manager.getID();
        if (flag == 1) {
            if (cbManager.getSelectionModel().isEmpty()) {
                MyMethods.showAlert("Please Select a Manager", "ERROR", Alert.AlertType.ERROR, window);
                return;
            } else
                id = getManagerID(cbManager.getSelectionModel().getSelectedItem());
        }

        String INSERT_QUERY = "INSERT INTO `waiter_Table` " +
                                "(`first_name`,`last_name`,`username`,`password`,`email`,`mobile_number`,`salary`,`manager_id`)" +
                                "VALUES(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = MySqlConnection.MakeConnection().getConnection().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, mobile);
            preparedStatement.setDouble(7, Double.parseDouble(salary));
            preparedStatement.setInt(8, id);
            preparedStatement.executeQuery();
            MyMethods.addtoManagerLog("ADD NEW WAITER WITH NAME = " + firstname + " " + lastname);
            notifyObserver();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void ShowManager(ComboBox <String> cbManager) {
        ObservableList <String> managers = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT * FROM `manager_Table` WHERE `manager_Table`.admin = '0'";

        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next())
                managers.add (resultSet.getString("username"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cbManager.setItems(managers);
    }

    private int getManagerID (String username) {
        String SELECT_QUERY = "SELECT * FROM `manager_Table` WHERE `manager_Table`.username = '" + username + "'";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            if (resultSet.next())
                    return resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void notifyObserver() {
        ((Observer)object).updateTable();
    }
}
