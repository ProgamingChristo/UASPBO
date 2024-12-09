package com.example.inventorydesktop.pembelian;

import com.example.inventorydesktop.BaseApiConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

public class PembelianController {

    @FXML
    private TableView<Pembelian> tableView;
    @FXML
    private TableColumn<Pembelian, Integer> colPembelianID;
    @FXML
    private TableColumn<Pembelian, String> colTanggal;
    @FXML
    private TableColumn<Pembelian, String> colTotalHarga;
    @FXML
    private TableColumn<Pembelian, String> colPelanggan;
    @FXML
    private TableColumn<Pembelian, Void> colAction;

    private final ObservableList<Pembelian> pembelianData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Bind kolom ke properti di kelas Pembelian
        colPembelianID.setCellValueFactory(new PropertyValueFactory<>("pembelianID"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPembelian"));
        colTotalHarga.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));
        colPelanggan.setCellValueFactory(cellData -> cellData.getValue().getPelangganProperty());

        // Tambahkan kolom aksi
        addButtonToTable();

        // Set data ke TableView
        tableView.setItems(pembelianData);

        // Fetch data dari API
        fetchPembelianData();

        // Handle double-click event
        tableView.setOnMousePressed(this::handleRowDoubleClick);
    }

    private void handleRowDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Check if it was a double-click
            Pembelian selectedPembelian = tableView.getSelectionModel().getSelectedItem();
            if (selectedPembelian != null) {
                showDetailModal(selectedPembelian);
            }
        }
    }

    private void showDetailModal(Pembelian pembelian) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Detail Pembelian");

        VBox modalLayout = new VBox(10);
        modalLayout.setStyle("-fx-padding: 10; -fx-spacing: 10;");

        // Header informasi pembelian
        modalLayout.getChildren().add(new Text("Detail Pembelian"));
        modalLayout.getChildren().add(new Text("Pembelian ID: " + pembelian.getPembelianID()));
        modalLayout.getChildren().add(new Text("Tanggal: " + pembelian.getTanggalPembelian()));
        modalLayout.getChildren().add(new Text("Total Harga: " + pembelian.getTotalHarga()));

        // Informasi pelanggan
        if (pembelian.getPelanggan() != null) {
            modalLayout.getChildren().add(new Text("Pelanggan: " + pembelian.getPelanggan().getNamaPelanggan()));
            modalLayout.getChildren().add(new Text("Alamat: " + pembelian.getPelanggan().getAlamat()));
            modalLayout.getChildren().add(new Text("Nomor Telepon: " + pembelian.getPelanggan().getNomorTelepon()));
        }

        // Tambahkan daftar detail pembelian
        if (pembelian.getDetailPembelian() != null) {
            modalLayout.getChildren().add(new Text("Produk yang Dibeli:"));

            pembelian.getDetailPembelian().forEach(detail -> {
                Produk produk = detail.getProduk();
                String produkInfo = (produk != null)
                        ? "Produk: " + produk.getNamaProduk()
                        : "Produk: Tidak diketahui";
                modalLayout.getChildren().add(new Text(produkInfo));
                modalLayout.getChildren().add(new Text("Jumlah: " + detail.getJumlahProduk()));
                modalLayout.getChildren().add(new Text("Subtotal: " + detail.getSubtotal()));
            });
        } else {
            modalLayout.getChildren().add(new Text("Tidak ada detail pembelian."));
        }

        Scene modalScene = new Scene(modalLayout, 400, 400);
        modalStage.setScene(modalScene);
        modalStage.show();
    }
    private void fetchPembelianData() {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/pembelian");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    List<Pembelian> pembelianList = new Gson().fromJson(reader, new TypeToken<List<Pembelian>>() {}.getType());

                    Platform.runLater(() -> {
                        pembelianData.clear();
                        if (pembelianList != null) {
                            pembelianData.addAll(pembelianList);
                        }
                    });
                } else {
                    Platform.runLater(() -> {
                        try {
                            showAlert("Error fetching data", "HTTP Error Code: " + connection.getResponseCode());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showAlert("Error", "Failed to fetch data: " + e.getMessage());
                    e.printStackTrace();
                });
            }
        }).start();
    }

    private void addButtonToTable() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final javafx.scene.control.Button btnUpdate = new javafx.scene.control.Button("Accept");
            private final javafx.scene.control.Button btnReject = new javafx.scene.control.Button("Reject");

            {
                btnUpdate.setOnAction(event -> {
                    Pembelian pembelian = getTableView().getItems().get(getIndex());
                    if (pembelian != null) {
                        updatePembelianStatus(pembelian.getPembelianID(), "accepted");
                    }
                });

                btnReject.setOnAction(event -> {
                    Pembelian pembelian = getTableView().getItems().get(getIndex());
                    if (pembelian != null) {
                        updatePembelianStatus(pembelian.getPembelianID(), "rejected");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Gunakan HBox untuk menyusun tombol secara horizontal dengan lebar penuh
                    HBox actionButtons = new HBox(10); // 10 adalah jarak antar tombol
                    actionButtons.getChildren().addAll(btnUpdate, btnReject);
                    actionButtons.setSpacing(10); // Spacing antar tombol

                    // Membuat HBox memiliki lebar penuh
                    HBox.setHgrow(btnUpdate, javafx.scene.layout.Priority.ALWAYS);
                    HBox.setHgrow(btnReject, javafx.scene.layout.Priority.ALWAYS);

                    setGraphic(actionButtons);
                }
            }
        });
    }

    private void updatePembelianStatus(int pembelianID, String status) {
        new Thread(() -> {
            try {
                // First, update the purchase status
                URL updateUrl = new URL(BaseApiConfig.getBaseUrl() + "api/pembelian/" + pembelianID);
                HttpURLConnection updateConnection = (HttpURLConnection) updateUrl.openConnection();
                updateConnection.setRequestMethod("PUT");
                updateConnection.setRequestProperty("Content-Type", "application/json");
                updateConnection.setDoOutput(true);

                String jsonInputString = new Gson().toJson(new PembelianStatus(status));
                try (OutputStream os = updateConnection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                if (updateConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // If status update is successful, proceed to create sales entry
                    Pembelian pembelian = getPembelianById(pembelianID);

                    if (pembelian != null && status.equals("accepted")) {
                        // Prepare sales data
                        JSONObject salesData = new JSONObject();
                        salesData.put("TanggalPenjualan", LocalDateTime.now().toString());
                        salesData.put("TotalHarga", pembelian.getTotalHarga());
                        salesData.put("pelangganID", pembelian.getPelanggan().getPelangganID());

                        JSONArray detailPenjualan = new JSONArray();
                        for (DetailPembelian detail : pembelian.getDetailPembelian()) {
                            JSONObject detailItem = new JSONObject();
                            detailItem.put("ProdukID", detail.getProduk().getProdukID());
                            detailItem.put("JumlahProduk", detail.getJumlahProduk());
                            detailItem.put("Subtotal", detail.getSubtotal());
                            detailPenjualan.put(detailItem);
                        }
                        salesData.put("detailPenjualan", detailPenjualan);

                        // Post to sales API
                        postSalesData(salesData);
                    }

                    Platform.runLater(() -> {
                        showAlert("Success", "Pembelian updated successfully!");
                        fetchPembelianData();
                    });
                } else {
                    Platform.runLater(() -> {
                        try {
                            showAlert("Error", "Failed to update status: HTTP " + updateConnection.getResponseCode());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showAlert("Error", "Failed to update status: " + e.getMessage());
                    e.printStackTrace();
                });
            }
        }).start();
    }

    private void postSalesData(JSONObject salesData) {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/penjualan");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = salesData.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Platform.runLater(() -> {
                        showAlert("Success", "Sales data posted successfully!");
                    });
                } else {
//                    Platform.runLater(() -> {
//                        try {
//                            showAlert("Error", "Failed to post sales data: HTTP " + connection.getResponseCode());
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showAlert("Error", "Failed to post sales data: " + e.getMessage());
                    e.printStackTrace();
                });
            }
        }).start();
    }

    private Pembelian getPembelianById(int pembelianID) {
        return pembelianData.stream()
                .filter(p -> p.getPembelianID() == pembelianID)
                .findFirst()
                .orElse(null);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
