public class ParkingSpot {

    private int id;
    private double price;
    private ParkingSpotType type;

    public ParkingSpot(int id, double price, ParkingSpotType type){
        this.id = id;
        this.price = price;
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpotType getType() {
        return type;
    }

    public void setType(ParkingSpotType type) {
        this.type = type;
    }


}
