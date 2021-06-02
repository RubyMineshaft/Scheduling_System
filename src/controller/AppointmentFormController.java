package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.*;


/**
 * Controller for the add/edit appointment form.
 */
public class AppointmentFormController implements Initializable {
    /**
     * The save button.
     */
    @FXML
    private Button saveBtn;

    /**
     * The label for the title of the form.
     * Will be set to 'Add Appointment' or 'Edit Appointment' depending on the context.
     */
    @FXML
    private Label titleLbl;

    /** Appointment ID field. */
    @FXML
    private TextField idTxt;

    /** Title field. */
    @FXML
    private TextField titleTxt;

    /** Description field. */
    @FXML
    private TextField descTxt;

    /** Location field. */
    @FXML
    private TextField locationTxt;

    /** Contact combo box. */
    @FXML
    private ComboBox<Contact> contactComboBox;

    /** Type field. */
    @FXML
    private TextField typeTxt;

    /** Date picker for the start date. */
    @FXML
    private DatePicker startDatePicker;

    /** Combo box for selecting start time. */
    @FXML
    private ComboBox<LocalTime> startTime;

    /** Date picker for the end date. */
    @FXML
    private DatePicker endDatePicker;

    /** Combo box for selecting the end time. */
    @FXML
    private ComboBox<LocalTime> endTime;

    /** Combo box for selecting the customer. */
    @FXML
    private ComboBox<Customer> customerComboBox;

