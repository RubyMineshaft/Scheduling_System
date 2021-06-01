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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import util.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    private Stage stage;
    private Parent scene;

    @FXML
    private Button saveBtn;

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
    void onCancel(ActionEvent event) throws IOException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Changes not saved");
        confirmation.setHeaderText("Changes will not be saved");
        confirmation.setContentText("The changes to this customer will not be saved. Are you sure you want to cancel?");

        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.get() == ButtonType.OK) {
            showCustomers(event);
        }
    }

    @FXML
    void onSave(ActionEvent event) throws IOException {
        String name = nameTxt.getText();
        int divisionID = divisionBox.getSelectionModel().getSelectedItem().getId();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();

        DBCustomers.createCustomer(name, address, postalCode, phone, divisionID);

        showCustomers(event);
    }

    private void showCustomers(ActionEvent event) throws IOException {
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

    public void editCustomer(Customer customer) {
        FirstLevelDivision division = DBFirstLevelDivisions.getDivision(customer.getDivisionId());
        Country country = DBCountries.getCountry(division.getCountryID());

        idTxt.setText(String.valueOf(customer.getId()));
        nameTxt.setText(customer.getName());
        countryBox.getSelectionModel().select(country);
        divisionBox.getSelectionModel().select(division);
        addressTxt.setText(customer.getAddress());
        postalTxt.setText(customer.getPostalCode());
        phoneTxt.setText(customer.getPhone());

        titleLbl.setText("Edit Customer");
        saveBtn.setText("Update");
        saveBtn.setOnAction(event -> {
            try {
                updateCustomer(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void updateCustomer(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idTxt.getText());
        String name = nameTxt.getText();
        int divisionID = divisionBox.getSelectionModel().getSelectedItem().getId();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();

        DBCustomers.updateCustomer(id, name, address, postalCode, phone, divisionID);

        showCustomers(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBCountries.getAllCountries());
    }
}
