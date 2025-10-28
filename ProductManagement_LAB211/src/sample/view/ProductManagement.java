package sample.view;

import java.util.ArrayList;
import sample.dto.I_List;
import sample.dto.I_Menu;
import sample.controllers.Menu;
import sample.controllers.ProductList;
import sample.dto.Product;
import sample.utils.Utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TUF
 */
public class ProductManagement {

    public static void main(String args[]) {
        I_Menu menu = new Menu();
        menu.addItem("1. Create a product");
        menu.addItem("2. Check if a product exists");
        menu.addItem("3. Search for a product by name");
        menu.addItem("4. Update a product");
        menu.addItem("5. Delete a product");
        menu.addItem("6. Display products from file");
        menu.addItem("7. Save products to file");
        menu.addItem("8. Quit");

        int choice;
        boolean cont = false;
        I_List list = new ProductList();

        // Tải data từ file
        list.loadFromFile();

        do {
            System.out.println("\n=== PRODUCT MANAGEMENT SYSTEM ===");
            menu.showMenu();
            choice = menu.getChoice();

            switch (choice) {
                case 1:
                    System.out.println("\n--- CREATE PRODUCT ---");
                    if(list.create()){
                        System.out.println("Add success.");
                    }else{
                        System.out.println("Add fail.");
                    }
                    break;
                case 2:
                    System.out.println("\n--- CHECK PRODUCT EXISTS ---");
                    String idCheck = Utils.getString("Enter ID to Check: ");
                    if(list.checkExist(idCheck)){
                        System.out.println("Exist product.");
                    }else{
                        System.out.println("Not found product.");
                    }
                    break;
                case 3:
                    System.out.println("\n--- SEARCH PRODUCT BY NAME ---");
                    String name = Utils.getString("Enter Name to Search: ");
                    ArrayList<Product> listResult = list.searchByName(name);
                    if(listResult.isEmpty()){
                        System.out.println("Have no product!");
                    }else{
                        System.out.println("Here is list product find by name: " + name);
                        for (Product product : listResult) {
                            product.display();
                        }
                    }
                    break;
                case 4:
                    System.out.println("\n--- UPDATE PRODUCT ---");
                    String idUpdate = Utils.getString("Enter ID to Update: ");
                    if(list.update(idUpdate)){
                        System.out.println("Update success.");
                    }else{
                        System.out.println("Update fail.");
                    }
                    break;
                case 5:
                    System.out.println("\n--- DELETE PRODUCT ---");
                    String idDelete = Utils.getString("Enter ID to Delete: ");
                    if(list.delete(idDelete)){
                        System.out.println("Delete success.");
                    }else{
                        System.out.println("Delete fail.");
                    }
                    break;
                case 6:
                    System.out.println("\n--- DISPLAY PRODUCTS ---");
                    list.display();
                    break;
                case 7:
                    System.out.println("\n--- SAVE PRODUCTS TO FILE ---");
                    if(list.saveToFile()){
                        System.out.println("Save file success.");
                    }else{
                        System.out.println("Save file fail.");
                    }
                    break;
                case 8:
                    cont = menu.confirmYesNo("Do you want to quit ? (Yes/No) ");
                    System.out.println("Goodbye!");
                    break;
            }

        } while (choice >= 0 && choice <= 8 && !cont);
    }
}
