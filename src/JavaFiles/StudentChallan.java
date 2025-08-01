package JavaFiles;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
public class StudentChallan {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty routeName;
    private final SimpleDoubleProperty fee;
    private final SimpleStringProperty status;

    public StudentChallan(String firstName, String lastName, String routeName, double fee, String status) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.routeName = new SimpleStringProperty(routeName);
        this.fee = new SimpleDoubleProperty(fee);
        this.status = new SimpleStringProperty(status);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getRouteName() {
        return routeName.get();
    }

    public void setRouteName(String routeName) {
        this.routeName.set(routeName);
    }

    public double getFee() {
        return fee.get();
    }

    public void setFee(double fee) {
        this.fee.set(fee);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    @Override
    public String toString() {
        return "studentFeeDetails{" +
                "firstName=" + firstName.get() +
                ", lastName=" + lastName.get() +
                ", routeName=" + routeName.get() +
                ", fee=" + fee.get() +
                ", status=" + status.get() +
                '}';
    }
}
