package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label userLabel;

    @FXML
    void onAllSelected(ActionEvent event) {

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

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Log In");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void onManageContacts(ActionEvent event) {

    }

    @FXML
    void onManageCustomers(ActionEvent event) {

    }

    @FXML
    void onMonthSelected(ActionEvent event) {

    }

    @FXML
    void onNewAppointment(ActionEvent event) {

    }

    @FXML
    void onReports(ActionEvent event) {

    }

    @FXML
    void onWeekSelected(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLabel.setText(User.getCurrentUser().getUsername());
    }
}