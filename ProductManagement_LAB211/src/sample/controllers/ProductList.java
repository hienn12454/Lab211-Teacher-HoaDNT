package sample.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TUF
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;
import sample.dto.I_List;
import sample.dto.Product;
import sample.utils.Utils;

public class ProductList extends ArrayList<Product> implements I_List {

    private static final String FILE_NAME = "Product.dat";

    @Override
    public boolean create() {
        boolean result = false;
        try {
            boolean checkExist = true;
            String id = "";
            do {
                id = Utils.getStringByFormat("Input ID (format Pxxx, e.g., P001): ", "P\\d{3}", "Invalid format! ID must match Pxxx where xxx are digits.");
                checkExist = this.contains(new Product(id));
                if (checkExist) {
                    System.out.println("Id is exist, input again");
                }
            } while (checkExist);
            Product p = new Product(id);
            p.create();
            this.add(p);
            result = true;
            System.out.println("Product created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating product: " + e.getMessage());
        }
        return result;
    }

    @Override
    public void display() {
        ArrayList<Product> loadedList = loadFromFile();
        Collections.sort(loadedList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                if (p1.getQuantity() != p2.getQuantity()) {
                    return Integer.compare(p2.getQuantity(), p1.getQuantity());
                } else {
                    return Float.compare(p1.getPrice(), p2.getPrice());
                }
            }
        });

        System.out.println("=== PRODUCT LIST ===");
        for (Product product : loadedList) {
            product.display();
        }

    }

    @Override
    public boolean checkExist(String id) {
        boolean exists = false;
        ArrayList<Product> loadedList = loadFromFile();
        for (Product product : loadedList) {
            if (product.getId().equalsIgnoreCase(id)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    @Override
    public ArrayList<Product> searchByName(String name) {
        ArrayList<Product> foundProducts = new ArrayList<>();

        for (Product product : this) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                foundProducts.add(product);
            }
        }

        if (!foundProducts.isEmpty()) {
            Collections.sort(foundProducts, new Comparator<Product>() {
                @Override
                public int compare(Product p1, Product p2) {
                    return p1.getName().compareToIgnoreCase(p2.getName());
                }
            });
        }
        return foundProducts;
    }

    @Override
    public boolean update(String id) {
        boolean result = false;

        Product searchProduct = new Product(id);
        int index = this.indexOf(searchProduct);

        if (index != -1) {
            Product productToUpdate = this.get(index);
            productToUpdate.update();
            result = true;
        }

        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;

        Product searchProduct = new Product(id);
        int index = this.indexOf(searchProduct);

        if (index != -1) {
            Product productToDelete = this.get(index);
            System.out.println("Product to delete:");
            productToDelete.display();

            boolean confirm = Utils.confirmYesNo("Do you want to delete this product? (Y/N): ");
            if (confirm) {
                this.remove(index);
                result = true;
            } else {
                System.out.println("Delete cancelled!");
            }
        }
        return result;
    }

    @Override
    public boolean saveToFile() {
        boolean result = false;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(this);
            oos.close();
            result = true;
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    @Override
    public ArrayList<Product> loadFromFile() {
        ArrayList<Product> listFile = new ArrayList();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            listFile = (ArrayList<Product>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
        return listFile;
    }
}
