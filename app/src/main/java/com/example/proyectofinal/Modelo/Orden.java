package com.example.proyectofinal.Modelo;

import java.util.List;

public class Orden {
    private String celular;
    private String nombre;
    private String cliente;
    private String NroMesa;
    private String total;
    private String estado;
    private List<Pedido> comidas;

    public Orden() {
    }

    public Orden(String celular, String nombre, String cliente, String nroMesa, String total,  List<Pedido> comidas) {
        this.celular = celular;
        this.nombre = nombre;
        this.cliente = cliente;
        NroMesa = nroMesa;
        this.total = total;
        this.comidas = comidas;
        this.estado = "0";// default 0, atendido 1, listo 2

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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Pedido> getComidas() {
        return comidas;
    }

    public void setComidas(List<Pedido> comidas) {
        this.comidas = comidas;
    }
}
