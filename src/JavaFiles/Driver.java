package JavaFiles;

import javafx.beans.property.*;
public class Driver {

    private final IntegerProperty driverId;
    private final StringProperty name;
    private final StringProperty licenseNumber;
    private final StringProperty phone;
    private final StringProperty email;

    // Constructors
    public Driver(int driverId, String name, String licenseNumber, String phone, String email) {
        this.driverId = new SimpleIntegerProperty(driverId);
        this.name = new SimpleStringProperty(name);
        this.licenseNumber = new SimpleStringProperty(licenseNumber);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
    }

    public Driver(String name, String licenseNumber, String phone, String email) {
        this(-1, name, licenseNumber, phone, email);
    }

    // Getters and setters
    public int getDriverId() {
        return driverId.get();
    }

    public void setDriverId(int driverId) {
        this.driverId.set(driverId);
    }

    public IntegerProperty driverIdProperty() {
        return driverId;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getLicenseNumber() {
        return licenseNumber.get();
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber.set(licenseNumber);
    }

    public StringProperty licenseNumberProperty() {
        return licenseNumber;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }
}
