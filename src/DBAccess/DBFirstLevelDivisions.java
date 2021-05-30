package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class performs SQL queries on the first_level_divisions table.
 */
public class DBFirstLevelDivisions {

    /** This method queries the database for first level divisions of the specified country.
     * @param countryID id of the country to get divisions for
     * @return first level divisions of the country provided
     */
    public static ObservableList<FirstLevelDivision> getFirstLevelDivisions(int countryID) {
        ObservableList<FirstLevelDivision> divisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");

                FirstLevelDivision division = new FirstLevelDivision(divisionID, divisionName, countryID);
                divisionList.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionList;
    }

    public static FirstLevelDivision getDivision(int id) {
        FirstLevelDivision division = null;

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();

            String name = rs.getString("Division");
            int countryID = rs.getInt("COUNTRY_ID");

                division = new FirstLevelDivision(id, name, countryID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return division;
    }

}
