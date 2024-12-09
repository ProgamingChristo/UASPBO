package com.example.inventorydesktop.penjualan;

import com.example.inventorydesktop.BaseApiConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class PenjualanController {

    @FXML
    private TableView<Penjualan> tableView;
    @FXML
    private TableColumn<Penjualan, Integer> colPenjualanID;
    @FXML
    private TableColumn<Penjualan, String> colTanggal;
    @FXML
    private TableColumn<Penjualan, String> colTotalHarga;
    @FXML
    private TableColumn<Penjualan, String> colPelanggan;

    private final ObservableList<Penjualan> penjualanData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Bind kolom ke properti di kelas Penjualan
        colPenjualanID.setCellValueFactory(new PropertyValueFactory<>("penjualanID"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPenjualan"));
        colTotalHarga.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));
        colPelanggan.setCellValueFactory(cellData -> cellData.getValue().getPelangganProperty());

        // Set data ke TableView
        tableView.setItems(penjualanData);

        // Fetch data dari API
        fetchPenjualanData();

        // Handle double-click event
        tableView.setOnMousePressed(this::handleRowDoubleClick);
    }

    private void handleRowDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Check if it was a double-click
            Penjualan selectedPenjualan = tableView.getSelectionModel().getSelectedItem();
            if (selectedPenjualan != null) {
                // Show modal with details of the selected Penjualan
                showDetailModal(selectedPenjualan);
            }
        }
    }

    private void showDetailModal(Penjualan penjualan) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Penjualan Detail");

        VBox modalLayout = new VBox(10);
        modalLayout.getChildren().add(new Text("Detail Penjualan"));
        modalLayout.getChildren().add(new Text("Penjualan ID: " + penjualan.getPenjualanID()));
        modalLayout.getChildren().add(new Text("Tanggal: " + penjualan.getTanggalPenjualan()));
        modalLayout.getChildren().add(new Text("Total Harga: " + penjualan.getTotalHarga()));

        // Add customer details with null checks
        if (penjualan.getPelanggan() != null) {
            modalLayout.getChildren().add(new Text("Pelanggan: " + penjualan.getPelanggan().getNamaPelanggan()));
        }

        // Tambahkan detail produk
        if (penjualan.getDetailpenjualan() != null) {
            for (DetailPenjualan detail : penjualan.getDetailpenjualan()) {
                if (detail.getProduk() != null) {
                    modalLayout.getChildren().add(new Text("Produk: " + detail.getProduk().getNamaProduk()));
                }
                modalLayout.getChildren().add(new Text("Jumlah: " + detail.getJumlahProduk()));
                modalLayout.getChildren().add(new Text("Subtotal: " + detail.getSubtotal()));
            }
        }

        Scene modalScene = new Scene(modalLayout, 400, 300);
        modalStage.setScene(modalScene);
        modalStage.show();
    }

    private void fetchPenjualanData() {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/penjualan");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    List<Penjualan> penjualanList = new Gson().fromJson(reader, new TypeToken<List<Penjualan>>() {}.getType());

                    Platform.runLater(() -> {
                        penjualanData.clear();
                        if (penjualanList != null) {
                            penjualanData.addAll(penjualanList);
                        }
                    });
                } else {
                    Platform.runLater(() -> showAlert("Error fetching data", "HTTP Error Code: " ));
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showAlert("Error", "Failed to fetch data: " + e.getMessage());
                    e.printStackTrace();
                });
            }
        }).start();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}