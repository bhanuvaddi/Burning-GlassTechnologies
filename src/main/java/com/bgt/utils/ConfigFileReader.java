package com.bgt.utils;

import java.io.*;
import java.util.Properties;

public class ConfigFileReader {
    private Properties properties;
    private static String userPath=System.getProperty ("user.dir");
    private final String propertyFilePath= userPath + "/src/main/resources/Config.properties";

    public ConfigFileReader(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Config.properties not found at " + propertyFilePath);
        }
    }

    public String getEnvironment(){
        String environment = properties.getProperty("environment");
        if(environment!= null) return environment;
        else throw new RuntimeException("environment not specified in the Config.properties file.");
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
        else throw new RuntimeException("implicitlyWait not specified in the Config.properties file.");
    }

    public String getBrowser() {
        String driverType = properties.getProperty("browser");
        if(driverType != null) return driverType;
        else throw new RuntimeException("browser not specified in the Config.properties file.");
    }

    public String getApplicationUrl() {
        String url = properties.getProperty("url");
        if(url != null) return url;
        else throw new RuntimeException("url not specified in the Config.properties file.");
    }
}
