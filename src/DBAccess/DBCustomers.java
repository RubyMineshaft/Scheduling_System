package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;
import util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class performs SQL queries on the customers table. */
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

    /** Gets a specific customer from the database.
     * @param id the customer ID
     * @return the specified customer
     */
    public static Customer getCustomer(int id) {
        Customer customer = null;
        String sql = "SELECT * FROM customers c JOIN first_level_divisions d ON c.Division_ID = d.Division_ID WHERE Customer_ID = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();

            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            String division = rs.getString("Division");

            customer = new Customer(customerID, customerName, address, postalCode, phone, divisionId, division);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customer;
    }

    /** Inserts a customer into the database.
     * @param name the customer name
     * @param address the customer address
     * @param postalCode the postal code
     * @param phone the customer's phone number
     * @param divisionID the customer's division ID
     */
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

    /** Deletes a customer from the database.
     * @param customerID the id of the customer to delete
     */
    public static void deleteCustomer(int customerID){
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Gets the number of customers per division for a specific country.
     * @param countryID the country ID
     * @return the results of the query
     * @throws SQLException
     */
    public static ResultSet getCustomerPerDivisionForCountry(int countryID) throws SQLException {
        String sql = "SELECT d.Division AS Division, c.Customer_Name AS Customer, COUNT(*) AS Num FROM first_level_divisions d JOIN customers c ON c.Division_ID = d.Division_ID WHERE Country_ID = ? GROUP BY Division, Customer";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, countryID);

        return ps.executeQuery();
    }

    /** Updates a customer in the database.
     * @param id the customer ID
     * @param name the customer name
     * @param address the customer's address
     * @param postalCode the customer's postal code
     * @param phone the customer's phone number
     * @param divisionID the division ID
     */
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
