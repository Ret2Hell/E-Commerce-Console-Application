import java.io.*;
import java.util.*;
public class Admin {
    public void addProduct() throws IOException {
        Scanner scanner=new Scanner(System.in);

        System.out.println("Select the product category:");
        System.out.println("1. Laptop");
        System.out.println("2. TV");
        System.out.println("3. Fashion");
        System.out.println("4. Phone");
        System.out.print("Enter choice: ");
        String category = scanner.nextLine();

        String filename;
        switch (category) {
            case "1":
                filename="Laptop.txt";
                break;
            case "2":
                filename="TV.txt";
                break;
            case "3":
                filename="Fashion.txt";
                break;
            case "4":
                filename="Phone.txt";
                break;
            default:
                System.out.println("Invalid category");
                return;
        }

        System.out.println("Enter Product ID :");
        System.out.println("Note1: Product ID should be unique");
        System.out.print("Note2: TV ID: from 0 to 999 Phone ID: from 1000 to 1999 Laptop ID: from 2000 to 2999 Fashion ID: from 3000 to 3999 \n ");
        String id=scanner.nextLine();
        if (!isUniqueId(id,filename)) {
            System.out.println("Product ID already exists. Please use a unique ID");
            return;
        }
        if (!isIdInRange(id, category)) {
            System.out.println("Product ID is not in the valid range for the selected category");
            return;
        }
        System.out.print("Enter Name: ");
        String name=scanner.nextLine();
        System.out.print("Enter Price (in $): ");
        double price=Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Quantity: ");
        int quantity;
        try {
            quantity=Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for quantity. Please enter a valid integer");
            return;
        }

        String productDetails=id + "," + name + "," + price + "," + quantity;

        switch (category) {
            case "1":
                addLaptop(scanner,productDetails);
                break;
            case "2":
                addTV(scanner,productDetails);
                break;
            case "3":
                addFashion(scanner,productDetails);
                break;
            case "4":
                addPhone(scanner,productDetails);
                break;
            default:
                System.out.println("Invalid category");
                return;
        }
        System.out.println("Product added successfully");
    }

    public void addLaptop(Scanner scanner,String productDetails) throws IOException {
        System.out.print("Enter RAM(in GB): ");
        int ram=Integer.parseInt(scanner.nextLine());
        System.out.print("Enter SSD(in GB): ");
        int ssd=Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Marque: ");
        String marque=scanner.nextLine();
        System.out.print("Enter GPU: ");
        String gpu=scanner.nextLine();
        System.out.print("Enter Processor: ");
        String processor=scanner.nextLine();

        String fullDetails=productDetails + "," + ram + "," + ssd + "," + marque + "," + gpu + "," + processor;
        writeToFile("Laptop.txt",fullDetails);
    }

    private void addTV(Scanner scanner,String productDetails) throws IOException {
        System.out.print("Enter Resolution (e.g., 1080p, 4K): ");
        String resolution=scanner.nextLine();
        System.out.print("Enter Size (in inches): ");
        int inches=Integer.parseInt(scanner.nextLine());

        String fullDetails=productDetails + "," + resolution + "," + inches;
        writeToFile("TV.txt",fullDetails);
    }

    public void addFashion(Scanner scanner,String productDetails) throws IOException {
        System.out.print("Enter Size (e.g., S, M, L): ");
        String size=scanner.nextLine();

        String fullDetails=productDetails + "," + size;
        writeToFile("Fashion.txt",fullDetails);
    }

    public void addPhone(Scanner scanner,String productDetails) throws IOException {
        System.out.print("Enter RAM (in GB): ");
        int ram=Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Storage (in GB): ");
        int storage=Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Camera Resolution (in MP): ");
        int camera=Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Marque: ");
        String marque=scanner.nextLine();
        System.out.print("Enter Processor: ");
        String processor=scanner.nextLine();

        String fullDetails=productDetails + "," + ram + "," + storage + "," + camera + "," + marque + "," + processor;
        writeToFile("Phone.txt",fullDetails);
    }

