package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
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
    void onAddCustomer(ActionEvent event) throws IOException {
        loadScene(event, "customerForm");
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

    }
}
