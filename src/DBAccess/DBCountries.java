package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class perfoms SQL queries on the Countries table.
 */
public class DBCountries {

    /** This method returns all countries from the database.
     * @return ObservableList of countries.
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int countryID = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");

                Country country = new Country(countryID, countryName);

                countryList.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countryList;
    }

    public static Country getCountry(int id) {
        Country country = null;

        try{
            String sql = "SELECT * FROM countries WHERE Country_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();
            String name =  rs.getString("Country");

            country = new Country(id, name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return country;
    }

}
