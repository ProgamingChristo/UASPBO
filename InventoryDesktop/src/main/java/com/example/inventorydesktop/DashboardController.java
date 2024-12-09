package com.example.inventorydesktop;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DashboardController {

    @FXML
    private Label totalPembelianLabel;
    @FXML
    private Label totalPenjualanLabel;
    @FXML
    private Label totalPelangganLabel;

    public void initialize() {
        fetchStatistics();
    }

    private void fetchStatistics() {
        String apiUrl = BaseApiConfig.getBaseUrl() + "api/statistik";

        new Thread(() -> {
            try {
                // Membuat koneksi HTTP
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Membaca response dari API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }

                reader.close();
                connection.disconnect();

                // Memproses response JSON
                updateStatistics(responseBuilder.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateStatistics(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);

            int penjualan = root.path("penjualan").asInt();
            int pembelian = root.path("pembelian").asInt();
            int pelanggan = root.path("pelanggan").asInt();

            // Perbarui Label di UI (Pastikan dilakukan di JavaFX Thread)
            Platform.runLater(() -> {
                totalPenjualanLabel.setText(penjualan + " Produk");
                totalPembelianLabel.setText(pembelian + " Produk");
                totalPelangganLabel.setText(pelanggan + " User");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
