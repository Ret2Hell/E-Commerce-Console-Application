import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        try {
            UserInterface runApp=new UserInterface();
            runApp.Menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