    public void writeToFile(String filename,String content) throws IOException {
        try (BufferedWriter writer=new BufferedWriter(new FileWriter(filename,true))) {
            writer.write(content);
            writer.newLine();
        }
    }

    public boolean isUniqueId(String productId,String filename) throws IOException {
        try (BufferedReader reader=new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line=reader.readLine())!=null) {
                if (line.startsWith(productId + ",")) {
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
        }
        return true;
    }
    private boolean isIdInRange(String id,String category) {
        int productId;
        try {
            productId=Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return false;
        }

        switch (category) {
            case "1":
                return productId>=2000 && productId<=2999;
            case "2":
                return productId>=0 && productId<=999;
            case "3":
                return productId>=3000 && productId<=3999;
            case "4":
                return productId>=1000 && productId<=1999;
            default:
                return false;
        }
    }

    public void deleteProduct() throws IOException {
        Scanner scanner=new Scanner(System.in);

        System.out.println("Select the product category to delete from:");
        System.out.println("1. Laptop");
        System.out.println("2. TV");
        System.out.println("3. Fashion");
        System.out.println("4. Phone");
        System.out.print("Enter choice: ");
        String category=scanner.nextLine();

        System.out.print("Enter Product ID to delete: ");
        String id=scanner.nextLine();

        String filename="";
        switch (category) {
            case "1":
                filename="Laptop.txt";
                break;
            case "2":
                filename="TV.txt";
                break;
            case "3":
                filename="Fashion.txt";
                break;
            case "4":
                filename="Phone.txt";
                break;
            default:
                System.out.println("Invalid category");
                return;
        }

        deleteFromFile(filename,id);
        System.out.println("Product deleted successfully");
    }

