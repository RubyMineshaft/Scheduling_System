package model;

/** Model for Customer objects. */
public class Customer {

    /** Customer ID. */
    private int id;

    /** Customer name. */
    private String name;

    /** Customer's address. */
    private String address;

    /** Customer's postal code. */
    private String postalCode;

    /** Customer's phone number. */
    private String phone;

    /** Customer's division ID. */
    private int divisionId;

    /** Customer's division name. */
    private String division;

    /** Constructor for Customer objects.
     * @param id the customer ID
     * @param name the customer's name
     * @param address the customer's address
     * @param postalCode the customer's postal code
     * @param phone the customer's phone number
     * @param divisionId the customer's division ID
     * @param division the name of the customer's division
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId, String division) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.division = division;
    }

    /** Getter for customer ID.
     * @return the customer ID
     */
    public int getId() {
        return id;
    }

    /** Setter for customer ID.
     * @param id the customer ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for customer name.
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /** Setter for customer name.
     * @param name the customer's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for customer address.
     * @return the customer's address
     */
    public String getAddress() {
        return address;
    }

    /** Getter for customer postal code.
     * @return the customer's postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /** Getter for the customer's phone number.
     * @return the customer's phone number
     */
    public String getPhone() {
        return phone;
    }

    /** Getter for the customer's division ID.
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /** Getter for division name.
     * @return the division name
     */
    public String getDivision() {
        return division;
    }

    /** Overridden toString method for displaying customer information in combo boxes.
     * @return custom string describing the customer
     */
    @Override
    public String toString(){
        return id + ": " + name;
    }
}
