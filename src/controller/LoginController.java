package controller;

import DBAccess.DBAppointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label loginLbl, userIDLbl, passwordLbl, errorLbl, zoneLbl, zoneIDLbl;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userIDField;

    @FXML
    private Button submitBtn;

    private ResourceBundle rb = ResourceBundle.getBundle("Resources/Login", Locale.getDefault());

    @FXML
    void onSubmit(ActionEvent event) throws IOException {

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter log = new PrintWriter(fileWriter);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String username = userIDField.getText();
        String password = passwordField.getText();

        if (User.authenticate(username, password)) {
            log.println(LocalDateTime.now().format(formatter) + " -- Successful login by " + username);
            showAppointments(event);
            checkUpcomingAppointments();
        } else {
            log.println(LocalDateTime.now().format(formatter) + " -- Failed login by " + username);
            userIDField.setText("");
            passwordField.setText("");
            errorLbl.setText(rb.getString("loginError"));
            userIDField.requestFocus();
        }
        log.close();
    }

    private void showAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    private void checkUpcomingAppointments() {
        ObservableList<Appointment> appointments = DBAppointments.getUpcomingAppointmentsForUser(User.getCurrentUser().getId());
        if (appointments.size() > 0) {
            Appointment appointment = appointments.get(0);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointment");
            alert.setHeaderText("Appointment within 15 minutes");
            alert.setContentText("Appointment " + appointment.getId() + " is starting at " + appointment.getStart().toLocalDate() + " " + appointment.getStart().toLocalTime());
            alert.showAndWait();
        }
    }

        @FXML
    void clearError(KeyEvent event) {
        errorLbl.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLbl.setText("");
        errorLbl.setWrapText(true);
        zoneLbl.setText(ZoneId.systemDefault().getId());

        loginLbl.setText(rb.getString("login"));
        userIDLbl.setText(rb.getString("userID"));
        passwordLbl.setText(rb.getString("password"));
        zoneIDLbl.setText(rb.getString("zoneID"));
        submitBtn.setText(rb.getString("submit"));
    }
}
