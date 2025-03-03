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

    public User(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}