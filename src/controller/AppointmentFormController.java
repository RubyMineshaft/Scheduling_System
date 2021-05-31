package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import javafx.collections.FXCollections;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentFormController implements Initializable {

    @FXML
    private Button saveBtn;

    @FXML
    private Label titleLbl;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField descTxt;

    @FXML
    private TextField locationTxt;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private TextField typeTxt;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<LocalTime> startTime;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<LocalTime> endTime;

    @FXML
    private ComboBox<Customer> customerComboBox;

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

    @FXML
    void onSave(ActionEvent event) throws IOException {

        String title = titleTxt.getText();
        String description = descTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), startTime.getValue());
        LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), endTime.getValue());
        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getId();
        int contactID = contactComboBox.getSelectionModel().getSelectedItem().getId();

        if (!hasConflictingAppointment(customerID, start, end) && isWithinBusinessHours(start, end)) {
            DBAppointments.createAppointment(title, description, location, type, start, end, customerID, contactID);

            showAppointments(event);
        }
    }

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

        if (!hasConflictingAppointment(customerID, start, end) && isWithinBusinessHours(start, end)) {
            DBAppointments.updateAppointment(id, title, description, location, type, start, end, customerID, contactID, userID);

            showAppointments(event);
        }
    }

    private void showAppointments(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }
    
    private boolean hasConflictingAppointment(int customerID, LocalDateTime start, LocalDateTime end){
        ObservableList<Appointment> appointments = DBAppointments.getAppointmentsForCustomer(customerID);

        for (Appointment appointment : appointments) {
            if (start.isEqual(appointment.getStart()) || start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd()) || end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd())) {
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

                return true;
            }
        }
        return false;
    }

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

    private void populateTimes(){
        LocalTime start = LocalTime.of(5,0);
        LocalTime end = LocalTime.of(23,0,1);

        while (start.isBefore(end)) {
            startTime.getItems().add(start);
            endTime.getItems().add(start);
            start = start.plusMinutes(30);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerComboBox.setItems(DBCustomers.getAllCustomers());
        contactComboBox.setItems(DBContacts.getAllContacts());
        populateTimes();
    }
}
