package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Country;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** Controller for the reports view. */
public class ReportsController implements Initializable {

    /** Containers for the reports. */
    @FXML
    private VBox typeBox, contactScheduleBox, divisionBox;

    /** Event handler for the close button.
     * Loads the appointments view.
     * @param event the button click event
     * @throws IOException
     */
    @FXML
    void onClose(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /** Generates a report showing the number of each type of appointment per month.
     * @throws SQLException
     */
    private void generateTypeReport() throws SQLException {
        ResultSet rs = DBAppointments.getTypePerMonth();

        while (rs.next()) {
            Label label = new Label("-- " + rs.getString("Month") + " has " + rs.getInt("Num") + " " + rs.getString("Type") + " appointment(s)");
            label.setWrapText(true);
            typeBox.getChildren().add(label);
        }

    }

    /** Generates a report showing the schedules for each contact in the database. */
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

    /** Generates a report showing the number of customers in each first level division.
     * @throws SQLException
     */
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


    /** Generates the reports when the view is loaded. */
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
