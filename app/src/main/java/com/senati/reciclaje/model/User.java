package com.senati.reciclaje.model;

public class User {
    private int id;
    private String apellidos;
    private String nombres;
    private String username;
    private String password;

    public User(String apellidos, String nombres, String username, String password) {
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}