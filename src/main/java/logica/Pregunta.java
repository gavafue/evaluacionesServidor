package logica;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gabriel
 */
import java.io.Serializable;

/**
 * Esta clase permite crear una pregunta, la cual puede ser de tipo verdadero o
 * falso, multiple opcion o completar espacios en blanco. Esta clase por si sola
 * no es instanciable.
 */
public abstract class Pregunta implements Serializable {

    //Atributos
    private String enunciado;
    private int puntaje;

    //Constructor comun
    public Pregunta(String enunciado, int puntaje) {
        this.enunciado = enunciado;
        this.puntaje = puntaje;
    }

    //Getters
    public String getEnunciado() {
        return enunciado;
    }

    public int getPuntaje() {
        return puntaje;
    }

    //Setters
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    //Metodos abstractos
    public abstract boolean esCorrecta(String respuesta);

    public abstract void mostrarPregunta();
    
    public abstract String obtenerTipo();
}
