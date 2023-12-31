import java.io.*;
import java.util.*;

public class DiscountManager {
    private Map<String,Integer> discountCodes;

    public DiscountManager() {
        discountCodes=new HashMap<>();
        loadDiscountCodes();
    }

    private void loadDiscountCodes() {
        try (BufferedReader br=new BufferedReader(new FileReader("Codes.txt"))) {
            String line;
            while ((line=br.readLine())!=null) {
                String[] parts=line.split(",");
                if (parts.length==2) {
                    discountCodes.put(parts[0],Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getDiscountPercentage(String code) {
        return discountCodes.getOrDefault(code,0);
    }

    public boolean isValidCode(String code) {
        return discountCodes.containsKey(code);
    }
}