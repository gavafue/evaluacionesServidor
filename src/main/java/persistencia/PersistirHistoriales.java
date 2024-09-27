package persistencia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import logica.Historial;
import logica.Historiales;

/**
 * La clase {@code PersistirHistoriales} maneja la persistencia de historiales
 * en un archivo de texto.
 * Permite cargar historiales desde un archivo y guardar los puntajes obtenidos
 * por cada estudiante.
 * 
 */
public class PersistirHistoriales {

    /**
     * Carga los historiales desde un archivo de texto.
     *
     * @return Una instancia de {@code Historiales} con los datos cargados.
     */
    public Historiales cargarHistorialesDesdeArchivo() {
        Historiales listaHistorial = new Historiales();
        try (Scanner s = new Scanner(new File("historiales.txt"))) {
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                String[] historial = linea.split(";");

                // Extraemos los datos principales del historial
                String idEstudiante = historial[0];
                String nombreEvaluacion = historial[1];
                int puntaje = Integer.parseInt(historial[2]);

                // Las respuestas están a partir de historial[3] hasta el final
                String[] respuestas = new String[historial.length - 3];
                for (int i = 0; i < respuestas.length; i++) {
                    respuestas[i] = historial[i + 3];  // Copiamos las respuestas
                }

                // Creamos el objeto Historial con las respuestas
                listaHistorial.agregarHistorial(new Historial(idEstudiante, nombreEvaluacion, puntaje, respuestas));
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de historiales: " + e.getMessage());
        }
        return listaHistorial;
    }

    /**
     * Persiste el puntaje obtenido por cada estudiante al realizar una
     * evaluación.
     *
     * @param listaHistorial Una lista de historiales a persistir.
     */
    public void persistirHistorialesEnArchivo(LinkedList<Historial> listaHistorial) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historiales.txt"))) {
            for (Historial historial : listaHistorial) {
                // Escribimos los datos principales del historial
                writer.write(historial.getTituloEvaluacion() + ";" + historial.getCiEstudiante() + ";"
                        + historial.getPuntaje());

                // Añadimos las respuestas del estudiante al final, separadas por ;
                String[] respuestas = historial.getRespuestas();
                if (respuestas != null && respuestas.length > 0) {
                    for (String respuesta : respuestas) {
                        writer.write(";" + respuesta); // Escribimos cada respuesta separada por ';'
                    }
                }

                writer.newLine(); // Nueva línea después de cada historial
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de historiales: " + e.getMessage());
            e.printStackTrace(); // @todo: redirigir a un log
        }
    }
}
