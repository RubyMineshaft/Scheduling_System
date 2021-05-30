package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class ContactsController {

    private Stage stage;
    private Parent scene;

    @FXML
    private Label userLabel;

    @FXML
    void onLogOut(ActionEvent event) throws IOException {
        User.setCurrentUser(null);
        System.out.println("Logged out.");

        loadScene(event, "login");
    }

    @FXML
    void onManageAppointments(ActionEvent event) throws IOException {
        loadScene(event, "appointments");
    }

    @FXML
    void onManageCustomers(ActionEvent event) throws IOException {
        loadScene(event, "customers");
    }

    @FXML
    void onReports(ActionEvent event) {

    }

    private void loadScene(ActionEvent event, String view) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/" + view + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

}
