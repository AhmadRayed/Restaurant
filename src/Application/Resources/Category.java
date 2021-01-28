package Application.Resources;

import java.util.ArrayList;
import java.util.List;

public class Category extends Menu {
    private List <Menu> products;

    public Category (int id, String Category_name) {
        this.id = id;
        this.name = Category_name;
        products = new ArrayList<>();
    }

    public void addProduct (Menu product) {
        products.add(product);
    }

    public List<Menu> getProducts() {
        return products;
    }
}
