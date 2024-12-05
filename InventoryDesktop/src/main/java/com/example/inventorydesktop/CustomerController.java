package com.example.inventorydesktop;

import com.example.inventorydesktop.customer.CreateCustomerDTO;
import com.example.inventorydesktop.customer.Customer;
import com.example.inventorydesktop.customer.CustomerDTO;
import com.example.inventorydesktop.customer.UpdateCustomerDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerController {

    @FXML
    private TableView<Customer> tableView;
    @FXML
    private TableColumn<Customer, Integer> columnID;
    @FXML
    private TableColumn<Customer, String> columnName;
    @FXML
    private TableColumn<Customer, String> columnAddress;
    @FXML
    private TableColumn<Customer, String> columnPhone;
    @FXML
    private AnchorPane modalPane;
    @FXML
    private TextField txtName, txtAddress, txtPhone;
    @FXML
    private Button btnSave;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private Customer selectedCustomer = null;

    @FXML
    public void initialize() {
        setupTable();
        fetchCustomers();
    }

    private void setupTable() {
        columnID.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty().asObject());
        columnName.setCellValueFactory(cellData -> cellData.getValue().namaPelangganProperty());
        columnAddress.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        columnPhone.setCellValueFactory(cellData -> cellData.getValue().nomorTeleponProperty());
        tableView.setItems(customerList);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Set single selection
        tableView.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    selectedCustomer = row.getItem();
                    showUpdateModal(selectedCustomer);
                }
            });
            return row;
        });
    }

    private void fetchCustomers() {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/pelanggan");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    List<CustomerDTO> customersDTO = new Gson().fromJson(reader, new TypeToken<List<CustomerDTO>>() {}.getType());

                    List<Customer> customers = customersDTO.stream()
                            .map(dto -> new Customer(dto.getPelangganID(), dto.getNamaPelanggan(), dto.getAlamat(), dto.getNomorTelepon()))
                            .collect(Collectors.toList());

                    Platform.runLater(() -> customerList.setAll(customers));
                } else {
                    System.out.println("Error fetching data: " + connection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void showAddModal() {
        selectedCustomer = null;
        modalPane.setVisible(true);
        txtName.clear();
        txtAddress.clear();
        txtPhone.clear();
        btnSave.setText("Add");
    }

    private void showUpdateModal(Customer customer) {
        modalPane.setVisible(true);
        txtName.setText(customer.getNamaPelanggan());
        txtAddress.setText(customer.getAlamat());
        txtPhone.setText(customer.getNomorTelepon());
        btnSave.setText("Update");
    }

    @FXML
    private void saveCustomer() {
        if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtPhone.getText().isEmpty()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        String name = txtName.getText();
        String address = txtAddress.getText();
        String phone = txtPhone.getText();

        if (selectedCustomer == null) {
            createCustomer(name, address, phone);
        } else {
            updateCustomer(selectedCustomer.getCustomerID(), name, address, phone);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void createCustomer(String name, String address, String phone) {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/pelanggan");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                String json = new Gson().toJson(new CreateCustomerDTO(name, address, phone));

                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    writer.write(json);
                    writer.flush();
                }

                if (connection.getResponseCode() == 201) {
                    fetchCustomers();
                    Platform.runLater(this::closeModal);
                } else {
                    System.out.println("Error creating customer: " + connection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateCustomer(int id, String name, String address, String phone) {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/pelanggan/" + id);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                String json = new Gson().toJson(new UpdateCustomerDTO(name, address, phone));

                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    writer.write(json);
                    writer.flush();
                }

                if (connection.getResponseCode() == 200) {
                    fetchCustomers();
                    Platform.runLater(this::closeModal);
                } else {
                    System.out.println("Error updating customer: " + connection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void deleteCustomer() {
        Customer selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("No Selection", "Please select a customer to delete.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Confirmation");
        confirmation.setHeaderText("Are you sure you want to delete this customer?");
        confirmation.setContentText("Customer: " + selected.getNamaPelanggan());

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                new Thread(() -> {
                    try {
                        URL url = new URL("http://localhost:3030/api/pelanggan/" + selected.getCustomerID());
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("DELETE");
                        connection.connect();

                        if (connection.getResponseCode() == 200) {
                            Platform.runLater(() -> {
                                customerList.remove(selected);
                                tableView.getSelectionModel().clearSelection();
                            });
                        } else {
                            System.out.println("Error deleting customer: " + connection.getResponseCode());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    @FXML
    private void closeModal() {
        modalPane.setVisible(false);
    }
}
