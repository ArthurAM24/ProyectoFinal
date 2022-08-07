package com.example.proyectofinal.Modelo;

public class Usuario {
    private String celular;
    private String password;
    private String correo;
    private String nombres;
    private String tipouser;


    public Usuario() {

    }

    public Usuario(String correo, String nombres, String password) {
        this.correo = correo;
        this.nombres = nombres;
        this.password = password;
        tipouser="false";
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipouser() {
        return tipouser;
    }

    public void setTipouser(String tipouser) {
        this.tipouser = tipouser;
    }
}
