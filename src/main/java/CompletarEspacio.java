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
    public boolean esCorrecta(String respuesta) { //Al comienzo las respuestas provienen en un unico String, separadas por coma.
        //Que sucede si alguna es correcta pero faltan?
        //en principio 0 puntos y que siga estudiando 
        boolean todasCorrectas = true;
        boolean encontrada;//Si se encuentra la respuesta del estudiante dentro de las posibles
        String[] respuestas = respuesta.split(",");
        //para pruebas
        System.out.println("COMPLETAR ESPACIOS:");
        System.out.println("RESPUESTAS ESPERADAS: "+Arrays.toString(respuestasCorrectas)+respuestasCorrectas.length);     
        System.out.println("RECIBIDAS:"+ Arrays.toString(respuestas)+respuestas.length);      
        //Comparo con for el arreglo de las respuestas del estudiante con el de las respuestas correctas                         
            for (String respuestaX : respuestas) {
                encontrada =false;
                for (String respuestaCorrecta : respuestasCorrectas) {
                    // Eliminar espacios al inicio y final, y comparar ignorando mayúsculas/minúsculas
                    if (respuestaCorrecta!=null){
                        if (respuestaX.trim().equalsIgnoreCase(respuestaCorrecta.trim())) {
                            encontrada = true;
                            break; // Salir del bucle si se encuentra la coincidencia
                        }
                    }
                }

                if (encontrada) {
                    System.out.println("La respuesta \"" + respuestaX.trim() + "\" es correcta.");
                } else {
                    System.out.println("La respuesta \"" + respuestaX.trim() + "\" es incorrecta.");
                    todasCorrectas = false; // Marcar que no todas son correctas
                }
            }
      //  } 
        
        return todasCorrectas; // Retorna si todas son correctas o no       
        
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
