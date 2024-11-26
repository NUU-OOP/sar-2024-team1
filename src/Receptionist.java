import java.util.ArrayList;

public class Receptionist extends Person {
    protected ArrayList<Room> rooms;

    public Receptionist() {
        rooms = new ArrayList<>();
    }


    public void addRoom(int roomNumber, RoomStyle type) {
        rooms.add(new Room(roomNumber, type));
        System.out.println("Room added: " + roomNumber + " (" + type + ")");
    }
    public void searchRoom(RoomStyle type, boolean isAvailable) {
        System.out.println("Searching for " + (isAvailable ? "available" : "all") + " " + type + " rooms:");
        for (Room room : rooms) {
            if (room.getType().equals(type) && room.isAvailable() == isAvailable) {
                System.out.println(room);
            }
        }
    }
    public void bookRoom(int roomNumber, String customerName) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                room.bookRoom(customerName);
                System.out.println("Room " + roomNumber + " booked successfully for " + customerName);
                return;
            }
        }
        System.out.println("Room " + roomNumber + " is not available for booking.");
    }
    public void checkIn(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && !room.isAvailable()) {
                System.out.println("Customer checked into Room " + roomNumber);
                return;
            }
        }
        System.out.println("Room " + roomNumber + " is not booked yet.");
    }
    public void checkOut(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && !room.isAvailable()) {
                room.checkOut();
                System.out.println("Room " + roomNumber + " is now available.");
                return;
            }
        }
        System.out.println("Room " + roomNumber + " is already available.");
    }

    public void displayRooms() {
        System.out.println("All Rooms:");
        for (Room room : rooms) {
            System.out.println(room);
        }
    }
}
