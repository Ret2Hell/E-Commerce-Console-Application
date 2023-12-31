import java.util.List;

public class Order {
    private CustomerDetails customerDetails;
    private List<Product> purchasedProducts;
    private double totalAmount;

    public Order(CustomerDetails customerDetails,List<Product> purchasedProducts,double totalAmount) {
        this.customerDetails=customerDetails;
        this.purchasedProducts=purchasedProducts;
        this.totalAmount=totalAmount;
    }

    public String getOrderDetails() {
        StringBuilder sb=new StringBuilder();
        sb.append("Customer Details: ").append(customerDetails.toString()).append("\n");
        sb.append("Purchased Products:\n");
        for (Product product : purchasedProducts) {
            sb.append(product.toString()).append("\n");
        }
        sb.append("Total Amount: $").append(totalAmount).append("\n");
        return sb.toString();
    }
}
