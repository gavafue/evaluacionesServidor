/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gabriel
 */
import java.util.Arrays;

/**
 * Clase que permite crear el tipo de pregunta completar espacios en blaco. Este
 * tipo de pregunta admite multiples espacios en blanco con sus respectivas
 * respuestas.
 */
public class CompletarEspacio extends Pregunta {

    //Atributos
    private String[] respuestasCorrectas;

    /**
     * Constructor comun.
     * 
     * @param enunciado
     * @param puntaje
     * @param respuestasCorrectas
     */
    public CompletarEspacio(String enunciado, int puntaje, String[] respuestasCorrectas) {
        super(enunciado, puntaje);
        this.respuestasCorrectas = respuestasCorrectas;
    }

    //Getter
    public String[] getRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    //Setter
    public void setRespuestasCorrectas(String[] respuestasCorrectas) {
        this.respuestasCorrectas = respuestasCorrectas;
    }

    /**
     * Metodo que determina si las respuestas dadas por el estudiante son
     * correctas o no.
     *
     * @param respuesta
     * @return
     */
    @Override
    public boolean esCorrecta(String respuesta) { //Al comienzo las respuestas provienen en un unico String, separadas por espacios.
        String[] respuestas = respuesta.split(" ");
        return respuestasCorrectas == respuestas;
    }

    @Override
    public void mostrarPregunta() {
        System.out.println(getEnunciado() + " || [ Respuesta: ] " + Arrays.toString(respuestasCorrectas));
    }
    
    @Override
    public String obtenerTipo(){
        String tipo = "Completar";
        return tipo;
    }
}
