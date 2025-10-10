/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties (ignoreUnknown =  true)

public class partido_model {
    private int id_partido;
    private String equipo_visitantes;
    private String equipo_local;
    private int fecha_partido;
    private String estadio;
    private String estado;

    public int getId_partido() {
        return id_partido;
    }

    public void setId_partido(int id_partido) {
        this.id_partido = id_partido;
    }

    public String getEquipo_visitantes() {
        return equipo_visitantes;
    }

    public void setEquipo_visitantes(String equipo_visitantes) {
        this.equipo_visitantes = equipo_visitantes;
    }

    public String getEquipo_local() {
        return equipo_local;
    }

    public void setEquipo_local(String equipo_local) {
        this.equipo_local = equipo_local;
    }

    public int getFecha_partido() {
        return fecha_partido;
    }

    public void setFecha_partido(int fecha_partido) {
        this.fecha_partido = fecha_partido;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
