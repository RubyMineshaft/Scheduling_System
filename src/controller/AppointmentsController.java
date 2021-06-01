package controller;

import DBAccess.DBAppointments;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AppointmentsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label userLabel, upcomingLbl;

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
    private TabPane apptTabs;

    @FXML
    private Tab allTab;

    @FXML
    private Tab monthTab;

    @FXML
    private Tab weekTab;



    /** Uses lambda expression to clear filter and show all appointments in one line of code.
     */
    @FXML
    void onAllSelected() {
        setTableItems();
    }

    private void setTableItems() {
        ObservableList<Appointment> appointmentList = DBAppointments.getAllAppointments();
        FilteredList<Appointment> appointmentFilteredList = new FilteredList<>(appointmentList, p -> true);
        Tab selectedTab = apptTabs.getSelectionModel().getSelectedItem();


        if (selectedTab == allTab) {
            appointmentTableView.setItems(appointmentFilteredList);
        } else if (selectedTab == weekTab) {
            LocalDateTime oneWeek = LocalDateTime.now().plusWeeks(1);

            appointmentFilteredList.setPredicate(appointment -> appointment.getStart().isAfter(LocalDateTime.now()) && appointment.getStart().isBefore(oneWeek));
            appointmentTableView.setItems(appointmentFilteredList);
        } else if (selectedTab == monthTab) {
            LocalDateTime oneMonth = LocalDateTime.now().plusMonths(1);

            appointmentFilteredList.setPredicate(appointment -> appointment.getStart().isAfter(LocalDateTime.now()) && appointment.getStart().isBefore(oneMonth));
            appointmentTableView.setItems(appointmentFilteredList);
        }

    }

    @FXML
    void onDeleteAppointment() {
        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if (appointment == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Delete Appointment");
            error.setHeaderText("Selection Error");
            error.setContentText("You must choose an appointment to delete.");

            error.showAndWait();
        } else {

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Delete");
            confirmation.setHeaderText("Delete appointment");
            confirmation.setContentText("Appointment #" + appointment.getId() + " will be deleted. Do you wish to continue?");

            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.get() == ButtonType.OK) {
                DBAppointments.deleteAppointment(appointment.getId());
                //appointmentTableView.getItems().remove(appointment);
                setTableItems();


                Alert deleted = new Alert(Alert.AlertType.INFORMATION);
                deleted.setTitle("Appointment Deleted");
                deleted.setHeaderText("Appointment Deleted");
                deleted.setContentText("Appointment #" + appointment.getId() + " has been deleted.");
                deleted.showAndWait();
            }
        }
    }

    @FXML
    void onEditAppointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/appointmentForm.fxml"));
        loader.load();

        AppointmentFormController controller = loader.getController();

        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Edit Appointment");
            error.setHeaderText("Selection Error");
            error.setContentText("You must choose an appointment to edit.");

            error.showAndWait();
        } else {
            controller.editAppointment(selectedAppointment);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        }
    }

    @FXML
    void onLogOut(ActionEvent event) throws IOException {
        User.setCurrentUser(null);
        System.out.println("Logged out.");

        loadScene(event, "login");
    }

    @FXML
    void onManageCustomers(ActionEvent event) throws IOException {
        loadScene(event, "customers");
    }

    /** Uses lambda expression to reduce lines of code when filtering by upcoming month.
     */
    @FXML
    void onMonthSelected() {
        setTableItems();
    }

    @FXML
    void onNewAppointment(ActionEvent event) throws IOException {
        loadScene(event, "appointmentForm");
    }

    @FXML
    void onReports(ActionEvent event) throws IOException {
        loadScene(event, "reports");
    }

    /** Uses lambda expression to reduce lines of code when filtering appointments.
     */
    @FXML
    void onWeekSelected() {
        setTableItems();
    }

    private void loadScene(ActionEvent event, String view) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/" + view + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    private void checkUpcomingAppointments() {
        ObservableList<Appointment> appointments = DBAppointments.getUpcomingAppointmentsForUser(User.getCurrentUser().getId());

        if (appointments.size() == 0) {
            upcomingLbl.setText("No appointments within 15 minutes");
        } else {
            Appointment appointment = appointments.get(0);
            upcomingLbl.setText("Appointment " + appointment.getId() + "    " + appointment.getStart().toLocalDate() + "    " + appointment.getStart().toLocalTime());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLabel.setText(User.getCurrentUser().getUsername());
        checkUpcomingAppointments();

        setTableItems();
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