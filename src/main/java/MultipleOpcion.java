/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gabriel
 */
/**
 * Esta clase permite crear el tipo de pregunta multiple opcion y verdadero o
 * falso.
 */
public class MultipleOpcion extends Pregunta {

    //Atributos
    private String[] opciones;
    /**
     * Es verdadero o falso cuando la cantidad de opciones es dos.
     */
    private boolean esVerdaderoOFalso;
    /**
     * Este tipo de pregunta admite una unica respuesta correcta.
     */
    private String respuestaCorrecta;

    //Constructor comun
    public MultipleOpcion(String enunciado, int puntaje, String[] opciones, boolean esVerdaderoOFalso, String respuestaCorrecta) {
        super(enunciado, puntaje);
        this.opciones = opciones;
        this.esVerdaderoOFalso = esVerdaderoOFalso;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    //Getters
    public String[] getOpciones() {
        return opciones;
    }

    public boolean getEsVerdaderoOFalso() {
        return esVerdaderoOFalso;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    //Setters
    public void setOpciones(String[] opciones) {
        this.opciones = opciones;
    }

    public void setEsVerdaderoOFalso(boolean esVerdaderoOFalso) {
        this.esVerdaderoOFalso = esVerdaderoOFalso;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    /**
     * Metodo que determina si la repuesta dada por el estudiante es correcta o
     * no.
     *
     * @param respuesta
     * @return
     */
    @Override
    public boolean esCorrecta(String respuesta) {
        return (respuesta.equals(respuestaCorrecta));
    }

    @Override
    //Probablemente no interese ver la pregunta en pantalla de esta manera
    public void mostrarPregunta() {
        System.out.println(getEnunciado());
        String[] opciones = getOpciones();
        for (int i = 0; i < opciones.length; i++) {
            System.out.println("  " + (i + 1) + ". " + opciones[i]);
        }
    }
}
