package Application.Model;

import Application.Resources.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportModel {

    int detector;
    private Manager manager = ManagerDashBoardModel.manager;

    public ReportModel (int detector) {
        this.detector = detector;
        initializeEmployees();
    }

    public void ShowTable(TableView<Report> tableView, TableColumn<Report, Integer> colId, TableColumn<Report, String> colActivity) {
        ObservableList<Report> list = getReportList(detector);
        colId.setCellValueFactory(new PropertyValueFactory<Report, Integer>("ID"));
        colActivity.setCellValueFactory(new PropertyValueFactory<Report, String>("Activity"));
        tableView.setItems(list);
    }

    private ObservableList<Report> getReportList(int detecter) {
        String SELECT_QUERY;
        if (detecter == 0) {
            return getWaiterslist();
        } else if (detecter == 1) {
            SELECT_QUERY = "SELECT * FROM `manager_Log`";
        } else
            return getSalesList ();
        ObservableList <Report> ReportList = FXCollections.observableArrayList();
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            Report report;
            while (resultSet.next()) {
                report = new Report (resultSet.getInt("id"), resultSet.getString("Activity"));
                ReportList.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return ReportList;
    }

    private ObservableList<Report> getWaiterslist() {
        String SELECT_QUERY = "SELECT * FROM `waiter_Log`";
        ObservableList <Report> ReportList = FXCollections.observableArrayList();
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            Report report;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                for (Employee X :
                        manager.getEmployees()) {
                    if (X.getID() == id) {
                        report = new Report (id, resultSet.getString("Activity"));
                        ReportList.add(report);
                        break;
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return ReportList;
    }

    private ObservableList<Report> getSalesList() {
        String SELECT_QUERY = "SELECT * FROM `Cash_table`";
        ObservableList <Report> ReportList = FXCollections.observableArrayList();
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        Report report;
        try {
            while (resultSet.next()) {
                report = new Report (resultSet.getInt("order_id"), resultSet.getString("amount"));
                ReportList.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        SELECT_QUERY = "SELECT * FROM `CC_table`";
        resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                report = new Report (resultSet.getInt("order_id"), resultSet.getString("amount") +
                                    " paid by Card of id = " + resultSet.getString("crerditCard_number"));
                ReportList.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return ReportList;
    }

    public void initializeEmployees () {
        manager.getEmployees().clear();
        String SELECT_QUERY;
        if (manager.getAdmin() == 0)
            SELECT_QUERY = "SELECT * FROM `waiter_Table` WHERE `waiter_Table`.manager_id = '" + manager.getID() + "'";
        else
            SELECT_QUERY = "SELECT * FROM `waiter_Table`";

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
}
