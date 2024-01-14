package campusCafeOrderingSystem;

import java.util.ArrayList;
import java.util.List;

public class OrderProcessor {
    private static OrderProcessor instance = null;
    private List<Order> orders;

    private OrderProcessor() {
        this.orders = new ArrayList<>();
    }

    public static synchronized OrderProcessor getInstance() {
        if (instance == null) {
            instance = new OrderProcessor();
        }
        return instance;
    }


    public void cancelOrder(Order order) {
        order.setOrderStatus(OrderStatus.CANCELED);
        System.out.println("Order canceled successfully.");
    }

    public void submitOrder(Order order) {
        if (!order.getItems().isEmpty()) {
            order.setOrderStatus(OrderStatus.SUBMITTED);
            System.out.println("Order submitted successfully.");
        } else {
            System.out.println("Cannot submit an empty order.");
        }
    }

    public List<Order> getOrders() {
        return orders;
    }
}
