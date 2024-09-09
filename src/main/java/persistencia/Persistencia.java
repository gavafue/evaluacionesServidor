package persistencia;

import logica.CompletarEspacio;
import logica.Evaluacion;
import logica.Evaluaciones;
import logica.Historial;
import logica.Historiales;
import logica.MultipleOpcion;
import logica.Pregunta;
import logica.Preguntas;
import logica.Usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * La clase Persistencia se encarga de la persistencia de datos en archivos
 * relacionados con usuarios, evaluaciones y historiales.
 */
public class Persistencia {

    /**
     * Persiste la lista de usuarios en un archivo de texto.
     *
     * @param hashUsuarios un HashMap que contiene los usuarios a persistir.
     */
    public void persistirListaDeUsuariosEnArchivo(HashMap<String, Usuario> hashUsuarios) {
        try (FileWriter fw = new FileWriter("passwords.txt")) {
            for (Map.Entry<String, Usuario> entry : hashUsuarios.entrySet()) {
                fw.write(entry.getKey() + ";" + entry.getValue().getContrasenia() + ";"
                        + entry.getValue().getTipoDeUsuario() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de usuarios: " + e.getMessage());
            e.printStackTrace(); // @todo: redirigir a un log
        }
    }

    /**
     * Persiste una lista de evaluaciones en un archivo de texto.
     *
     * @param listaEvaluaciones una lista de evaluaciones a persistir.
     */
    public void persistirEvaluacionesEnArchivo(List<Evaluacion> listaEvaluaciones) {
        List<String> titulosExistentes = new ArrayList<>();
        try {
            titulosExistentes = obtenerTitulosDeEvaluacionesDesdeArchivo();
        } catch (IOException e) {
            System.err.println("Error al obtener títulos de evaluaciones: " + e.getMessage());
        }

        try (FileWriter fw = new FileWriter("evaluaciones.txt", true)) {
            for (Evaluacion evaluacion : listaEvaluaciones) {
                if (!titulosExistentes.contains(evaluacion.getTitulo())) {
                    fw.write(evaluacion.getTitulo() + ";");
                    LinkedList<Pregunta> listaPreguntas = evaluacion.getListaPreguntas().getPreguntas();
                    for (Pregunta pregunta : listaPreguntas) {
                        fw.write(pregunta.getEnunciado() + ",,," + pregunta.getPuntaje() + ",,,");
                        if (pregunta instanceof CompletarEspacio) {
                            CompletarEspacio ce = (CompletarEspacio) pregunta;
                            fw.write("Completar,,," + String.join(",,,", ce.getRespuestasCorrectas()) + ";");
                        } else if (pregunta instanceof MultipleOpcion) {
                            MultipleOpcion mo = (MultipleOpcion) pregunta;
                            if (mo.getEsVerdaderoOFalso()) {
                                fw.write("VF,,," + mo.getRespuestaCorrecta() + ";");
                            } else {
                                fw.write("Multiple,,," + String.join(",,,", mo.getOpciones()) + ",,,"
                                        + mo.getRespuestaCorrecta() + ";");
                            }
                        }
                    }
                    fw.write(String.valueOf(evaluacion.isRespuestasValidas()));
                    fw.write(";");
                    fw.write(String.valueOf(evaluacion.getCantidadDePreguntas()));
                    fw.write("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de evaluaciones: " + e.getMessage());
            e.printStackTrace(); // @todo: redirigir a un log
        }
    }

    /**
     * Obtiene los títulos de evaluaciones desde un archivo de texto.
     *
     * @return una lista de títulos de evaluaciones.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    public List<String> obtenerTitulosDeEvaluacionesDesdeArchivo() throws IOException {
        List<String> resultados = new ArrayList<>();
        String archivoRuta = "evaluaciones.txt"; // Ruta del archivo a leer

        try (BufferedReader br = new BufferedReader(new FileReader(archivoRuta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length > 0) {
                    resultados.add(partes[0]);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo de evaluaciones: " + e.getMessage(), e);
        }

        return resultados;
    }

    /**
     * Carga las evaluaciones desde un archivo de texto.
     *
     * @return una instancia de Evaluaciones con los datos cargados.
     */
    public Evaluaciones cargarEvaluacionesDesdeArchivo() {
        Evaluaciones evaluaciones = new Evaluaciones();
        try (Scanner scanner = new Scanner(new File("evaluaciones.txt"))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                boolean respuestasValidas = false;
                String[] arregloDePreguntas = linea.split(";");
                Integer cantidadPreguntas = Integer.parseInt(arregloDePreguntas[arregloDePreguntas.length - 1]);

                String tituloEvaluacion = arregloDePreguntas[0].trim();
                Preguntas listaPreguntas = new Preguntas();

                for (int i = 1; i < arregloDePreguntas.length; i++) {
                    String[] datosPregunta = arregloDePreguntas[i].split(",,,");

                    if (datosPregunta.length < 4) {
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
                    Evaluacion evaluacion = new Evaluacion(tituloEvaluacion, listaPreguntas, respuestasValidas);
                    evaluacion.setCantidadDePreguntas(cantidadPreguntas);
                    Boolean permiteVerCorrectas = Boolean.valueOf(arregloDePreguntas[arregloDePreguntas.length - 2]);
                    evaluacion.setRespuestasValidas(permiteVerCorrectas);
                    evaluaciones.agregarEvaluacion(evaluacion);
                    listaPreguntas.listarPreguntas();
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
     * Carga los historiales desde un archivo de texto.
     *
     * @return una instancia de Historiales con los datos cargados.
     */
    public Historiales cargarHistorialesDesdeArchivo() {
        Historiales listaHistorial = new Historiales();
        try (Scanner s = new Scanner(new File("historiales.txt"))) {
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                String[] historial = linea.split(";");
                listaHistorial
                        .agregarHistorial(new Historial(historial[0], historial[1], Integer.parseInt(historial[2])));
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de historiales: " + e.getMessage());
        }
        return listaHistorial;
    }

    /**
     * Persiste el puntaje obtenido por cada estudiante al realizar una evaluación.
     *
     * @param listaHistorial una lista de historiales a persistir.
     */
    public void persistirHistorialesEnArchivo(LinkedList<Historial> listaHistorial) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historiales.txt"))) {
            for (Historial historial : listaHistorial) {
                writer.write(historial.getTituloEvaluacion() + ";" + historial.getCiEstudiante() + ";"
                        + historial.getPuntaje());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de historiales: " + e.getMessage());
            e.printStackTrace(); // @todo: redirigir a un log
        }
    }
}
