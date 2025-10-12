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
import models.detalle_venta_model;
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

public class detalle_venta_service {
    private static final String BASE_URL = "https://venta-boletos.onrender.com";
    private static final ObjectMapper mapper = new ObjectMapper();

    // GET clientes
    public List<detalle_venta_model> getDetails() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL);
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
            InputStream is = response.getEntity().getContent();
            return mapper.readValue(is, new TypeReference<List<detalle_venta_model>>() {});
        }
    }

    // POST crear cliente
    public detalle_venta_model createDetails(detalle_venta_model c) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/create");
            
            String json = mapper.writeValueAsString(c);

            request.setEntity(EntityBuilder.create()
                    .setText(json)
                    .setContentType(ContentType.APPLICATION_JSON) //Para especificar el tipo de lenguaje del body que va a recibir
                    .build());

            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
            InputStream is = response.getEntity().getContent();
            return mapper.readValue(is, detalle_venta_model.class);
        }
    }
    
    public detalle_venta_model updateDetails(int id, detalle_venta_model c) throws Exception {
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPut request = new HttpPut(BASE_URL + "/update/" + id);
            String json = mapper.writeValueAsString(c);
            
            request.setEntity(EntityBuilder.create()
            .setText(json)
            .setContentType(ContentType.APPLICATION_JSON)
                    .build());
            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
            InputStream is = response.getEntity().getContent();
            return mapper.readValue(is, detalle_venta_model.class);
        }
    }
    
    public void deleteCustomer (int id) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()){
            HttpDelete request = new HttpDelete(BASE_URL + "/delete/" + id);
            client.execute(request).close();
        }
    }
}
