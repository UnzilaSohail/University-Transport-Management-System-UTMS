package JavaFiles;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FeeChallan {

    private String rollNumber;
    private String fullName;
    private String route;
    private BigDecimal amount;
    private LocalDate dueDate;
    private String status;

    // Constructor
    public FeeChallan(String rollNumber, String fullName, String route, BigDecimal amount, LocalDate dueDate, String status) {
        this.rollNumber = rollNumber;
        this.fullName = fullName;
        this.route = route;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getters and setters
    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to generate a fee challan based on student information
    public static FeeChallan generateFeeChallan(StudentInfo student, BigDecimal amount, LocalDate dueDate, String status) {
        return new FeeChallan(student.getRollNumber(), student.getFullName(), student.getRoute(), amount, dueDate, status);
    }
}
