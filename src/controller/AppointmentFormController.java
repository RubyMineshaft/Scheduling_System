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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AppointmentFormController implements Initializable {

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
    void onCancel(ActionEvent event) {

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

        return true;
    }


    private void populateTimes(){

        LocalTime start = LocalTime.of(5,0);
        LocalTime end = LocalTime.of(22,0,1);



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
