package Application.Resources;

public class IndividualOrder {

    private int     id,
                    order_id,
                    quantity;
    private String  comment,
                    product_name;
    private double  price,
                    Total;
//

    public IndividualOrder(int id, int order_id, String product_name, int quantity, String comment, double total)
    {
        this.comment = comment;
        this.order_id = order_id;
        this.quantity = quantity;
        this.product_name = product_name;
        this.id = id;
        this.Total = total;
        price = this.Total/quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getComment() {
        return comment;
    }

    public String getProduct_name() {
        return product_name;
    }

    public double getPrice() {
        return price;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public double getTotal() {
        return Total;
    }
}
