package com.example.proyectofinal.Modelo;

import java.util.List;

public class Orden {
    private String celular;
    private String nombre;
    private String NroMesa;
    private String total;
    private String estado;
    private List<Pedido> comidas;

    public Orden() {
    }

    public Orden(String celular, String nombre, String nroMesa, String total, List<Pedido> comidas) {
        this.celular = celular;
        this.nombre = nombre;
        NroMesa = nroMesa;
        this.total = total;
        this.comidas = comidas;
        this.estado="0";// default 0, atendido 1, listo 2
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public List<Pedido> getComidas() {
        return comidas;
    }

    public void setComidas(List<Pedido> comidas) {
        this.comidas = comidas;
    }
}
