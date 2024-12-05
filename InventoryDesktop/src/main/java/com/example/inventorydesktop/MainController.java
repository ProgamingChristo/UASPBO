package com.example.inventorydesktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainController extends Application {

    @FXML
    private StackPane contentArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), javafx.scene.layout.Region.USE_COMPUTED_SIZE, javafx.scene.layout.Region.USE_COMPUTED_SIZE);

        stage.setMaximized(true);
        stage.setResizable(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Inventory Application");
        stage.setScene(scene);
        stage.show();

        Platform.runLater(() -> loadContent("dashboard.fxml"));
    }

    @FXML
    private void loadHome() {
        loadContent("dashboard.fxml");
    }

    @FXML
    private void loadProduct() {
        loadContent("product.fxml");
    }

    @FXML
    private void loadCustomer() {
        loadContent("customer.fxml");
    }

    @FXML
    private void loadPembelian() {
        loadContent("pembelian.fxml");
    }
    @FXML
    private void loadPenjualan() {
        loadContent("penjualan.fxml");
    }

    @FXML
    private void loadAccount() {
        loadContent("account.fxml");
    }

    @FXML
    private void viewProfile() {
        loadContent("profile.fxml");
    }

    @FXML
    private void viewNotifications() {
        loadContent("notifications.fxml");
    }

    @FXML
    private void logout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent loginPage = fxmlLoader.load();
            Scene loginScene = new Scene(loginPage, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            Stage currentStage = (Stage) contentArea.getScene().getWindow();
            currentStage.hide();

            Stage loginStage = new Stage();
            loginStage.setMaximized(true);
            loginStage.setResizable(true);
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setTitle("Login");
            loginStage.setScene(loginScene);

            loginStage.show();

            loginStage.setOnCloseRequest(event -> {
                Platform.exit();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newContent = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
