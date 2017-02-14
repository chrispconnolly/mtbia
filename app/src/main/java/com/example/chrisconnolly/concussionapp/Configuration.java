package com.example.chrisconnolly.concussionapp;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Configuration {
    private static final String _fileName = "configuration.properties";
    private static Properties properties = new Properties();

    public static void write(Context context, String key, String value) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(_fileName, Context.MODE_PRIVATE);
            properties.setProperty(key, value);
            properties.store(fileOutputStream, null);
        }
        catch (Exception e){}
    }

    public static String read(Context context, String key) {
        try {
            FileInputStream fileInputStream = context.openFileInput(_fileName);
            properties.load(fileInputStream);
            return properties.getProperty(key);
        }
        catch(Exception e) {return null;}
    }
}
