package Application.Resources;

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
        String UPDATE_QUERY = "UPDATE `orders_table` SET `current` = '0', `total` = '" +
                order.getTotal()+ "' WHERE `orders_table`.`order_id` = '" +
                order.getOrder_ID()+ "'";
        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
        String INSERT_QUERY = "INSERT INTO `CC_table` (order_id, amount, crerditCard_number) VALUES ('" +
                order.getOrder_ID() +"', '"+ order.getTotal() +"', '"+ cardNumber+"')";
        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);
    }

    @Override
    public void Discount() {
        String UPDATE_QUERY = "UPDATE `orders_table` SET `current` = '0', `total` = '" +
                order.getTotal()+ "' WHERE `orders_table`.`order_id` = '" +
                order.getOrder_ID()+ "'";
        MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, null);
        String INSERT_QUERY = "INSERT INTO `CC_table` (order_id, amount, crerditCard_number) VALUES ('" +
                order.getOrder_ID() +"', '"+ (order.getTotal() - (order.getTotal()*percent/100)) +"', '"+ cardNumber+"')";
        MySqlConnection.MakeConnection().executeQuery(INSERT_QUERY, null);
    }
}
