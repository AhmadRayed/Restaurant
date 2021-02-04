package Application.Model;

import Application.Controller.ManageManagersController;
import Application.Resources.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ManageManagersModel implements Observable{

    public static Object object;
    private Manager manager = ManagerDashBoardModel.manager;

    public void ShowTable(TableView<Employee> tableView, TableColumn<Employee, Integer> colID,
                          TableColumn<Employee, String> colFName, TableColumn<Employee, String> colLName,
                          TableColumn<Employee, String> colUsername, TableColumn<Employee, String> colPassword,
                          TableColumn<Employee, String> colEmail, TableColumn<Employee, String> colMobile,
                          TableColumn<Employee, Double> colSalary) {
        ObservableList<Employee> list = getManagerList ();
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

    private ObservableList<Employee> getManagerList() {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        initializeEmployees();
        List<Employee> employees = manager.getEmployees();
        int i = 0;
        while (i < employees.size()) {
            list.add(employees.get(i));
            i ++;
        }
        return list;
    }

    private void initializeEmployees() {
        manager.getEmployees().clear();
        String SELECT_QUERY = "SELECT * FROM `manager_Table` WHERE admin = '0'";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                manager.addEmployee(new Manager (   resultSet.getInt("id"),                 resultSet.getString("first_name"),
                                                    resultSet.getString("last_name"),       resultSet.getString("username"),
                                                    resultSet.getString("password"),        resultSet.getString("email"),
                                                    resultSet.getString("mobile_number"),   resultSet.getDouble("salary"),
                                                    resultSet.getInt("admin")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ShowOption(ComboBox<String> cbOption) {
        ObservableList <String> Options = FXCollections.observableArrayList();
        Options.add("first_name");
        Options.add("last_name");
        Options.add("password");
        Options.add("email");
        Options.add("mobile_number");
        Options.add("salary");
        cbOption.setItems(Options);
    }

    public void UpdateManager(TableView<Employee> tableView, String text, String option) {

        Manager m = (Manager) tableView.getSelectionModel().getSelectedItem();
        if (text.isEmpty() || option == null)    return;
        manager.updateEmployee(m, option, text, false);
        MyMethods.addtoManagerLog("UPDATE MANAGER WITH ID = " + m.getId());
        notifyObserver();
    }

    public void DeleteManager(TableView<Employee> tableView, Window window) {
        Manager m = (Manager) tableView.getSelectionModel().getSelectedItem();

        String alert = "Remove all waiters belongs to this manager!!";

        String DELETE_QUERY = "DELETE FROM `manager_Table` WHERE `manager_Table`.id = '"+ m.getId() +"'";
        String SELECT_QUERY = "SELECT * FROM `waiter_Table` WHERE manager_id = '"+ m.getId() +"'";

        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            if (resultSet.next()) {
                MyMethods.showAlert(alert, "ERROR", Alert.AlertType.ERROR, window);
            }
            else {
                MySqlConnection.MakeConnection().executeQuery(DELETE_QUERY, "error in delete Query");
                notifyObserver();
                MyMethods.addtoManagerLog("DELETE MANAGER WITH ID = " + m.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AddManager(String firstname, String lastname, String username, String password,
                           String email, String mobile, String salary) {

        String INSERT_QUERY = "INSERT INTO `manager_Table` " +
                "(`first_name`,`last_name`,`username`,`password`,`email`,`mobile_number`,`salary`,`admin`)" +
                "VALUES(?,?,?,?,?,?,?,0)";
        try {
            PreparedStatement preparedStatement = MySqlConnection.MakeConnection().getConnection().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, mobile);
            preparedStatement.setDouble(7, Double.parseDouble(salary));

            preparedStatement.executeQuery();
            MyMethods.addtoManagerLog("ADD NEW MANAGER WITH NAME = " + firstname + " " + lastname);
            notifyObserver();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyObserver() {
        ((Observer) object).updateTable();
    }
}
