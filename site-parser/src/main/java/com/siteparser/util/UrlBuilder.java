package com.siteparser.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sundial on 16.04.2017.
 */
public class UrlBuilder {

    private static Properties properties;

    static {

        init();

    }

    private static void init() {

        properties = new Properties();
        FileInputStream inputStream;

        try {
            inputStream = new FileInputStream("src/main/resources/site.properties");
            properties.load(inputStream);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String s) {
        return properties.getProperty(s);
    }


    public static String getItemLink(String id) {
        String baseUrl = properties.getProperty("base.url");
        return baseUrl + "/product-" + id;

    }

    /**
      Concatenates a keyword with predefined searchUrl and as a result method returned link for search on keyword.
    */

    public static String createLinkForRequest(String keyword) {
        String searchUrl = properties.getProperty("search.url");
        return searchUrl + keyword;
    }


    public static String createLinkForNextPage(String nextPage) {
        String baseUrl = properties.getProperty("base.url");
        return baseUrl + nextPage;

    }
}