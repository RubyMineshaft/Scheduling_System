package controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBFirstLevelDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Country;
import model.FirstLevelDivision;
import util.DBConnection;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerForm implements Initializable {

    @FXML
    private Label titleLbl;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private ComboBox<Country> countryBox;

    @FXML
    private ComboBox<FirstLevelDivision> divisionBox;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField postalTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    void cancelAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {
        String name = nameTxt.getText();
        int divisionID = divisionBox.getSelectionModel().getSelectedItem().getId();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();

        DBCustomers.createCustomer(name, address, postalCode, phone, divisionID);
        //TODO: Return to previous screen.
    }

    @FXML
    void onCountryChosen(ActionEvent event) {
        Country chosenCountry = countryBox.getValue();
        divisionBox.setItems(DBFirstLevelDivisions.getFirstLevelDivisions(chosenCountry.getID()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBCountries.getAllCountries());
    }
}
