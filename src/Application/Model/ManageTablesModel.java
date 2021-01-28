package Application.Model;

import Application.Resources.MyMethods;
import Application.Resources.MySqlConnection;
import Application.Resources.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageTablesModel {

    private ObservableList <Table> getTablesList () {
        ObservableList<Table> tableList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT * FROM `tables_Table`";

        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            Table table;
            while (resultSet.next()) {
                table = new Table(resultSet.getInt("id"), resultSet.getString("table_name"));
                tableList.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return tableList;
    }

    public void ShowTable(TableView <Table> tableView, TableColumn <Table, Integer> colID, TableColumn <Table, String> colName) {
        ObservableList <Table> list = getTablesList();
        colID.setCellValueFactory(new PropertyValueFactory <Table, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory <Table, String>("name"));
        tableView.setItems(list);
    }

    public void AddTable (Window window, TableView<Table> tableView, TableColumn<Table, String> colName, TableColumn<Table, Integer> colID, String text) {
        String alert = "Enter a table name!!";
        String error = "ERROR!!! While adding new table!";
        if (!text.isEmpty()) {
            String DATABASE_QUERY = "INSERT INTO `tables_Table` (table_name) VALUES('" + text + "')";
            MySqlConnection.MakeConnection().executeQuery (DATABASE_QUERY, error);
            MyMethods.addtoWaiterLog("ADD A TABLE UNDER NAME " + text);
            ShowTable(tableView, colID, colName);
        }
        else MyMethods.showAlert(alert, "ERROR", Alert.AlertType.ERROR, window);
    }

    public void DeleteTable (Window window, TableView<Table> tableView, TableColumn<Table, String> colName, TableColumn<Table, Integer> colID, String text) {
        Table table = (Table) tableView.getSelectionModel().getSelectedItem();
        String error = "ERROR!!! While deleting the table!";
        String alert = "!!Close Order First!!";

        String SELECT_QUERY = "SELECT * FROM `orders_table` WHERE `table_id` = '"+ table.getId() +"' AND `current` = 1";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            if (resultSet.next()) {
                MyMethods.showAlert(alert, "ERROR", Alert.AlertType.ERROR, window);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String DELETE_QUERY = "DELETE FROM `tables_Table` WHERE `tables_Table`.`id` = '" + table.getId() + "'";
        MySqlConnection.MakeConnection().executeQuery (DELETE_QUERY, error);
        MyMethods.addtoWaiterLog("REMOVE A TABLE UNDER NAME " + text);
        ShowTable (tableView, colID, colName);
    }
}
