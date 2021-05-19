package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label loginLbl, userIDLbl, passwordLbl, errorLbl, zoneLbl, zoneIDlbl;

    private ZoneId zone = ZoneId.systemDefault();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLbl.setText("");
        zoneLbl.setText(zone.getId());
    }
}
