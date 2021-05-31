package model;

import java.time.LocalDateTime;

public class Appointment {

    private int id, customerID, contactID, userID;
    private String title, description, location, type, contactName, customerName;
    private LocalDateTime start, end, createDate;

    public int getId() {
        return id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getContactID() {
        return contactID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public int getUserID() {
        return userID;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getContactName() {
        return contactName;
    }

    public String getCustomerName() {
        return customerName;
    }

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
}
