public class Room {
    private int roomNumber;
    private RoomStyle type;
    private boolean isAvailable;
    private String customerName;
    public Room(int roomNumber, RoomStyle type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isAvailable = true; // Default: room is available
        this.customerName = null;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public RoomStyle getType() {
        return type;
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    public void bookRoom(String customerName) {
        this.isAvailable = false;
        this.customerName = customerName;
    }
    public void checkOut() {
        this.isAvailable = true;
        this.customerName = null;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - " +
                (isAvailable ? "Available" : "Occupied by " + customerName);
    }
//
//    public void displayInfo(){
//        System.out.println("Room ");
//    }
}
