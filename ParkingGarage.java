import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ParkingGarage{

    private final TreeMap<ParkingSpot, Vehicle> parkingSpots;
    private List<Vehicle> leftOutVehicles;

    public ParkingGarage(){
        parkingSpots = new TreeMap<>();
        leftOutVehicles = new ArrayList<>();
    }

    public void addSpots(ParkingSpot... spots){
        for(ParkingSpot spot : spots){
            parkingSpots.put(spot, null);
        }
    }

    private boolean isOccupied(ParkingSpot spot){
        return parkingSpots.get(spot) != null;
    }

    public void reserveParkingSpot(Vehicle vehicle){
        if(parkingSpots.keySet().stream().allMatch(this::isOccupied)) {
            return;
        }

        ParkingSpotType type = ParkingSpotType.LARGE;

        if(vehicle.getType() == VehicleTypes.CAR && parkingSpots
                .keySet()
                .stream()
                .anyMatch(spot -> spot.getType() == ParkingSpotType.SMALL && !isOccupied(spot))){
            type = ParkingSpotType.SMALL;
        }

        ParkingSpotType finalType = type;
        Optional<ParkingSpot> availableSpot = parkingSpots
                .keySet()
                .stream()
                .filter(spot -> !isOccupied(spot) && spot.getType() == finalType)
                .findFirst();

        if(availableSpot.isPresent()){
            this.parkingSpots.put(availableSpot.get(), vehicle);
        } else {
            leftOutVehicles.add(vehicle);
        }
    }

    public TreeMap<ParkingSpot, Vehicle> getParkingSpots() {
        return parkingSpots;
    }

    public List<Vehicle> getLeftOutVehicles() {
        return leftOutVehicles;
    }

    public void output(){
        File file = new File("output.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream writer = null;
        try {
            writer = new FileOutputStream("output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(writer == null){
            System.out.println("Could not init writer successfully.");
            return;
        }

        for(Map.Entry<ParkingSpot, Vehicle> entry : parkingSpots.entrySet()){
            ParkingSpot spot = entry.getKey();
            Vehicle vehicle = entry.getValue();

            if(vehicle == null)
                continue;

            try {
                writer.write(("Vehicle with id: " + vehicle.getId() + " (" + vehicle.getType()  + ")"
                        + " is parked on spot with id: " + spot.getId() + " and type: " + spot.getType()
                        + ". Fare: " + spot.getPrice() + " RON").getBytes());
                writer.write("\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(Vehicle vehicle : leftOutVehicles){
            try {
                writer.write(("Vehicle with id: " + vehicle.getId() + " (" + vehicle.getType()  + ")"
                        + " hasn't been parked. It did not fit.").getBytes());
                writer.write("\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
