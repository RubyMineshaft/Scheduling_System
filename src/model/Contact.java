package model;

/** Model for Contact objects. */
public class Contact {

    /** Contact ID. */
    private int id;

    /** Contact name. */
    private String name;

    /** Contact email. */
    private String email;

    /** Getter for contact ID.
     * @return the contact ID
     */
    public int getId() {
        return id;
    }

    /** Setter for contact ID.
     * @param id the contact ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for contact name.
     * @return the contact name
     */
    public String getName() {
        return name;
    }

    /** Setter for contact name.
     * @param name the contact name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Constructor for Contact objects.
     * @param id the contact ID
     * @param name the contact name
     * @param email the contact's email address
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /** Overridden toString method for displaying contacts in combo boxes.
     * @return a custom string describing the contact
     */
    @Override
    public String toString(){
        return name + " - " + email;
    }
}
