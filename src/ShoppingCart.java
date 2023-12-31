import java.util.*;
public class ShoppingCart {
    private Map<Product,Integer> items;

    public ShoppingCart() {
        items=new HashMap<>();
    }

    public void addItem(Product product,int quantity) {
        if (items.containsKey(product)) {
            items.put(product,items.get(product) + quantity);
        } else {
            items.put(product,quantity);
        }
    }

    public void addtoCart(Customer customer) {
        Scanner scanner=new Scanner(System.in);
        String category="";

        while (true) {
            System.out.print("Enter the category of the product (Laptop/Phone/TV/Fashion): ");
            category=scanner.nextLine();

            // Check if the category is valid
            if (isValidCategory(category)) {
                break;
            } else {
                System.out.println("Invalid category. Please enter a valid category.");
            }
        }

        customer.viewProductsByCategory(category);
        System.out.print("Enter the ID of the product you want to add to your cart: ");
        String productId=scanner.nextLine();
        System.out.print("Enter the quantity: ");
        int quantity=Integer.parseInt(scanner.nextLine());
        if (quantity<=0) {
            System.out.println("Quantity must be greater than 0");
            return;
        }
        Product selectedProduct=customer.findProductById(category,productId);
        if (selectedProduct!=null) {
            boolean productInCart=items.containsKey(selectedProduct);
            int totalQuantity=productInCart ? items.get(selectedProduct) + quantity : quantity;

            if (selectedProduct.getQuantity()>=totalQuantity) {
                if (productInCart) {
                    items.put(selectedProduct,totalQuantity);
                    System.out.println("Updated quantity in cart for: " + selectedProduct.getName());
                } else {
                    addItem(selectedProduct,quantity);
                    System.out.println("Added to cart: " + selectedProduct.getName() + " Quantity: " + quantity);
                }
                selectedProduct.updateQuantity(quantity);
            } else {
                System.out.println("Cannot add more. Total quantity exceeds available stock");
            }
        } else {
            System.out.println("Product not found");
        }
    }
    private boolean isValidCategory(String category) {
        Set<String> validCategories=new HashSet<>(Arrays.asList("Laptop","Phone","TV","Fashion"));
        return validCategories.contains(category);
    }

    public void display(Scanner scanner) {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty");
        } else {
            System.out.println("Items in your cart:");
            for (Map.Entry<Product,Integer> entry : items.entrySet()) {
                Product product=entry.getKey();
                Integer quantity=entry.getValue();
                System.out.println(product + ", Quantity Ordered: " + quantity);
            }
            System.out.print("Do you want to proceed to checkout? (Y/N): ");
            String choice=scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                Payment payment=new Payment();
                paymentMenu(payment,scanner);
            }
        }
    }
    private void paymentMenu(Payment payment,Scanner scanner) {
        double totalAmount=payment.calculateTotal(this);
        System.out.println("Your total amount is: $" + totalAmount);
        payment.processPayment(this,scanner);
    }

    public void updateQuantityInCart() {
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter the ID of the product you want to update: ");
        String productId=scanner.nextLine();
        Product productToUpdate=null;

        for (Product p : items.keySet()) {
            if (p.getId().equals(productId)) {
                productToUpdate=p;
                break;
            }
        }

        if (productToUpdate!=null) {
            System.out.print("Enter the new quantity: ");
            int newQuantity=Integer.parseInt(scanner.nextLine());
            int currentQuantityInCart=items.get(productToUpdate);

            if (productToUpdate.getQuantity() + currentQuantityInCart >= newQuantity) {
                items.put(productToUpdate,newQuantity);
                productToUpdate.setQuantity(productToUpdate.getQuantity() + currentQuantityInCart - newQuantity);
                System.out.println("Updated quantity for " + productToUpdate.getName() + " to " + newQuantity);
            } else {
                System.out.println("Cannot update. The new quantity exceeds the available stock");
            }
        } else {
            System.out.println("Product not found in your cart");
        }
    }
    public void removeFromCart() {
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter the ID of the product you want to remove: ");
        String productId=scanner.nextLine();
        Product productToRemove=null;

        for (Product p : items.keySet()) {
            if (p.getId().equals(productId)) {
                productToRemove=p;
                break;
            }
        }

        if (productToRemove!=null) {
            int removedQuantity=items.get(productToRemove);
            productToRemove.setQuantity(productToRemove.getQuantity() + removedQuantity);
            items.remove(productToRemove);
            System.out.println("Removed " + productToRemove.getName() + " from your cart.");
        } else {
            System.out.println("Product not found in your cart");
        }
    }
    public void clearCart() {
        items.clear();
    }

    public Map<Product, Integer> getItems() {
        return new HashMap<>(items);
    }


}