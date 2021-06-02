package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Handles database queries for contact objects. */
public class DBContacts {

    /** Gets all contacts from the database.
     * @return List of all contacts
     */
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String email = resultSet.getString("Email");

                Contact contact = new Contact(contactID, contactName, email);

                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactList;
    }

    /** Gets a specific contact from the database.
     * @param id the contact ID
     * @return the specified contact
     */
    public static Contact getContact(int id) {
        Contact contact = null;
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();

            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            contact = new Contact(contactID, contactName, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contact;
    }

}
