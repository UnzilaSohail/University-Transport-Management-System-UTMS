package JavaFiles;
import java.sql.Date;
public class CoasterAndVans {

    private int busNumber;
    private String type;
    private String make;
    private int modelYear;
    private int capacity;
    private String status;
    private Date lastServiced;

    public CoasterAndVans(int busNumber, String type, String make, int modelYear, int capacity, String status, Date lastServiced) {
        this.busNumber = busNumber;
        this.type = type;
        this.make = make;
        this.modelYear = modelYear;
        this.capacity = capacity;
        this.status = status;
        this.lastServiced = lastServiced;
    }

    public int getBusNumber() { return busNumber; }
    public void setBusNumber(int busNumber) { this.busNumber = busNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public int getModelYear() { return modelYear; }
    public void setModelYear(int modelYear) { this.modelYear = modelYear; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getLastServiced() { return lastServiced; }
    public void setLastServiced(Date lastServiced) { this.lastServiced = lastServiced; }
}
