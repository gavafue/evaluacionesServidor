package logica;

/**
 * La clase abstracta {@code Pregunta} representa una pregunta en un
 * cuestionario.
 * Puede ser de tipo verdadero o falso, opción múltiple o completar espacios en
 * blanco.
 * Esta clase no se puede instanciar directamente y debe ser extendida por otras
 * clases
 * que implementen los tipos específicos de preguntas.
 * 
 */
public abstract class Pregunta {
    
    private String enunciado; // Enunciado de la pregunta
    private int puntaje; // Puntaje asignado a la pregunta

    /**
     * Constructor de la clase {@code Pregunta}.
     * 
     * @param enunciado El enunciado de la pregunta.
     * @param puntaje   El puntaje asociado a la pregunta.
     */
    public Pregunta(String enunciado, int puntaje) {
        this.enunciado = enunciado;
        this.puntaje = puntaje;
    }

    /**
     * Obtiene el enunciado de la pregunta.
     * 
     * @return El enunciado de la pregunta.
     */
    public String getEnunciado() {
        return enunciado;
    }

    /**
     * Obtiene el puntaje asociado a la pregunta.
     * 
     * @return El puntaje de la pregunta.
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Establece el enunciado de la pregunta.
     * 
     * @param enunciado El nuevo enunciado de la pregunta.
     */
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    /**
     * Establece el puntaje asociado a la pregunta.
     * 
     * @param puntaje El nuevo puntaje de la pregunta.
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    // Métodos abstractos

    /**
     * Determina si la respuesta proporcionada es correcta. Este método debe ser
     * implementado por las clases que extienden {@code Pregunta}.
     * 
     * @param respuesta La respuesta proporcionada por el estudiante.
     * @return {@code true} si la respuesta es correcta, {@code false} en caso
     *         contrario.
     */
    public abstract boolean esCorrecta(String respuesta);

    /**
     * Obtiene el tipo de pregunta. Este método debe ser implementado por las
     * clases que extienden {@code Pregunta}.
     * 
     * @return Un {@code String} que representa el tipo de pregunta.
     */
    public abstract String obtenerTipo();
}
