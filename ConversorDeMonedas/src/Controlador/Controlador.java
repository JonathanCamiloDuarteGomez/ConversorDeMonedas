package Controlador;

import Modelo.ConsumoDeLaApi;
import Modelo.UsuarioDTO;
import Vista.VisualizarDatos;

import java.util.Scanner;

public class Controlador {
    VisualizarDatos visualizarDatos = new VisualizarDatos();
    ConsumoDeLaApi consumoDeLaApi = new ConsumoDeLaApi();
    double resultado=0;

    public void ejecutarPrograma(){
        Scanner sc = new Scanner(System.in);
        String opcion;
        do{
            visualizarDatos.mostrarMenu();
            UsuarioDTO usuarioDTO = new UsuarioDTO(visualizarDatos.getMonedaBase(), visualizarDatos.getMonedaDestino(), visualizarDatos.getMonto());
            double tasaDeCambio = consumoDeLaApi.consultarApi(usuarioDTO.monedaBase(), usuarioDTO.monedaDestino());
            resultado=tasaDeCambio* usuarioDTO.monto();
            visualizarDatos.mostrarResultado(usuarioDTO.monto(), usuarioDTO.monedaBase(), usuarioDTO.monedaDestino(),resultado);
            opcion = visualizarDatos.capturarSalida(sc);
        }while (!opcion.equals("si"));
        sc.close();
        System.out.println("Gracias por usar el conversor. ¡Hasta luego!");
    }

}
