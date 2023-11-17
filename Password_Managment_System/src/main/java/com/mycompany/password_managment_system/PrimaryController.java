/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.password_managment_system;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Furkan
 */
public class PrimaryController implements Initializable {

    @FXML
    private TextField statusTF;
    @FXML
    private TextField appName;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField userName;
    @FXML
    private TableView<Account> tableView;
    @FXML
    private TableColumn<Account, String> appNameColumn;
    @FXML
    private TableColumn<Account, String> userNameColumn;
    @FXML
    private TableColumn<Account, String> passwordColumn;
    @FXML
    private TableColumn<Account, String> emailColumn;

    static final String DB_URL = "jdbc:derby:AccountDB; create = true";

    private final ObservableList<Account> data
            = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appNameColumn.setCellValueFactory(new PropertyValueFactory<>("appName"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableView.setItems(data);
    }

    @FXML
    private void addButtonHandler(ActionEvent event) throws SQLException {
        System.out.println("\nAdding Employee...");
        String checkDuplicateQuery = "SELECT * FROM Account WHERE AppNAme = ?";
        String insertQuery = "INSERT INTO Account (AppName, UserName, Email, Password) "
                + "VALUES (?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();

            PreparedStatement checkStmt = conn.prepareStatement(checkDuplicateQuery);
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);

            String appName, userName, email, password;

            appName = this.appName.getText();
            userName = this.userName.getText();
            email = this.email.getText();
            password = this.password.getText();

            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                statusTF.setText("Error happened");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("DUPLICATE");
                alert.setHeaderText("Duplicate Detected");
                alert.setContentText("This email address is already in the system. Please ensure you are not adding a duplicate employee.");
                alert.showAndWait();
            } else {
                statusTF.setText("An Account has been added to the Databse.");
                data.add(new Account(appName, userName, email, password));
                insertStmt.setString(1, appName);
                insertStmt.setString(2, userName);
                insertStmt.setString(3, email);
                insertStmt.setString(4, password);
                int rows = insertStmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @FXML
    private void deleteButtonHandler(ActionEvent event) throws SQLException {
        System.out.println("\nAn Account has been deleted!");
        String sql = "DELETE FROM Account WHERE appName = ?";
        Account selectedAccount = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().removeAll(selectedAccount);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, selectedAccount.getAppName());

            int rows = pstm.executeUpdate();
            System.out.println(rows + " record(s) were deleted from the database. ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        statusTF.setText("An Account has been deleted!");
    }

    @FXML
    private void createMenuItemHandler(ActionEvent event) throws SQLException {
        System.out.println("Account Table is being created....");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();

            //stmt.execute("DROP TABLE employeee");
            //System.out.println("Table coffee dropped.");
            String query = "CREATE TABLE Account ("
                    + "AppName VARCHAR(50)  NOT NULL PRIMARY KEY,"
                    + "UserName VARCHAR(50),"
                    + "Email CHAR(50),"
                    + "Password CHAR(50))";

            stmt.execute(query);

            //conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        statusTF.setText("Account Table has been created...");
    }

    @FXML
    private void listButtonHandler(ActionEvent event) throws SQLException {
        System.out.println("\nRecords are being listed...");
        System.out.println("");

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM Account";
            ResultSet result = stmt.executeQuery(query);

            String appName, userName, email, password;

            while (result.next()) {
                appName = result.getString("AppName");
                userName = result.getString("UserNAme");
                email = result.getString("Email");
                password = result.getString("Password");
                data.add(new Account(appName, userName, email, password));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        statusTF.setText("Account table displayed");
    }

    @FXML
    private void closeButtonHandler(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void clearAllButtonHandler(ActionEvent event) {
        tableView.getItems().clear();
    }
}
