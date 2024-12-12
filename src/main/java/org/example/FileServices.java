package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FileServices {
    private final Gson gson;

    public FileServices() {
        
        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (com.google.gson.JsonDeserializer<Date>) (json, typeOfT, context) -> {
                    try {
                        return Date.valueOf(json.getAsString());  
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Failed to parse date: " + json.getAsString(), e);
                    }
                })
                .create();
    }

    public List<Order> getOrders(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            Type orderListType = new TypeToken<List<Order>>() {}.getType();
            return gson.fromJson(jsonObject.get("orders"), orderListType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file: " + filePath, e);
        }
    }
    public void writeOrders(List<Order> orders, String path) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            
            List<Order> existingOrders;


            try (FileReader reader = new FileReader(path)) {
                Type ordersListType = new TypeToken<List<Order>>() {}.getType();
                existingOrders = gson.fromJson(reader, ordersListType);
            }
            if (existingOrders == null) {
                existingOrders = new ArrayList<>(); 

            
            existingOrders.addAll(orders);

            
            try (FileWriter writer = new FileWriter(path)) {
                gson.toJson(existingOrders, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
