package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    @FXML
    private Button button_log_out;
    @FXML
    private Button button_delete_account;
    @FXML
    private Text welcome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_log_out.setOnAction(this::handleLogOut);
        button_delete_account.setOnAction(this::handleDeleteAccount);
    }

    private void handleLogOut(ActionEvent event) {
        DBUtils.changeScene(event, "/HomePage.fxml", "Log in!", null,null);
    }

    private void handleDeleteAccount(ActionEvent event) {
        // Implement the logic to delete the account
        // For example, you can call a method from DBUtils to delete the user from the database
        boolean success = DBUtils.deleteUser(welcome.getText().replace("Welcome ", ""));
        if (success) {
            DBUtils.changeScene(event, "/HomePage.fxml", "Account Deleted", null,null);
        } else {
            // Handle the case where the account deletion failed
            System.out.println("Failed to delete account");
        }
    }

    public void setUserInformation(String user) {
        welcome.setText("Welcome " + user);
    }
}