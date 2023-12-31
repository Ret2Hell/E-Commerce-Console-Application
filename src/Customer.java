import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Customer {
    private Map<String,List<Product>> productsByCategory;
    private ShoppingCart cart;

    public Customer() {
        productsByCategory=new HashMap<>();
        this.cart=new ShoppingCart();
        loadAllProducts();
    }

    public void viewProducts() throws IOException {
        Scanner scanner=new Scanner(System.in);

        System.out.println("Select the product category to view:");
        System.out.println("1. Laptop");
        System.out.println("2. TV");
        System.out.println("3. Fashion");
        System.out.println("4. Phone");
        System.out.print("Enter choice: ");
        String categoryChoice=scanner.nextLine();

        String category;
        switch (categoryChoice) {
            case "1":
                category="Laptop";
                break;
            case "2":
                category="TV";
                break;
            case "3":
                category="Fashion";
                break;
            case "4":
                category="Fashion";
                break;
            default:
                System.out.println("Invalid category");
                return;
        }
        viewProductsByCategory(category);



        System.out.println("Do you want to search within this category?");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Price Range");
        System.out.println("3. No, go back");
        System.out.print("Enter choice: ");
        String searchChoice=scanner.nextLine();

        switch (searchChoice) {
            case "1":
                System.out.print("Enter product name to search: ");
                String name=scanner.nextLine();
                searchProductsByName(category.replace(".txt",""), name);
                break;
            case "2":
                System.out.print("Enter minimum price: ");
                double minPrice = Double.parseDouble(scanner.nextLine());
                System.out.print("Enter maximum price: ");
                double maxPrice = Double.parseDouble(scanner.nextLine());
                searchProductsByPriceRange(category.replace(".txt",""), minPrice, maxPrice);
                break;
            case "3":
                // Go back to the previous menu
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
    public void viewProductsByCategory(String category) {
        List<Product> products=loadProducts(category);
        if (products.isEmpty()) {
            System.out.println("No products found in this category.");
        } else {
            products.forEach(System.out::println);
        }
    }

    private List<Product> loadProducts(String category) {
        String filename=category + ".txt";
        List<Product> productList=new ArrayList<>();

        try (BufferedReader reader=new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line=reader.readLine()) != null) {
                String[] parts=line.split(","); // Assuming format: ID,Name,Price,Quantity
                String id=parts[0].trim();
                String name=parts[1].trim();
                double price=Double.parseDouble(parts[2].trim());
                int quantity=Integer.parseInt(parts[3].trim());
                productList.add(new Product(id,name,price,quantity));
            }
            productsByCategory.put(category,productList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productsByCategory.getOrDefault(category,new ArrayList<>());
    }

    public void searchProductsByName(String category,String name) {
        List<Product> products=productsByCategory.getOrDefault(category,Collections.emptyList());
        List<Product> result=products.stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            System.out.println("No products found with the name: " + name);
        } else {
            result.forEach(System.out::println);
        }
    }

    public void searchProductsByPriceRange(String category,double minPrice,double maxPrice) {
        List<Product> products=productsByCategory.getOrDefault(category,Collections.emptyList());
        List<Product> result=products.stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            System.out.println("No products found in the price range: " + minPrice + " - " + maxPrice);
        } else {
            result.forEach(System.out::println);
        }
    }
    public ShoppingCart getCart() {
        return this.cart;
    }
    public Product findProductById(String category,String productId) {
        List<Product> products=productsByCategory.getOrDefault(category,Collections.emptyList());
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
    private void loadAllProducts() {
        List<String> categories=Arrays.asList("Laptop","TV","Fashion","Phone");
        for (String category : categories) {
            loadProducts(category);
        }
    }
}
