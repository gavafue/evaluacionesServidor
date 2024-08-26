/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
    public void persistirEvaluacionesEnArchivo(List<Evaluacion> listaEvaluaciones, String cantidadDePreguntas) {
        List<String> titulosExistentes = new ArrayList<>();
        try {
            titulosExistentes = obtenerTitulosDeEvaluacionesDesdeArchivo();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter("evaluaciones.txt", true);
            for (Evaluacion evaluacion : listaEvaluaciones) {
                if (!titulosExistentes.contains(evaluacion.getTitulo())) {
                    fw.write(evaluacion.getTitulo() + ";");
                    LinkedList<Pregunta> listaPreguntas = evaluacion.getListaPreguntas().getPreguntas();
                    for (Pregunta pregunta : listaPreguntas) {
                        fw.write(pregunta.getEnunciado() + "," + pregunta.getPuntaje() + ",");
                        if (pregunta instanceof CompletarEspacio) {
                            CompletarEspacio ce = (CompletarEspacio) pregunta;
                            fw.write("Completar," + String.join(",", ce.getRespuestasCorrectas()) + ";");
                        } else if (pregunta instanceof MultipleOpcion) {
                            MultipleOpcion mo = (MultipleOpcion) pregunta;
                            if (mo.getEsVerdaderoOFalso()) {
                                fw.write("VF," + mo.getRespuestaCorrecta() + ";");
                            } else {
                                fw.write("Multiple," + String.join(",", mo.getOpciones()) + ","
                                        + mo.getRespuestaCorrecta() + ";");
                            }
                        }
                    }
                    fw.write(cantidadDePreguntas);
                    fw.write("\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> obtenerTitulosDeEvaluacionesDesdeArchivo() throws IOException {
        List<String> resultados = new ArrayList<String>();
        String archivoRuta = "evaluaciones.txt"; // Ruta del archivo a leer

        try (BufferedReader br = new BufferedReader(new FileReader(archivoRuta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Divide la línea en partes usando el delimitador ';' y toma la primera parte
                String[] partes = linea.split(";");
                if (partes.length > 0) {
                    resultados.add(partes[0]);
                }
            }
        } catch (IOException e) {
            // Manejo de excepciones en caso de error al leer el archivo
            throw new IOException("Error al leer el archivo de evaluaciones", e);
        }

        return resultados;
    }

    public Evaluaciones cargarEvaluacionesDesdeArchivo() {
        Evaluaciones evaluaciones = new Evaluaciones();
        try (Scanner scanner = new Scanner(new File("evaluaciones.txt"))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                // Separa la línea en partes por ';;;'
                String[] arregloDePreguntas = linea.split(";");

                String tituloEvaluacion = arregloDePreguntas[0].trim();
                Preguntas listaPreguntas = new Preguntas();

                for (int i = 1; i < arregloDePreguntas.length - 1; i++) {
                    String[] datosPregunta = arregloDePreguntas[i].split(",");

                    if (datosPregunta.length < 4) {
                        // Si no hay suficientes datos para procesar la pregunta, saltar
                        System.err.println("Datos de pregunta incompletos en la línea: " + linea);
                        continue;
                    }

                    try {
                        String enunciado = datosPregunta[0].trim();
                        int puntaje = Integer.parseInt(datosPregunta[1].trim());
                        String tipoPregunta = datosPregunta[2].trim();
                        String respuestaCorrecta = datosPregunta[datosPregunta.length - 1].trim();
                        Pregunta pregunta = null;

                        switch (tipoPregunta) {
                            case "Completar":
                                String[] respuestasFiltradas = new String[2];
                                respuestasFiltradas[0] = datosPregunta.length >= 4 ? datosPregunta[3].trim() : null;
                                respuestasFiltradas[1] = datosPregunta.length >= 5 ? datosPregunta[4].trim() : null;
                                pregunta = new CompletarEspacio(enunciado, puntaje, respuestasFiltradas);
                                break;
                            case "Multiple":
                                if (datosPregunta.length < 7) {
                                    throw new IllegalArgumentException(
                                            "Faltan opciones en la pregunta de tipo Multiple.");
                                }
                                String[] respuestas1 = {
                                        datosPregunta[3].trim(),
                                        datosPregunta[4].trim(),
                                        datosPregunta[5].trim(),
                                        datosPregunta[6].trim()
                                };
                                pregunta = new MultipleOpcion(enunciado, puntaje, respuestas1, false,
                                        respuestaCorrecta);
                                break;
                            case "VF":
                                if (!respuestaCorrecta.equals("Verdadero") && !respuestaCorrecta.equals("Falso")) {
                                    throw new IllegalArgumentException(
                                            "Respuesta incorrecta para pregunta de tipo VF.");
                                }
                                String[] respuestas2 = { "Verdadero", "Falso" };
                                pregunta = new MultipleOpcion(enunciado, puntaje, respuestas2, true, respuestaCorrecta);
                                break;
                            default:
                                throw new IllegalArgumentException("Tipo de pregunta no reconocido: " + tipoPregunta);
                        }

                        listaPreguntas.agregarPregunta(pregunta);
                    } catch (NumberFormatException e) {
                        System.err.println("Error al convertir el puntaje en número: " + datosPregunta[1].trim());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error al procesar la pregunta: " + e.getMessage());
                    }
                }

                try {
                    Evaluacion evaluacion = new Evaluacion(tituloEvaluacion, listaPreguntas);
                    evaluaciones.agregarEvaluacion(evaluacion);
                } catch (Exception e) {
                    System.err.println("Error al crear la evaluación: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
        return evaluaciones;
    }

    /**
     * Metodo que permite cargar al sistema los resultados de las evaluaciones
     * extraidos de un archivo de texto.
     * 
     * @return lista
     */
    public Historiales cargarHistorialesDesdeArchivo() {
        Historiales listaHistorial = new Historiales();
        try {
            Scanner s = new Scanner(new File("historiales.txt"));
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                String[] historial = linea.split(";");
                listaHistorial.agregarHistorial(new Historial(historial[0], historial[1], parseInt(historial[2])));
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaHistorial;
    }

    /**
     * Metodo que persiste el puntaje obtenido por cada estudiante al realizar
     * una evaluacion.
     */
    public void persistirHistorialesEnArchivo(LinkedList<Historial> listaHistorial) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historiales.txt"))) {
            for (Historial historial : listaHistorial) {
                writer.write(historial.getTituloEvaluacion() + ";" + historial.getCiEstudiante() + ";"
                        + historial.getPuntaje());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
