package Vista;

import java.util.Scanner;

public class VisualizarDatos {
    private String monedaBase;
    private String monedaDestino;
    private double monto = 0;

    public VisualizarDatos() {
    }

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        String[] topMonedas = {
                "USD -> Dólar estadounidense",
                "EUR -> Euro",
                "GBP -> Libra esterlina",
                "JPY -> Yen japonés",
                "AUD -> Dólar australiano",
                "CAD -> Dólar canadiense",
                "CHF -> Franco suizo",
                "CNY -> Yuan chino",
                "MXN -> Peso mexicano",
                "COP -> Peso colombiano"
        };


        // Encabezado
        System.out.println("***********************************************");
        System.out.println("      BIENVENIDO A SU CONVERSOR DE DIVISAS     ");
        System.out.println("***********************************************");

        // Lista de monedas
        System.out.println("Monedas disponibles (Top 10):");
        for (int i = 0; i < topMonedas.length; i++) {
            System.out.printf("%2d. %s%n", i + 1, topMonedas[i]);
        }
        System.out.println();

        // Ejemplo de uso para guiar al usuario
        System.out.println("Ejemplo de uso:");
        System.out.println("  1. Ingrese moneda de referencia (USD)");
        System.out.println("  2. Ingrese valor a convertir (10)");
        System.out.println("  3. Ingrese moneda a convertir (COP)");

        System.out.println();

        // Solicitud de datos
        System.out.print("1. Moneda de referencia: ");
        monedaBase = sc.nextLine().trim().toUpperCase();
        System.out.print("2. Valor a convertir: ");

        try {
            monto = Double.parseDouble(sc.nextLine().replace(",", ""));
        } catch (NumberFormatException e) {
            System.out.print("2. Valor a convertir: ");
            System.err.println("Error: ingrese un número válido.");
            monto = Double.parseDouble(sc.nextLine().replace(",", ""));
        }
        System.out.print("3. Moneda a convertir: ");
        monedaDestino = sc.nextLine().trim().toUpperCase();


    }
    public void mostrarResultado(double monto, String monedaBase, String monedaDestino, double resultado) {
        System.out.printf("\nResultado: %.2f %s = %.2f %s%n", monto, monedaBase, resultado, monedaDestino);
    }

    public String capturarSalida(Scanner sc){
        System.out.print("¿Desea salir? (si/no) \n");
        String respuesta = sc.nextLine().trim().toLowerCase();
        while (!respuesta.equals("si") && !respuesta.equals("no")) {
            System.err.print("Por favor ingrese 'si' o 'no': ");
            respuesta = sc.nextLine().trim().toLowerCase();
        }
        return respuesta;
    }

    public String getMonedaBase() {
        return monedaBase;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public double getMonto() {
        return monto;
    }
}
