package com.senati.reciclaje.model;

/**
 * Entidad User. Plantilla requiere parametros "apellidos, nombres,
 * username, password"
 */
public class User {
    private Integer id;
    private String apellidos;
    private String nombres;
    private String username;
    private String password;

    public User(Integer id, String apellidos, String nombres, String username, String password) {
        this.id = id;
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