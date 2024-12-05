package com.example.inventorydesktop.user;

public class UserDTO {

    private int user_id;
    private String username;
    private String email;
    private String role;

    // Constructor for creating a user (without ID)
    public UserDTO(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Constructor for response (with ID)
    public UserDTO(int user_id, String username, String email, String role) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Getter and Setter Methods
    public int getuser_id() {
        return user_id;
    }

    public void setuser_id(int user_id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
