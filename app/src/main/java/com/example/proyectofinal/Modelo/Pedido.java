package com.example.proyectofinal.Modelo;

public class Pedido {
    private String UsercelID;
    private String ProductoID;
    private String ProductoNomb;
    private String Cantidad;
    private String Precio;
    private String Descuento;

    public Pedido() {
    }

    public Pedido(String usercelID, String productoID, String productoNomb, String cantidad, String precio, String descuento) {
        UsercelID = usercelID;
        ProductoID = productoID;
        ProductoNomb = productoNomb;
        Cantidad = cantidad;
        Precio = precio;
        Descuento = descuento;
    }

    public String getUsercelID() {
        return UsercelID;
    }

    public void setUsercelID(String usercelID) {
        UsercelID = usercelID;
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
