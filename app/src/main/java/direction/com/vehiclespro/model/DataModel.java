package direction.com.vehiclespro.model;

public class DataModel {

    private int id;
    private String dateT;
    private String name;
    private String vehicleNo;
    private String make;
    private String model;
    private String variant;
    private String fuelType;
    private String photo;

    //getters
    public int getId() {
        return id;
    }

    public String getName() { return name; }

    public String getDateT() {
        return dateT;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVariant() {
        return variant;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getPhoto() {
        return photo;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateT(String dateT) {
        this.dateT = dateT;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
