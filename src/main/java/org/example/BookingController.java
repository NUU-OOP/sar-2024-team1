package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField roomNumberField;
    @FXML
    private TextField fromDateField;
    @FXML
    private TextField untilDateField;
    @FXML
    private Button bt_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_back.setOnAction(this::handleBackButton);
    }

    @FXML
    public void handleBookRoom(ActionEvent event) {
        String username = usernameField.getText();
        String roomNumber = roomNumberField.getText();
        String fromDate = fromDateField.getText();
        String untilDate = untilDateField.getText();

        if (username.isEmpty() || roomNumber.isEmpty() || fromDate.isEmpty() || untilDate.isEmpty()) {
            showAlert("All fields must be filled out.");
        } else {
            DBUtils.book(event, username, roomNumber, fromDate, untilDate);
            DBUtils.changeScene(event, "/HomePage.fxml", "Welcome back", null, null);
        }
    }

    private void handleBackButton(ActionEvent event) {
        DBUtils.goBack(event);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}