    public void deleteFromFile(String filename,String id) throws IOException {
        File file=new File(filename);
        List<String> out=new ArrayList<>();

        try (BufferedReader br=new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(id + ",")) {
                    out.add(line);
                }
            }
        }

        try (BufferedWriter bw=new BufferedWriter(new FileWriter(file))) {
            for (String line : out) {
                bw.write(line);
                bw.newLine();
            }
        }
    }


    public void modifyProduct() throws IOException {
        Scanner scanner=new Scanner(System.in);

        System.out.println("Select the product category to modify:");
        System.out.println("1. Laptop");
        System.out.println("2. TV");
        System.out.println("3. Fashion");
        System.out.println("4. Phone");
        System.out.print("Enter choice: ");
        String category=scanner.nextLine();

        System.out.print("Enter Product ID to modify: ");
        String productId=scanner.nextLine();

        String filename;
        switch (category) {
            case "1":
                filename="Laptop.txt";
                break;
            case "2":
                filename="TV.txt";
                break;
            case "3":
                filename="Fashion.txt";
                break;
            case "4":
                filename="Phone.txt";
                break;
            default:
                System.out.println("Invalid category");
                return;
        }

        List<String> lines=new ArrayList<>();
        String modifiedLine=null;

        // Reading the file and finding the product line
        try (BufferedReader br=new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line=br.readLine()) != null) {
                if (line.startsWith(productId + ",")) {
                    modifiedLine=modifyLine(scanner, line, category);
                }
                lines.add(line);
            }
        }

        if (modifiedLine!=null) {
            try (BufferedWriter bw=new BufferedWriter(new FileWriter(filename))) {
                for (String line : lines) {
                    if (!line.startsWith(productId + ",")) {
                        bw.write(line);
                        bw.newLine();
                    } else {
                        bw.write(modifiedLine);
                        bw.newLine();
                    }
                }
            }
        } else {
            System.out.println("Product not found");
        }
    }

    public String modifyLine(Scanner scanner,String originalLine,String category) {
        String[] parts=originalLine.split(",");

        // Modify based on category
        switch (category){
            case "1": // Laptop
                System.out.print("Enter new Name (current: " + parts[1] + "): ");
                parts[1]=scanner.nextLine();
                System.out.print("Enter new Price (current: " + parts[2] + "): ");
                parts[2]=scanner.nextLine();
                System.out.print("Enter new quantity (current: " + parts[3] + "): ");
                parts[3]=scanner.nextLine();
                System.out.print("Enter new RAM (current: " + parts[4] + "): ");
                parts[4]=scanner.nextLine();
                System.out.print("Enter new SSD (current: " + parts[5] + "): ");
                parts[5]=scanner.nextLine();
                System.out.print("Enter new Marque (current: " + parts[6] + "): ");
                parts[6]=scanner.nextLine();
                System.out.print("Enter new GPU (current: " + parts[7] + "): ");
                parts[7]=scanner.nextLine();
                System.out.print("Enter new Processor (current: " + parts[8] + "): ");
                parts[8]=scanner.nextLine();
                break;

            case "2": // TV
                System.out.print("Enter new Name (current: " + parts[1] + "): ");
                parts[1]=scanner.nextLine();
                System.out.print("Enter new Price (current: " + parts[2] + "): ");
                parts[2]=scanner.nextLine();
                System.out.print("Enter new quantity (current: " + parts[3] + "): ");
                parts[3]=scanner.nextLine();
                System.out.print("Enter new Resolution (current: " + parts[4] + "): ");
                parts[4]=scanner.nextLine();
                System.out.print("Enter new Inches (current: " + parts[5] + "): ");
                parts[5]=scanner.nextLine();
                break;

            case "3": // Fashion
                System.out.print("Enter new Name (current: " + parts[1] + "): ");
                parts[1]=scanner.nextLine();
                System.out.print("Enter new Price (current: " + parts[2] + "): ");
                parts[2]=scanner.nextLine();
                System.out.print("Enter new quantity (current: " + parts[3] + "): ");
                parts[3]=scanner.nextLine();
                System.out.print("Enter new Size (current: " + parts[4] + "): ");
                parts[4]=scanner.nextLine();
                break;

            case "4": // Phone
                // Assuming parts are: ID, Name, Price, RAM, Storage, Camera, Marque, Processor
                System.out.print("Enter new Name (current: " + parts[1] + "): ");
                parts[1]=scanner.nextLine();
                System.out.print("Enter new Price (current: " + parts[2] + "): ");
                parts[2]=scanner.nextLine();
                System.out.print("Enter new quantity (current: " + parts[3] + "): ");
                parts[3]=scanner.nextLine();
                System.out.print("Enter new RAM (current: " + parts[4] + "): ");
                parts[4]=scanner.nextLine();
                System.out.print("Enter new Storage (current: " + parts[5] + "): ");
                parts[5]=scanner.nextLine();
                System.out.print("Enter new Marque (current: " + parts[6] + "): ");
                parts[6]=scanner.nextLine();
                System.out.print("Enter new Camera (current: " + parts[7] + "): ");
                parts[7]=scanner.nextLine();
                System.out.print("Enter new Processor (current: " + parts[8] + "): ");
                parts[8]=scanner.nextLine();
                break;
            default:
                System.out.println("Invalid category");
                return null;
        }
        return String.join(",",parts);
    }

    public void viewProducts() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select the product category to view:");
        System.out.println("1. Laptop");
        System.out.println("2. TV");
        System.out.println("3. Fashion");
        System.out.println("4. Phone");
        System.out.print("Enter choice: ");
        String categoryChoice=scanner.nextLine();

        String filename;
        switch (categoryChoice) {
            case "1":
                filename="Laptop.txt";
                break;
            case "2":
                filename="TV.txt";
                break;
            case "3":
                filename="Fashion.txt";
                break;
            case "4":
                filename="Phone.txt";
                break;
            default:
                System.out.println("Invalid category");
                return;
        }

        // Reading and displaying the file contents
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("=== Product List ===");
            while ((line=reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No products found in this category");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
    }
}
