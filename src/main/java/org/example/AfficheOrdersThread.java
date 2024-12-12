package org.example;

import java.util.ArrayList;
import java.util.List;

public class AfficheOrdersThread extends Thread {
    private final Object key;
    private final FileServices fileServices = new FileServices();

    public AfficheOrdersThread(Object key) {
        this.key = key;
    }

    public void run() {
        synchronized (key) {
            try {
                key.wait();
            } catch (InterruptedException e) {
                
                Thread.currentThread().interrupt();
                System.err.println("Thread was interrupted: " + e.getMessage());
                return;  
            }

            
            List<Order> orders = fileServices.getOrders("src/main/resources/Data/output.json");

          
            if (orders != null) {
                for (Order order : orders) {
                    System.out.println(order);  
                }
            } else {
                System.err.println("Failed to retrieve orders or orders list is empty.");
            }
        }
    }
}
