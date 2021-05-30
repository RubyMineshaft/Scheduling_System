package controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBFirstLevelDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDivision;
import util.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerForm implements Initializable {

    private Stage stage;
    private Parent scene;

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
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onSave(ActionEvent event) throws IOException {
        String name = nameTxt.getText();
        int divisionID = divisionBox.getSelectionModel().getSelectedItem().getId();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();

        DBCustomers.createCustomer(name, address, postalCode, phone, divisionID);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void onCountryChosen(ActionEvent event) {
        Country chosenCountry = countryBox.getValue();
        divisionBox.setItems(DBFirstLevelDivisions.getFirstLevelDivisions(chosenCountry.getID()));
        if (chosenCountry.getID() == 1){
            addressTxt.setPromptText("123 ABC Street, White Plains");
        } else if (chosenCountry.getID() == 2){
            addressTxt.setPromptText("123 ABC Street, Greenwich, London");
        } else {
            addressTxt.setPromptText("123 ABC Street, Newmarket");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBCountries.getAllCountries());
    }
}
