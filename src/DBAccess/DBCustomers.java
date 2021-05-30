package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;
import util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class performs SQL queries on the customers table
 */
public class DBCustomers {

    /** Gets all customers from the database.
     * @return ObservableList of all customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT c.*, d.Division FROM customers c LEFT JOIN first_level_divisions d ON c.Division_ID = d.Division_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");

                Customer customer = new Customer(customerID, customerName, address, postalCode, phone, divisionId, division);
                customerList.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    public static void createCustomer(String name, String address, String postalCode, String phone, int divisionID) {
        try{
           String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Created_By) VALUES (?,?,?,?,?,?)";
           PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

           ps.setString(1, name);
           ps.setString(2, address);
           ps.setString(3, postalCode);
           ps.setString(4, phone);
           ps.setInt(5, divisionID);
           ps.setString(6, User.getCurrentUser().getUsername());

           ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    //TODO: Preserve create date.
    public static void updateCustomer(int id, String name, String address, String postalCode, String phone, int divisionID) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Division_ID = ?, Phone = ?, Last_Updated_By = ? WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setInt(4, divisionID);
            ps.setString(5, phone);
            ps.setString(6, User.getCurrentUser().getUsername());
            ps.setInt(7, id);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
