import java.io.*;
import java.util.*;
public class Payment {
    private DiscountManager discountManager;
    private CustomerDetails customerDetails;
    private final double HomeDeliveryFee=10.0;

    public Payment() {
        discountManager=new DiscountManager();
    }

    public void processPayment(ShoppingCart cart,Scanner scanner) {
        String deliveryMethod=askForDeliveryMethod(scanner);
        double finalAmount=calculateTotal(cart);

        if ("Home Delivery".equalsIgnoreCase(deliveryMethod)) {
            finalAmount+=HomeDeliveryFee;
        }
        CustomerDetails customerDetails=askForCustomerDetails(scanner);

        finalAmount=askForDiscount(scanner,finalAmount);


        selectPaymentMethod(scanner,cart,finalAmount);
    }

    public double calculateTotal(ShoppingCart cart) {
        double total=0;
        for (Map.Entry<Product,Integer> entry:cart.getItems().entrySet()) {
            total+=entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    private String askForDeliveryMethod(Scanner scanner) {
        while (true) {
            System.out.println("Please select a delivery method:");
            System.out.println("1. Home Delivery (Additional $10 fee)");
            System.out.println("2. Pick-up in Store (Free)");
            System.out.print("Your choice (1/2): ");
            String choice=scanner.nextLine();
            if (choice.equals("1")) {
                return "Home Delivery";
            } else if (choice.equals("2")) {
                return "Pick-up in Store";
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2");
            }
        }
    }

    private double askForDiscount(Scanner scanner,double originalAmount) {
        System.out.println("Do you have a discount code? (Y/N)");
        String response=scanner.nextLine();

        if (response.equalsIgnoreCase("Y")) {
            System.out.println("Enter your discount code:");
            String code=scanner.nextLine();
            if (code.length()==8 && discountManager.isValidCode(code)) {
                int discountPercentage=discountManager.getDiscountPercentage(code);
                double discountedAmount=originalAmount * (1 - (discountPercentage / 100.0));
                System.out.println(discountPercentage + "% Discount applied. New amount: " + discountedAmount);
                return discountedAmount;
            } else {
                System.out.println("Invalid discount code");
            }
        }
        return originalAmount;
    }

    private CustomerDetails askForCustomerDetails(Scanner scanner) {
        System.out.println("Please enter your shipping details:");
        System.out.print("Full Name: ");
        String fullName=scanner.nextLine();
        System.out.print("Country: ");
        String country=scanner.nextLine();
        System.out.print("Region: ");
        String region=scanner.nextLine();
        System.out.print("Town: ");
        String town=scanner.nextLine();
        System.out.print("Zip Code: ");
        String zipCode=scanner.nextLine();
        System.out.print("Address: ");
        String address=scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber=scanner.nextLine();

        this.customerDetails=new CustomerDetails(fullName,country,region,town,zipCode,address,phoneNumber);
        return this.customerDetails;
    }

    private void selectPaymentMethod(Scanner scanner,ShoppingCart cart,double amount) {
        System.out.println("Select a payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Payment upon Delivery/Receiving");
        System.out.print("Your choice (1/2): ");
        String choice=scanner.nextLine();

        switch (choice) {
            case "1":
                processCreditCardPayment(scanner,cart,amount);
                break;
            case "2":
                System.out.println("You have selected Payment upon Delivery/Receiving.");
                System.out.println("Total amount to pay: $" + amount);
                Order order=new Order(customerDetails,new ArrayList<>(cart.getItems().keySet()),amount);


                writeOrderToFile(order);
                Inventory inventory=new Inventory();
                inventory.updateProductStock(cart);
                cart.clearCart();
                break;
            default:
                System.out.println("Invalid option. Please try again");
                selectPaymentMethod(scanner,cart,amount); // Recursion instead of loop
                break;
        }
    }

    private void processCreditCardPayment(Scanner scanner,ShoppingCart cart,double amount) {
        System.out.println("Please enter your credit card details:");
        System.out.print("Card Number: ");
        String cardNumber=scanner.nextLine();
        System.out.print("Expiration Date (MM/YY): ");
        String expDate=scanner.nextLine();
        System.out.print("CVV: ");
        String cvv=scanner.nextLine();


        System.out.println("Processing your credit card payment...");


        System.out.println("Payment of $" + amount + " successful.");
        Order order=new Order(customerDetails,new ArrayList<>(cart.getItems().keySet()),amount);


        writeOrderToFile(order);

        Inventory inventory=new Inventory();
        inventory.updateProductStock(cart);

        cart.clearCart();
    }
    private void writeOrderToFile(Order order) {
        try (BufferedWriter bw=new BufferedWriter(new FileWriter("transactions.txt",true))) {
            bw.write(order.getOrderDetails());
            bw.newLine();
            bw.write("--------------------------------------------------");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
