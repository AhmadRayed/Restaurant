package Application.Resources;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class Cash implements Payment {

    private Order order;
    private double percent;

    Cash (Order order, double percent) {
        this.order = order;
        this.percent = percent;
    }

    @Override
    public void Pay() {
//        String UPDATE_QUERY = "UPDATE `orders_table` SET `current` = '0', `total` = '" +
//                order.getTotal()+ "' WHERE `orders_table`.`order_id` = '" +
//                order.getOrder_ID()+ "'";
//        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
//        String INSERT_QUERY = "INSERT INTO `Cash_table` (order_id, amount) VALUES ('" +
//                order.getOrder_ID() +"', '"+ order.getTotal() +"')";
//        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);
        String CALL_PROCEDURE = "{CALL sp_PAY(?,?,?,?)}";

        try {
            CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
            callableStatement.setString(1, "CASH");
            callableStatement.setInt(2, order.getOrder_ID());
            callableStatement.setDouble(3, order.getTotal());
            callableStatement.registerOutParameter(4, Types.INTEGER);
            callableStatement.execute();
            if (callableStatement.getInt(4) == 0)
                System.out.println("Error in Pay");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void Discount() {
//        String UPDATE_QUERY = "UPDATE `orders_table` SET `current` = '0', `total` = '" +
//                order.getTotal() + "' WHERE `orders_table`.`order_id` = '" +
//                order.getOrder_ID()+ "'";
//        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
//        String INSERT_QUERY = "INSERT INTO `Cash_table` (order_id, amount) VALUES ('" +
//                order.getOrder_ID() +"', '"+ (order.getTotal() - (order.getTotal()*percent/100)) +"')";
//        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);
        String CALL_PROCEDURE = "{CALL sp_PAY(?,?,?,?)}";

        try {
            CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
            callableStatement.setString(1, "CASH");
            callableStatement.setInt(2, order.getOrder_ID());
            callableStatement.setDouble(3, order.getTotal()*percent/100);
            callableStatement.registerOutParameter(4, Types.INTEGER);
            callableStatement.execute();
            if (callableStatement.getInt(4) == 0)
                System.out.println("Error in Pay");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
