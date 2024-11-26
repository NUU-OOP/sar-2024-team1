import java.util.Scanner;

public class LoginPage {

    // Predefined credentials (for simplicity)
    private static final String USERNAME = "manager";
    private static final String PASSWORD = "password123";

    // Method to handle login
    public static boolean login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==== Hotel Management System Login ====");
        System.out.print("Enter Username: ");
        String enteredUsername = scanner.nextLine();

        System.out.print("Enter Password: ");
        String enteredPassword = scanner.nextLine();

        if (USERNAME.equals(enteredUsername) && PASSWORD.equals(enteredPassword)) {
            System.out.println("Login successful! Welcome, Manager.");
            return true;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return false;
        }
    }

    // Main method for testing the login system
    public static void main(String[] args) {
        boolean isLoggedIn = false;

        // Allow multiple attempts to log in
        while (!isLoggedIn) {
            isLoggedIn = login();
        }
    }
}

