package controller;

import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Contact;
import model.Customer;

import java.net.URL;
import java.time.LocalDate;
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
    void onSave(ActionEvent event) {

    }

    private void populateTimes(){
        LocalTime start = LocalTime.of(5,0);
        LocalTime end = LocalTime.of(22,0,1);

        while (start.isBefore(end)) {
            startTime.getItems().add(start);
            endTime.getItems().add(start);
            start = start.plusMinutes(15);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        customerComboBox.setItems(DBCustomers.getAllCustomers());
        contactComboBox.setItems(DBContacts.getAllContacts());
        populateTimes();
    }
}
