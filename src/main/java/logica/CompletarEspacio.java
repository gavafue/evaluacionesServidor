package logica;

import java.util.Arrays;

/**
 * La clase CompletarEspacio representa un tipo de pregunta en la que el
 * estudiante
 * debe completar los espacios en blanco con las respuestas correctas. Esta
 * clase
 * permite manejar preguntas con múltiples espacios en blanco y sus respectivas
 * respuestas correctas.
 * 
 * 
 * 
 */
public class CompletarEspacio extends Pregunta {

    private String[] respuestasCorrectas;

    /**
     * Constructor de la clase CompletarEspacio.
     * 
     * Inicializa el enunciado de la pregunta, el puntaje asignado y las respuestas
     * correctas para la pregunta de completar espacios en blanco.
     * 
     * @param enunciado           El enunciado de la pregunta.
     * @param puntaje             El puntaje asignado a la pregunta.
     * @param respuestasCorrectas Un arreglo de cadenas que contiene las respuestas
     *                            correctas para la pregunta.
     */
    public CompletarEspacio(String enunciado, int puntaje, String[] respuestasCorrectas) {
        super(enunciado, puntaje);
        this.respuestasCorrectas = respuestasCorrectas;
    }

    /**
     * Obtiene las respuestas correctas para la pregunta.
     * 
     * @return Un arreglo de cadenas que contiene las respuestas correctas.
     */
    public String[] getRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    /**
     * Establece las respuestas correctas para la pregunta.
     * 
     * @param respuestasCorrectas Un arreglo de cadenas que contiene las respuestas
     *                            correctas a ser asignadas.
     */
    public void setRespuestasCorrectas(String[] respuestasCorrectas) {
        this.respuestasCorrectas = respuestasCorrectas;
    }

    /**
     * 
     * Compara las respuestas proporcionadas por el estudiante con las respuestas
     * correctas, verificando si todas las respuestas dadas son correctas.
     * 
     * @param respuesta Un String que contiene las respuestas del estudiante,
     *                  separadas por comas.
     * @return true si todas las respuestas dadas son correctas,
     *         false en caso contrario.
     */
    @Override
    public boolean esCorrecta(String respuesta) {
        String[] respuestasUsuario = respuesta.split(",");
        String[] respuestas = this.getRespuestasCorrectas();
        
        boolean todasCorrectas = true;
        if(respuestas[1] == null){ // Si no son dos respuestas
            if(!respuestasUsuario[0].equals(respuestas[0])||respuestasUsuario.length>1){ // Comparo solo primer campo y verifico que no haya dado una segunda respuesta
                todasCorrectas = false;
            }
        }else{
            if(!Arrays.equals(respuestasUsuario, respuestas)){
                todasCorrectas = false;
            }
        }
        return todasCorrectas;
    }


    /**
     * Obtiene el tipo de pregunta.
     * 
     * @return Un String que indica el tipo de pregunta, en este caso "Completar".
     */
    @Override
    public String obtenerTipo() {
        return "Completar";
    }
}
