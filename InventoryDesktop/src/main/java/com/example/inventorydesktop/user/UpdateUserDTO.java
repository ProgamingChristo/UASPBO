package com.example.inventorydesktop.user;

import com.google.gson.annotations.SerializedName;

public class UpdateUserDTO {

    @SerializedName("user_id")
    private int user_id; // ID, digunakan jika diperlukan untuk respons

    @SerializedName("username")
    private String username; // Properti untuk username

    @SerializedName("email")
    private String email; // Properti untuk email

    @SerializedName("password")
    private String password; // Properti untuk password

    @SerializedName("role")
    private String role; // Properti untuk role

    // Constructor untuk membuat pengguna baru (tanpa ID)
    public UpdateUserDTO(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Constructor untuk respons dengan ID (jika diperlukan)
    public UpdateUserDTO(int user_id, String username, String email, String password, String role) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getter dan Setter
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
