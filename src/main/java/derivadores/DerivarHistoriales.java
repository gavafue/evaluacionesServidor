package derivadores;

import java.util.LinkedList;

import logica.Historial;
import logica.Historiales;

/**
 * La clase DerivarHistoriales es responsable de gestionar las operaciones
 * relacionadas con los historiales de evaluaciones
 * de los estudiantes. Permite realizar diversas acciones dependiendo de la
 * operación solicitada.
 * 
 * Se utiliza para ver los historiales específicos de un estudiante y devolver
 * los resultados en función de las operaciones
 * que se realizan. Esta clase interactúa con otras clases como Historial y
 * Historiales para obtener y actualizar la información.
 * 
 */
public class DerivarHistoriales {

    private String operacion;
    private String mensaje;
    private Historiales historiales;

    /**
     * Constructor que inicializa la operación y el mensaje de la clase
     * DerivarHistoriales.
     * 
     * Este constructor acepta dos parámetros que permiten configurar la operación a
     * realizar
     * (como ver los historiales de los estudiantes) y el mensaje que contiene la
     * información
     * relevante para dicha operación.
     * 
     * @param operacion Indica la operación que se va a realizar, por ejemplo,
     *                  "Ver".
     * @param mensaje   Define el mensaje que contiene los datos necesarios para la
     *                  operación,
     *                  como el número de cédula del estudiante.
     */
    public DerivarHistoriales(String operacion, String mensaje) {
        this.operacion = operacion;
        this.mensaje = mensaje;
        this.historiales = new Historiales();
        this.historiales.actualizarHistoriales();
    }

    /**
     * Método que permite establecer un nuevo mensaje en la instancia de la clase.
     * 
     * @param mensaje Nuevo valor para el mensaje que puede contener, por ejemplo,
     *                el número
     *                de identificación de un estudiante.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Método que permite obtener el mensaje actual almacenado en la instancia de la
     * clase.
     * 
     * 
     * 
     * @return El valor actual del mensaje.
     */
    public String getMensaje() {
        return this.mensaje;
    }

    /**
     * Método que permite establecer una nueva operación en la instancia de la
     * clase.
     * 
     * 
     * 
     * @param operacion Nuevo valor para la operación, como "Ver".
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    /**
     * Método que permite obtener la operación actual almacenada en la instancia de
     * la clase.
     * 
     * 
     * 
     * @return El valor actual de la operación.
     */
    public String getOperacion() {
        return this.operacion;
    }
    
     /**
     * Método que permite establecer la lista de historiales.
     * 
     * 
     * 
     * @param historiales La lista de historiales nueva.
     */
    public void setHistoriales(Historiales historiales){
        this.historiales = historiales;
    }
    
    /**
     * Método que permite obtener los historiales de estudiantes.
     * 
     * 
     * 
     * @return La lista de historiales.
     */
    public Historiales getHistoriales(){
        return historiales;
    }

    /**
     * Método principal que dirige la ejecución de las operaciones relacionadas con
     * los historiales.
     * 
     * Dependiendo del valor de la operación establecida, este método llama al
     * correspondiente
     * método para realizar la acción solicitada. En el caso de que la operación no
     * sea reconocida,
     * devuelve un mensaje de error.
     * 
     * @return El resultado de la operación en forma de cadena, junto con un código
     *         de estado
     *         HTTP que indica si la operación fue exitosa o no.
     */
    public String derivarHistoriales() {
        switch (this.getOperacion()) {
            case "Ver":
                return verHistorial();
            default:
                return "Operación desconocida,;,400";
        }
    }

    /**
     *
     *
     * Este método busca los historiales del estudiante identificado por el
     * valor del mensaje. Si existen historiales para el estudiante, devuelve
     * una cadena con la información sobre los historiales, incluyendo el número
     * de identificación del estudiante y su puntaje. Si no existen historiales,
     * devuelve un mensaje de error.
     *
     * @return Una cadena con los historiales del estudiante, incluyendo su
     * número de identificación y puntaje, o un mensaje de error si no existen
     * historiales. El formato de la cadena incluye también un código de estado
     * HTTP que indica el éxito o fallo de la operación.
     */
    private String verHistorial() {
        String retorno = "";
        if (this.getHistoriales().existeHistorial(mensaje)) {
            LinkedList<Historial> hls = this.getHistoriales().obtenerHistoriales(mensaje);
            for (int i = 0; i < hls.size(); i++) {
                Historial historial = hls.get(i);
                retorno += historial.getCiEstudiante() + ",,," + historial.getPuntaje() + ";;;";
            }
            retorno = retorno.substring(0, retorno.length() - 3); // Eliminar los últimos ",,," sobrantes.
            retorno += ",;,200";
        } else {
            retorno = "NO existen historiales,;,500";
        }
        return retorno;
    }
}
