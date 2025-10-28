/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import java.io.Serializable;
import sample.utils.Utils;

public class User implements Serializable{
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    private String email;
    
    @Override
    public boolean equals(Object obj) {
        User p= (User) obj;
        return this.userName.equalsIgnoreCase(p.userName);
    }
    
    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }
    
    public User(String userName, String firstName, String lastName, String password, String phone, String email) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean create(){
        boolean check = true;
        try{
            this.firstName = Utils.getString("Enter FirstName: ");
            this.lastName = Utils.getString("Enter LastName: ");
            this.password = Utils.getStringByFormat("Enter Password(>=6 character and no space): ", Utils.PASSWORD_VALID);
            String confirmPassword = Utils.getString("Enter Confirm Password: ");
            this.phone = Utils.getStringByFormat("Enter Phone Number(0xx..): ", Utils.PHONE_VALID);
            this.email = Utils.getStringByFormat("Enter Email: ", Utils.EMAIL_VALID);
            if(!confirmPassword.equals(password)){
                System.out.println("Confirm password must equal password.");
                check = false;
            }
        }catch(Exception e){
        }
        return check;
    }
    public void display(){
        System.out.println("User{" + "userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", phone=" + phone + ", email=" + email + '}');
    }
    
}
