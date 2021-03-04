package Application.Resources;

import java.sql.Blob;

public class Product extends Menu {
    private String  description,
                    status;
    private int     category_ID;
    private double  price;
    private Blob    image;
    private String category_name;

    public Product(int id, String product_name, String description, double price, String status, int category_ID, String category_name, Blob image)
    {
        this.id = id;
        this.name = product_name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.category_ID = category_ID;
        this.image = image;
        this.category_name = category_name;

    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Blob getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public int getCategory_ID() {
        return category_ID;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_name() {
        return category_name;
    }
}
