package sample.dto;

import sample.utils.Utils;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hoa Doan
 */
public class Product implements Serializable {

    private String id;
    private String name;
    private float price;
    private int quantity;
    private String status;

    public Product(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        Product p = (Product) obj;
        return this.getId().equals(p.getId());
    }

    public Product() {
    }

    public Product(String id, String name, float price, int quantity, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void create() {
        this.name = Utils.getString("Input name (>=5 characters, no spaces): ");
        while (this.name.length() < 5 || this.name.contains(" ")) {
            System.out.println("Name must be >= 5 characters and no spaces!");
            this.name = Utils.getString("Input name (>=5 characters, no spaces): ");
        }

        this.price = Utils.getFloat("Input price (0-10000): ", 0, 10000);

        this.quantity = Utils.getInt("Input quantity (0-1000): ", 0, 1000);

        this.status = readStatusStrict("Input status (1-Available, 2-Not Available, or type Available/Not Available): ");
    }

    public void update() {
        String newName = Utils.getString("Input new name (>=5 characters, no spaces, empty to keep current): ", this.name);
        if (!newName.isEmpty()) {
            while (newName.length() < 5 || newName.contains(" ")) {
                System.out.println("Name must be >= 5 characters and no spaces!");
                newName = Utils.getString("Input new name (>=5 characters, no spaces, empty to keep current): ", this.name);
            }
            this.name = newName;
        }

        float newPrice = Utils.getFloat("Input new price (0-10000, empty to keep current): ", 0, 10000, (int) this.price);
        this.price = newPrice;

        int newQuantity = Utils.getInt("Input new quantity (0-1000, empty to keep current): ", 0, 1000, this.quantity);
        this.quantity = newQuantity;

        String tmpStatus = Utils.getOptionalString("Input new status (1-Available, 2-Not Available, or type Available/Not Available, empty to keep current): ");
        if (!tmpStatus.isEmpty()) {
            this.status = readStatusStrictFromGiven(tmpStatus);
        }
    }

    private String readStatusStrict(String welcome) {
        String input;
        while (true) {
            input = Utils.getString(welcome);
            if (input.equals("1") || input.equalsIgnoreCase("Available")) {
                return "Available";
            }
            if (input.equals("2") || input.equalsIgnoreCase("Not Available")) {
                return "Not Available";
            }
            System.out.println("Invalid status! Please enter 1, 2, 'Available' or 'Not Available'.");
        }
    }

    private String readStatusStrictFromGiven(String input) {
        while (true) {
            if (input.equals("1") || input.equalsIgnoreCase("Available")) {
                return "Available";
            }
            if (input.equals("2") || input.equalsIgnoreCase("Not Available")) {
                return "Not Available";
            }
            System.out.println("Invalid status! Please enter 1, 2, 'Available' or 'Not Available'.");
            input = Utils.getOptionalString("Re-enter status (or empty to cancel): ");
            if (input.isEmpty()) {
                return this.status;
            }
        }
    }

    public void display() {
        System.out.println("Product: " + "id=" + id + ", name=" + name + ", price=" + price + " VND" + ", quantity=" + quantity + ", status=" + status);
    }
}
