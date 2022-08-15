package com.example.proyectofinal.Modelo;

public class Orden {
    private String ProductoID;
    private String ProductoNomb;
    private String Cantidad;
    private String Precio;
    private String Descuento;

    public Orden() {
    }

    public Orden(String productoID, String productoNomb, String cantidad, String precio, String descuento) {
        ProductoID = productoID;
        ProductoNomb = productoNomb;
        Cantidad = cantidad;
        Precio = precio;
        Descuento = descuento;
    }

    public String getProductoID() {
        return ProductoID;
    }

    public void setProductoID(String productoID) {
        ProductoID = productoID;
    }

    public String getProductoNomb() {
        return ProductoNomb;
    }

    public void setProductoNomb(String productoNomb) {
        ProductoNomb = productoNomb;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
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
}
