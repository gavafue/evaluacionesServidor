package logica;

import java.util.ArrayList;

/**
 * La clase Evaluacion representa una evaluación que contiene una lista de
 * preguntas
 * y sus respectivas configuraciones, como el título de la evaluación, la
 * cantidad de
 * preguntas y la validez de las respuestas.
 * 
 */
public class Evaluacion {

    private String titulo;
    private Preguntas listaPreguntas;
    private int cantidadDePreguntas;
    private boolean verRespuestasHabilitado; // Si permite o no al estudiante ver las respuestas válidas

    /**
     * 
     * Inicializa el título de la evaluación y crea una nueva lista de preguntas
     * vacía.
     * 
     * @param titulo El título de la evaluación.
     */
    public Evaluacion(String titulo) {
        this.titulo = titulo;
        this.listaPreguntas = new Preguntas();
    }

    /**
     * 
     * Inicializa el título de la evaluación y establece la lista de preguntas
     * proporcionada.
     * 
     * @param titulo         El título de la evaluación.
     * @param listaPreguntas La lista de preguntas que se incluirán en la
     *                       evaluación.
     */
    public Evaluacion(String titulo, Preguntas listaPreguntas) {
        this.titulo = titulo;
        this.listaPreguntas = listaPreguntas;
        this.verRespuestasHabilitado = false;
    }

    /**
     * 
     * Inicializa el título de la evaluación, establece la lista de preguntas y la
     * validez
     * de las respuestas.
     * 
     * @param titulo            El título de la evaluación.
     * @param listaPreguntas    La lista de preguntas que se incluirán en la
     *                          evaluación.
     * @param respuestasValidas Indica si las respuestas en la evaluación son
     *                          válidas.
     */
    public Evaluacion(String titulo, Preguntas listaPreguntas, boolean respuestasValidas) {
        this.titulo = titulo;
        this.listaPreguntas = listaPreguntas;
        this.verRespuestasHabilitado = respuestasValidas;
    }

    /**
     * Obtiene el título de la evaluación.
     * 
     * @return El título de la evaluación.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Obtiene la lista de preguntas asociadas a la evaluación.
     * 
     * @return La lista de preguntas.
     */
    public Preguntas getListaPreguntas() {
        return listaPreguntas;
    }

    /**
     * Obtiene la cantidad de preguntas en la evaluación.
     * 
     * @return La cantidad de preguntas.
     */
    public int getCantidadDePreguntas() {
        return cantidadDePreguntas;
    }

    /**
     * Indica si las respuestas en la evaluación son válidas.
     * 
     * @return true si las respuestas son válidas, false
     *         en caso contrario.
     */
    public boolean getVerRespuestasHabilitado() {
        return verRespuestasHabilitado;
    }

    // Setters

    /**
     * Establece la cantidad de preguntas en la evaluación.
     * 
     * @param cantidadDePreguntas La nueva cantidad de preguntas.
     */
    public void setCantidadDePreguntas(int cantidadDePreguntas) {
        this.cantidadDePreguntas = cantidadDePreguntas;
    }

    /**
     * Establece el título de la evaluación.
     * 
     * @param titulo El nuevo título de la evaluación.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Establece la lista de preguntas para la evaluación.
     * 
     * @param listaPreguntas La nueva lista de preguntas.
     */
    public void setListaPreguntas(Preguntas listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    /**
     * Establece si las respuestas en la evaluación son válidas.
     * 
     * @param respuestasValidas true si las respuestas son válidas,
     *                          false en caso contrario.
     */
    public void setVerRespuestasHabilitado(boolean respuestasValidas) {
        this.verRespuestasHabilitado = respuestasValidas;
    }

    /**
     * Obtiene una lista de enunciados de preguntas con sus respectivas respuestas
     * correctas.
     * 
     * Para preguntas del tipo CompletarEspacio, concatena las respuestas correctas
     * con un
     * asterisco (*) como separador. Para preguntas del tipo MultipleOpcion, indica
     * la opción
     * seleccionada junto con la descripción de la opción.
     * 
     * @return Una lista de cadenas en el formato
     *         "enunciado,,,respuesta,,,respuesta,,,respuesta".
     */
    public ArrayList<String> obtenerRespuestasCorrectas() {
        ArrayList<String> enunciadosConRespuestas = new ArrayList<>();
        for (Pregunta pregunta : this.getListaPreguntas().getPreguntas()) {
            String enunciado = pregunta.getEnunciado();
            String respuesta = "";
            if (pregunta instanceof CompletarEspacio completar) {
                String[] respuestas = completar.getRespuestasCorrectas();
                respuesta = String.join(",", respuestas);
            } else if (pregunta instanceof MultipleOpcion multiple) {
                if (multiple.getRespuestaCorrecta().matches("[1-4]")) {
                    int indice = Integer.parseInt(multiple.getRespuestaCorrecta()) - 1;
                    respuesta = "Opcion " + multiple.getRespuestaCorrecta() + ": " + multiple.getOpciones()[indice];
                } else { // VF
                    respuesta = multiple.getRespuestaCorrecta();
                }
            }
            enunciadosConRespuestas.add(enunciado + ",,," + respuesta);
        }
        return enunciadosConRespuestas;
    }
}
