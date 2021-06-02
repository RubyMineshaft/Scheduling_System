package model;

import DBAccess.DBCustomers;

import java.time.LocalDateTime;

/**
 *  Model for appointment objects.
 */
public class Appointment {

    private int id, customerID, contactID, userID;
    private String title, description, location, type, contactName, customerName;
    private LocalDateTime start, end, createDate;

    /** Getter for appointment ID.
     * @return the appointment ID
     */
    public int getId() {
        return id;
    }

    /** Getter for Customer ID.
     * @return the customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /** Getter for Contact ID.
     * @return the contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /** Getter for appointment title.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /** Getter for appointment description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /** Getter for appointment location.
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /** Getter for appointment type.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /** Getter for associated user ID.
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /** Getter for start date.
     * @return the start date
     */
    public LocalDateTime getStart() {
        return start;
    }

    /** Getter for end date.
     * @return the end date
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /** Getter for contact name.
     * @return the contact's name
     */
    public String getContactName() {
        return contactName;
    }

    /** Getter for customer name.
     * @return the customer's name
     */
    public String getCustomerName() {
        return customerName;
    }

    /** Constructor for Appointment objects with contact names and customer names.
     * @param id the appointment id
     * @param customerID the customer id
     * @param contactID the contact id
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of appointment
     * @param type the type of appointment
     * @param start the start date and time of the appointment
     * @param end the end date and time of the appointment
     * @param createDate the date the appointment was created
     * @param contactName the name of the contact for the appointment
     * @param customerName the name of the customer for the appointment
     * @param userID the user ID
     */
    public Appointment(int id, int customerID, int contactID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String contactName, String customerName, int userID) {
        this.id = id;
        this.customerID = customerID;
        this.contactID = contactID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.contactName = contactName;
        this.customerName = customerName;
        this.userID = userID;
    }

    /** Constructor for Appointment objects without contact or customer names.
     * @param id the appointment id
     * @param customerID the customer id
     * @param contactID the contact id
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of appointment
     * @param type the type of appointment
     * @param start the start date and time of the appointment
     * @param end the end date and time of the appointment
     * @param userID the user ID
     */
    public Appointment(int id, int customerID, int contactID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime created, int userID) {
        this.id = id;
        this.customerID = customerID;
        this.contactID = contactID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = created;
    }

    /** Overridden toString method for Appointments.
     * Used when generating reports.
     * @return a custom String describing the appointment
     */
    @Override
    public String toString(){
            String apptString = "Apt. " + id + " - " + title + " - " + description + "\n"
                    + start.toLocalDate() + " " + start.toLocalTime() + " to " + end.toLocalDate() + " " + end.toLocalTime() + "\n"
                    + "Customer " + DBCustomers.getCustomer(customerID);

        return apptString;
    }
}
