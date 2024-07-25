# Weather App

Esta aplicación consulta el clima de diferentes ciudades utilizando la API de OpenWeatherMap. La arquitectura está diseñada para ser extensible, modular y utiliza varios patrones de diseño.

## Descripción de la Arquitectura

La aplicación se basa en una arquitectura de microservicios desplegada en Heroku y sigue el patrón de diseño de cliente-servidor. A continuación, se detallan los componentes principales:

### Componentes de la Arquitectura

1. **Cliente Web**
   - **Frontend**: Construido con HTML, CSS (Bootstrap) y JavaScript (jQuery y Google Maps API).
   - **Interfaz de Usuario (UI)**: Proporciona una interfaz limpia y fácil de usar para que los usuarios ingresen el nombre de una ciudad y obtengan información del clima.
   - **AJAX**: Utilizado para hacer solicitudes asíncronas al servidor y actualizar dinámicamente la interfaz sin recargar la página.

2. **Servidor de Backend**
   - **API REST**: Implementada usando Spring Boot y desplegada en Heroku.
   - **Endpoints**: Proporciona endpoints para obtener datos del clima. Los endpoints se comunican con servicios externos (OpenWeatherMap API).
   - **Caché**: Implementado para almacenar respuestas de la API externa y evitar solicitudes repetidas. Los datos en caché se invalidan después de 5 minutos.
   - **Servicio de Clima**: Clase en Java que maneja la lógica para obtener datos del clima, manejar el caché y hacer solicitudes a la API externa.

3. **Servicios Externos**
   - **OpenWeatherMap API**: Proporciona datos del clima y geocodificación para obtener coordenadas de ciudades.

4. **Base de Datos**
   - **Caché en Memoria**: Utilizado para almacenar temporalmente las respuestas de la API externa.

### Extensibilidad

- **Uso de APIs Alternativas**: La arquitectura permite fácilmente integrar otras APIs de clima. Esto se puede lograr modificando el servicio de clima (`WeatherService`) para que utilice una nueva API. Por ejemplo, se podría agregar una nueva clase de servicio que implemente una interfaz común.
- **Nuevas Funcionalidades**: Funcionalidades adicionales, como pronósticos a largo plazo, se pueden agregar creando nuevos endpoints en el backend y actualizando la lógica del cliente web.

### Patrones de Diseño

- **Patrón de Fachada**: El servidor de backend actúa como una fachada, ocultando la complejidad de las interacciones con las APIs externas.
- **Patrón de Caché**: Implementa un mecanismo de caché para almacenar temporalmente las respuestas y reducir la carga en las APIs externas.
- **Patrón de Controlador**: El uso de controladores en Spring Boot sigue el patrón de diseño de controlador, manejando las solicitudes HTTP y enviando respuestas adecuadas.

### Modularidad

- **Cliente Web**: El frontend es independiente del backend y puede ser modificado o actualizado sin afectar la lógica del servidor.
- **Servicio de Clima**: La lógica de obtención y procesamiento de datos del clima está encapsulada en el `WeatherService`, facilitando su modificación o reemplazo.
- **Caché**: El caché se implementa como una capa separada, lo que permite cambiar su implementación (por ejemplo, usar Redis en lugar de memoria local) sin afectar otros componentes.

### Función de Componentes Individuales

1. **Cliente Web**
   - **HTML/CSS/JS**: Proporciona la interfaz de usuario y maneja las interacciones del usuario.
   - **AJAX**: Envía solicitudes al backend y actualiza la interfaz con los datos recibidos.

2. **Servidor de Backend**
   - **API REST**: Define los endpoints para interactuar con el cliente web.
   - **Caché**: Almacena respuestas de la API externa para mejorar el rendimiento y reducir la latencia.
   - **Servicio de Clima**: Gestiona la lógica de negocio, incluyendo la obtención de datos del clima y la gestión del caché.

3. **Servicios Externos**
   - **OpenWeatherMap API**: Fuente de datos del clima y geocodificación.

### Diagrama de la Arquitectura

![image](https://github.com/user-attachments/assets/48e1e0b2-81e5-4c35-a36f-2cc2a0846a3a)

### Implementación de Extensibilidad, Patrones y Modularidad

#### Extensibilidad:

- **Implementar nuevas APIs**: Crear una interfaz común para las APIs del clima e implementar clases concretas para cada API.
- **Agregar nuevas funcionalidades**: Crear nuevos endpoints en el backend y actualizar el frontend para interactuar con ellos.

#### Patrones de Diseño:

- **Fachada**: El backend oculta la complejidad de la integración con las APIs externas.
- **Caché**: Implementado para almacenar respuestas y reducir la carga en las APIs.
- **Controlador**: Los controladores manejan las solicitudes HTTP.

#### Modularidad:

- **Frontend y Backend independientes**: Permite modificar uno sin afectar al otro.
- **Servicio de Clima encapsulado**: Facilita la modificación de la lógica de negocio.
- **Caché modular**: Permite cambiar su implementación fácilmente.
