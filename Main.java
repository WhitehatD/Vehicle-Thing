public class Main {


    public static void main(String[] args){
        ParkingGarage garage = new ParkingGarage();

        garage.addSpots(
                new ParkingSpot(1, 10, ParkingSpotType.LARGE),
                new ParkingSpot(2, 15, ParkingSpotType.LARGE),
                new ParkingSpot(3, 9, ParkingSpotType.SMALL),
                new ParkingSpot(4, 8, ParkingSpotType.SMALL),
                new ParkingSpot(5, 7, ParkingSpotType.LARGE));

        garage.reserveParkingSpot(new Vehicle(1, VehicleTypes.TRUCK));
        garage.reserveParkingSpot(new Vehicle(2, VehicleTypes.CAR));
        garage.reserveParkingSpot(new Vehicle(3, VehicleTypes.TRUCK));
        garage.reserveParkingSpot(new Vehicle(4, VehicleTypes.TRUCK));
        garage.reserveParkingSpot(new Vehicle(5, VehicleTypes.TRUCK));


        garage.output();
    }

}
