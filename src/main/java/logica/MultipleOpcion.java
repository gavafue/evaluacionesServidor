package logica;

/**
 * La clase MultipleOpcion extiende la clase {@link Pregunta} y representa una
 * pregunta
 * de opción múltiple, que puede ser del tipo verdadero o falso o con múltiples
 * opciones.
 * 
 */
public class MultipleOpcion extends Pregunta {

    // Atributos
    private String[] opciones; // Opciones disponibles para la pregunta
    private boolean esVerdaderoOFalso; // Indica si la pregunta es de tipo verdadero o falso
    private String respuestaCorrecta; // Respuesta correcta para la pregunta

    /**
     * Constructor de la clase MultipleOpcion.
     * 
     * @param enunciado         El enunciado de la pregunta.
     * @param puntaje           El puntaje asociado a la pregunta.
     * @param opciones          Las opciones disponibles para la pregunta.
     * @param esVerdaderoOFalso Indica si la pregunta es de tipo verdadero o falso.
     * @param respuestaCorrecta La respuesta correcta para la pregunta.
     */
    public MultipleOpcion(String enunciado, int puntaje, String[] opciones, boolean esVerdaderoOFalso,
            String respuestaCorrecta) {
        super(enunciado, puntaje);
        this.opciones = opciones;
        this.esVerdaderoOFalso = esVerdaderoOFalso;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    // Getters

    /**
     * Obtiene las opciones disponibles para la pregunta.
     * 
     * @return Las opciones de la pregunta.
     */
    public String[] getOpciones() {
        return opciones;
    }

    /**
     * Determina si la pregunta es de tipo verdadero o falso.
     * 
     * @return True si la pregunta es de tipo verdadero o falso, false si es de
     *         opción múltiple.
     */
    public boolean getEsVerdaderoOFalso() {
        return esVerdaderoOFalso;
    }

    /**
     * Obtiene la respuesta correcta para la pregunta.
     * 
     * @return La respuesta correcta.
     */
    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    // Setters

    /**
     * Establece las opciones disponibles para la pregunta.
     * 
     * @param opciones Las nuevas opciones.
     */
    public void setOpciones(String[] opciones) {
        this.opciones = opciones;
    }

    /**
     * Establece si la pregunta es de tipo verdadero o falso.
     * 
     * @param esVerdaderoOFalso True si la pregunta es de tipo verdadero o falso,
     *                          false si es de opción múltiple.
     */
    public void setEsVerdaderoOFalso(boolean esVerdaderoOFalso) {
        this.esVerdaderoOFalso = esVerdaderoOFalso;
    }

    /**
     * Establece la respuesta correcta para la pregunta.
     * 
     * @param respuestaCorrecta La nueva respuesta correcta.
     */
    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    /**
     * Determina si la respuesta dada por el estudiante es correcta.
     * 
     * @param respuesta La respuesta dada por el estudiante.
     * @return True si la respuesta es correcta, false en caso contrario.
     */
    @Override
    public boolean esCorrecta(String respuesta) {
        return respuesta.equals(respuestaCorrecta);
    }

    /**
     * Obtiene el tipo de pregunta.
     * 
     * @return "VF" si la pregunta es de tipo verdadero o falso, "Multiple" si es de
     *         opción múltiple.
     */
    @Override
    public String obtenerTipo() {
        return esVerdaderoOFalso ? "VF" : "Multiple";
    }
}
