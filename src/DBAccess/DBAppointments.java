package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBAppointments {

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT a.*, c.Contact_Name, x.Customer_Name FROM appointments a JOIN contacts c ON a.Contact_ID = c.Contact_ID JOIN customers x ON a.Customer_ID = x.Customer_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime created = rs.getTimestamp("Create_Date").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String customerName = rs.getString("Customer_Name");

                Appointment appointment = new Appointment(id, customerID, contactID, title, description, location, type, start, end, created, contactName, customerName);

                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    public static void deleteAppointmentsForCustomer(int customerID) {
        try {
            String sql = "DELETE FROM appointments WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
