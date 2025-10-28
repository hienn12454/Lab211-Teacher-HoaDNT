package sample.dto;

import java.util.ArrayList;

/* Interface for a group of objects
 */

/**
 *
 * @author Hoa Doan
 */
public interface I_List {
  boolean create();
  boolean checkExistInFile(String username);
  boolean checkLogin(String userName, String password);
  ArrayList<User> search(String name);
  boolean update(String username);
  boolean delete(String username);
  void printListFile();
  boolean saveDataToFile();
  ArrayList<User> loadDataFromFile();
  void loadDataToList();
}
