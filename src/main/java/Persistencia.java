/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Gabriel
 */
public class Persistencia {
    public void persistirListaDeUsuariosEnArchivo(HashMap<String, Usuario> hashUsuarios) {
        try {
            FileWriter fw = new FileWriter("passwords.txt");
            for (Map.Entry<String, Usuario> entry : hashUsuarios.entrySet()) { // CODIFICACION SUGERIDA EN LA
                                                                               // DOCUMENTACION OFICIAL DE JAVA.
                fw.write(entry.getKey() + ";" + entry.getValue().getContrasenia() + ";"
                        + entry.getValue().getTipoDeUsuario() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace(); // @todo, despues tenemos que redirigir estos errores a un log.
        }
    }

    /**
     * Método que persiste las evaluaciones en un archivo utilizando
     * serialización.
     *
     * @throws java.io.FileNotFoundException
     */
    public void persistirEvaluacionesEnArchivo(List<Evaluacion> listaEvaluaciones) {
        try {
            FileWriter fw = new FileWriter("evaluaciones.txt");
            for (Evaluacion evaluacion : listaEvaluaciones) {
                // Escribe el título de la evaluación
                fw.write(evaluacion.getTitulo() + ";");

                // Recorre la lista de preguntas (ArrayList) y las escribe en una misma línea
                LinkedList<Pregunta> listaPreguntas = evaluacion.getListaPreguntas().getPreguntas();
                for (Pregunta pregunta : listaPreguntas) {
                    fw.write(pregunta.getEnunciado() + "," + pregunta.getPuntaje() + ",");

                    // Si la pregunta es de tipo CompletarEspacio
                    if (pregunta instanceof CompletarEspacio) {
                        CompletarEspacio ce = (CompletarEspacio) pregunta;
                        fw.write("CompletarEspacio," + Arrays.toString(ce.getRespuestasCorrectas()) + ";");
                    }
                    // Si la pregunta es de tipo MultipleOpcion
                    else if (pregunta instanceof MultipleOpcion) {
                        MultipleOpcion mo = (MultipleOpcion) pregunta;

                        if (mo.getEsVerdaderoOFalso()) {
                            // Escribe la pregunta como verdadero o falso
                            fw.write("VerdaderoOFalso," + mo.getRespuestaCorrecta() + ";");
                        } else {
                            // Escribe la pregunta como opción múltiple
                            fw.write("MultipleOpcion," + Arrays.toString(mo.getOpciones()) + ","
                                    + mo.getRespuestaCorrecta() + ";");
                        }
                    }
                }

                // Añade un salto de línea después de cada evaluación
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace(); // @todo: Redirigir estos errores a un log en lugar de imprimirlos en consola.
        }
    }

}
