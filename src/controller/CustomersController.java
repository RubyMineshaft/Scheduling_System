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

public class CustomersController implements Initializable {

    private Stage stage;
    private Parent scene;

    @FXML
    private Label userLabel;

    @FXML
    private TableView<Customer> customersTableView;

    @FXML
    private TableColumn<Customer, Integer> idCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, String> divisionCol;


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
    void onManageContacts(ActionEvent event) throws IOException {
        loadScene(event, "contacts");
    }

    @FXML
    void onReports(ActionEvent event) {

    }

    @FXML
    void onDeleteCustomer(ActionEvent event) {
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

    @FXML
    void onAddCustomer(ActionEvent event) throws IOException {
        loadScene(event, "customerForm");
    }

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

    private void loadScene(ActionEvent event, String view) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/" + view + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

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
