import java.util.*;

class AddonService {

    private String serviceName;
    private double cost;

    public AddonService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddonServiceManager {

    private Map<String, List<AddonService>> servicesByReservation;

    public AddonServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddonService service) {
        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
        servicesByReservation.get(reservationId).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        double total = 0;

        List<AddonService> services = servicesByReservation.get(reservationId);

        if (services != null) {
            for (AddonService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        String reservationId = "Single-1";

        AddonServiceManager manager = new AddonServiceManager();

        AddonService s1 = new AddonService("Breakfast", 500.0);
        AddonService s2 = new AddonService("Airport Pickup", 1000.0);

        manager.addService(reservationId, s1);
        manager.addService(reservationId, s2);

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}
