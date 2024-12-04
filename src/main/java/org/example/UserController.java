package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.management.Notification;
import java.net.URL;
import java.util.ResourceBundle;



public class UserController implements Initializable {
    @FXML
    private MenuItem mi_theater;
    @FXML
    private MenuItem mi_swimming_pool;
    @FXML
    private Button bt_bookRoom;
    @FXML
    public Text tx_username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mi_theater.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking service");
                alert.setContentText("Booked successfully");
                alert.showAndWait();
            }
        });
        mi_swimming_pool.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        bt_bookRoom.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"/ListOfRooms.fxml",null,null,null);
            }
        });

    }
    public void setUserInformation(String username){
        tx_username.setText(username);
    }


}
