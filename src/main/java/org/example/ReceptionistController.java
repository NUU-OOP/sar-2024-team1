package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class ReceptionistController {

    @FXML
    private Button add_user;
    @FXML
    private Button book_room;
    @FXML
    private Button remove_user;
    @FXML
    private Button cancel_booking;
    @FXML
    private TitledPane notifications;
    @FXML
    private Label username;

    @FXML
    public void initialize() {
        add_user.setOnAction(this::handleAddUser);
        book_room.setOnAction(this::handleBookRoom);
        remove_user.setOnAction(this::handleRemoveUser);
        cancel_booking.setOnAction(this::handleCancelBooking);
    }

    private void handleAddUser(ActionEvent event) {
        DBUtils.changeScene(event, "/sign-up.fxml", "Sign Up", null, "user");
    }

    private void handleBookRoom(ActionEvent event) {
        DBUtils.changeScene(event, "/Booking.fxml", "Book Room", null, "user");
    }

    private void handleRemoveUser(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove User");
        dialog.setHeaderText("Enter the username of the user to remove:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(username -> {
            boolean success = DBUtils.removeUser(username);
            if (success) {
                showAlert("User removed successfully.");
            } else {
                showAlert("Failed to remove user.");
            }
        });
    }

    private void handleCancelBooking(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cancel Booking");
        dialog.setHeaderText("Enter the order id:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(input -> {
            String[] parts = input.split(",");
            if (parts.length == 1) {
                String roomNumber = parts[0];
                DBUtils.cancelBooking(event, roomNumber);
            } else {
                showAlert("Invalid input format. Please use the format: order id");
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}