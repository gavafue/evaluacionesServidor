package logica;

import java.util.LinkedList;

/**
 * La clase {@code Preguntas} representa una colección de preguntas.
 * Permite gestionar una lista de preguntas de manera organizada.
 * 
 */
public class Preguntas {

    private LinkedList<Pregunta> preguntas; // Lista de preguntas

    /**
     * Constructor de la clase {@code Preguntas}.
     * Inicializa la lista de preguntas como una {@code LinkedList}.
     */
    public Preguntas() {
        this.preguntas = new LinkedList<Pregunta>();
    }

    /**
     * Obtiene la lista de preguntas.
     * 
     * @return Una {@code LinkedList} de {@code Pregunta}.
     */
    public LinkedList<Pregunta> getPreguntas() {
        return preguntas;
    }

    /**
     * Establece la lista de preguntas.
     * 
     * @param preguntas Una {@code LinkedList} de {@code Pregunta} a asignar.
     */
    public void setPreguntas(LinkedList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    /**
     * Agrega una pregunta a la lista de preguntas.
     * 
     * @param pregunta La {@code Pregunta} a agregar.
     */
    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    /**
     * Obtiene una pregunta de la lista dado su índice.
     * 
     * @param indice El índice de la pregunta en la lista.
     * @return La {@code Pregunta} en el índice especificado.
     */
    public Pregunta obtenerPregunta(int indice) {
        return preguntas.get(indice);
    }
}
