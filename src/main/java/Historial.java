
/**
 * Esta clase permite guardar el puntaje obtenido por un estudiante al realizar
 * una evaluacion, junto a su CI.
 */
public class Historial {

    // Atributos
    private String tituloEvaluacion; // Evaluacion a la que corresponde el historial
    private String ciEstudiante;

    private int puntaje;

    // Constructor comun
    public Historial(String tituloEvaluacion, String ciEstudiante, int puntaje) {
        this.tituloEvaluacion = tituloEvaluacion;
        this.ciEstudiante = ciEstudiante;
        this.puntaje = puntaje;

    }

    // Getters
    public String getTituloEvaluacion() {
        return tituloEvaluacion;
    }

    public String getCiEstudiante() {
        return ciEstudiante;
    }

    public int getPuntaje() {
        return puntaje;
    }

    // Setters
    public void setTituloEvaluacion(String tituloEvaluacion) {
        this.tituloEvaluacion = tituloEvaluacion;
    }

    public void setCiEstudiante(String ciEstudiante) {
        this.ciEstudiante = ciEstudiante;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
