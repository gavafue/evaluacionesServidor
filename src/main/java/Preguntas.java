/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gabriel
 */
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Esta clase permite crear una lista de preguntas.
 */
public class Preguntas implements Serializable {

    // Atributos
    private LinkedList<Pregunta> preguntas;

    // Contructor comun
    public Preguntas() {
        this.preguntas = new LinkedList<Pregunta>();
    }

    // Getter
    public LinkedList<Pregunta> getPreguntas() {
        return preguntas;
    }

    // Setter
    public void setPreguntas(LinkedList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    /**
     * Metodo que dado una pregunta la agrega a la evaluacion.
     *
     * @param pregunta
     */
    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    /**
     * Merodo que permite obtener una pregunta dado su numero de indice.
     *
     * @param indice
     * @return pregunta
     */
    public Pregunta obtenerPregunta(int indice) {
        return preguntas.get(indice);
    }

    /**
     * Metodo que permite listar las preguntas de una evaluacion.
     */
    public void listarPreguntas() {
        for (int i = 0; i < preguntas.size(); i++) {
            this.obtenerPregunta(i).mostrarPregunta();
        }
    }
}
