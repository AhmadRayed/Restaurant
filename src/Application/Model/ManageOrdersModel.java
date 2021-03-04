package Application.Model;

import Application.Controller.ButtonController;
import Application.Resources.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageOrdersModel implements Observable{

    private List <OrderButton> orderButtons = new ArrayList<>();
    private List <Order> orders = new ArrayList<>();

    private Window window = null;

    private java.sql.Date currentDate;
    private java.sql.Time currentTime;

    private Observer observer;
    private Order Gorder;
    private Table Gtable;

    public ManageOrdersModel()
    {
        currentDate = new java.sql.Date(new java.util.Date().getTime());
        currentTime = new java.sql.Time(new java.util.Date().getTime());

        orders.addAll(getOrders());
        orderButtons.addAll(getOrderButtons());
        SetCurrent();

    }
    private List <OrderButton> getOrderButtons () {
        List<OrderButton> ButtonList = new ArrayList<>();
        OrderButton orderButton;

        String SELECT_QUERY = "SELECT * FROM `Table`";
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                orderButton = new OrderButton (new Table (resultSet.getInt("ID"),
                                                resultSet.getString("Name")),
                                                0);
                ButtonList.add(orderButton);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ButtonList;
    }

    private void SetCurrent () {
        String SELECT_QUERY = "SELECT * FROM `Open_Order`";
        int table_id;
        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                    table_id = resultSet.getInt("table_id");
                    for (OrderButton X :
                            orderButtons) {
                        if (table_id == X.getTable().getId()) {
                            X.setCurrent(1);
                            break;
                        }
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    private List <Order> getOrders () {
        List <Order> orderList = new ArrayList<>();
        Order order;
        String SELECT_QUERY = "SELECT * FROM `Open_Order`";

        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {
            while (resultSet.next()) {
                order = new Order(  resultSet.getInt("Order_ID"),
                                    resultSet.getInt("Table_ID"),
                                    resultSet.getInt("Waiter_ID"),
                                    1,
                                    resultSet.getDate("Order_Date"),
                                    resultSet.getTime("Order_Time"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public void ShowButtons (GridPane gridPane) {

        int col = 0,
                row = 1,
                    i = 0;
        try {
            while (i < orderButtons.size()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Application/View/OrderButton.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ButtonController buttonController = fxmlLoader.getController();

                if (orderButtons.get(i).getCurrent() == 1)
                buttonController.setData(orderButtons.get(i), new ButtonOnAction() {
                    @Override
                    public void onAction(OrderButton orderButton) {
                        continue_Order(orderButton.getTable(), window);
                    }
                });
                else buttonController.setData(orderButtons.get(i), new ButtonOnAction() {
                    @Override
                    public void onAction(OrderButton orderButton) {
                        create_Order(orderButton.getTable());
                    }
                });

                if (col == 3){
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create_Order (Table table) {
        String CALL_PROCEDURE = "{CALL sp_Create_Order(?,?,?)}";
        Connection connection = MySqlConnection.MakeConnection().getConnection();


        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_PROCEDURE);
            callableStatement.setInt(1, table.getId());
            callableStatement.setInt(2, LoginModel.employee.getID());
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.execute();

            if (callableStatement.getInt(3) == 0)
                MyMethods.showAlert("!!ERROR ORDER NOT CREATED!!", "ERROR", Alert.AlertType.ERROR, window);
            else {
                Order order = getOrder(table.getId());
                orders.add(order);
//            MyMethods.addtoWaiterLog("CREATE AN ORDER WITH ID = " + order.getOrder_ID());
                Gorder = order;
                Gtable = table;
                notifyObserver();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Order getOrder (int table_ID) {
        Order order = null;
        String SELECT_QUERY = "SELECT * FROM `Open_Order` "+
                "WHERE `Table_ID` = " + table_ID;

        ResultSet resultSet = MySqlConnection.MakeConnection().getResultOfQuery(SELECT_QUERY);
        try {

            if (resultSet.next())
                order = new Order(  resultSet.getInt("Order_ID"),
                                    resultSet.getInt("Table_ID"),
                                    resultSet.getInt("Waiter_ID"),
                                1,
                                    resultSet.getDate("Order_Date"),
                                    resultSet.getTime("Order_Time"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public void continue_Order (Table table, Window window) {
        for (Order X:
                orders) {
            if (X.getTable_ID() == table.getId()) {
                if (X.getWaiter_ID() != LoginModel.employee.getID())
                    MyMethods.showAlert("!!This Table is not owned By you!!", "ERROR", Alert.AlertType.ERROR, window);
                else {
                    Gorder = X;
                    Gtable = table;
                    notifyObserver();
              }
            }
        }
    }

    @Override
    public void notifyObserver() {
        observer.updateOrder(Gtable, Gorder);
    }
}
