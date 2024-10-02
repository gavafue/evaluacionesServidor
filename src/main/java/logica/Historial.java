package logica;

/**
 * La clase Historial representa el registro del puntaje obtenido por un
 * estudiante
 * al realizar una evaluación, junto con: el titulo de la evaluación y su número
 * de cédula de identidad (CI).
 * 
 * 
 */
public class Historial {

    /**
     *  Evaluación a la que corresponde el historial
     */
    private String tituloEvaluacion; 
    
    /**
     * Númro de cédula de identidad del estudiante
     */
    private String ciEstudiante;
    
    /**
     * Puntaje obtenido por el estudiante
     */
    private int puntaje;
    
    /**
     * Respuestas dadas por el estudiante
     */
    private String[] respuestas; 

    /**
     * Constructor de la clase Historial.
     * 
     * @param tituloEvaluacion El título de la evaluación realizada por el
     *                         estudiante.
     * @param ciEstudiante     El número de cédula de identidad del estudiante.
     * @param puntaje          El puntaje obtenido por el estudiante en la
     *                         evaluación.
     * @param respuestas       Respuestas dadas por el estudiante.
     */
    public Historial(String tituloEvaluacion, String ciEstudiante, int puntaje, String[] respuestas) {
        this.tituloEvaluacion = tituloEvaluacion;
        this.ciEstudiante = ciEstudiante;
        this.puntaje = puntaje;
        this.respuestas = respuestas;
    }

    /**
     * Obtiene el título de la evaluación.
     * 
     * @return El título de la evaluación.
     */
    public String getTituloEvaluacion() {
        return tituloEvaluacion;
    }

    /**
     * Obtiene el número de cédula de identidad del estudiante.
     * 
     * @return El número de cédula de identidad del estudiante.
     */
    public String getCiEstudiante() {
        return ciEstudiante;
    }

    /**
     * Obtiene el puntaje obtenido por el estudiante.
     * 
     * @return El puntaje obtenido en la evaluación.
     */
    public int getPuntaje() {
        return puntaje;
    }
    
    /**
     * Obtiene las respuestas dadas por el estudiante.
     * 
     * @return Las respuestas del estudiante.
     */
    public String[] getRespuestas(){
        return respuestas;
    }

    // Setters

    /**
     * Establece el título de la evaluación.
     * 
     * @param tituloEvaluacion El nuevo título de la evaluación.
     */
    public void setTituloEvaluacion(String tituloEvaluacion) {
        this.tituloEvaluacion = tituloEvaluacion;
    }

    /**
     * Establece el número de cédula de identidad del estudiante.
     * 
     * @param ciEstudiante El nuevo número de cédula de identidad del estudiante.
     */
    public void setCiEstudiante(String ciEstudiante) {
        this.ciEstudiante = ciEstudiante;
    }

    /**
     * Establece el puntaje obtenido por el estudiante.
     * 
     * @param puntaje El nuevo puntaje obtenido en la evaluación.
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    /**
     * Establece las respuestas dadas por el estudiante.
     * 
     * @param respuestas Respuestas dadas por el estudiante.
     */
    public void setRespuestas(String[] respuestas){
        this.respuestas = respuestas;
    }
}
