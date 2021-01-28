package Application.Resources;

public class PaymentFactory {
    public static Payment getPayment (String PaymentType, Order order, double percent, String CreditNumber)
    {
        if (PaymentType.equalsIgnoreCase("CARD"))
            return new Card(order, percent, CreditNumber);
        else
        if (PaymentType.equalsIgnoreCase("CASH"))
            return new Cash(order, percent);
        else
            return null;
    }
}
