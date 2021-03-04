package Application.Model;

import Application.Resources.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.sql.*;

public class ManageMenuModel implements Observable{

    private FileInputStream fileInputStream;
    private File file;
    public static Object object;

    public void ShowTable(TableView<Product> tableView, TableColumn<Product, Integer> colID,
                          TableColumn<Product, String> colName, TableColumn<Product, String> colDescription,
                          TableColumn<Product, Double> colPrice, TableColumn<Product, String> colCategory,
                          TableColumn<Product, String> colStatus) {

        colID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("category_name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<Product, String>("status"));
        tableView.setItems(getProductsList());
    }

    private ObservableList <Product> getProductsList ()
    {
        ObservableList <Product> products = FXCollections.observableArrayList();
        Product product;
        String CALL_PROCEDURE = "{CALL sp_get_Products()}";
        try {
            CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();
            while (resultSet.next()) {
                    product = new Product(  resultSet.getInt("Product_ID"),
                            resultSet.getString("Product_Name"),
                            resultSet.getString("Product_Description"),
                            resultSet.getDouble("Price"),
                            resultSet.getString("Status"),
                            resultSet.getInt("Category_ID"),
                            resultSet.getString("Category_Name"),
                            resultSet.getBlob("Product_Image"));
                    products.add(product);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    public void ShowCategory(ComboBox <String> cbCategory) {
        cbCategory.setItems(getCategoriesNames());
    }

    private ObservableList <String> getCategoriesNames ()
    {
        ObservableList <String> categoriesNames = FXCollections.observableArrayList();
        String SELECT_QUERY = "SELECT * FROM `Category`";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                categoriesNames.add(resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoriesNames;

    }

    public void ShowStatus(ComboBox<String> cbStatus) {
        ObservableList <String> StatusOptions = FXCollections.observableArrayList();
        StatusOptions.add("Available");
        StatusOptions.add("Not Available");
        cbStatus.setItems(StatusOptions);
    }

    public void Browser(TextField txtBrowser, ImageView imProduct, Window window) throws MalformedURLException, FileNotFoundException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        file = chooser.showOpenDialog(window);
        if (file != null) {
            String imagepath = file.toURI().toURL().toString();
            txtBrowser.setText(imagepath);
            Image image = new Image(imagepath);
            imProduct.setImage(image);
            fileInputStream = new FileInputStream(file);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please Select an Image!!");
            alert.showAndWait();
        }
    }

    public void AddProduct(String name, String description, String Price, String category, String status, Window window) {

        double price = 0;
        if (!Price.isEmpty())
            price = Double.parseDouble(Price);

        if (name.isEmpty() || category == null || status == null || fileInputStream == null) {
            MyMethods.showAlert("Please Complete All required fields.", "Not Complete", Alert.AlertType.ERROR, window);
            return;
        }

        int category_id = 0;
        String SELECT_QUERY = "SELECT * FROM `Category` WHERE `Category`.`Name` = '" + category + "'";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            if (resultSet.next())
                    category_id = resultSet.getInt("ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String CALL_PROCEDURE = "{CALL sp_Add_Product(?,?,?,?,?,?,?)}";

        try {
            CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
            callableStatement.setString(1, name);
            callableStatement.setString(2, description);
            callableStatement.setString(3, status);
            callableStatement.setBlob(4, fileInputStream);
            callableStatement.setDouble(5, price);
            callableStatement.setInt(6, category_id);

            callableStatement.registerOutParameter(7, Types.INTEGER);
            callableStatement.execute();
            if (callableStatement.getInt(7) == 0)
                System.out.println("ERROR");

//            MyMethods.addtoManagerLog("ADD NEW ITEM WITH NAME = " + name);
            else {
                fileInputStream = null;
                notifyObserver();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void DeleteProduct(TableView<Product> tableView, Window window) {
        int id = tableView.getSelectionModel().getSelectedItem().getId();
        String CALL_PROCEDURE = "{CALL sp_Delete_Product(?,?)}";

        String SELECT_QUERY = "SELECT * FROM `Open_Order`";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            if (resultSet.next())
                MyMethods.showAlert("Close ALL orders First", "ERROR", Alert.AlertType.ERROR, window);
            else {
                CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);

                callableStatement.setInt(1, id);
                callableStatement.registerOutParameter(2, Types.INTEGER);

                callableStatement.execute();
                if (callableStatement.getInt(2) == 0)
                    System.out.println("ERROR");
                notifyObserver();
//                MyMethods.addtoManagerLog("DELETE ITEM WITH ID = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateStatus(TableView<Product> tableView, String status, Window window) {
        int id = tableView.getSelectionModel().getSelectedItem().getId();
        if (status == null) {
            MyMethods.showAlert("Please select the status first", "ERROR", Alert.AlertType.ERROR, window);
            return;
        }
        String UPDATE_QUERY = "UPDATE `Product` SET `Status` = '"+ status +"' WHERE `Product`.ID = '"+ id +"'";
        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
        notifyObserver();
    }


    @Override
    public void notifyObserver() {
        ((Observer) object).updateTable();
    }

}
