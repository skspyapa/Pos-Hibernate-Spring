import java.io.File;

public class FileTest {
    public static void main(String[] args) {
        File file = new File("");
        new FileTest().m();
    }
    void m(){
        System.out.println(this.getClass().getResource("/view/PlaceOrder.fxml"));
    }
}
