import java.util.*;

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        requestQueue.offer(r);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public int getAvailableRooms(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int count) {
        availability.put(type, count);
    }
}

class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;
    private Map<String, Integer> counters;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
        counters = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();

        if (inventory.getAvailableRooms(type) <= 0) {
            System.out.println("No rooms available for " + reservation.getGuestName());
            return;
        }

        String roomId = generateRoomId(type);

        allocatedRoomIds.add(roomId);

        assignedRoomsByType.putIfAbsent(type, new HashSet<>());
        assignedRoomsByType.get(type).add(roomId);

        inventory.updateAvailability(type, inventory.getAvailableRooms(type) - 1);

        System.out.println("Booking confirmed for Guest: " +
                reservation.getGuestName() + ", Room ID: " + roomId);
    }

    private String generateRoomId(String type) {

        int count = counters.getOrDefault(type, 0) + 1;
        counters.put(type, count);

        String roomId = type + "-" + count;

        while (allocatedRoomIds.contains(roomId)) {
            count++;
            counters.put(type, count);
            roomId = type + "-" + count;
        }

        return roomId;
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        while (queue.hasPendingRequests()) {
            service.allocateRoom(queue.getNextRequest(), inventory);
        }
    }
}