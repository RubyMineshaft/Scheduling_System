package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Appointment;
import model.Contact;
import model.Country;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private VBox typeBox, contactScheduleBox, divisionBox;

    private void generateTypeReport() throws SQLException {
        ResultSet rs = DBAppointments.getTypePerMonth();

        while (rs.next()) {
            Label label = new Label("-- " + rs.getString("Month") + " has " + rs.getInt("Num") + " " + rs.getString("Type") + " appointment(s)");
            label.setWrapText(true);
            typeBox.getChildren().add(label);
        }

    }

    private void generateContactSchedules() {
        ObservableList<Contact> contacts = DBContacts.getAllContacts();

        for (Contact contact : contacts) {
            Label label = new Label(contact.toString());
            label.setFont(new Font(20));
            label.setUnderline(true);
            contactScheduleBox.getChildren().add(label);

            ObservableList<Appointment> appointments = DBAppointments.getAppointmentsForContact(contact.getId());

            for (Appointment appointment : appointments) {
                Label appointmentInfo = new Label(appointment.toString());
                appointmentInfo.setPadding(new Insets(0,0,0,5));
                contactScheduleBox.getChildren().add(appointmentInfo);
            }
        }
    }

    private void generateDivisionCustomers() throws SQLException {
        ObservableList<Country> countries = DBCountries.getAllCountries();

        for (Country country : countries) {
            Label countryLabel = new Label(country.getName());
            countryLabel.setUnderline(true);
            countryLabel.setFont(new Font(20));
            divisionBox.getChildren().add(countryLabel);

            ResultSet divisionCustomers = DBCustomers.getCustomerPerDivisionForCountry(country.getID());

            while(divisionCustomers.next()) {
                Label divisionCount = new Label(divisionCustomers.getString("Division") + ":  " + divisionCustomers.getInt("Num"));
                divisionCount.setPadding(new Insets(0,0,0,5));
                divisionBox.getChildren().add(divisionCount);
            }
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            generateTypeReport();
            generateContactSchedules();
            generateDivisionCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
