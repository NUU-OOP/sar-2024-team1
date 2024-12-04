package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class HomePageController {

    @FXML
    private Button sign_in;
    @FXML
    private Button sign_up;
    @FXML Button list_rooms;


    @FXML
    public void initialize() {
        sign_in.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"/LogIn.fxml","Sign in!",null,null);
            }
        });

        sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"/sign-up.fxml","Sign up!",null,null);
            }
        });
        list_rooms.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                openListOfRooms(event,false);
            }
        });






    }
    private ListOfRoomsController listOfRoomsController; // Reference to the controller

    private void openListOfRooms(ActionEvent event, boolean buttonVisible) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListOfRooms.fxml"));
            Parent root = loader.load();
            ListOfRoomsController controller = loader.getController();
            controller.setButtonVisibility(buttonVisible);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
