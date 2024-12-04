package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    Button button_signup;
    @FXML
    Button button_login;
    @FXML
    TextField tf_username;
    @FXML
    TextField tf_password;
    @FXML
    private ChoiceBox<String> cb_role;
    public void setRole(String role) {
        cb_role.setValue(role);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()&&!cb_role.getValue().trim().isEmpty()) {
                    DBUtils.signUpUser(event,tf_username.getText(),tf_password.getText(),cb_role.getValue());
                }else{
                    System.out.println("The credentials are required!");
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The credentials are required!");
                    alert.show();
                }
            }
        });
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/LogIn.fxml","Log in!",null,null);
            }
        });
    }
}
