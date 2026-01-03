

import java.util.Objects;

public class Product {
    private int productID;
    private String name;
    private double price;
    private String category;

    public Product(int productID, String name, double price, String category) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getProductID() { return productID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    public void setPrice(double price) { this.price = price; }

    public double getDiscountPercentage() {
        return 0.0;   // default, overridden in subclasses
    }

    public double getDiscountedPrice() {
        double d = getDiscountPercentage();
        return price * (1 - d / 100.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return productID == p.productID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID);
    }

    @Override
    public String toString() {
        return String.format("%d - %s (%s) : ₹%.2f", productID, name, category, price);
    }
}


