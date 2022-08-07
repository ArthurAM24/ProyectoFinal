package com.example.proyectofinal.Modelo;

public class Comida {
    private String Nombre, Image, Descripcion, Precio, Descuento, MenuID;

    public Comida() {

    }

    public Comida(String nombre, String image, String descripcion, String precio, String descuento, String menuID) {
        Nombre = nombre;
        Image = image;
        Descripcion = descripcion;
        Precio = precio;
        Descuento = descuento;
        MenuID = menuID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getDescuento() {
        return Descuento;
    }

    public void setDescuento(String descuento) {
        Descuento = descuento;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }
}
