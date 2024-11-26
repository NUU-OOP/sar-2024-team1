import java.util.Scanner;

public class HotelManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Receptionist receptionist = new Receptionist();
        Manager manager = new Manager();
        Guest guest = null;

        System.out.println("Welcome to the Hotel Management System!");
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Manager Functions");
            System.out.println("2. Receptionist Functions");
            System.out.println("3. Guest Functions");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Manager Functions
                    System.out.println("\nManager Functions:");
                    System.out.println("1. Add Worker");
                    System.out.println("2. Remove Worker");
                    System.out.println("3. Display Workers");
                    System.out.print("Choose an option: ");
                    int managerChoice = scanner.nextInt();

                    if (managerChoice == 1) {
                        System.out.print("Enter worker ID: ");
                        int workerId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter worker role (e.g., MANAGER, RECEPTIONIST): ");
                        String role = scanner.nextLine().toUpperCase();
                        AccountType accountType = AccountType.valueOf(role);
                        manager.addWorker(workerId, accountType.name());
                    } else if (managerChoice == 2) {
                        System.out.print("Enter worker ID to remove: ");
                        int workerId = scanner.nextInt();
                        manager.removeWorker(workerId);
                    } else if (managerChoice == 3) {
                        manager.display();
                    }
                    break;

                case 2: // Receptionist Functions
                    System.out.println("\nReceptionist Functions:");
                    System.out.println("1. Add Room");
                    System.out.println("2. Search Room");
                    System.out.println("3. Display All Rooms");
                    System.out.print("Choose an option: ");
                    int receptionistChoice = scanner.nextInt();

                    if (receptionistChoice == 1) {
                        System.out.print("Enter room number: ");
                        int roomNumber = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter room type (SINGLE, DOUBLE, SUITE): ");
                        String roomTypeInput = scanner.nextLine().toUpperCase();
                        RoomStyle roomType = RoomStyle.valueOf(roomTypeInput);
                        receptionist.addRoom(roomNumber, roomType);
                    } else if (receptionistChoice == 2) {
                        scanner.nextLine();
                        System.out.print("Enter room type to search (SINGLE, DOUBLE, SUITE): ");
                        String roomTypeInput = scanner.nextLine().toUpperCase();
                        RoomStyle roomType = RoomStyle.valueOf(roomTypeInput);
                        System.out.print("Search only available rooms? (true/false): ");
                        boolean isAvailable = scanner.nextBoolean();
                        receptionist.searchRoom(roomType, isAvailable);
                    } else if (receptionistChoice == 3) {
                        receptionist.displayRooms();
                    }
                    break;

                case 3: // Guest Functions
                    if (guest == null) {
                        System.out.println("You are not registered as a guest. Let's register you first.");
                        scanner.nextLine();
                        System.out.print("Enter your name: ");
                        String guestName = scanner.nextLine();
                        System.out.print("Enter your guest ID: ");
                        int guestId = scanner.nextInt();
                        guest = new Guest(guestName, guestId);
                        System.out.println("Welcome, " + guestName + "!");
                    }

                    System.out.println("\nGuest Functions:");
                    System.out.println("1. Search Available Rooms");
                    System.out.println("2. Book a Room");
                    System.out.println("3. Cancel Booking");
                    System.out.println("4. View My Booked Rooms");
                    System.out.print("Choose an option: ");
                    int guestChoice = scanner.nextInt();

                    if (guestChoice == 1) {
                        scanner.nextLine();
                        System.out.print("Enter room type to search (SINGLE, DOUBLE, SUITE): ");
                        String roomTypeInput = scanner.nextLine().toUpperCase();
                        RoomStyle roomType = RoomStyle.valueOf(roomTypeInput);
                        guest.searchAvailableRooms(receptionist.rooms, roomType);
                    } else if (guestChoice == 2) {
                        System.out.print("Enter room number to book: ");
                        int roomNumber = scanner.nextInt();
                        guest.bookRoom(receptionist.rooms, roomNumber);
                    } else if (guestChoice == 3) {
                        System.out.print("Enter room number to cancel booking: ");
                        int roomNumber = scanner.nextInt();
                        guest.cancelBooking(receptionist.rooms, roomNumber);
                    } else if (guestChoice == 4) {
                        guest.viewBookedRooms();
                    }
                    break;

                case 4: // Exit
                    System.out.println("Thank you for using the Hotel Management System. Goodbye!");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
