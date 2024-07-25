package com.example.ParcialFinal.service;

import com.example.ParcialFinal.model.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Anotación para declarar esta clase como un servicio en Spring
@Service
public class WeatherService {

    // Clave de la API de OpenWeatherMap
    private final String apiKey = "09c8fffb24a03e07b3cb8078648052ef";
    // Define un agente de usuario para las solicitudes HTTP
    private final String USER_AGENT = "Mozilla/5.0";

    // Mapa concurrente que actúa como caché para almacenar respuestas de API
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    // Instancia de Gson para manejar la conversión entre JSON y objetos Java
    private final Gson gson = new Gson();

    // Método público para obtener los datos del clima de una ciudad
    public WeatherResponse getWeather(String city) {
        // Convierte el nombre de la ciudad a minúsculas para usarlo como clave de caché
        String cacheKey = city.toLowerCase();

        // Verificar si la entrada está en caché y no ha expirado
        CacheEntry cachedEntry = cache.get(cacheKey);
        if (cachedEntry != null && !cachedEntry.isExpired()) {
            System.out.println("Se utilizo cache");
            // Si la entrada está en caché y no ha expirado, devuelve la respuesta desde el caché
            return gson.fromJson(cachedEntry.getResponse(), WeatherResponse.class);
        }

        // Construye la URL para obtener las coordenadas de la ciudad desde la API de OpenWeatherMap
        String coordinatesUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=" + apiKey;
        try {
            // Realiza una solicitud GET para obtener las coordenadas
            String coordinatesResponse = sendGetRequest(coordinatesUrl);
            if (coordinatesResponse == null) return null;

            // Parsea las coordenadas de la respuesta
            double[] coordinates = parseCoordinates(coordinatesResponse);
            if (coordinates == null) return null;

            // Construye la URL para obtener el clima usando las coordenadas obtenidas
            String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + coordinates[0] + "&lon=" + coordinates[1] + "&appid=" + apiKey;
            // Realiza una solicitud GET para obtener los datos del clima
            String weatherResponse = sendGetRequest(weatherUrl);
            if (weatherResponse == null) return null;

            // Almacena la respuesta en el caché
            cache.put(cacheKey, new CacheEntry(weatherResponse));

            // Convierte la respuesta JSON a un objeto WeatherResponse y lo devuelve
            return gson.fromJson(weatherResponse, WeatherResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método privado que realiza una solicitud GET a la URL especificada y devuelve la respuesta como un String
    private String sendGetRequest(String url) throws IOException {
        // Crea un objeto URL a partir de la cadena de URL proporcionada
        URL obj = new URL(url);
        // Abre una conexión HTTP a la URL
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // Establece el método de la solicitud como GET
        con.setRequestMethod("GET");
        // Establece la propiedad del agente de usuario en la solicitud
        con.setRequestProperty("User-Agent", USER_AGENT);

        // Obtiene el código de respuesta de la solicitud
        int responseCode = con.getResponseCode();
        // Si la solicitud fue exitosa (código 200 OK)
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Crea un BufferedReader para leer la respuesta de la solicitud
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            // Utiliza un StringBuilder para construir la respuesta como un String
            StringBuilder response = new StringBuilder();

            // Lee cada línea de la respuesta y la añade al StringBuilder
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            // Cierra el BufferedReader
            in.close();
            // Devuelve la respuesta como un String
            return response.toString();
        } else {
            // Si la solicitud no fue exitosa, imprime un mensaje de error y devuelve null
            System.out.println("GET request not worked");
            return null;
        }
    }

    // Método privado que parsea las coordenadas de una respuesta JSON
    private double[] parseCoordinates(String jsonResponse) {
        // Define el tipo de lista de HashMaps para Gson
        Type listType = new TypeToken<HashMap<String, Object>[]>() {}.getType();
        // Convierte el JSON a un array de HashMaps
        HashMap<String, Object>[] locations = gson.fromJson(jsonResponse, listType);
        // Si hay al menos una ubicación en el array
        if (locations.length > 0) {
            HashMap<String, Object> location = locations[0];
            // Obtiene la latitud y longitud de la ubicación y las devuelve como un array de doubles
            double lat = ((Number) location.get("lat")).doubleValue();
            double lon = ((Number) location.get("lon")).doubleValue();
            return new double[]{lat, lon};
        }
        // Si no hay ubicaciones, devuelve null
        return null;
    }

    // Clase estática interna que representa una entrada en el caché
    private static class CacheEntry {
        // Define el tiempo de expiración del caché (5 minutos en milisegundos)
        private static final long EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutos
        // Almacena la respuesta de la API
        private final String response;
        // Almacena el timestamp en el que se creó la entrada del caché
        private final long timestamp;

        // Constructor de la clase CacheEntry
        public CacheEntry(String response) {
            this.response = response;
            this.timestamp = System.currentTimeMillis();
        }

        // Método que devuelve la respuesta almacenada
        public String getResponse() {
            return response;
        }

        // Método que verifica si la entrada del caché ha expirado
        public boolean isExpired() {
            return (System.currentTimeMillis() - timestamp) > EXPIRATION_TIME;
        }
    }
}
