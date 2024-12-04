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


public class HousekeepingListController implements Initializable {

    @FXML
    private TableView<HousekeepingListController.Room> tableViewRooms;
    @FXML
    private TableColumn<HousekeepingListController.Room, Integer> columnid;
    @FXML
    private TableColumn<HousekeepingListController.Room, String> columnusername;
    @FXML
    private TableColumn<HousekeepingListController.Room, String> columncleaning;
    @FXML
    private TableColumn<HousekeepingListController.Room, String> columnlaundry;
    @FXML
    private TableColumn<HousekeepingListController.Room, Integer> columnroom_number;

    private ObservableList<HousekeepingListController.Room> roomList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        columnid.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        columncleaning.setCellValueFactory(new PropertyValueFactory<>("cleaning"));
        columnlaundry.setCellValueFactory(new PropertyValueFactory<>("laundry"));
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
             ResultSet resultSet = statement.executeQuery("SELECT id,username,cleaning,laundry,room_number FROM housekeeping");) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String  cleaning = resultSet.getString("cleaning");
                String laundry = resultSet.getString("laundry");
                int room_number = resultSet.getInt("room_number");

                roomList.add(new HousekeepingListController.Room(id,username,cleaning,laundry,room_number));
            }

            tableViewRooms.setItems(roomList);


        }catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors here
        }
    }
    public static class Room extends HousekeepingListController {
        private int id;
        private String username;
        private String cleaning;
        private String laundry;
        private int room_number;

        public Room(int id, String username, String cleaning, String laundry, int room_number) {
           this.id = id;
           this.username= username;
           this.cleaning = cleaning;
           this.laundry = laundry;
           this.room_number = room_number;
        }

        public int getId() {
            return id;
        }

        public String getUserName() {
            return username;
        }

        public String getCleaning() {
            return cleaning;
        }

        public String getLaundry() {
            return laundry;
        }

        public int getRoom_number() {
            return room_number;
        }
    }
}
