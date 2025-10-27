/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties (ignoreUnknown = true)

public class RespuestaLogin {
    private String message;
    
    @JsonProperty("usuario")
    private usuario_model usuario;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public usuario_model getUsuario() {
        return usuario;
    }

    public void setUsuario(usuario_model usuario) {
        this.usuario = usuario;
    }
    
    
}
