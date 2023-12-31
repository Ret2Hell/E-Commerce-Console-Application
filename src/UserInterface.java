import java.io.*;
import java.util.*;

public class UserInterface{
    private static final String ADMIN_FILE="admin_login.txt";
    private static final String CUSTOMER_FILE="customer_login.txt";
    private static Scanner scanner=new Scanner(System.in);

    public static void Menu() throws IOException {
        boolean running=true;

        while (running) {
            System.out.println("= = = = = Control = = = = =");
            System.out.println(" 1. Admin                   ");
            System.out.println(" 2. Customer                ");
            System.out.println(" 3. Exit                    ");
            System.out.println("= = = = =  Panel  = = = = =");
            System.out.println("Enter your profile type or 'Exit' to close: ");
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                case "2":
                    if (userInput.equals("1")){
                        userInput="Admin";
                    }
                    else {
                        userInput="Customer";
                    }
                    authenticateUser(userInput);
                    break;
                case "exit":
                    running=false;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
                    break;
            }
        }
    }

    private static void authenticateUser(String userType) throws IOException {
        System.out.println("Do you want to Sign Up or Login?");
        System.out.println(" 1. Sign Up");
        System.out.println(" 2. Login");
        String action=scanner.nextLine();

        switch (action) {
            case "1":
                signUp(userType);
                break;
            case "2":
                login(userType);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    private static void signUp(String userType) throws IOException {
        System.out.println("Enter Username:");
        String username=scanner.nextLine();
        System.out.println("Enter Password:");
        String password=scanner.nextLine();
        System.out.println("Confirm Password:");
        String confirmPassword=scanner.nextLine();
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            return;
        }
        String filename="Admin".equalsIgnoreCase(userType) ? ADMIN_FILE:CUSTOMER_FILE;
        BufferedWriter writer=new BufferedWriter(new FileWriter(filename,true));
        writer.write(username + " " + password);
        writer.newLine();
        writer.close();
        System.out.println("Signup successful!");
    }
    private static void login(String userType) throws IOException {
        System.out.println("Enter Username:");
        String username=scanner.nextLine();
        System.out.println("Enter Password:");
        String password=scanner.nextLine();
        String filename="Admin".equalsIgnoreCase(userType) ? ADMIN_FILE : CUSTOMER_FILE;
        BufferedReader reader=new BufferedReader(new FileReader(filename));
        String line;
        boolean isAuthenticated=false;
        while ((line=reader.readLine())!=null) {
            String[] login_info=line.split(" ");
            if (login_info[0].equals(username) && login_info[1].equals(password)) {
                isAuthenticated=true;
                break;
            }
        }
        reader.close();
        if (isAuthenticated) {
            System.out.println("Login successful!");
            ProductManagement pm=new ProductManagement();
            pm.manageProducts(userType);
        } else {
            System.out.println("Invalid username or password");
        }
    }
}
