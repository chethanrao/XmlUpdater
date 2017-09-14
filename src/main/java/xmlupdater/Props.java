package xmlupdater;
import java.io.IOException;
import java.util.Properties;


public class Props{
	public  Properties getPropertiesFromFile(String fileName) {
        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            System.err.println("Error detected");
            e.printStackTrace();
        }
        return properties;
    }

}