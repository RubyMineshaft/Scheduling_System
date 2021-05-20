package model;

import DBAccess.DBUsers;

public class User {

    /**
     * Keeps track of which user is logged in.
     */
    private static User currentUser;

    private int id;
    private String username;
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

    public static boolean authenticateUser(String username, String password){
        User user = DBUsers.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            setCurrentUser(user);
            System.out.println("User " + user.getUsername() + " authenticated.");
            return true;
        }
        System.out.println("Authentication error.");
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
