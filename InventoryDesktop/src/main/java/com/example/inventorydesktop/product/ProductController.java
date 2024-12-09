package com.example.inventorydesktop.product;

import com.example.inventorydesktop.BaseApiConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.image.Image;


public class ProductController  {

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> columnID;
    @FXML
    private TableColumn<Product, String> columnName;
    @FXML
    private TableColumn<Product, Double> columnPrice;
    @FXML
    private TableColumn<Product, Integer> columnStock;
    @FXML
    private AnchorPane modalPane;
    @FXML
    private TextField txtName, txtPrice, txtStock;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txtImgUrl;
    @FXML
    private ImageView imgPreview;

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private Product selectedProduct = null;

    @FXML
    public void initialize() {
        setupTable();
        fetchProducts();

        // Listener untuk ImgUrl
        txtImgUrl.textProperty().addListener((observable, oldValue, newValue) -> {
            loadImagePreview(newValue);
        });
    }

    private void loadImagePreview(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl);
                if (image.isError()) {
                    imgPreview.setImage(null);
                    System.out.println("Invalid image URL");
                    showAlert("Error", "Failed to load image from the provided URL.");
                } else {
                    imgPreview.setImage(image);
                }
            } catch (Exception e) {
                imgPreview.setImage(null);
                System.out.println("Invalid image URL or error loading image");
                e.printStackTrace();
            }
        } else {
            imgPreview.setImage(null);
        }
    }

    private void setupTable() {
        columnID.setCellValueFactory(cellData -> cellData.getValue().produkIDProperty().asObject());
        columnName.setCellValueFactory(cellData -> cellData.getValue().namaProdukProperty());
        columnPrice.setCellValueFactory(cellData -> cellData.getValue().hargaProperty().asObject());
        columnStock.setCellValueFactory(cellData -> cellData.getValue().stokProperty().asObject());
        tableView.setItems(productList);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Set single selection
        tableView.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    selectedProduct = row.getItem();
                    showUpdateModal(selectedProduct);
                }
            });
            return row;
        });
    }


    private void fetchProducts() {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/produk");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    List<ProductDTO> productsDTO = new Gson().fromJson(reader, new TypeToken<List<ProductDTO>>() {}.getType());

                    List<Product> products = productsDTO.stream()
                            .map(dto -> new Product(dto.getProdukID(), dto.getNamaProduk(), dto.getHarga(), dto.getStok(), dto.getImg()))
                            .collect(Collectors.toList());

                    Platform.runLater(() -> productList.setAll(products));
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
        selectedProduct = null;
        modalPane.setVisible(true);
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
        txtImgUrl.clear();
        btnSave.setText("Add");
    }

    private void showUpdateModal(Product product) {
        modalPane.setVisible(true);
        txtName.setText(product.getNamaProduk());
        txtPrice.setText(String.valueOf(product.getHarga()));
        txtStock.setText(String.valueOf(product.getStok()));
        txtImgUrl.setText(product.getimgUrl());
        btnSave.setText("Update");
    }

    @FXML
    private void saveProduct() {
        // Validasi input
        if (txtName.getText().isEmpty() || txtPrice.getText().isEmpty() || txtStock.getText().isEmpty()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        String name = txtName.getText();
        double price;
        int stock;
        String imgUrl = txtImgUrl.getText();

        try {
            price = Double.parseDouble(txtPrice.getText());
            stock = Integer.parseInt(txtStock.getText());
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Price and Stock must be numeric.");
            return;
        }

        // Tambahkan produk baru atau update produk yang dipilih
        if (selectedProduct == null) {
            createProduct(name, price, stock, imgUrl); // Kirim ImgUrl
        } else {
            updateProduct(selectedProduct.getProdukID(), name, price, stock, imgUrl); // Kirim ImgUrl
        }
    }

    // Utility untuk menampilkan alert
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void createProduct(String name, double price, int stock, String imgUrl) {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/produk");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Buat JSON payload
                CreateProductDTO createProductDTO = new CreateProductDTO(name, price, stock, imgUrl);
                String json = new Gson().toJson(createProductDTO);

                // Menulis data JSON ke body request
                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    writer.write(json);
                    writer.flush();
                }

                // Periksa apakah response status 201 (created)
                if (connection.getResponseCode() == 201) {
                    fetchProducts(); // Refresh produk setelah berhasil menambahkan
                    Platform.runLater(this::closeModal); // Menutup modal
                } else {
                    System.out.println("Error creating product: " + connection.getResponseCode());
                    showAlert("Error", "Failed to create product.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateProduct(int id, String name, double price, int stock, String imgUrl) {
        new Thread(() -> {
            try {
                URL url = new URL(BaseApiConfig.getBaseUrl() + "api/produk/" + id);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Include ID for update
                UpdateProductDTO updateProductDTO = new UpdateProductDTO(name, price, stock, imgUrl);
                String json = new Gson().toJson(updateProductDTO); // Serialize to JSON

                // Write JSON data to output stream
                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    writer.write(json);
                    writer.flush();
                }

                // Check if response status is 200 (OK)
                if (connection.getResponseCode() == 200) {
                    fetchProducts(); // Refresh products after update
                    Platform.runLater(this::closeModal);
                } else {
                    System.out.println("Error updating product: " + connection.getResponseCode());
                    showAlert("Error", "Failed to update product.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void deleteProduct() {
        // Pastikan ada produk yang dipilih
        Product selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("No Selection", "Please select a product to delete.");
            return;
        }

        // Konfirmasi penghapusan
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Confirmation");
        confirmation.setHeaderText("Are you sure you want to delete this product?");
        confirmation.setContentText("Product: " + selected.getNamaProduk());

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                new Thread(() -> {
                    try {
                        URL url = new URL(BaseApiConfig.getBaseUrl() + "api/produk/" + selected.getProdukID());
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("DELETE");
                        connection.connect();

                        if (connection.getResponseCode() == 200) {
                            Platform.runLater(() -> {
                                productList.remove(selected); // Hapus dari daftar
                                tableView.getSelectionModel().clearSelection(); // Bersihkan pilihan
                            });
                        } else {
                            System.out.println("Error deleting product: " + connection.getResponseCode());
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
