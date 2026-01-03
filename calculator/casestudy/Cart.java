
// Cart.java 

import java.util.*;

public class Cart {
    private HashMap<Product, Integer> items = new HashMap<>();

    public void addProduct(Product p, int qty) {
        items.put(p, items.getOrDefault(p, 0) + qty);
        System.out.println(p.getName() + " x " + qty + " added to cart.");
    }

    public void removeProduct(Product p) {
        items.remove(p);
        System.out.println(p.getName() + " removed.");
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double calculateTotalBeforeDiscount() {
        double total = 0;
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            total += e.getKey().getPrice() * e.getValue();
        }
        return total;
    }

    public double calculateTotalAfterDiscount() {
        double total = 0;
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            total += e.getKey().getDiscountedPrice() * e.getValue();
        }
        return total;
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("----------- CART -----------");
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            Product p = e.getKey();
            System.out.printf("%s | Qty: %d | Unit: ₹%.2f | After Disc: ₹%.2f%n",
                    p.getName(), e.getValue(), p.getPrice(), p.getDiscountedPrice());
        }
        System.out.printf("Total Before Discount : ₹%.2f%n", calculateTotalBeforeDiscount());
        System.out.printf("Total After Discount  : ₹%.2f%n", calculateTotalAfterDiscount());
    }

    public String buildItemsDescription() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            Product p = e.getKey();
            sb.append(p.getName()).append(" x").append(e.getValue()).append(", ");
        }
        return sb.toString();
    }

    public Map<Product, Integer> getItems() {
        return items;
    }
}


// Order.java 

import java.sql.*;

class Order {
    private String items;
    private double totalAmount;

    public Order(String items, double totalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public boolean saveToDatabase() {
        String sql = "INSERT INTO orders (items, total_amount) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, items);
            ps.setDouble(2, totalAmount);
            ps.executeUpdate();

            System.out.println("Order stored in DB successfully.");
            return true;

        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
            return false;
        }
    }
}