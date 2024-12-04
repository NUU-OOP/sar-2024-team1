package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.Stack;


public class DBUtils {
    private static Stack<Scene> sceneHistory = new Stack<>();

    public static boolean deleteUser(String username) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/un", "root", "admin123");
            psDelete = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            psDelete.setString(1, username);
            int affectedRows = psDelete.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database error: " + e.getMessage());
            return false;
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void book(ActionEvent event, String username, String room_number, String from_date, String until_date) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckRoomExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/un", "root", "admin123");

            psCheckRoomExists = connection.prepareStatement("SELECT * FROM orders WHERE room_number = ? AND from_date <= ? AND until_date >= ?");
            psCheckRoomExists.setString(1, room_number);
            psCheckRoomExists.setString(2, until_date);
            psCheckRoomExists.setString(3, from_date);
            resultSet = psCheckRoomExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                showAlert("Room is already booked for the selected dates.");
            } else {
                psInsert = connection.prepareStatement("INSERT INTO orders (username, room_number, from_date, until_date) VALUES (?, ?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, room_number);
                psInsert.setString(3, from_date);
                psInsert.setString(4, until_date);
                psInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database error: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckRoomExists != null) {
                try {
                    psCheckRoomExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void cancelBooking(ActionEvent event, String order_id) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/un", "root", "admin123");
            psDelete = connection.prepareStatement("DELETE FROM orders WHERE OrderID = ? ");
            psDelete.setString(1, order_id);
            int affectedRows = psDelete.executeUpdate();

            if (affectedRows > 0) {
                showAlert("Booking canceled successfully.");
            } else {
                showAlert("No booking found for the specified details.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database error: " + e.getMessage());
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String role) {
        Parent root = null;

        try {
            URL fxmlResource = DBUtils.class.getResource(fxmlFile);
            if (fxmlResource == null) {
                throw new NullPointerException("FXML file '" + fxmlFile + "' not found.");
            }
            FXMLLoader loader = new FXMLLoader(fxmlResource);
            root = loader.load();

            if (username != null && role != null) {
                switch (role) {
                    case "user" -> {
                        UserController userController = loader.getController();
                        userController.setUserInformation(username);
                    }
                    case "admin" -> {
                        ManagerController managerController = loader.getController();
                        // Set any necessary information for the managerController
                    }
                    case "registrian" -> {
                        ReceptionistController receptionistController = loader.getController();
                        // Set any necessary information for the receptionistController
                    }
                    default -> throw new IllegalArgumentException("Invalid role: " + role);
                }
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
            sceneHistory.push(stage.getScene());
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to load the specified FXML file.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Unexpected error occurred: " + e.getMessage());
        }
    }
    public static void goBack(ActionEvent event) {
        if (!sceneHistory.isEmpty()) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene previousScene = sceneHistory.pop();
            stage.setScene(previousScene);
            stage.show();
        }else {
            changeScene(event,"/HomePage.fxml",null,null,null);
        }
    }
    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
    public static void signUpUser(ActionEvent event, String username, String password, String role) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/un", "root", "admin123");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This username is already taken");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, role);
                psInsert.executeUpdate();

                switch (role) {
                    case "user" -> changeScene(event, "/User.fxml", "Welcome!", username,role);
                    case "admin" -> changeScene(event, "/manager.fxml", "Welcome!", username,role);
                    case "registrian" -> changeScene(event, "/receptionist.fxml", "Welcome!", username,role);
                    default -> showAlert("Invalid role!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Database error: " + e.getMessage());
            alert.show();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/un", "root", "admin123");
            preparedStatement = connection.prepareStatement("SELECT password, role FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User could not be found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Wrong username or password");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String dbPassword = resultSet.getString("password");
                    String role = resultSet.getString("role");

                    if (dbPassword.equals(password)) {
                        switch (role) {
                            case "user" -> changeScene(event, "/User.fxml", "Welcome!", username,role);
                            case "admin" -> changeScene(event, "/manager.fxml", "Welcome!", username,role);
                            case "registrian" -> changeScene(event, "/receptionist.fxml", "Welcome!", username,role);
                            default -> {
                                System.out.println("Invalid role!");
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("Invalid role!");
                                alert.show();
                            }
                        }
                    } else {
                        System.out.println("Wrong input!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Wrong input!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean removeUser(String username) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/un", "root", "admin123");
            psDelete = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            psDelete.setString(1, username);
            int affectedRows = psDelete.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database error: " + e.getMessage());
            return false;
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean removeReceptionist(String username) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/un", "root", "admin123");
            psDelete = connection.prepareStatement("DELETE FROM users WHERE username = ? AND role = 'registrian'");
            psDelete.setString(1, username);
            int affectedRows = psDelete.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database error: " + e.getMessage());
            return false;
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
