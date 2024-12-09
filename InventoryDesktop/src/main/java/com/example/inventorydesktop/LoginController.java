package com.example.inventorydesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXML;

import org.json.JSONObject; // Library JSON
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginController extends Application {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    // To store tokens globally within the application
    private String accessToken;
    private String refreshToken;

    private String role; // Role of the logged-in user

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), javafx.scene.layout.Region.USE_COMPUTED_SIZE, javafx.scene.layout.Region.USE_COMPUTED_SIZE);

        stage.setMaximized(true);
        stage.setResizable(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Inventory Application");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleLogin() {
        String email = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Email and Password cannot be empty.", AlertType.ERROR);
            return;
        }

        try {
            String response = loginUser(email, password);

            if (response != null) {
                JSONObject jsonResponse = new JSONObject(response);

                // Periksa jika ada error pada respons
                if (jsonResponse.has("error")) {
                    String errorMessage = jsonResponse.getString("error");
                    showAlert("Login Failed", errorMessage, AlertType.ERROR);
                    return;
                }

                // Extract tokens dan role
                accessToken = jsonResponse.optString("accessToken", null);
                refreshToken = jsonResponse.optString("refreshToken", null);
                role = jsonResponse.optString("role", null);

                // Validasi jika token atau role hilang
                if (accessToken == null || refreshToken == null || role == null) {
                    showAlert("Login Error", "Invalid server response. Missing required data.", AlertType.ERROR);
                    return;
                }

                // Cek jika role adalah 'user', maka login tidak diterima
                if ("user".equalsIgnoreCase(role)) {
                    showAlert("Login Failed", "Your account does not have the required permissions.", AlertType.ERROR);
                    return;
                }

                // Login sukses untuk admin atau kasir
                showAlert("Login Success", "Welcome, " + role + email + "!", AlertType.INFORMATION);
                loadMainScreen();
            } else {
                showAlert("Login Failed", "Invalid email or password.", AlertType.ERROR);
            }
        } catch (IOException e) {
            // Tangani kesalahan koneksi
            showAlert("Network Error", "Invalid Credentials.", AlertType.ERROR);
        } catch (Exception e) {
            // Tangani kesalahan tidak terduga
            showAlert("Unexpected Error", "An unexpected error occurred: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private boolean isValidRole(String role) {
        // Define valid roles
        return "admin".equalsIgnoreCase(role) || "kasir".equalsIgnoreCase(role);
    }

    private String loginUser(String email, String password) throws IOException {
        String apiUrl = BaseApiConfig.getBaseUrl() + "api/auth/login";
        HttpURLConnection connection = null;

        try {
            String requestBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);

            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Send the request
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();

            // If the server responds with HTTP 200 (OK), read the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    return response.toString(); // Return JSON response as a string
                }
            } else {
                throw new IOException("HTTP error code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadMainScreen() {
        try {
            FXMLLoader fxmlLoader;
            if ("admin".equalsIgnoreCase(role)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("mainAdmin.fxml"));
            } else if ("kasir".equalsIgnoreCase(role)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("mainKasir.fxml"));
            } else {
                showAlert("Error", "Unknown role: " + role, AlertType.ERROR);
                return;
            }

            Parent root = fxmlLoader.load();

            // Optional: Pass role information to the controller
            MainController mainController = fxmlLoader.getController();
            // mainController.setRole(role); // Uncomment if MainController has a setRole method

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setMaximized(true);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Inventory Application - Dashboard");
            stage.setScene(scene);
            stage.show();

            // Close current login stage
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showAlert("Error", "Unable to load main screen.", AlertType.ERROR);
        }
    }



    public static void main(String[] args) {
        launch();
    }
}
