/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties (ignoreUnknown = true)

public class inventario_model {
    private int id_inventario;
    private partido_model id_partido;
    private localidad_model4 id_localidad;
    private int cantidad_total;
    private int cantidad_disponible;

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public partido_model getId_partido() {
        return id_partido;
    }

    public void setId_partido(partido_model id_partido) {
        this.id_partido = id_partido;
    }

    public localidad_model4 getId_localidad() {
        return id_localidad;
    }

    public void setId_localidad(localidad_model4 id_localidad) {
        this.id_localidad = id_localidad;
    }

    public int getCantidad_total() {
        return cantidad_total;
    }

    public void setCantidad_total(int cantidad_total) {
        this.cantidad_total = cantidad_total;
    }

    public int getCantidad_disponible() {
        return cantidad_disponible;
    }

    public void setCantidad_disponible(int cantidad_disponible) {
        this.cantidad_disponible = cantidad_disponible;
    }
    
    
}
