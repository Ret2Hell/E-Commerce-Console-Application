import java.io.*;
import java.util.*;

public class ProductManagement {
    private Admin admin;
    private Customer customer;
    public ProductManagement() {
        this.admin=new Admin();
        this.customer=new Customer();
    }

    public void manageProducts(String userType) throws IOException {
        if ("admin".equalsIgnoreCase(userType)) {
            adminMenu();
        } else if ("customer".equalsIgnoreCase(userType)) {
            customerMenu();
        }
    }

    private void adminMenu() throws IOException {
        Scanner scanner=new Scanner(System.in);
        boolean running=true;
        while (running) {
            System.out.println("= = = = =Admin Product Management Menu= = = = =");
            System.out.println("1. Add Product");
            System.out.println("2. Delete Product");
            System.out.println("3. Modify Product");
            System.out.println("4. View Products");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            String choice1=scanner.nextLine();

            switch (choice1) {
                case "1":
                    admin.addProduct();
                    break;
                case "2":
                    admin.deleteProduct();
                    break;
                case "3":
                    admin.modifyProduct();
                    break;
                case "4":
                    admin.viewProducts();
                    break;
                case "5":
                    running=false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
                    break;
            }
        }
    }
    private void customerMenu() throws IOException {
        Scanner scanner=new Scanner(System.in);
        ShoppingCart cart=customer.getCart();
        boolean running=true;
        while (running) {
            System.out.println("= = = = =Customer Menu= = = = =");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to your cart");
            System.out.println("3. display your cart");
            System.out.println("4. update the quantity ordered in your cart");
            System.out.println("5. remove product from your cart");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            String choice2=scanner.nextLine();
            switch (choice2){
                case "1":
                    customer.viewProducts();
                    break;
                case "2":
                    cart.addtoCart(customer);
                    break;
                case "3":
                    cart.display(scanner);
                    break;
                case "4":
                    cart.updateQuantityInCart();
                    break;
                case "5":
                    cart.removeFromCart();
                    break;
                case "6":
                    running=false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
                    break;
            }
        }
    }


}