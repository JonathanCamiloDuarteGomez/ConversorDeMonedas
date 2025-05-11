package Modelo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoDeLaApi {

    private static final String API_KEY = "dd59271d271cdaeadc740863";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public ConsumoDeLaApi() {
    }

    /**
     * Consulta la tasa de cambio desde la API para una moneda base y la convierte a COP.
     * @param monedaBase código ISO de la moneda base (por ejemplo "USD").
     * @return la tasa de cambio de monedaBase → COP, o 0 si hay error o moneda inválida.
     */
    public double consultarApi(String monedaBase, String monedaDestino) {
        if (!verificacionEnum(monedaBase)) {
            System.err.println("Modelo.Moneda inválida: " + monedaBase);
            return 0;
        }

        String url = String.format("%s%s/latest/%s", BASE_URL, API_KEY, monedaBase.toUpperCase());

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Error en la respuesta HTTP: código " + response.statusCode());
                return 0;
            }
            //System.out.println(response.body());

            // Extraemos la tasa de COP
            return obtenerTasa(response.body(), monedaDestino.toUpperCase());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consumir la API", e);
        }
    }

    /**
     * Verifica que el string corresponda a un valor válido del enum Modelo.Moneda.
     */
    private boolean verificacionEnum(String tipoMoneda) {
        if (tipoMoneda == null || tipoMoneda.isBlank()) {
            return false;
        }

        try {
            Moneda.valueOf(tipoMoneda.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Parsea el JSON de respuesta y devuelve la tasa de cambio para la moneda objetivo.
     *
     * @param responseBody        el cuerpo de la respuesta HTTP (JSON).
     * @param tasaDeCambioAConvertir el código ISO de la moneda destino (ej. "COP").
     * @return el valor de la tasa de cambio, o 0 si no se encuentra.
     */
    private static double obtenerTasa(String responseBody, String tasaDeCambioAConvertir) {
        //Conversión a JSON
        JsonElement elemento = JsonParser.parseString(responseBody);
        JsonObject root = elemento.getAsJsonObject();

        // Obtiene el objeto "conversion_rates"
        JsonObject rates = root
                .getAsJsonObject("conversion_rates");

        if (rates == null || !rates.has(tasaDeCambioAConvertir)) {
            System.err.println("No se encontró la tasa para: " + tasaDeCambioAConvertir);
            return 0;
        }

        return rates
                .get(tasaDeCambioAConvertir)
                .getAsDouble();
    }
}
