package sample.view;

import java.util.ArrayList;
import sample.dto.I_List;
import sample.dto.I_Menu;
import sample.controllers.Menu;
import sample.controllers.UserList;
import sample.dto.User;
import sample.utils.Utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hoa Doan
 */
public class UserManagement {

    public static void main(String args[]) {
        I_Menu menu = new Menu();
        menu.addItem("1. Create user account.");
        menu.addItem("2. Check exits user.");
        menu.addItem("3. Search user information by name");
        menu.addItem("4. Update user.");
        menu.addItem("5. Delete user.");
        menu.addItem("6. Save Products to file.");
        menu.addItem("7. Print list user from file.");
        menu.addItem("8: Quit");
        int choice;
        boolean cont = false;
        I_List list = new UserList();
        list.loadDataToList();
        do {
            menu.showMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    if (list.create()) {
                        System.out.println("Add success.");
                    } else {
                        System.out.println("Add fail.");
                    }
                    break;
                case 2:
                    String username = Utils.getString("Enter Username to check(file): ");
                    if (list.checkExistInFile(username)) {
                        System.out.println("Exist User.");
                    } else {
                        System.out.println("No User Found!");
                    }
                    break;
                case 3:
                    String name = Utils.getString("Enter Name to search: ");
                    ArrayList<User> listSearch = list.search(name);
                    if (listSearch.isEmpty()) {
                        System.out.println("Have no any user.");
                    } else {
                        for (User user : listSearch) {
                            user.display();
                        }
                    }
                    break;
                case 4:
                    System.out.println("Login system: ");
                    String userNameUpdate = Utils.getString("Enter userName: ");
                    String passwordUpdate = Utils.getString("Enter password: ");
                    if (list.checkLogin(userNameUpdate, passwordUpdate)) {
                        if (list.update(userNameUpdate)) {
                            System.out.println("Update success.");
                        } else {
                            System.out.println("Update fail.");
                        }
                    } else {
                        System.out.println("Login fail!");
                    }
                    break;
                case 5:
                    System.out.println("Login system: ");
                    String userNameDelete = Utils.getString("Enter userName: ");
                    String passwordDelete = Utils.getString("Enter password: ");
                    if (list.checkLogin(userNameDelete, passwordDelete)) {
                        if (list.delete(userNameDelete)) {
                            System.out.println("Delete success.");
                        } else {
                            System.out.println("Delete fail.");
                        }
                    } else {
                        System.out.println("Login fail!");
                    }
                    break;
                case 6:
                    if (list.saveDataToFile()) {
                        System.out.println("Save file success.");
                    } else {
                        System.out.println("Save file fail.");
                    }
                    break;
                case 7:
                    list.printListFile();
                    break;
                case 8:
                    cont = menu.confirmYesNo("Do you want to quit?(Y/N)");
                    break;
            }
        } while (choice >= 0 && choice <= 8 && !cont);
    }
}
