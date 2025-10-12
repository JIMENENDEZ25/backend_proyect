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
import models.partido_model;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class partido_service {
    private static final String BASE_URL = "https://venta-boletos.onrender.com";
    private static final ObjectMapper mapper = new ObjectMapper();

    // GET clientes
    public List<partido_model> getPartidos() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL);
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
            InputStream is = response.getEntity().getContent();
            return mapper.readValue(is, new TypeReference<List<partido_model>>() {});
        }
    }

    // POST crear cliente
    public partido_model createPartido(partido_model c) throws Exception {
        if(c.getFecha_partido() <= 0){
                LocalDate now = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD-MM-YYYY");
                String fechaStr = now.format(formatter);
                c.setFecha_partido(Integer.parseInt(fechaStr));
            }
        
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/create");
            
            String json = mapper.writeValueAsString(c);

            request.setEntity(EntityBuilder.create()
                    .setText(json)
                    .setContentType(ContentType.APPLICATION_JSON) //Para especificar el tipo de lenguaje del body que va a recibir
                    .build());

            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
            InputStream is = response.getEntity().getContent();
            return mapper.readValue(is, partido_model.class);
        }
    }
    
    public partido_model updatePartido(int id, partido_model c) throws Exception {
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPut request = new HttpPut(BASE_URL + "/update/" + id);
            String json = mapper.writeValueAsString(c);
            
            request.setEntity(EntityBuilder.create()
            .setText(json)
            .setContentType(ContentType.APPLICATION_JSON)
                    .build());
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
            InputStream is = response.getEntity().getContent();
            return mapper.readValue(is, partido_model.class);
        }
    }
    
    public void deleteCustomer (int id) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()){
            HttpDelete request = new HttpDelete(BASE_URL + "/delete/" + id);
            client.execute(request).close();
        }
    }
}
