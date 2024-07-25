package com.example.ParcialFinal.model;

// Define una clase pública llamada WeatherResponse
public class WeatherResponse {
    // Atributos privados para almacenar el nombre de la ciudad, los datos principales del clima y una lista de condiciones del clima
    private String name;
    private Main main;
    private Weather[] weather;

    // Método getter para obtener el nombre de la ciudad
    public String getName() {
        return name;
    }

    // Método setter para establecer el nombre de la ciudad
    public void setName(String name) {
        this.name = name;
    }

    // Método getter para obtener los datos principales del clima
    public Main getMain() {
        return main;
    }

    // Método setter para establecer los datos principales del clima
    public void setMain(Main main) {
        this.main = main;
    }

    // Método getter para obtener la lista de condiciones del clima
    public Weather[] getWeather() {
        return weather;
    }

    // Método setter para establecer la lista de condiciones del clima
    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    // Define una clase estática interna llamada Main para almacenar los datos principales del clima
    public static class Main {
        // Atributo privado para almacenar la temperatura
        private double temp;

        // Método getter para obtener la temperatura
        public double getTemp() {
            return temp;
        }

        // Método setter para establecer la temperatura
        public void setTemp(double temp) {
            this.temp = temp;
        }
    }

    // Define una clase estática interna llamada Weather para almacenar las condiciones del clima
    public static class Weather {
        // Atributos privados para almacenar la descripción y el ícono del clima
        private String description;
        private String icon;

        // Método getter para obtener la descripción del clima
        public String getDescription() {
            return description;
        }

        // Método setter para establecer la descripción del clima
        public void setDescription(String description) {
            this.description = description;
        }

        // Método getter para obtener el ícono del clima
        public String getIcon() {
            return icon;
        }

        // Método setter para establecer el ícono del clima
        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
