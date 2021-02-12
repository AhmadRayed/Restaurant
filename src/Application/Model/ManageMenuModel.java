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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String SELECT_QUERY = "SELECT * FROM products_Table P INNER JOIN caregory_Table C WHERE P.category_id = C.id";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                    product = new Product(  resultSet.getInt("id"),
                                            resultSet.getString("product_name"),
                                            resultSet.getString("description"),
                                            resultSet.getDouble("price"),
                                            resultSet.getString("status"),
                                            resultSet.getInt("category_id"),
                                            resultSet.getBlob("image"));
                    product.setCategory_name(resultSet.getString("category_name"));
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
        String SELECT_QUERY = "SELECT * FROM `caregory_Table`";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                categoriesNames.add(resultSet.getString("category_name"));
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
        String SELECT_QUERY = "SELECT * FROM `caregory_Table` WHERE `caregory_Table`.`category_name` = '" + category + "'";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            if (resultSet.next())
                    category_id = resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String INSERT_QUERY = "INSERT INTO `products_Table` " +
                "(`product_name`,`description`,`price`,`status`,`category_id`,`image`) " +
                "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = MySqlConnection.MakeConnection().getConnection().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4,status);
            preparedStatement.setInt(5, category_id);
            preparedStatement.setBlob(6, fileInputStream);
            preparedStatement.executeQuery();
            MyMethods.addtoManagerLog("ADD NEW ITEM WITH NAME = " + name);
            fileInputStream = null;
            notifyObserver();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void DeleteProduct(TableView<Product> tableView, Window window) {
        int id = tableView.getSelectionModel().getSelectedItem().getId();
        String DELETE_QUERY = "DELETE FROM `products_Table` WHERE `products_Table`.id = '" + id +"'";

        String SELECT_QUERY = "SELECT * FROM `orders_table` WHERE `orders_table`.current = '1' ";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            if (resultSet.next())
                MyMethods.showAlert("Close ALL orders First", "ERROR", Alert.AlertType.ERROR, window);
            else {
                MySqlConnection.MakeConnection().executeQuery(DELETE_QUERY, null);
                notifyObserver();
                MyMethods.addtoManagerLog("DELETE ITEM WITH ID = " + id);
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
        String UPDATE_QUERY = "UPDATE `products_Table` SET `status` = '"+ status +"' WHERE `products_Table`.id = '"+ id +"'";
        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
        notifyObserver();
    }


    @Override
    public void notifyObserver() {
        ((Observer) object).updateTable();
    }

}
