/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.password_managment_system;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ozen
 */
public class Account {

    private SimpleStringProperty appName;
    private SimpleStringProperty userName;
    private SimpleStringProperty password;
    private SimpleStringProperty email;

    public Account(String appName, String userName, String password, String email) {
        this.appName = new SimpleStringProperty(appName);
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
    }

    public String getAppName() {
        return this.appName.get();
    }

    public void setAppName(String appName) {
        this.appName.set(appName);
    }

    public String getUserName() {
        return this.userName.get();
    }

    public void setUserName(String UserName) {
        this.userName.set(UserName);
    }

    public String getPassword() {
        return this.password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

}
