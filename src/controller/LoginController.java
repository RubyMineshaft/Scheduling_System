package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
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

    @FXML
    void onSubmit(ActionEvent event) throws IOException {
        String username = userIDField.getText();
        String password = passwordField.getText();

        if (User.authenticate(username, password)) {
            showAppointments(event);
        } else {
            userIDField.setText("");
            passwordField.setText("");
            errorLbl.setText("Error logging in. Check credentials and try again.");
            userIDField.requestFocus();
        }
    }

    private void showAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Appointments");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void clearError(KeyEvent event) {
        errorLbl.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLbl.setText("");
        zoneLbl.setText(ZoneId.systemDefault().getId());
    }
}
