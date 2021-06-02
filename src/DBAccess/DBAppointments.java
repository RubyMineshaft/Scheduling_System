package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;
import util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** Handles database queries for appointment objects. */
public class DBAppointments {

    /** Gets all appointments from the database.
     * @return observable list of all appointments
     */
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

                Appointment appointment = new Appointment(id, customerID, contactID, title, description, location, type, start, end, created, contactName, customerName, userID);

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /** Deletes a specific appointment from the database.
     * @param id the id of the appointment to delete
     */
    public static void deleteAppointment(int id) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Deletes all appointments for a specified customer.
     * @param customerID the customer id to delete appointments for
     */
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

    /** Gets upcoming appointments for a specific user.
     * @param userID the user id
     * @return list of upcoming appointments
     */
    public static ObservableList<Appointment> getUpcomingAppointmentsForUser(int userID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT a.*, c.Contact_Name, x.Customer_Name FROM appointments a JOIN contacts c ON a.Contact_ID = c.Contact_ID JOIN customers x ON a.Customer_ID = x.Customer_ID WHERE User_ID = ? AND Start > ? AND Start < ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, userID);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));

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
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String customerName = rs.getString("Customer_Name");

                Appointment appointment = new Appointment(id, customerID, contactID, title, description, location, type, start, end, created, contactName, customerName, userID);

                appointments.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }

    /** Inserts a new appointment into the database.
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param start the start date and time
     * @param end the end date and time
     * @param customerID the customer id
     * @param contactID the contact id
     */
    public static void createAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int contactID) {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Created_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setString(7, User.getCurrentUser().getUsername());
            ps.setInt(8, customerID);
            ps.setInt(9, User.getCurrentUser().getId());
            ps.setInt(10, contactID);

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Updates an appointment in the database.
     * @param id the id of the appointment to update
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param start the appointment start date and time
     * @param end the appointment end date and time
     * @param customerID the customer ID
     * @param contactID the contact ID
     * @param userID the user ID
     */
    public static void updateAppointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int contactID, int userID) {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?, Last_Updated_By = ? WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.setString(10, User.getCurrentUser().getUsername());
            ps.setInt(11, id);

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Gets appointments for a specific customer.
     * @param customerID the customer ID
     * @return List of appointments
     */
    public static ObservableList<Appointment> getAppointmentsForCustomer(int customerID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime created = rs.getTimestamp("Create_Date").toLocalDateTime();
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(id, customerID, contactID, title, description, location, type, start, end, created, userID);

                appointments.add(appointment);            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }

    /** Queries database for the number of appointments of each type for each month.
     * @return result set from query
     * @throws SQLException
     */
    public static ResultSet getTypePerMonth() throws SQLException {
        String sql = "SELECT MONTH(Start) AS MonthNum, MONTHNAME(Start) AS Month, Type, COUNT(*) AS Num FROM appointments GROUP BY MonthNum, Month, Type ORDER BY MonthNum ASC";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    /** Gets the appointments for a specific contact.
     * @param contactID the contact ID
     * @return List of appointments for contact
     */
    public static ObservableList<Appointment> getAppointmentsForContact(int contactID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, contactID);
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
                int userID = rs.getInt("User_ID");
                int customerID = rs.getInt("Customer_ID");

                Appointment appointment = new Appointment(id, customerID, contactID, title, description, location, type, start, end, created, userID);

                appointments.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointments;
    }
}
