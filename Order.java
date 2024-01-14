package campusCafeOrderingSystem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    private static final long serialVersionUID = 5095362264488498796L;
    private static final double zeroWholeOne = 0.1;
    private static final double zeroWholeTwo = 0.2;
    private static final double zeroWholeTree = 0.3;
    private Map<MenuItem, Integer> items;

    private OrderStatus orderStatus;

    public Order() {
        this.items = new HashMap<>();
        this.orderStatus = OrderStatus.CREATED;
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            MenuItem item = entry.getKey();
            int quantity = entry.getValue();
            total += item.getPrice() * quantity;
        }
        return total;
    }

    public double calculateTotalWithBilling() {
        double subtotal = calculateTotal();
        double serviceFee = subtotal * zeroWholeOne;
        double tax = subtotal * zeroWholeTwo;
        double tip = subtotal * zeroWholeTree;

        return subtotal + serviceFee + tax + tip;
    }

    public void printOrderDetails(Order order) {
        Map<MenuItem, Integer> items = order.getItems();

        if (!items.isEmpty()) {
            System.out.println("*** Current Order ***");
            for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
                MenuItem item = entry.getKey();
                int quantity = entry.getValue();
                System.out.println(quantity + "x " + item.getName() + " - $" + item.getPrice() * quantity);
            }
            System.out.println("Total: $" + order.calculateTotal());
            System.out.println("**************");
        } else {
            System.out.println("No items in the order.");
        }
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
