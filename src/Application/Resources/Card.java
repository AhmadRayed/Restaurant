package Application.Resources;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class Card implements Payment{

    private Order order;
    private double percent;
    private String cardNumber;

    Card (Order order, double percent, String cardNumber){
        this.order = order;
        this.percent = percent;
        this.cardNumber = cardNumber;
    }

    @Override
    public void Pay() {
//        String UPDATE_QUERY = "UPDATE `orders_table` SET `current` = '0', `total` = '" +
//                order.getTotal()+ "' WHERE `orders_table`.`order_id` = '" +
//                order.getOrder_ID()+ "'";
//        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
//        String INSERT_QUERY = "INSERT INTO `CC_table` (order_id, amount, crerditCard_number) VALUES ('" +
//                order.getOrder_ID() +"', '"+ order.getTotal() +"', '"+ cardNumber+"')";
//        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);
        String CALL_PROCEDURE = "{CALL sp_PAY(?,?,?,?)}";

        try {
            CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
            callableStatement.setString(1, "CARD");
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
//                order.getTotal()+ "' WHERE `orders_table`.`order_id` = '" +
//                order.getOrder_ID()+ "'";
//        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
//        String INSERT_QUERY = "INSERT INTO `CC_table` (order_id, amount, crerditCard_number) VALUES ('" +
//                order.getOrder_ID() +"', '"+ (order.getTotal() - (order.getTotal()*percent/100)) +"', '"+ cardNumber+"')";
//        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);
        String CALL_PROCEDURE = "{CALL sp_PAY(?,?,?,?)}";

        try {
            CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
            callableStatement.setString(1, "CARD");
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
