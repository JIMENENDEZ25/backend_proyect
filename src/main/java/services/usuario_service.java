/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author jimem
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.usuario_model;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import java.io.InputStream; //
import java.util.List;
import util.Encryption;

public class usuario_service {
    private static final String BASE_URL = "https://venta-boletos.onrender.com";
    private static final ObjectMapper mapper = new ObjectMapper();

    // GET clientes
    public List<usuario_model> getUser() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL);
            System.out.println("Ejecutando GET a: " + BASE_URL);
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
            int status = response.getCode();
            System.out.println("Status GET: " + status);
            if(status != 200){
                throw new Exception("Error en GET: " + status);
            }
            InputStream is = response.getEntity().getContent();
            List<usuario_model> users = mapper.readValue(is, new TypeReference<List<usuario_model>>(){});
            System.out.println("Usuarios obtenidos: " + users.size());
            return users;
        }
    }

    // POST crear cliente
    public usuario_model createUser(usuario_model c) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/create");
            
            if(c.getNombre_usuario() == null || c.getNombre_usuario().isEmpty()){
                String now = java.time.Instant.now().toString();
                c.setNombre_usuario(now);
            }
            
            String json = mapper.writeValueAsString(c);
            System.out.println("Enviando POST a: " + BASE_URL + "/create con Json: " + json);
            request.setEntity(EntityBuilder.create()
                    .setText(json)
                    .setContentType(ContentType.APPLICATION_JSON) //Para especificar el tipo de lenguaje del body que va a recibir
                    .build());

            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
            int status = response.getCode();
            System.out.println("Status POST: " + status);
            if(status != 200 && status != 201){
                throw new Exception("Error en POST: status " + status);
            }
            InputStream is = response.getEntity().getContent();
            usuario_model created = mapper.readValue(is, usuario_model.class);
            System.out.println("Usuario creado: " + created.getNombre_usuario());
            return created;
        }
    }
    
    public boolean login(String nombre_usuario, String contrasena) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL);
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);

            if (response.getCode() == 200) {
                InputStream is = response.getEntity().getContent();
                List<usuario_model> users = mapper.readValue(is, new TypeReference<List<usuario_model>>() {});

                String hashIngresada = Encryption.hashMD5(contrasena);

                for (usuario_model s : users) {
                    if (s.getNombre_usuario().equalsIgnoreCase(nombre_usuario) && s.getContrasena().equals(hashIngresada)) {
                        return true; 
                    }
                }
            }
        }
        return false;
    }
    
    public usuario_model updateUser(int id, usuario_model c) throws Exception {
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPut request = new HttpPut(BASE_URL + "/update/" + id);
            String json = mapper.writeValueAsString(c);
            
            request.setEntity(EntityBuilder.create()
            .setText(json)
            .setContentType(ContentType.APPLICATION_JSON)
                    .build());
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
            InputStream is = response.getEntity().getContent();
            return mapper.readValue(is, usuario_model.class);
        }
    }
    
    public void deleteUser (int id) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()){
            HttpDelete request = new HttpDelete(BASE_URL + "/delete/" + id);
            client.execute(request).close();
        }
    }
}
