package com.example.inventorydesktop;

import com.example.inventorydesktop.pembelian.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PembelianController {

    @FXML
    private TableView<Pembelian> tableView;
    @FXML
    private TableColumn<Pembelian, Integer> columnID;
    @FXML
    private TableColumn<Pembelian, String> columnDate;
    @FXML
    private TableColumn<Pembelian, Double> columnTotalPrice;
    @FXML
    private TableColumn<Pembelian, String> columnCustomerName;
    @FXML
    private Button btnAdd;

    private ObservableList<Pembelian> pembelianList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        fetchPembelianData();
    }

    private void setupTable() {
        columnID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPembelianID()).asObject());

        // Format tanggal menggunakan DateTimeFormatter
        columnDate.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getTanggalPembelian().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
        ));

        columnTotalPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalHarga()).asObject());

        // Untuk Nama Pelanggan, pastikan properti pelanggan ada
        columnCustomerName.setCellValueFactory(cellData -> {
            Pelanggan pelanggan = cellData.getValue().getPelanggan();
            return new SimpleStringProperty(pelanggan != null ? pelanggan.getNamaPelanggan() : "Unknown");
        });

        tableView.setItems(pembelianList);
    }

    private void fetchPembelianData() {
        new Thread(() -> {
            try {
                URL url = new URL("http://localhost:3030/api/pembelian");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    StringBuilder responseString = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        responseString.append(line);
                    }

                    // Create a Gson instance with the custom LocalDateTime deserializer
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                            .create();

                    // Deserialize JSON into List<Pembelian>
                    List<Pembelian> pembelianData = gson.fromJson(responseString.toString(), new TypeToken<List<Pembelian>>() {}.getType());

                    // Menampilkan data di UI Thread (JavaFX)
                    Platform.runLater(() -> pembelianList.setAll(pembelianData));
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
        // Logic for adding a new Pembelian
    }

    @FXML
    private void showDetailPembelian(Pembelian pembelian) {
        // Display detail pembelian
        for (DetailPembelian detail : pembelian.getDetailPembelian()) {
            System.out.println("Produk: " + detail.getProduk().getNamaProduk() + ", Jumlah: " + detail.getJumlahProduk() + ", Subtotal: " + detail.getSubtotal());
        }
        // You can open a new window or show a modal with detailed information
    }
}
