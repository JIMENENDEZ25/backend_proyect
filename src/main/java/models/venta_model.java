/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties (ignoreUnknown =  true)

public class venta_model {
    private int id_venta;
    private int id_vendedor;
    private int fecha_venta;
    private int total_venta;

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public int getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(int fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public int getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(int total_venta) {
        this.total_venta = total_venta;
    }
    
    
}
