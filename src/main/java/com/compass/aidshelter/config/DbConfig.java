package com.compass.aidshelter.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbConfig {

    public static void build() {
       Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        props.forEach((k, v) -> System.setProperty(k.toString(), v.toString()));
    }

}
