import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.nio.file.*;
import java.util.*;
public class Inventory {

    public void updateProductStock(ShoppingCart cart) {
        Map<Product,Integer> items=cart.getItems();
        for (Map.Entry<Product,Integer> entry : items.entrySet()) {
            Product product=entry.getKey();
            int quantitySold=entry.getValue();
            updateStockInFile(product,quantitySold);
        }
    }

    private void updateStockInFile(Product product,int quantitySold) {
        String fileName=getFileNameForProduct(product);
        Path path=Paths.get(fileName);
        try {
            List<String> fileContent=new ArrayList<>(Files.readAllLines(path,StandardCharsets.UTF_8));

            for (int i=0;i<fileContent.size();i++) {
                String line=fileContent.get(i);
                if (line.startsWith(product.getId() + ",")) {
                    String[] splitLine=line.split(",");
                    int currentStock=Integer.parseInt(splitLine[3]);
                    String updatedLine=line.replaceFirst(splitLine[3],String.valueOf(currentStock - quantitySold));
                    fileContent.set(i,updatedLine);
                    break;
                }
            }

            Files.write(path,fileContent,StandardCharsets.UTF_8,StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileNameForProduct(Product product) {
        int productId=Integer.parseInt(product.getId());
        if (productId>=0 && productId<1000) {
            return "TV.txt";
        } else if (productId>=1000 && productId<2000) {
            return "Phone.txt";
        } else if (productId>=2000 && productId<3000) {
            return "Laptop.txt";
        } else if (productId>=3000 && productId<4000) {
            return "Fashion.txt";
        } else {

            return null;
        }
    }
}
