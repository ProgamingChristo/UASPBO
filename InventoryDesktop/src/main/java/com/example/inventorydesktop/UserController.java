package com.example.inventorydesktop;

import com.example.inventorydesktop.user.CreateUserDTO;
import com.example.inventorydesktop.user.UpdateUserDTO;
import com.example.inventorydesktop.user.User;
import com.example.inventorydesktop.user.UserDTO;
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

public class UserController {

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> columnID;
    @FXML
    private TableColumn<User, String> columnUsername;
    @FXML
    private TableColumn<User, String> columnEmail;
    @FXML
    private TableColumn<User, String> columnRole;
    @FXML
    private AnchorPane modalPane;
    @FXML
    private TextField txtUsername, txtEmail, txtPassword, txtRole;
    @FXML
    private Button btnSave;

    private ObservableList<User> userList = FXCollections.observableArrayList();
    private User selectedUser = null;

    @FXML
    public void initialize() {
        setupTable();
        fetchUsers();
    }

    private void setupTable() {
        columnID.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());
        columnUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        columnEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        columnRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        tableView.setItems(userList);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    selectedUser = row.getItem();
                    showUpdateModal(selectedUser);
                }
            });
            return row;
        });
    }

    private void fetchUsers() {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/admin/users");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    List<UserDTO> usersDTO = new Gson().fromJson(reader, new TypeToken<List<UserDTO>>() {}.getType());

                    List<User> users = usersDTO.stream()
                            .map(dto -> new User(dto.getuser_id(), dto.getUsername(), dto.getEmail(), dto.getRole()))
                            .collect(Collectors.toList());

                    Platform.runLater(() -> userList.setAll(users));
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
        selectedUser = null;
        modalPane.setVisible(true);
        txtUsername.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtRole.clear();
        btnSave.setText("Add");
    }

    private void showUpdateModal(User user) {
        modalPane.setVisible(true);
        txtUsername.setText(user.getUsername());
        txtEmail.setText(user.getEmail());
        txtPassword.setText(user.getPassword());
        txtRole.setText(user.getRole());
        btnSave.setText("Update");
    }

    @FXML
    private void saveUser() {
        if (txtUsername.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty() || txtRole.getText().isEmpty()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();

        if (selectedUser == null) {
            createUser(username, email, password, role);
        } else {
            updateUser(selectedUser.getUserID(), username, email, password, role);
        }
    }

    private void createUser(String username, String email, String password, String role) {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/admin/users");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Send only username, email, and role, no ID
                String json = new Gson().toJson(new CreateUserDTO(username, email, password,  role));

                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    writer.write(json);
                    writer.flush();
                }

                if (connection.getResponseCode() == 201) {
                    fetchUsers();
                    Platform.runLater(this::closeModal);
                } else {
                    System.out.println("Error creating user: " + connection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateUser(int id, String username, String email, String password, String role) {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/admin/users/" + id);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Include ID for update
                String json = new Gson().toJson(new UpdateUserDTO(username, email, password, role));

                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    writer.write(json);
                    writer.flush();
                }

                if (connection.getResponseCode() == 200) {
                    fetchUsers();
                    Platform.runLater(this::closeModal);
                } else {
                    System.out.println("Error updating user: " + connection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void deleteUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            deleteUserFromServer(selectedUser.getUserID());
        } else {
            showAlert("Selection Error", "Please select a user to delete.");
        }
    }

    private void deleteUserFromServer(int userID) {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/admin/users/" + userID);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    fetchUsers();
                } else {
                    System.out.println("Error deleting user: " + connection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void closeModal() {
        modalPane.setVisible(false);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
