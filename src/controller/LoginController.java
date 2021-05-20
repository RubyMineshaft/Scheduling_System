package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.User;

import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label loginLbl, userIDLbl, passwordLbl, errorLbl, zoneLbl, zoneIDlbl;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userIDField;

    @FXML
    private Button submitBtn;

    @FXML
    private void onSubmit(ActionEvent event) {
        String username = userIDField.getText();
        String password = passwordField.getText();

        if (User.authenticate(username, password)) {

        } else {
            userIDField.setText("");
            passwordField.setText("");
            errorLbl.setText("Error logging in. Check credentials and try again.");
            userIDField.requestFocus();
        }
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
