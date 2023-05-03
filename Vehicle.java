public class Vehicle {

    private int id;
    private VehicleTypes type;

    public Vehicle(int id, VehicleTypes type){
        this.id = id;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public VehicleTypes getType() {
        return type;
    }

    public void setType(VehicleTypes type) {
        this.type = type;
    }
}
