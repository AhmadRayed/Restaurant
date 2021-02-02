package Application.Model;

import Application.Controller.CategoryMenuController;
import Application.Resources.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMenuModel implements Observable {

    private Observer observer = (Observer) ManageMenuModel.object;
    private Observer observerC;

    public CategoryMenuModel(Object t) {
        observerC = (Observer) t;
    }

    private ObservableList <Category> getCategoriesList () {
        ObservableList <Category> CategoryList = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT * FROM `caregory_Table`";

        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            Category category;
            while (resultSet.next()) {
                category = new Category(resultSet.getInt("id"), resultSet.getString("category_name"));
                CategoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return CategoryList;
    }
    public void ShowTable(TableView<Category> tableView, TableColumn<Category, Integer> colID, TableColumn<Category, String> colName) {
        ObservableList <Category> list = getCategoriesList();
        colID.setCellValueFactory(new PropertyValueFactory<Category, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory <Category, String>("name"));
        tableView.setItems(list);
    }

    public void AddCategory(Window window, String text) {
        String alert = "Enter a Category name!!";
        String error = "ERROR!!! While adding new Category!";
        if (!text.isEmpty()) {
            String DATABASE_QUERY = "INSERT INTO `caregory_Table` (category_name) VALUES('" + text + "')";
            MySqlConnection.MakeConnection().executeQuery (DATABASE_QUERY, error);
            MyMethods.addtoManagerLog("ADD CATEGORY UNDER NAME = " + text);
            notifyObserver();
        }
        else MyMethods.showAlert(alert, "ERROR", Alert.AlertType.ERROR, window);
    }

    public void DeleteCategory(Window window, TableView<Category> tableView) {
        Category category = tableView.getSelectionModel().getSelectedItem();
        String error = "ERROR!!! While deleting the Category!";
        String alert = "!!Their Still Products of this Category!!";

        String SELECT_QUERY = "SELECT * FROM `products_Table` WHERE `category_id` = '"+ category.getId() +"'";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            if (resultSet.next()) {
                MyMethods.showAlert(alert, "ERROR", Alert.AlertType.ERROR, window);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String DELETE_QUERY = "DELETE FROM `caregory_Table` WHERE `caregory_Table`.`id` = '" + category.getId() + "'";
        MySqlConnection.MakeConnection().executeQuery (DELETE_QUERY, error);
        MyMethods.addtoManagerLog("DELETE CATEGORY WITH ID = " + category.getId());
        notifyObserver();
    }

    public void UpdateCategory(TableView<Category> tableView, String text) {
        Category category = (Category) tableView.getSelectionModel().getSelectedItem();
        String error = "ERROR!!! While updating the Category!";

        String UPDATE_QUERY = "UPDATE `caregory_Table` SET `category_name` = '"+ text +
                            "' WHERE `id` = '"+ category.getId() +"'";
        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, error);
        MyMethods.addtoManagerLog("UPDATE CATEGORY WITH ID = " + category.getId());
        notifyObserver();
    }

    @Override
    public void notifyObserver() {
        observerC.updateTable();
        observer.updateOrder(null, null);
    }
}
