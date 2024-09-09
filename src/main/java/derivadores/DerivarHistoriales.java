package derivadores;

import logica.Historial;
import logica.Historiales;
import java.util.LinkedList;

/**
 * La clase <code>DerivarHistoriales</code> gestiona operaciones relacionadas con los historiales
 * de evaluaciones de los estudiantes, como ver historiales específicos.
 * 
 * <p>Permite realizar diversas acciones en función de la operación solicitada.</p>
 * 
 * @author Gabriel
 */
public class DerivarHistoriales {

    private String operacion;
    private String mensaje;

    /**
     * Constructor que inicializa la operación y el mensaje.
     * 
     * @param operacion La operación a realizar (e.g., "Ver").
     * @param mensaje   El mensaje que contiene datos necesarios para la operación.
     */
    public DerivarHistoriales(String operacion, String mensaje) {
        this.operacion = operacion;
        this.mensaje = mensaje;
    }

    /**
     * Establece el mensaje.
     * 
     * @param mensaje El nuevo mensaje.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el mensaje.
     * 
     * @return El mensaje actual.
     */
    public String getMensaje() {
        return this.mensaje;
    }

    /**
     * Establece la operación.
     * 
     * @param operacion La nueva operación.
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    /**
     * Obtiene la operación.
     * 
     * @return La operación actual.
     */
    public String getOperacion() {
        return this.operacion;
    }

    /**
     * Método principal que dirige las operaciones relacionadas con los historiales.
     * 
     * <p>Dependiendo del valor de <code>operacion</code>, se realizan diferentes acciones.</p>
     * 
     * @return Una cadena con el resultado de la operación y el código de estado HTTP correspondiente.
     */
    public String derivarHistoriales() {
        Historiales hs = new Historiales();
        hs.actualizarHistoriales();
        
        switch (operacion) {
            case "Ver":
                return verHistorial();
            default:
                return "Operación desconocida,;,400";
        }
    }

    /**
     * Visualiza los historiales de un estudiante específico.
     * 
     * <p>Si existen historiales para el estudiante identificado por el <code>mensaje</code>,
     * devuelve una cadena con la información de cada historial (ci del estudiante y puntaje).</p>
     * 
     * @return Una cadena con los historiales del estudiante y el código de estado HTTP.
     */
    private String verHistorial() {
        String retorno = "";
        Historiales hs = new Historiales();
        
        // Actualiza la lista de historiales
        hs.actualizarHistoriales();
        
        // Verifica si existen historiales para el estudiante especificado en el mensaje
        if (hs.existeHistorial(mensaje)) {
            LinkedList<Historial> hls = hs.obtenerHistoriales(mensaje);
            
            // Itera sobre cada historial y construye la cadena de resultado
            for (int i = 0; i < hls.size(); i++) {
                Historial historial = hls.get(i);
                retorno += historial.getCiEstudiante() + ",,," + historial.getPuntaje() + ";;;";
            }
            
            // Elimina el último delimitador y añade el código de estado HTTP
            retorno = retorno.substring(0, retorno.length() - 3);
            retorno += ",;,200";
        } else {
            retorno = "NO existen historiales,;,500";
        }
        return retorno;
    }
}
