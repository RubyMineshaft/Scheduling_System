package controller;

import DBAccess.DBAppointments;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label userLabel;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Integer> idCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, Integer> custIDCol;

    @FXML
    private TableColumn<Appointment, String> customerNameCol;

    @FXML
    void onAllSelected(Event event) {

    }

    @FXML
    void onDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void onEditAppointment(ActionEvent event) {

    }

    @FXML
    void onLogOut(ActionEvent event) throws IOException {
        User.setCurrentUser(null);
        System.out.println("Logged out.");

        loadScene(event, "login");
    }

    @FXML
    void onManageContacts(ActionEvent event) {

    }

    @FXML
    void onManageCustomers(ActionEvent event) throws IOException {
        loadScene(event, "customers");
    }

    @FXML
    void onMonthSelected(Event event) {

    }

    @FXML
    void onNewAppointment(ActionEvent event) {

    }

    @FXML
    void onReports(ActionEvent event) {

    }

    @FXML
    void onWeekSelected(Event event) {

    }

    private void loadScene(ActionEvent event, String view) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/" + view + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLabel.setText(User.getCurrentUser().getUsername());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");

        appointmentTableView.setItems(DBAppointments.getAllAppointments());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
    }
}