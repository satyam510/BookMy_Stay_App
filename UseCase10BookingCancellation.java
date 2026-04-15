import java.util.*;

class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailableRooms(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int count) {
        availability.put(type, count);
    }
}

class CancellationService {

    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation not found");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        releasedRoomIds.push(reservationId);

        inventory.updateAvailability(
                roomType,
                inventory.getAvailableRooms(roomType) + 1
        );

        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    public void showRollbackHistory() {

        System.out.println("Rollback History (Most Recent First):");

        for (String id : releasedRoomIds) {
            System.out.println("Released Reservation ID: " + id);
        }
    }
}

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        service.registerBooking("Single-1", "Single");

        service.cancelBooking("Single-1", inventory);

        service.showRollbackHistory();

        System.out.println("Updated Single Room Availability: " +
                inventory.getAvailableRooms("Single"));
    }
}