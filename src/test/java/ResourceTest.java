import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ResourceTest {

    public static void main(String[] args) throws IOException {
        File file = new File("resources/application.properties");
        FileInputStream is = new FileInputStream(file);

        Properties properties = new Properties();

        properties.load(is);

        String db = properties.getProperty("lk/ijse/pos");
        System.out.println(db);

    }
}
