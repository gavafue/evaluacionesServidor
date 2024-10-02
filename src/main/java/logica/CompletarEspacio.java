package logica;

import java.util.Arrays;

/**
 * La clase CompletarEspacio representa un tipo de pregunta en la que el
 * estudiante debe completar los espacios en blanco con las respuestas
 * correctas. Esta clase permite manejar preguntas con múltiples espacios en
 * blanco y sus respectivas respuestas correctas.
 *
 *
 *
 */
public class CompletarEspacio extends Pregunta {

    private String[] respuestasCorrectas;

    /**
     * Constructor de la clase CompletarEspacio.
     *
     * Inicializa el enunciado de la pregunta, el puntaje asignado y las
     * respuestas correctas para la pregunta de completar espacios en blanco.
     *
     * @param enunciado El enunciado de la pregunta.
     * @param puntaje El puntaje asignado a la pregunta.
     * @param respuestasCorrectas Un arreglo de cadenas que contiene las
     * respuestas correctas para la pregunta.
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
     * @param respuestasCorrectas Un arreglo de cadenas que contiene las
     * respuestas correctas a ser asignadas.
     */
    public void setRespuestasCorrectas(String[] respuestasCorrectas) {
        this.respuestasCorrectas = respuestasCorrectas;
    }

    /**
     *
     * Compara las respuestas proporcionadas por el estudiante con las
     * respuestas correctas, verificando si todas las respuestas dadas son
     * correctas.
     *
     * @param respuesta Un String que contiene las respuestas del estudiante,
     * separadas por comas.
     * @return true si todas las respuestas dadas son correctas, false en caso
     * contrario.
     */
    @Override
    public boolean esCorrecta(String respuesta) {
        // Inicializamos una variable para almacenar el resultado
        boolean todasCorrectas = true;

        // Dividimos la respuesta del usuario en un arreglo y eliminamos espacios
        String[] respuestasUsuario = respuesta.split(",");
        for (int i = 0; i < respuestasUsuario.length; i++) {
            respuestasUsuario[i] = respuestasUsuario[i].trim().toLowerCase(); // Convertir a minúsculas
        }

        // Obtenemos las respuestas correctas y las procesamos
        String[] respuestasCorrectas = this.getRespuestasCorrectas();
        String valorPorDefecto = "xF_45&3"; // Valor por defecto

        // Aseguramos que las respuestas correctas no tengan espacios en blanco
        for (int i = 0; i < respuestasCorrectas.length; i++) {
            respuestasCorrectas[i] = respuestasCorrectas[i].trim().toLowerCase(); // Convertir a minúsculas
        }

        // Si solo hay una respuesta correcta, añadimos el valor por defecto como la segunda respuesta
        if (respuestasCorrectas.length == 1) {
            respuestasCorrectas = new String[]{respuestasCorrectas[0], valorPorDefecto};
        }

        // Verificamos si el número de respuestas proporcionadas es válido (1 o 2)
        if (respuestasUsuario.length < 1 || respuestasUsuario.length > 2) {
            todasCorrectas = false; // Si hay más de dos respuestas o ninguna, es incorrecto
        } else {
            // Caso donde el usuario dio una sola respuesta pero se esperan dos respuestas
            if (respuestasCorrectas.length == 2 && respuestasUsuario.length == 1) {
                todasCorrectas = respuestasUsuario[0].equals(respuestasCorrectas[0]);
            } // Caso donde el usuario dio dos respuestas y se esperan dos respuestas
            else if (respuestasCorrectas.length == 2 && respuestasUsuario.length == 2) {
                if (!respuestasUsuario[0].equals(respuestasCorrectas[0]) || !respuestasUsuario[1].equals(respuestasCorrectas[1])) {
                    todasCorrectas = false; // Si alguna de las dos respuestas no coincide, es incorrecto
                }
            }
        }

        // Devolvemos el resultado final
        return todasCorrectas;
    }

    /**
     * Obtiene el tipo de pregunta.
     *
     * @return Un String que indica el tipo de pregunta, en este caso
     * "Completar".
     */
    @Override
    public String obtenerTipo() {
        return "Completar";
    }
}
