package campusCafeOrderingSystem;

public class BillingProcessor {
    private static BillingProcessor instance = null;
    private static final double zeroWholeOne = 0.1;
    private static final double zeroWholeTwo = 0.2;
    private static final double zeroWholeTree = 0.3;

    private BillingProcessor() {
    }

    public static synchronized BillingProcessor getInstance() {
        if (instance == null) {
            instance = new BillingProcessor();
        }
        return instance;
    }

    public String generateBill(Order order) {
        if (order.equals(null)){
            System.out.println("Not fount");
            return null;
        }
        double totalWB = order.calculateTotalWithBilling();
        StringBuilder billDetails = new StringBuilder();
        billDetails.append("Subtotal: $").append(order.calculateTotal()).append("\n");
        billDetails.append("Service Fee: $").append(totalWB * zeroWholeOne).append("\n");
        billDetails.append("Tax: $").append(totalWB * zeroWholeTwo).append("\n");
        billDetails.append("Tip: $").append(totalWB * zeroWholeTree).append("\n");
        billDetails.append("Total: $").append(totalWB);

        return billDetails.toString();
    }
}
