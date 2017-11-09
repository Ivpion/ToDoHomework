package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigHolder {
    private static final String CONFIG_FILE_PATH = "/app.properties";
    private final Properties properties;

    public ConfigHolder() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(new File(ConfigHolder.class.getResource(CONFIG_FILE_PATH).
                getFile()).getAbsolutePath()));
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
