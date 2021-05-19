package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLbl.setText("");
        zoneLbl.setText(ZoneId.systemDefault().getId());
    }
}
