package com.example.proyectofinal.Modelo;

import java.util.List;

public class Pedido {
    private String celular;
    private String nombre;
    private String NroMesa;
    private String total;
    private List<Orden> comidas;

    public Pedido() {
    }

    public Pedido(String celular, String nombre, String nroMesa, String total, List<Orden> comidas) {
        this.celular = celular;
        this.nombre = nombre;
        NroMesa = nroMesa;
        this.total = total;
        this.comidas = comidas;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNroMesa() {
        return NroMesa;
    }

    public void setNroMesa(String nroMesa) {
        NroMesa = nroMesa;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Orden> getComidas() {
        return comidas;
    }

    public void setComidas(List<Orden> comidas) {
        this.comidas = comidas;
    }
}
