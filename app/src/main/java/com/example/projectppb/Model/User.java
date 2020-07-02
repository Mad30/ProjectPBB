package com.example.projectppb.Model;

public class User {

    private int id;
    private String nama,email,username,password;

    public User(int id, String nama, String email) {
        this.id = id;
        this.nama = nama;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }
}
