package JavaFiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class DatabaseManager {


    private static final String URL = "jdbc:mysql://localhost:3307/utmss";
    private static final String USER = "root";
    private static final String PASSWORD = "Kuwait914";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void saveDriver(Driver driver) throws SQLException {
        String query = "INSERT INTO Drivers (name, license_number, phone, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getLicenseNumber());
            stmt.setString(3, driver.getPhone());
            stmt.setString(4, driver.getEmail());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int driverId = generatedKeys.getInt(1);
                driver.setDriverId(driverId);
            }
        }
    }

    public static boolean updateDriver(Driver driver) throws SQLException {
        String query = "UPDATE Drivers SET name = ?, license_number = ?, phone = ?, email = ? WHERE driver_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getLicenseNumber());
            stmt.setString(3, driver.getPhone());
            stmt.setString(4, driver.getEmail());
            stmt.setInt(5, driver.getDriverId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static boolean deleteDriver(int driverId) throws SQLException {
        String query = "DELETE FROM Drivers WHERE driver_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public static List<Driver> getAllDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT driver_id, name, license_number, phone, email FROM Drivers";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int driverId = rs.getInt("driver_id");
                String name = rs.getString("name");
                String licenseNumber = rs.getString("license_number");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                Driver driver = new Driver(driverId, name, licenseNumber, phone, email);
                drivers.add(driver);
            }
        }

        return drivers;
    }
}
