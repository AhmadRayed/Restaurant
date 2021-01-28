package Application.Model;

import Application.Controller.WaiterDashBoardController;
import Application.Resources.MyMethods;
import Application.Resources.Payment;
import Application.Resources.PaymentFactory;

public class PaymentModel {

    private Payment payment;

    public void ByCard(String discount, String cardNumber) {
        String C = "";
        double percent = 0;
        if (!discount.isEmpty())
            percent = Double.parseDouble(discount);
        if (!cardNumber.isEmpty())
            C += cardNumber;

        payment = PaymentFactory.getPayment("CARD", WaiterDashBoardModel.order, percent, C);
        if (percent == 0)
            payment.Pay();
        else
            payment.Discount();

        MyMethods.addtoWaiterLog("CLOSE AN ORDER WITH ID = " + WaiterDashBoardModel.order.getOrder_ID() +
                "WITH TOTAL PRICE = " + WaiterDashBoardModel.order.getTotal() + "BY CARD");
        WaiterDashBoardModel.order = null;
        WaiterDashBoardModel.table = null;
    }

    public void ByCash(String discount) {
        double percent = 0;
        if (!discount.isEmpty())
            percent = Double.parseDouble(discount);
        payment = PaymentFactory.getPayment("CASH", WaiterDashBoardModel.order, percent, null);

        if (percent == 0)
            payment.Pay();
        else
            payment.Discount();
        MyMethods.addtoWaiterLog("CLOSE AN ORDER WITH ID = " + WaiterDashBoardModel.order.getOrder_ID() +
                                        "WITH TOTAL PRICE = " + WaiterDashBoardModel.order.getTotal() + "BY CASH");
        WaiterDashBoardModel.order = null;
        WaiterDashBoardModel.table = null;
    }
}
