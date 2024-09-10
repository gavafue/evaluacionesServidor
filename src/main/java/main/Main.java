package main;

import conexion.ConexionServidor;

/**
 * La clase {@code Main} es el punto de entrada para la aplicación.
 * Su método {@code main} inicializa la conexión con el servidor.
 */
public class Main {

    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados en esta
     *             implementación).
     */
    public static void main(String[] args) {
        ConexionServidor server = new ConexionServidor();
        server.iniciar();
    }
}
