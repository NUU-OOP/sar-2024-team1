package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ListOfRoomsController implements Initializable {
    @FXML
    private TableView<Room> tableViewRooms;

    @FXML
    private TableColumn<Room, Integer> columnRoomNumber;

    @FXML
    private TableColumn<Room, String> columnType;

    @FXML
    private TableColumn<Room, Double> columnPrice;
    @FXML
    private TableColumn<Room, String> columnStatus;

    private ObservableList<Room> roomList;
    @FXML
    Button bt_book;
    @FXML
    TextField tf_roomnumber;
    @FXML
    private Button back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/HomePage.fxml","Log in!",null,null);
            }
        });

        columnRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        roomList = FXCollections.observableArrayList();

        loadDataFromDatabase();

    }
    private void loadDataFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/un";
        String user = "root";
        String password = "admin123";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT room_number, type, price,status FROM hotel_rooms")) {

            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");
                String status = resultSet.getString("status");

                roomList.add(new Room(roomNumber, type, price,status));
            }

            tableViewRooms.setItems(roomList);

            bt_book.setVisible(false);

        }catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors here
        }
    }
    public static class Room {
        private int roomNumber;
        private String type;
        private double price;
        private String status;

        public Room(int roomNumber, String type, double price,String status) {
            this.roomNumber = roomNumber;
            this.type = type;
            this.price = price;
            this.status=status;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public String getType() {
            return type;
        }

        public double getPrice() {
            return price;
        }
        public String getStatus() {
            return status;
        }
    }
    public void setButtonVisibility(boolean isVisible) {

        bt_book.setVisible(isVisible);
        tf_roomnumber.setVisible(isVisible);

    }
}
