package controller;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for the customers view. */
public class CustomersController implements Initializable {

    /** The stage. */
    private Stage stage;

    /** The scene. */
    private Parent scene;

    /** The label for the current user. */
    @FXML
    private Label userLabel;

    /** The customers table view. */
    @FXML
    private TableView<Customer> customersTableView;

    /** The customer ID column. */
    @FXML
    private TableColumn<Customer, Integer> idCol;

    /** The name column. */
    @FXML
    private TableColumn<Customer, String> nameCol;

    /** The address column. */
    @FXML
    private TableColumn<Customer, String> addressCol;

    /** The postal code column. */
    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    /** The phone number column. */
    @FXML
    private TableColumn<Customer, String> phoneCol;

    /** The division column. */
    @FXML
    private TableColumn<Customer, String> divisionCol;

    /** Event handler for the log out button.
     * Logs the current user out and returns to the login form.
     * @param event the button click event
     * @throws IOException
     */
    @FXML
    void onLogOut(ActionEvent event) throws IOException {
        User.setCurrentUser(null);
        System.out.println("Logged out.");

        loadScene(event, "login");
    }

    /** Event handler for the manage appointments button.
     * Loads the appointments view.
     * @param event the button click event
     * @throws IOException
     */
    @FXML
    void onManageAppointments(ActionEvent event) throws IOException {
        loadScene(event, "appointments");
    }

    /** Event handler for the reports button.
     * Loads the reports view.
     * @param event the button click event
     * @throws IOException
     */
    @FXML
    void onReports(ActionEvent event) throws IOException {
        loadScene(event, "reports");
    }

    /** Event handler for the delete customer button.
     * Gets confirmation from the user and deletes the selected customer and all associated appointments from the database.
     */
    @FXML
    void onDeleteCustomer() {
        Customer customer = customersTableView.getSelectionModel().getSelectedItem();

        if (customer == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Delete Customer");
            error.setHeaderText("Selection Error");
            error.setContentText("You must choose a customer to delete.");

            error.showAndWait();
        } else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Delete");
            confirmation.setHeaderText("Delete Customer");
            confirmation.setContentText("Customer #" + customer.getId() + "(" + customer.getName() + ") and all associated appointments will be deleted. Do you wish to continue?");

            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.get() == ButtonType.OK) {
                DBAppointments.deleteAppointmentsForCustomer(customer.getId());
                DBCustomers.deleteCustomer(customer.getId());
                customersTableView.getItems().remove(customer);

                Alert deleted = new Alert(Alert.AlertType.INFORMATION);
                deleted.setTitle("Customer Deleted");
                deleted.setHeaderText("Customer Deleted");
                deleted.setContentText("Customer #" + customer.getId() + "(" + customer.getName() + ") has been deleted.");
                deleted.showAndWait();
            }
        }
    }

    /** Event handler for the add customer button.
     * Loads the add customer form.
     * @param event the button click event
     * @throws IOException
     */
    @FXML
    void onAddCustomer(ActionEvent event) throws IOException {
        loadScene(event, "customerForm");
    }

    /** Event handler for the edit customer button.
     * Loads the edit customer form and passes the customer data.
     * @param event the button click event
     * @throws IOException
     */
    @FXML
    void onEditCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/customerForm.fxml"));
        loader.load();

        CustomerFormController controller = loader.getController();

        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Edit Customer");
            error.setHeaderText("Selection Error");
            error.setContentText("You must choose a customer to edit.");

            error.showAndWait();
        } else {
            controller.editCustomer(selectedCustomer);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        }
    }

    /** Loads a new scene.
     * @param event the button click event
     * @param view the view to be loaded
     * @throws IOException
     */
    private void loadScene(ActionEvent event, String view) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/" + view + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /** Populates the customers table view. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userLabel.setText(User.getCurrentUser().getUsername());

        customersTableView.setItems(DBCustomers.getAllCustomers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
    }
}
