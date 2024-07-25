package com.example.ParcialFinal.controller;

// Importa la clase WeatherService desde el paquete de servicio
import com.example.ParcialFinal.service.WeatherService;
// Importa la clase WeatherResponse desde el paquete de modelo
import com.example.ParcialFinal.model.WeatherResponse;
// Importa las anotaciones necesarias de Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Anota esta clase como un controlador REST de Spring
@RestController
// Mapea las solicitudes HTTP a "/weather" a los métodos de esta clase
@RequestMapping("/weather")
public class WeatherController {

    // Inyecta una instancia de WeatherService en este controlador
    @Autowired
    private WeatherService weatherService;

    // Define un manejador de solicitudes GET para la URL "/weather/{city}"
    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponse> getWeather(@PathVariable String city) {
        // Llama al servicio para obtener los datos del clima de la ciudad especificada
        WeatherResponse response = weatherService.getWeather(city);
        // Retorna la respuesta dentro de un objeto ResponseEntity con un código de estado HTTP 200 (OK)
        return ResponseEntity.ok(response);
    }
}
