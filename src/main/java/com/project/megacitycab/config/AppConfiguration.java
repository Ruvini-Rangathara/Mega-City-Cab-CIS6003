package com.project.megacitycab.config;

import java.io.InputStream;
import java.util.Properties;

public class AppConfiguration {
    private final Properties properties;

    public AppConfiguration() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

//    public static void main(String[] args) {
//        AppConfiguration config = new AppConfiguration();
//        System.out.println("App Name: " + config.getProperty("app.name"));
//        System.out.println("App Version: " + config.getProperty("app.version"));
//        System.out.println("App Description: " + config.getProperty("app.description"));
//        System.out.println("DB URL: " + config.getProperty("db.url"));
//    }
}
