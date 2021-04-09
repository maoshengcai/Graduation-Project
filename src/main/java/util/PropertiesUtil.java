package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties getProperties(String fileName){
        Properties properties = new Properties();


        try {
            InputStream in=new FileInputStream(new File(fileName));
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }
}
