package com.example.proyectofinal.Modelo;

public class Categoria {

    private String Image;
    private String Nombre;


    public Categoria() {
    }

    public Categoria(String image, String nombre) {
        Image = image;
        Nombre = nombre;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
