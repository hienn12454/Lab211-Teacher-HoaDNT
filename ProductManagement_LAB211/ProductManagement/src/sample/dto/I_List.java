package sample.dto;

import java.util.ArrayList;

/* Interface for a group of objects
 */
/**
 *
 * @author TUF
 */
public interface I_List {

    boolean create();

    void display();

    boolean checkExist(String id);

    ArrayList<Product> searchByName(String name);

    boolean update(String id);

    boolean delete(String id);

    boolean saveToFile();

    ArrayList<Product> loadFromFile();
}