    /** Event handler for the cancel button.
     * Gets confirmation from user and returns to the appointments view.
     * @param event the button fire event.
     * @throws IOException
     */
    @FXML
    void onCancel(ActionEvent event) throws IOException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Changes not saved");
        confirmation.setHeaderText("Changes will not be saved");
        confirmation.setContentText("The changes to this appointment will not be saved. Are you sure you want to cancel?");

        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.get() == ButtonType.OK) {
            showAppointments(event);
        }
    }

    /**
     * Automatically sets end date when start date is chosen.
     */
    @FXML
    void onDateSelected() {
        endDatePicker.setValue(startDatePicker.getValue());
    }

    /** Event handler for the save button.
     * Validates form fields and saves appointment.
     * @param event the button fire event.
     * @throws IOException
     */
    @FXML
    void onSave(ActionEvent event) throws IOException {
        String title = titleTxt.getText();
        String description = descTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
        Contact selectedContact = contactComboBox.getSelectionModel().getSelectedItem();

        if (hasNull(title, description, location, type, startDatePicker.getValue(), startTime.getValue(), endDatePicker.getValue(), endTime.getValue(), selectedContact, selectedCustomer)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing Field(s)");
            alert.setContentText("All fields are required. Check inputs and try again.");
            alert.showAndWait();
        } else {
            LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), startTime.getValue());
            LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), endTime.getValue());
            int customerID = selectedCustomer.getId();
            int contactID = selectedContact.getId();

            if (hasNoConflictingAppointment(customerID, start, end, 0) && isWithinBusinessHours(start, end) && startIsBeforeEnd(start, end)) {
                DBAppointments.createAppointment(title, description, location, type, start, end, customerID, contactID);

                showAppointments(event);
            }
        }
    }

    /** Checks to see if any passed arguments have a null value.
     * @param args the fields to check.
     * @return true if null values are found.
     */
    private boolean hasNull(Object... args){
        List<Object> test = new ArrayList<Object>(Arrays.asList(args));
        return test.contains(null);
    }

    /** Checks whether the start time is before the end time.
     * @param start The appointment start
     * @param end the appointment end
     * @return true if start is before end
     */
    private boolean startIsBeforeEnd(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Check Dates");
            alert.setContentText("Appointments cannot end before they begin. Check the dates and try again.");
            alert.showAndWait();

            return false;
        }
        return true;
    }

    /** Sets up the form for editing an appointment.
     * Sets form title text, populates fields, and changes the event handler for the save button.
     * @param appointment the appointment to edit
     */
    public void editAppointment(Appointment appointment) {
        titleLbl.setText("Edit Appointment");
        idTxt.setText(String.valueOf(appointment.getId()));
        titleTxt.setText(appointment.getTitle());
        descTxt.setText(appointment.getDescription());
        locationTxt.setText(appointment.getLocation());
        contactComboBox.getSelectionModel().select(DBContacts.getContact(appointment.getContactID()));
        typeTxt.setText(appointment.getType());
        startDatePicker.setValue(appointment.getStart().toLocalDate());
        startTime.getSelectionModel().select(appointment.getStart().toLocalTime());
        endDatePicker.setValue(appointment.getEnd().toLocalDate());
        endTime.getSelectionModel().select(appointment.getEnd().toLocalTime());
        customerComboBox.getSelectionModel().select(DBCustomers.getCustomer(appointment.getCustomerID()));

        saveBtn.setText("Update");
        saveBtn.setOnAction(event -> {
            try {
                updateAppointment(event, appointment.getUserID());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /** Event handler for the update appointment button.
     * Validates input fields and updates appointment in the database.
     * @param event the button fire event
     * @param userID the ID of the assigned user
     * @throws IOException
     */
    private void updateAppointment(ActionEvent event, int userID) throws IOException {
        int id = Integer.parseInt(idTxt.getText());
        String title = titleTxt.getText();
        String description = descTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), startTime.getValue());
        LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), endTime.getValue());
        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getId();
        int contactID = contactComboBox.getSelectionModel().getSelectedItem().getId();

        if (hasNoConflictingAppointment(customerID, start, end, id) && isWithinBusinessHours(start, end)) {
            DBAppointments.updateAppointment(id, title, description, location, type, start, end, customerID, contactID, userID);

            showAppointments(event);
        }
    }

    /** Loads the appointments view.
     * @param event the event from the button clicked.
     * @throws IOException
     */
    private void showAppointments(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /** Checks if customer has conflicting appointments.
     * Generates an alert if a conflicting appointment is found.
     * @param customerID The id of the customer
     * @param start The appointment start time
     * @param end the appointment end time
     * @param appointmentID the appointment ID
     * @return true if no conflicting appointments found.
     */
    private boolean hasNoConflictingAppointment(int customerID, LocalDateTime start, LocalDateTime end, int appointmentID){
        ObservableList<Appointment> appointments = DBAppointments.getAppointmentsForCustomer(customerID);

        for (Appointment appointment : appointments) {
            if (appointmentID != appointment.getId()) { //Don't check appointment for conflicts with itself
                if (start.isEqual(appointment.getStart()) || start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd()) || end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd()) || start.isBefore(appointment.getStart()) && end.isAfter(appointment.getEnd())) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Appointment Conflict");
                    error.setHeaderText("Appointment Conflict");
                    error.setContentText("This appointment conflicts with the following appointment: \n \n"
                            + "ID: " + appointment.getId()
                            + "\nTitle: " + appointment.getTitle()
                            + "\nStart: " + appointment.getStart()
                            + "\nEnd: " + appointment.getEnd()
                            + "\n\nPlease choose a different time."
                    );
                    error.showAndWait();

                    return false;
                }
            }
        }
        return true;
    }

    /** Checks whether the appointment is within business hours of 8am to 10pm EST.
     * @param start appointment start time
     * @param end appointment end time
     * @return true if appointment is within business hours
     */
    private boolean isWithinBusinessHours(LocalDateTime start, LocalDateTime end) {
        ZoneId est = ZoneId.of("America/New_York");
        ZonedDateTime estStart = ZonedDateTime.of(2021,1,1,8,0,0,0,est);
        ZonedDateTime estEnd = ZonedDateTime.of(2021,1,1, 22, 0,0,0,est);
        LocalTime businessStart = estStart.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
        LocalTime businessEnd = estEnd.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();

        if (start.toLocalTime().isBefore(businessStart)) {
            String contentText = "Start time is not within business hours. All appointments must be between 08:00 and 22:00 EST. ";
            if (!ZoneId.systemDefault().getId().equals("America/New_York")){
                contentText = contentText + "(" + businessStart + " and " + businessEnd + " local time)";
            }
            contentText = contentText + "\n\nPlease choose a different start time.";

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Outside of Business Hours");
            error.setHeaderText("Outside of Business Hours");
            error.setContentText(contentText);
            error.showAndWait();
            return false;
        } else if (end.toLocalTime().isAfter(businessEnd)) {
            String contentText = "End time is not within business hours. All appointments must be between 08:00 and 22:00 EST.";
            if (!ZoneId.systemDefault().getId().equals("America/New_York")) {
                contentText = contentText + " (" + businessStart + " and " + businessEnd + " local time)";
            }
            contentText = contentText + "\n\nPlease choose a different end time.";

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Outside of Business Hours");
            error.setHeaderText("Outside of Business Hours");
            error.setContentText(contentText);
            error.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Sets time choices for the time combo boxes.
     */
    private void populateTimes(){
        LocalTime start = LocalTime.of(5,0);
        LocalTime end = LocalTime.of(23,0,1);

        while (start.isBefore(end)) {
            startTime.getItems().add(start);
            endTime.getItems().add(start);
            start = start.plusMinutes(30);
        }
    }

    /** Sets customer and contact combo boxes and populates time choices. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerComboBox.setItems(DBCustomers.getAllCustomers());
        contactComboBox.setItems(DBContacts.getAllContacts());
        populateTimes();
    }
}
