package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigHolder {

    private final Properties properties;

    public ConfigHolder(String path) throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(path));
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
