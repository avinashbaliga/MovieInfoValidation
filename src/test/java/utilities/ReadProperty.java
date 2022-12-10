package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperty {

    public static String getProperty(String property){
        File file = new File("src/test/resources/framework.properties");
        Properties properties = new Properties();
        try {
            FileInputStream stream = new FileInputStream(file);
            properties.load(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return properties.getProperty(property);
    }
}
