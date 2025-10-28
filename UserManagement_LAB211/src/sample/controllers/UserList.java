package sample.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hoa Doan
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import sample.dto.I_List;
import sample.dto.User;
import sample.utils.Utils;

public class UserList extends ArrayList<User> implements I_List {

    private static final String FILE_PATH = "user.dat";

    @Override
    public boolean create() {
        boolean result = false;
        try {
            boolean checkExist = true;
            String id = "";
            do {
                id = Utils.getStringByFormat("Enter Username(>=5 characters and no space): ", Utils.USER_NAME_VALID);
                checkExist = this.contains(new User(id));
                if (checkExist) {
                    System.out.println("Username is exist, input again");
                } else {
                    checkExist = false;
                }
            } while (checkExist);

            User u = new User(id);
            if (u.create()) {
                String encryptPassword = hashSHA256(u.getPassword());
                u.setPassword(encryptPassword);
                this.add(u);
                result = true;
            }
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public boolean checkExistInFile(String username) {
        boolean result = false;
        ArrayList<User> listFile = loadDataFromFile();
        for (User p : listFile) {
            if (p.getUserName().equalsIgnoreCase(username)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public ArrayList<User> search(String name) {
        ArrayList<User> listUser = new ArrayList<>();
        for (User u : this) {
            if (u.getFirstName().toLowerCase().contains(name.toLowerCase())
                    || u.getLastName().toLowerCase().contains(name.toLowerCase())) {
                listUser.add(u);
            }
        }
        if (!listUser.isEmpty()) {
            Collections.sort(listUser, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
                }
            }
            );
        }
        return listUser;
    }

    @Override
    public boolean checkLogin(String userName, String password) {
        boolean result = false;
        for (User u : this) {
            if (u.getUserName().equalsIgnoreCase(userName) && u.getPassword().equals(hashSHA256(password))) {
                result = true;
            }
        }
        return result;
    }

    public boolean update(String id) {
        boolean result = false;
        int index = this.indexOf(new User(id));
        if (index != -1) {
            User u = this.get(index);
            String newFirstName = Utils.updateString("Enter New FirstName: ", u.getFirstName());
            String lastFirstName = Utils.updateString("Enter New LastName: ", u.getLastName());
            String newPassword = Utils.updateString("Enter New Password: ", u.getPassword(), Utils.PASSWORD_VALID);
            String newPhone = Utils.updateString("Enter New Phone: ", u.getPhone(), Utils.PHONE_VALID);
            String newEmail = Utils.updateString("Enter New Email: ", u.getEmail(), Utils.EMAIL_VALID);

            u.setFirstName(newFirstName);
            u.setLastName(lastFirstName);
            u.setPassword(newPassword);
            u.setPhone(newPhone);
            u.setEmail(newEmail);
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(String username) {
        boolean result = false;
        int index = this.indexOf(new User(username));
        if (index != -1) {
            String confirm = Utils.getString("Are you sure delete(y/n): ");
            if (confirm.equalsIgnoreCase("y")) {
                this.remove(index);
                result = true;
            }
        }
        return result;
    }

    @Override
    public void printListFile() {
        ArrayList<User> listFile = loadDataFromFile();
        if (listFile.isEmpty()) {
            System.out.println("List empty.Nothing to print.");
        } else {
            Collections.sort(listFile, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
                }
            }
            );
            for (User p : listFile) {
                p.display();
            }
        }
    }

    private String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveDataToFile() {
        boolean check = true;
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (Exception e) {
            check = false;
        }
        return check;
    }

    @Override
    public ArrayList<User> loadDataFromFile() {
        ArrayList<User> listFile = new ArrayList();
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listFile = (ArrayList<User>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("Read data to user.dat fail!");
        }
        return listFile;
    }

    @Override
    public void loadDataToList() {
        ArrayList<User> listFile = loadDataFromFile();
        if (!listFile.isEmpty()) {
            for (User user : listFile) {
                this.add(user);
            }
        }
    }

}
