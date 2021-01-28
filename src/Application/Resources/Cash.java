package Application.Resources;

public class Cash implements Payment {

    private Order order;
    private double percent;

    Cash (Order order, double percent) {
        this.order = order;
        this.percent = percent;
    }

    @Override
    public void Pay() {
        String UPDATE_QUERY = "UPDATE `orders_table` SET `current` = '0', `total` = '" +
                order.getTotal()+ "' WHERE `orders_table`.`order_id` = '" +
                order.getOrder_ID()+ "'";
        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
        String INSERT_QUERY = "INSERT INTO `Cash_table` (order_id, amount) VALUES ('" +
                order.getOrder_ID() +"', '"+ order.getTotal() +"')";
        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);

    }

    @Override
    public void Discount() {
        String UPDATE_QUERY = "UPDATE `orders_table` SET `current` = '0', `total` = '" +
                order.getTotal() + "' WHERE `orders_table`.`order_id` = '" +
                order.getOrder_ID()+ "'";
        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
        String INSERT_QUERY = "INSERT INTO `Cash_table` (order_id, amount) VALUES ('" +
                order.getOrder_ID() +"', '"+ (order.getTotal() - (order.getTotal()*percent/100)) +"')";
        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);
    }
}
