package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UsersLIstController implements Initializable {
    @FXML
    private TableView<UsersLIstController.Room> tableViewRooms;

    @FXML
    private TableColumn<UsersLIstController.Room, Integer> columnid;

    @FXML
    private TableColumn<UsersLIstController.Room, String> columnusername;

    @FXML
    private TableColumn<UsersLIstController.Room, String> columnpassword;
    @FXML
    private TableColumn<UsersLIstController.Room, String> columnrole;
    @FXML
    private TableColumn<UsersLIstController.Room, Integer> columnroom_number;

    private ObservableList<UsersLIstController.Room> roomList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        columnid.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        columnrole.setCellValueFactory(new PropertyValueFactory<>("role"));
        columnroom_number.setCellValueFactory(new PropertyValueFactory<>("room_number"));

        roomList = FXCollections.observableArrayList();

        loadDataFromDatabase();

    }
    private void loadDataFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/un";
        String user = "root";
        String password = "admin123";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT iduser,username,password,role,room_number FROM users");) {

            while (resultSet.next()) {
                int id = resultSet.getInt("iduser");
                String username = resultSet.getString("username");
                String  password1 = resultSet.getString("password");
                String role = resultSet.getString("role");
                int room_number = resultSet.getInt("room_number");

                roomList.add(new UsersLIstController.Room(id,username,password1,role,room_number));
            }

            tableViewRooms.setItems(roomList);


        }catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors here
        }
    }
    public static class Room extends UsersLIstController {
        private int id;
        private String username;
        private String password;
        private String role;
        private int room_number;

        public Room(int id, String username, String password, String role, int room_number) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
            this.room_number = room_number;
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }

        public int getRoom_number() {
            return room_number;
        }
    }

}


