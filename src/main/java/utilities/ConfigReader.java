package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties prop = new Properties();

    private static final String FILE_PATH =
            "src/main/resources/config.properties";

    static {
        try (FileInputStream fis = new FileInputStream(FILE_PATH)) {

            prop.load(fis);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to load config.properties",
                    e
            );
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }

    public static void set(String key, String value) {
        prop.setProperty(key, value);
    }
}
