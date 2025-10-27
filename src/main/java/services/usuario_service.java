package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import models.usuario_model;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import java.util.HashMap;
import java.util.Map;
import models.RespuestaLogin;

/**
 * Servicio para consumir la API de usuarios desde Render
 * URL base: https://venta-boletos.onrender.com/api/usuarios
 * 
 * @author Jose
 */
public class usuario_service {

    private static final String BASE_URL = "https://venta-boletos.onrender.com/api/usuarios";
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public usuario_model login (String nombre_usuario, String contrasena) throws Exception{
        try(CloseableHttpClient user = HttpClients.createDefault()){
        System.out.println("Enviando credenciales: " + nombre_usuario + " " + contrasena);  // Opcional: separa con espacio
        HttpPost request = new HttpPost(BASE_URL + "/login");
        Map<String, String> credenciales = new HashMap<>();
        credenciales.put("nombre_usuario", nombre_usuario);
        credenciales.put("contrasena", contrasena);
        String json = mapper.writeValueAsString(credenciales);
       
        System.out.println("Usuario enviado: " + nombre_usuario);
        System.out.println("Contraseña enviada: " + contrasena);
        System.out.println("JSON enviado: " + json);
            request.setEntity(EntityBuilder.create()
                .setText(json)
                .setContentType(ContentType.APPLICATION_JSON)
                .build());
            ClassicHttpResponse response = (ClassicHttpResponse) user.execute(request);
            int status = response.getCode();
            System.out.println("Código de estado de la respuesta: " + status);
            if(status == 200){
                InputStream is = response.getEntity().getContent();
                String responseBody = new String(is.readAllBytes()); // Leer el cuerpo de la respuesta
                System.out.println("Respuesta exitosa de la API: " + responseBody);
                 is = new java.io.ByteArrayInputStream(responseBody.getBytes());
                RespuestaLogin loginResponse = mapper.readValue(is, RespuestaLogin.class);
                usuario_model usuarioLogueado = loginResponse.getUsuario();
                System.out.println("DEBUG SERVICE: Rol extraido: [" + usuarioLogueado.getRol() + "]");
                return usuarioLogueado;
            }else{
                InputStream is = response.getEntity().getContent();
            String responseBody = new String(is.readAllBytes()); // Leer el cuerpo de la respuesta
            System.out.println("Error de la API. Cuerpo de la respuesta: " + responseBody);
                throw new Exception("Credenciales incorrectas");
            }
        }
    }

   // === GET: Obtener un usuario por ID ===
public usuario_model getUsuarioPorId(int id) throws Exception {
    try (CloseableHttpClient client = HttpClients.createDefault()) {
        HttpGet request = new HttpGet(BASE_URL + "/" + id);
        request.setHeader("Accept", "application/json");

        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
        int status = response.getCode();

        if (status != 200) {
            throw new RuntimeException("Error al obtener usuario: " + status);
        }

        InputStream is = response.getEntity().getContent();
        return mapper.readValue(is, usuario_model.class);
    }
}

    
    public List<usuario_model> getTodosLosUsuarios() throws Exception {
    try (CloseableHttpClient client = HttpClients.createDefault()) {
        HttpGet request = new HttpGet(BASE_URL + "/");
        request.setHeader("Accept", "application/json");

        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
        int status = response.getCode();

        if (status != 200) {
            throw new RuntimeException("Error en respuesta: " + status);
        }

        InputStream is = response.getEntity().getContent();

        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(is, usuario_model[].class));
    }
}



    // === POST: Crear un nuevo usuario ===
    public usuario_model createUsuario(usuario_model u) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/create");

            String json = mapper.writeValueAsString(u);

            request.setEntity(EntityBuilder.create()
                    .setText(json)
                    .setContentType(ContentType.APPLICATION_JSON)
                    .build());

            ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
            InputStream is = response.getEntity().getContent();
            return mapper.readValue(is, usuario_model.class);
        }
    }

    // === PUT: Actualizar un usuario existente ===
public void actualizarUsuario(int id, usuario_model datos) throws Exception {
    try (CloseableHttpClient client = HttpClients.createDefault()) {
        HttpPut request = new HttpPut(BASE_URL + "/update/" + id);
        request.setHeader("Content-Type", "application/json");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(datos);
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
        if (response.getCode() != 200) {
            throw new RuntimeException("Error al actualizar usuario: " + response.getCode());
        }
    }
}

    // === DELETE: Eliminar un usuario por ID ===
public void deleteUsuario(int id) throws Exception {
    try (CloseableHttpClient client = HttpClients.createDefault()) {
        HttpDelete request = new HttpDelete(BASE_URL + "/delete/" + id);
        ClassicHttpResponse response = (ClassicHttpResponse) client.execute(request);
        int status = response.getCode();

        if (status != 200) {
            throw new RuntimeException("Error al eliminar usuario: " + status);
        }
    }
}
}

