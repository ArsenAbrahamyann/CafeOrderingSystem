package campusCafeOrderingSystem;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> menuItem;

    public Menu() {
        this.menuItem = new ArrayList<>();
    }

    public List<MenuItem> getMenuItem() {
        return menuItem;
    }

    public void addMenuItem(MenuItem item) {
        this.menuItem.add(item);
    }


    public MenuItem findMenuItemById(String itemId) {
        for (MenuItem item : getMenuItem()) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }
        return null;

    }

    public void deleteMenuItem(String idItem) {
        MenuItem menuToRemove = null;
        for (MenuItem menu : menuItem) {
            if (menu.getId().equals(idItem)) {
                menuToRemove = menu;
            }
        }
        if (menuToRemove != null) {
            menuItem.remove(menuToRemove);
        }

    }

    public void printMenu() {
        if (!menuItem.isEmpty()) {
            System.out.println("===== Menu =====");
            for (MenuItem item : menuItem) {
                System.out.println("ID: " + item.getId() + " Name: " + item.getName() + " - $" + item.getPrice() + " Description: " + item.getDescription());
            }
            System.out.println("***********************");
        } else {
            System.out.println("No items in the menu.");
        }
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuItem=" + menuItem +
                '}';
    }
}
