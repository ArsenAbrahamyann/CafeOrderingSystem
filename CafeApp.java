package campusCafeOrderingSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class CafeApp {

    private static final String FILE_PATH = "storage.ser";
    private static Scanner sc = new Scanner(System.in);
    private static Menu menu = new Menu();
    private static OrderProcessor orderProcessor = OrderProcessor.getInstance();

    public static void main(String[] args) {
        Map<String, List<?>> data = loadState();
        orderProcessor.getOrders().addAll((List<Order>) data.get("orders"));
        menu.getMenuItem().addAll((List<MenuItem>) data.get("menuItems"));
        while (true) {
            Order order = orderProcessor.getOrders()
                    .stream()
                    .filter(o -> o.getOrderStatus().equals(OrderStatus.CREATED))
                    .findFirst()
                    .orElse(null);
            discipleMenu();
            int choice = 0;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid value , should be number from the manu, please try again!");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    manageMenu();
                    break;
                case 2:
                    processOrder(order, orderProcessor);
                    break;
                case 3:
                    if (order == null) {
                        System.out.println("Cannot generate a bill for an empty order.");
                        break;
                    }
                    generateAndDisplayBill(order);
                    break;
                case 4:
                    System.out.println("Exiting Campus Cafe Ordering System. Goodbye!");
                    saveState(orderProcessor.getOrders(), menu.getMenuItem());
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void generateAndDisplayBill(Order order) {

        if (order.getItems() == null) {
            System.out.println("Cannot generate a bill for an empty order.");
            return;
        }

        BillingProcessor billingProcessor = BillingProcessor.getInstance();
        System.out.println(billingProcessor.generateBill(order));
    }

    private static void manageMenu() {
        while (true) {
            displayMenuManagementMenu();
            int choice = 0;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid value , should be number from the manu, please try again!");
                sc.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    addMenuItem();
                    break;
                case 2:
                    updateMenuItem();
                    break;
                case 3:
                    removeMenuItem();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void printMenuItemDetails(MenuItem itemToUpdate) {
        System.out.println("Item ID: " + itemToUpdate.getId());
        System.out.println("Item Name: " + itemToUpdate.getName());
        System.out.println("Item Description: " + itemToUpdate.getDescription());
        System.out.println("Item Price: $" + itemToUpdate.getPrice());
        System.out.println("Item Category: " + itemToUpdate.getCategory());
    }

    private static void displayMenuManagementMenu() {
        System.out.println("1. Add Menu Item");
        System.out.println("2. Update Menu Item");
        System.out.println("3. Remove Menu Item");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private static void discipleMenu() {
        System.out.println("1. Menu Management");
        System.out.println("2. Order Processing");
        System.out.println("3. Billing and Payment");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void discipleOrderMenu() {
        System.out.println("1. Place Order");
        System.out.println("2. Modify Order");
        System.out.println("3. Cancel Order");
        System.out.println("4. Submit Order");
        System.out.println("5. Return to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private static void placeOrder(Order order) {
        if (order != null) {
            System.out.println("You already have order, please submit, modify or cancel");
        } else {
            order = new Order();
            boolean addItems = true;
            menu.printMenu();
            Map<MenuItem, Integer> orderItems = new HashMap<>();
            while (addItems) {
                System.out.print("Enter Item ID to add to the order or enter exit to finish: ");
                String idOrExit = sc.next();
                switch (idOrExit) {
                    case "exit": {
                        addItems = false;
                    }
                    default: {
                        MenuItem item = menu.findMenuItemById(idOrExit);
                        if (item != null) {
                            System.out.print("Enter new quantity: ");
                            try {
                                int quantity = sc.nextInt();
                                sc.nextLine();
                                orderItems.put(item, quantity);
                                addItems = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid value , should be number from the manu, please try again!");
                                sc.nextLine();
                            }
                        }

                    }
                }
            }
            if (orderItems.size() > 0) {
                order.getItems().putAll(orderItems);
            }
            orderProcessor.getOrders().add(order);
        }
    }

    private static void modifyOrder(Order order) {
        if (order != null) {
            System.out.println("You already have order, please submit, modify or cancel");
        } else {
            order = new Order();
            boolean addItems = true;
            menu.printMenu();
            Map<MenuItem, Integer> orderItems = new HashMap<>();
            while (addItems) {
                System.out.print("Enter Item ID to add to the order or enter exit to finish: ");
                String idOrExit = sc.next();
                switch (idOrExit) {
                    case "exit": {
                        addItems = false;
                    }
                    default: {
                        MenuItem item = menu.findMenuItemById(idOrExit);
                        if (item != null) {
                            System.out.print("Enter new quantity: ");
                            try {
                                int quantity = sc.nextInt();
                                sc.nextLine();
                                orderItems.put(item, quantity);
                                addItems = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid value , should be number from the manu, please try again!");
                                sc.nextLine();
                            }
                        }

                    }
                }
            }
            if (orderItems.size() > 0) {
                order.getItems().putAll(orderItems);
            }
            orderProcessor.getOrders().add(order);
        }
    }

    private static void cancelOrder(Order order) {
        if (order != null) {
            orderProcessor.cancelOrder(order);
        } else {
            System.out.println("Order not found!");
        }
    }

    private static void submitOrder(Order order) {
        if (order != null) {
            orderProcessor.submitOrder(order);
        } else {
            System.out.println("Order not found!");
        }
    }

    private static void processOrder(Order order, OrderProcessor orderProcessor) {


        menu.printMenu();
        if (order != null && order.getItems().size() > 0) {
            System.out.println("Current Order:");
            order.printOrderDetails(order);
        }

        while (true) {
            order = orderProcessor.getOrders().stream()
                    .filter(o -> o.getOrderStatus().equals(OrderStatus.CREATED))
                    .findFirst().orElse(null);
            discipleOrderMenu();
            int choice = 0;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid value , should be number from the manu, please try again!");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    placeOrder(order);
                    break;
                case 2:
                    modifyOrder(order);
                    break;
                case 3:
                    cancelOrder(order);
                    break;
                case 4:
                    submitOrder(order);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void saveState(List<Order> orders, List<MenuItem> menuItems) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH, true))) {
            objectOutputStream.writeObject(orders);
            objectOutputStream.writeObject(menuItems);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Map<String, List<?>> loadState() {
        Map<String, List<?>> data = new HashMap<>();
        File dataFile = new File(FILE_PATH);
        if (dataFile.exists() && dataFile.length() > 0) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                List<Order> orders = (List<Order>) objectInputStream.readObject();
                List<MenuItem> menuItems = (List<MenuItem>) objectInputStream.readObject();
                data.put("orders", orders);
                data.put("menuItems", menuItems);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            data.put("orders", new ArrayList<>());
            data.put("menuItems", new ArrayList<>());
        }
        return data;
    }

    private static void addMenuItem() {
        System.out.print("Enter Item Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Item Description: ");
        String description = sc.nextLine();

        System.out.print("Enter Item Price: ");
        double price = 0;
        try {
            price = sc.nextDouble();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid value for price, should be double, please try again!");
            return;
        }

        System.out.print("Enter Item Category: ");
        String category = sc.nextLine();

        MenuItem newItem = new MenuItem(name, description, category, price);
        menu.addMenuItem(newItem);
        System.out.println("Menu Item added successfully.");
    }

    private static void updateMenuItem() {
        for (MenuItem menu1 : menu.getMenuItem()) {
            System.out.println(menu1);
        }
        System.out.print("Enter Item ID to update: ");
        String itemId = sc.next();
        sc.nextLine();
        MenuItem itemToUpdate = menu.findMenuItemById(itemId);

        if (itemToUpdate != null) {
            System.out.println("Current Item Details:");
            printMenuItemDetails(itemToUpdate);

            System.out.print("Enter new Item Name (Enter to keep current): ");
            String newName = sc.nextLine();
            if (!newName.isEmpty()) {
                itemToUpdate.setName(newName);
            }

            System.out.print("Enter new Item Description (Enter to keep current): ");
            String newDescription = sc.nextLine();
            if (!newDescription.isEmpty()) {
                itemToUpdate.setDescription(newDescription);
            }

            System.out.print("Enter new Item Price (Enter to keep current): ");
            String newPriceStr = sc.nextLine();
            if (!newPriceStr.isEmpty()) {
                double newPrice = Double.parseDouble(newPriceStr);
                itemToUpdate.setPrice(newPrice);
            }

            System.out.print("Enter new Item Category (Enter to keep current): ");
            String newCategory = sc.nextLine();
            if (!newCategory.isEmpty()) {
                itemToUpdate.setCategory(newCategory);
            }

            System.out.println("Menu Item updated successfully.");
        } else {
            System.out.println("Item not found in the menu.");
        }
    }

    private static void removeMenuItem() {
        for (MenuItem menu1 : menu.getMenuItem()) {
            System.out.println(menu1);
        }
        System.out.print("Enter Item ID to remove: ");
        String Id = sc.next();
        sc.nextLine();

        MenuItem itemToRemove = menu.findMenuItemById(Id);

        if (itemToRemove != null) {
            menu.deleteMenuItem(Id);
            System.out.println("Menu Item removed successfully.");
        } else {
            System.out.println("Item not found in the menu.");
        }
    }
}
