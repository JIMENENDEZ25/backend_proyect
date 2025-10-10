/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author jimem
 */
public class detalle_venta_model {
    private int id_detalle;
    private venta_model id_venta;
    private localidad_model4 id_localidad;
    private partido_model id_partido;
    private int cantidad;
    private double precio_unitario;

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public venta_model getId_venta() {
        return id_venta;
    }

    public void setId_venta(venta_model id_venta) {
        this.id_venta = id_venta;
    }

    public localidad_model4 getId_localidad() {
        return id_localidad;
    }

    public void setId_localidad(localidad_model4 id_localidad) {
        this.id_localidad = id_localidad;
    }

    public partido_model getId_partido() {
        return id_partido;
    }

    public void setId_partido(partido_model id_partido) {
        this.id_partido = id_partido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
    
    
}
