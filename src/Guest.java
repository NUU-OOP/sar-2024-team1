import java.util.ArrayList;

public class Guest extends Person {
    private ArrayList<Room> bookedRooms;
    public Guest(){bookedRooms = new ArrayList<>();}

    public Guest(String guestName, int guestId) {
        super();
    }

    public void searchAvailableRooms(ArrayList<Room> rooms, RoomStyle type) {
        System.out.println("Available " + type + " rooms:");
        boolean found = false;
        for (Room room : rooms) {
            if (room.getType().equals(type) && room.isAvailable()) {
                System.out.println(room);
                found = true;
            }
        }if (!found) {
            System.out.println("No available rooms of type " + type);
        }
    }
    public void bookRoom(ArrayList<Room> rooms, int roomNumber) {
        for(Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                room.bookRoom(getName());
                bookedRooms.add(room);
                System.out.println("Room " + roomNumber + " booked successfully by " + getName());
                return;
            }
        }
        System.out.println("Room number " + roomNumber + " is not available");
    }
    public void cancelBooking(ArrayList<Room> rooms, int roomNumber) {
        for(Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.checkOut();
                bookedRooms.remove(room);
                System.out.println("Booking for room " + roomNumber + " had been cancelled successfully");
                return;
            }
        }
        System.out.println("Room number " + roomNumber + " is not in your booked list");
    }
    public void viewBookedRooms() {
        if (bookedRooms.isEmpty()) {
            System.out.println("No booked rooms available");
        } else {
            for (Room room : bookedRooms) {
                System.out.println(room);
            }
        }
    }
}
