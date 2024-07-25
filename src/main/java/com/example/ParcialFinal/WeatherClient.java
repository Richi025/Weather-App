package com.example.ParcialFinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class WeatherClient {

    // Define una constante para el agente de usuario que se usará en la solicitud HTTP
    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) {
        try {
            // Obtiene el nombre del host local donde se está ejecutando el programa
            String host = InetAddress.getLocalHost().getHostName();
            // Define el protocolo a usar (HTTP)
            String protocol = "http";
            // Construye la URL base combinando el protocolo, el host y el puerto 8080
            String baseUrl = protocol + "://" + host + ":8080";

            // Imprime la URL base calculada
            System.out.println("Calculated REST host URL: " + baseUrl);

            // Define la ciudad a consultar
            String city = "London";
            // Construye la URL completa para la solicitud GET y la envía, obteniendo la respuesta
            String response = sendGetRequest(baseUrl + "/weather/" + city);
            // Imprime la respuesta de la solicitud
            System.out.println(response);
        } catch (IOException e) {
            // Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        }
    }

    // Método que realiza una solicitud GET a la URL especificada y devuelve la respuesta como un String
    private static String sendGetRequest(String url) {
        try {
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
                StringBuilder content = new StringBuilder();

                // Lee cada línea de la respuesta y la añade al StringBuilder
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Cierra el BufferedReader
                in.close();
                // Devuelve la respuesta como un String
                return content.toString();
            } else {
                // Si la solicitud no fue exitosa, devuelve un mensaje de error
                return "GET request not worked";
            }
        } catch (IOException e) {
            // Imprime la traza de la excepción en caso de error y devuelve el mensaje de la excepción
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
    }
}
