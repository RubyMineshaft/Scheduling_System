package model;

import DBAccess.DBUsers;

/** Model for User objects. */
public class User {

    /** Keeps track of which user is logged in. */
    private static User currentUser;

    /** The User ID. */
    private int id;

    /** The username. */
    private String username;

    /** The user's password. */
    private String password;

    /** Constructor for the User class.
     * @param id the user id
     * @param username the username
     * @param password the user's password
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /** Gets the current user.
     * @return the currently logged in user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /** Sets the currently logged in user.
     * @param user the user to log in
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /** Authenticates the user based on provided username and password.
     * @param username provided username
     * @param password provided password
     * @return true if username and password are correct
     */
    public static boolean authenticate(String username, String password){
        User user = DBUsers.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            setCurrentUser(user);
            System.out.println("User " + user.getUsername() + " authenticated.");
            return true;
        }
        System.out.println("Authentication error.");
        return false;
    }

    /** Getter for user ID.
     * @return the user ID
     */
    public int getId() {
        return id;
    }

    /** Setter for user ID.
     * @param id the user ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /** Getter for user's password.
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

}
