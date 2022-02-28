package utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesHelper {

    private String url;
    private int rows;
    private List<String> fields;

    public PropertiesHelper() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();

            prop.load(input);

            url = prop.getProperty("url");
            rows = Integer.parseInt(prop.getProperty("rows"));
            fields = Arrays.asList(prop.getProperty("fields").split(","));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public String getUrl(int os, int rows) {
        return String.format(url, os, rows);
    }

    public int getRows() {
        return rows;
    }

    public List<String> getFields() {
        return fields;
    }

    public static void main(String[] args) {
        PropertiesHelper prop=  new PropertiesHelper();

        System.err.println(prop.getRows());
        System.err.println(prop.getUrl(0, 0));
        System.err.println(prop.getFields());
    }


}
