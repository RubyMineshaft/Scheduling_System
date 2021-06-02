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

import java.io.IOException;
import java.net.URL;
import java.util.*;

/** Controller for the Customer form. */
public class CustomerFormController implements Initializable {

    /** The save button. */
    @FXML
    private Button saveBtn;

    /** The form title label. */
    @FXML
    private Label titleLbl;

    /** The id field. */
    @FXML
    private TextField idTxt;

    /** The name field. */
    @FXML
    private TextField nameTxt;

    /** The country combo box. */
    @FXML
    private ComboBox<Country> countryBox;

    /** The division combo box. */
    @FXML
    private ComboBox<FirstLevelDivision> divisionBox;

    /** The address field. */
    @FXML
    private TextField addressTxt;

    /** The postal code field. */
    @FXML
    private TextField postalTxt;

    /** The phone number field. */
    @FXML
    private TextField phoneTxt;

    /** Event handler for the cancel button.
     * Gets confirmation from the user and returns to the customers view.
     * @param event the button click event
     * @throws IOException
     */
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

    /** Event handler for the save button.
     * Validates input fields and saves the customer to the database.
     * @param event the button click event
     * @throws IOException
     */
    @FXML
    void onSave(ActionEvent event) throws IOException {
        String name = nameTxt.getText();
        Country selectedCountry = countryBox.getSelectionModel().getSelectedItem();
        FirstLevelDivision selectedDivision = divisionBox.getSelectionModel().getSelectedItem();
        String address = addressTxt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();

        if (hasNull(name, selectedCountry, selectedDivision, address, postalCode, phone)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing Field(s)");
            alert.setContentText("All fields are required. Check inputs and try again.");
            alert.showAndWait();
        } else {
            int divisionID = divisionBox.getSelectionModel().getSelectedItem().getId();

            DBCustomers.createCustomer(name, address, postalCode, phone, divisionID);

            showCustomers(event);
        }
    }

    /** Checks to see if any passed arguments have a null value.
     * @param args the fields to check.
     * @return true if null values are found.
     */
    private boolean hasNull(Object... args){
        List<Object> test = new ArrayList<>(Arrays.asList(args));
        return test.contains(null);
    }


    /** Loads the customers view.
     * @param event the button click event
     * @throws IOException
     */
    private void showCustomers(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /** Populates the division combo box with divisions within the selected country and sets the address prompt text. */
    @FXML
    void onCountryChosen() {
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

    /** Populates fields with the customer's information and sets the event handler for the update button.
     * @param customer the customer to be updated
     */
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

    /** Event handler for the update customer button.
     * Validates inputs and updates the customer in the database.
     * @param event the button click event
     * @throws IOException
     */
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

    /** Populates the country combo box. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBCountries.getAllCountries());
    }
}
