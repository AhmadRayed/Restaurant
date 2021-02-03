package Application.Model;

import Application.Controller.ButtonController;
import Application.Resources.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WaiterDashBoardModel {

//    For open new Windows
    Scene scene;
    Parent root;
    Stage window;
//
    public static Waiter waiter;
    public static Order order;
    public static Table table;
    public static Object T;
    ObservableList <IndividualOrder> individualOrders;

    private GridPane gridPane;

    public WaiterDashBoardModel(VBox vBox, GridPane gridPane) {
        this.gridPane = gridPane;
        List <Menu> Categories = getCategories();
        prepareProducts(Categories);
        ShowCategories(vBox, Categories);

    }

    public void setWaiterName (Label label) {
        label.setText(waiter.FullName());
    }

    private List <Menu> getCategories () {
        List <Menu> Categories = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM `caregory_Table`";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                Categories.add(new Category (resultSet.getInt("id"),
                                                resultSet.getString("category_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Categories;
    }

    private void prepareProducts (List <Menu> Categories) {
        String SELECT_QUERY = "SELECT * FROM `products_Table`";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                int id  = resultSet.getInt("category_id");
                for (Menu X :
                        Categories) {
                    if (X.getId() == id) {
                        ((Category) X).addProduct(
                                new Product(
                                        resultSet.getInt("id"),
                                        resultSet.getString("product_name"),
                                        resultSet.getString("description"),
                                        resultSet.getDouble("price"),
                                        resultSet.getString("status"),
                                        resultSet.getInt("category_id"),
                                        resultSet.getBlob("image")
                                )
                        );
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ShowCategories (VBox vBox, List <Menu> Categories) {
        vBox.getChildren().clear();
        int i = 0;
        try {
            while (i < Categories.size()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Application/View/CButton.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ButtonController buttonController = fxmlLoader.getController();
                buttonController.setCategory((Category) Categories.get(i), new ButtonCAction() {
                    @Override
                    public void CAction(Category category) {
                        ShowProducts(category.getProducts());                    }
                });

                vBox.getChildren().add(anchorPane);
                vBox.setMinWidth(Region.USE_COMPUTED_SIZE);
                vBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
                vBox.setMaxWidth(Region.USE_COMPUTED_SIZE);
                vBox.setMinHeight(Region.USE_COMPUTED_SIZE);
                vBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
                vBox.setMaxHeight(Region.USE_COMPUTED_SIZE);
                vBox.setMargin(anchorPane, new Insets(10));
                i ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ShowProducts (List <Menu> products) {
        gridPane.getChildren().clear();
        int col = 0,
                row = 1,
                    i = 0;
        try {
            while (i < products.size()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Application/View/ProductView.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                ButtonController buttonController = fxmlLoader.getController();
                buttonController.setProduct((Product) products.get(i), new ProductOnView() {
                    @Override
                    public void onView(Product product) {
                        try {
                            if (product.getStatus().equalsIgnoreCase("Not Available")) {
                                MyMethods.showAlert("!This Products is NOT Available right know!",
                                                    "Product Not Available", Alert.AlertType.INFORMATION, window);
                                return;
                            }
                            QuantityModel.product = product;
                            openNewWindow("/Application/View/Quantity.fxml", "Quantity");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                if (col == 3) {
                    col = 0;
                    row ++;
                }

                gridPane.add(anchorPane, col ++, row);
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMargin(anchorPane, new Insets(10));
                i ++;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void ShowTable(TableView<IndividualOrder> tableview, TableColumn<IndividualOrder, String> colName,
                          TableColumn<IndividualOrder, Double> colPrice, TableColumn<IndividualOrder, Integer> colQuantity,
                          TableColumn<IndividualOrder, Double> colTotal) {
        if (order != null){
            individualOrders = getIndividualOrderList ();
            colName.setCellValueFactory(new PropertyValueFactory<IndividualOrder, String>("product_name"));
            colPrice.setCellValueFactory(new PropertyValueFactory<IndividualOrder, Double>("price"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<IndividualOrder, Integer>("quantity"));
            colTotal.setCellValueFactory(new PropertyValueFactory<IndividualOrder, Double>("Total"));
            tableview.setItems(individualOrders);

        }
    }

    private ObservableList <IndividualOrder> getIndividualOrderList() {
        ObservableList <IndividualOrder> IndividualOrderList = FXCollections.observableArrayList();
        IndividualOrder individualOrder;
        String SELECT_QUERY = "SELECT o.id, o.order_id, o.product_id, o.quantity, o.comment, p.product_name, p.price "+
                                "FROM order_item_table o INNER JOIN products_Table p WHERE o.product_id = p.id";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                if (order.getOrder_ID() == resultSet.getInt("order_id")) {
                    individualOrder = new IndividualOrder(  resultSet.getInt("id"),
                                                            resultSet.getInt("order_id"),
                                                            resultSet.getString("product_name"),
                                                            resultSet.getInt("product_id"),
                                                            resultSet.getInt("quantity"),
                                                            resultSet.getString("comment"),
                                                            resultSet.getDouble("price"));
                    IndividualOrderList.add(individualOrder);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IndividualOrderList;
    }

    private void openNewWindow(String resource, String Title) throws IOException {
        root = FXMLLoader.load(getClass().getResource(resource));
        scene = new Scene(root);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setAlwaysOnTop(true);
        window.setIconified(false);
        window.setTitle(Title);
        window.showAndWait();
    }

    public void setTotal(Label lblTotal) {
        if (order != null)
            lblTotal.setText(String.format("%.2f", totalPrice()));
    }

    public double totalPrice ()
    {
        double Total = 0;
        for (IndividualOrder X :
                individualOrders) {
            Total += X.getTotal();
        }

        return Total;
    }

    public void setTableName(Label lblTableName) {
        if (table != null)
            lblTableName.setText(table.getName());
    }

    public static void setT(Object t) {
        T = t;
    }

    public static Object getT() {
        return T;
    }

    public void ShowEmpty(TableView<IndividualOrder> tableview, TableColumn<IndividualOrder, String> colName, TableColumn<IndividualOrder, Double> colPrice, TableColumn<IndividualOrder, Integer> colQuantity, TableColumn<IndividualOrder, Double> colTotal) {
        colName.setCellValueFactory(new PropertyValueFactory<IndividualOrder, String>("product_name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<IndividualOrder, Double>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<IndividualOrder, Integer>("quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<IndividualOrder, Double>("Total"));
        tableview.setItems(null);
    }

    public void setPrice () {
        if (order != null)
            order.setTotal(totalPrice ());
    }
}

