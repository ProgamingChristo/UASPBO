package com.example.inventorydesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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

                // Extract tokens and role
                accessToken = jsonResponse.getString("accessToken");
                refreshToken = jsonResponse.getString("refreshToken");
                String role = jsonResponse.getString("role");

                // Handle login success based on role
                showAlert("Login Success", "Welcome, " + role + "!", AlertType.INFORMATION);
                loadMainScreen(); // Proceed to main screen
            } else {
                showAlert("Login Failed", "Invalid email or password.", AlertType.ERROR);
            }
        } catch (IOException e) {
            showAlert("Network Error", "Unable to connect to the server. Please try again.", AlertType.ERROR);
        }
    }

    private String loginUser(String email, String password) throws IOException {
        String apiUrl = "http://localhost:3030/api/auth/login";
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
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
