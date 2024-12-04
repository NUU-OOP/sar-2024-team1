package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {

    @FXML
    private Button add_receptionist;
    @FXML
    private Button remove_receptionist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_receptionist.setOnAction(this::handleAddReceptionist);
        remove_receptionist.setOnAction(this::handleRemoveReceptionist);
    }

    private void handleAddReceptionist(ActionEvent event) {
        changeScene(event, "/sign-up.fxml", "Sign Up", "registrian");
    }

    private void handleRemoveReceptionist(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove Receptionist");
        dialog.setHeaderText("Enter the username of the receptionist to remove:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(username -> {
            boolean success = DBUtils.removeReceptionist(username);
            if (success) {
                showAlert("Receptionist removed successfully.");
            } else {
                showAlert("Failed to remove receptionist.");
            }
        });
    }

    private void changeScene(ActionEvent event, String fxmlFile, String title, String role) {
        Parent root = null;
        try {
            URL fxmlResource = getClass().getResource(fxmlFile);
            if (fxmlResource == null) {
                throw new NullPointerException("FXML file '" + fxmlFile + "' not found.");
            }
            FXMLLoader loader = new FXMLLoader(fxmlResource);
            root = loader.load();

            if (role != null) {
                SignUpController signUpController = loader.getController();
                signUpController.setRole(role);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to load the specified FXML file.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Unexpected error occurred: " + e.getMessage());
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}