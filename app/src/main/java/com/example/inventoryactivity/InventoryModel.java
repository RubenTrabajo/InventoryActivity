package com.example.inventoryactivity;

public class InventoryModel {

    private String nombre;
    private String descripcion;
    private String categoria;
    private String imagen;
    private double precioCompra;
    private int cantidadComprada;
    private double precioActualMercado;
    private String actualizacionPrecios;
    private String moneda;

    // Constructor vacío requerido para Firebase Firestore
    public InventoryModel() {
        // Necesario para la deserialización
    }

    public InventoryModel(String nombre, String descripcion, String categoria, String imagen,
                          double precioCompra, int cantidadComprada, double precioActualMercado,
                          String actualizacionPrecios, String moneda) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.imagen = imagen;
        this.precioCompra = precioCompra;
        this.cantidadComprada = cantidadComprada;
        this.precioActualMercado = precioActualMercado;
        this.actualizacionPrecios = actualizacionPrecios;
        this.moneda = moneda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(int cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    public double getPrecioActualMercado() {
        return precioActualMercado;
    }

    public void setPrecioActualMercado(double precioActualMercado) {
        this.precioActualMercado = precioActualMercado;
    }

    public String getActualizacionPrecios() {
        return actualizacionPrecios;
    }

    public void setActualizacionPrecios(String actualizacionPrecios) {
        this.actualizacionPrecios = actualizacionPrecios;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

}
