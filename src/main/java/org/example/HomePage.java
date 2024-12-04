package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePage extends Application {
    private static final Logger logger = Logger.getLogger(HomePage.class.getName());

    @Override
    public void start(Stage stage) {
        try {
            URL fxmlResource = getClass().getResource("/payment.fxml");
            if (fxmlResource == null){
                logger.log(Level.SEVERE, "FXML file '/HomePage.fxml' not found in resources.");
                throw new NullPointerException("FXML file not found.");
            }
            Parent root = FXMLLoader.load(fxmlResource);
            Scene scene = new Scene(root);
            stage.setTitle("Sign in" +
                    "" +
                    "" +
                    "");
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Failed to load FXML file: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred: " + e.getMessage());  e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

