import java.util.Objects;

public class Product {
    protected String id,name;
    protected double price;
    protected int quantity;

    public Product(String id,String name,double price,int quantity) {
        this.id=id;
        this.name=name;
        this.price=price;
        this.quantity = quantity;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    // Setters

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity>=0) {
            this.quantity=quantity;
        } else {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
    }
    public String toString() {
        return "Product ID: " + id + ", Name: " + name + ", Price: $" + price + ", Quantity left in Stock: " + quantity;
    }
    public void updateQuantity(int purchasedQuantity) {
        if (this.quantity>=purchasedQuantity) {
            this.quantity-=purchasedQuantity;
        } else {
            System.out.println("Insufficient stock for product: " + this.name);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Product product=(Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}