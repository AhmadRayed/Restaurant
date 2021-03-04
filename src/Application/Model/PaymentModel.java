package Application.Model;

import Application.Resources.*;

public class PaymentModel implements Observable {

    private Observer observer;
    private Payment payment;

    public PaymentModel(Object t) {
        observer = (Observer) t;
    }

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

//        MyMethods.addtoWaiterLog("CLOSE AN ORDER WITH ID = " + WaiterDashBoardModel.order.getOrder_ID() +
//                "WITH TOTAL PRICE = " + WaiterDashBoardModel.order.getTotal() + "BY CARD");
        notifyObserver();
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
//        MyMethods.addtoWaiterLog("CLOSE AN ORDER WITH ID = " + WaiterDashBoardModel.order.getOrder_ID() +
//                                        "WITH TOTAL PRICE = " + WaiterDashBoardModel.order.getTotal() + "BY CASH");
        notifyObserver();
    }

    @Override
    public void notifyObserver() {
        observer.updateOrder(null, null);
    }
}
