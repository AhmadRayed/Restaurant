package Application.Controller;

import Application.Model.WaiterDashBoardModel;
import Application.Resources.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ButtonController  {

    //    Button to create order
    private OrderButton orderButton;
    private ButtonOnAction buttonOnAction;

    @FXML
    public Button btNew;

    @FXML
    private void Action(ActionEvent event) {
        buttonOnAction.onAction(orderButton);
        ((Node) event.getSource()).getScene().getWindow().hide();

    }

    public void setData (OrderButton orderButton, ButtonOnAction buttonOnAction){
        this.orderButton = orderButton;
        this.buttonOnAction = buttonOnAction;
        btNew.setText(orderButton.getTable().getName());
        if (orderButton.getCurrent() == 1)
            btNew.setStyle("-fx-background-color: #e84118");
        else
            btNew.setStyle("-fx-background-color: #44bd32");
    }

//
//    Button to select category
    private ButtonCAction buttonCAction;
    private Category category;

    @FXML
    private Button btcategory;

    @FXML
    private void CAction(ActionEvent event) {
        buttonCAction.CAction(category);
    }

    public void setCategory (Category category, ButtonCAction buttonCAction) {
        this.category = category;
        this.buttonCAction = buttonCAction;
        btcategory.setText(category.getName());
    }
//
//      productView
    @FXML
    private Label lbStatus;

    @FXML
    private Label lbName;

    @FXML
    private Label lbPrice;

    @FXML
    private ImageView ivImage;

    @FXML
    private Label lbDescription;

    @FXML
    private void SelectQuantity(MouseEvent mouseEvent) {
        if (WaiterDashBoardModel.order != null)
                productOnView.onView(product);
        else
            MyMethods.showAlert("Select Table First!!", "ERROR", Alert.AlertType.ERROR, null);
    }

    private Product product;
    private ProductOnView productOnView;

    public void setProduct (Product product, ProductOnView productOnView) throws SQLException {
        this.product = product;
        this.productOnView = productOnView;
        lbName.setText(product.getName());
        lbPrice.setText(product.getPrice()+" $");
        lbStatus.setText(product.getStatus());
        lbDescription.setText(product.getDescription());
        ivImage.setImage(new Image(product.getImage().getBinaryStream()));
    }
}
