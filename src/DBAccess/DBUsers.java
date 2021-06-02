package DBAccess;

import model.User;
import util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Handles database queries for User objects. */
public class DBUsers {

    /** Gets a user from the database.
     * @param username the username
     * @return the specified user
     */
    public static User getUser(String username) {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            rs.next();

            int id = rs.getInt("User_ID");
            String password = rs.getString("Password");

            User user = new User(id, username, password);
            return user;
        } catch (Exception ignored) {

        }
        return null;
    }

}
