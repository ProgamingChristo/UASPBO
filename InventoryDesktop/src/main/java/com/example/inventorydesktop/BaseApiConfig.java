package com.example.inventorydesktop;

public class BaseApiConfig {

    private static final String BASE_URL = "http://localhost:3030/";

    public static String getBaseUrl() {
        return BASE_URL;
    }

//    public static String getProductionUrl() {
//        return "http://production-server.com/";
//    }
}
