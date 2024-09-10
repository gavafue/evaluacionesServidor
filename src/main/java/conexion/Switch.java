package conexion;

import derivadores.DerivarUsuarios;
import derivadores.DerivarHistoriales;
import derivadores.DerivarEvaluaciones;
import logica.Evaluaciones;

/**
 * Clase para procesar mensajes y derivar operaciones según el contenido del
 * mensaje, la clase de destino y la operación especificada.
 * Se espera que la consulta del cliente tenga la estructura
 * [contenidoMensaje,;,ClaseDestino,;,Operacion].
 * La respuesta del servidor puede incluir códigos como 200 (éxito), 400 (error
 * en la consulta), o 500 (error en el servidor).
 * La respuesta tiene la estructura [respuesta,;,codigo].
 */
public class Switch {

    private String mensaje; // Contenido del mensaje.
    private String claseDestino; // Clase de destino a la cual se debe derivar la operación.
    private String operacion; // Operación que se debe realizar.
    Evaluaciones es;

    /**
     * Constructor para inicializar la clase Switch con los parámetros
     * proporcionados.
     * 
     * @param mensaje      El contenido del mensaje.
     * @param claseDestino La clase de destino.
     * @param operacion    La operación a realizar.
     */
    public Switch(String mensaje, String claseDestino, String operacion) {
        this.mensaje = mensaje;
        this.claseDestino = claseDestino;
        this.operacion = operacion;
        this.es = new Evaluaciones();
    }

    /**
     * Obtiene la operación.
     * 
     * @return La operación.
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * Establece el contenido del mensaje.
     * 
     * @param mensaje El nuevo contenido del mensaje.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
     * Establece la clase de destino.
     * 
     * @param claseDestino La nueva clase de destino.
     */
    public void setClaseDestino(String claseDestino) {
        this.claseDestino = claseDestino;
    }

    /**
     * Obtiene el contenido del mensaje.
     * 
     * @return El contenido del mensaje.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Obtiene la clase de destino.
     * 
     * @return La clase de destino.
     */
    public String getClaseDestino() {
        return claseDestino;
    }

    /**
     * Valida si el contenido del mensaje no está vacío.
     * 
     * @return true si el mensaje no está vacío, false de lo contrario.
     */
    public Boolean validarMensaje() {
        return !this.getMensaje().isBlank(); // Verifica que el mensaje no esté vacío o sean solo espacios en blanco.
    }

    /**
     * Valida si la clase de destino no está vacía.
     * 
     * @return true si la clase de destino no está vacía, false de lo contrario.
     */
    public Boolean validarClaseFinal() {
        return !this.getClaseDestino().isBlank(); // Verifica que la clase no esté vacía ni sean solo espacios en
                                                  // blanco.
    }

    /**
     * Valida si la operación no está vacía.
     * 
     * @return true si la operación no está vacía, false de lo contrario.
     */
    public Boolean validarOperacion() {
        return !this.getOperacion().isBlank(); // Verifica que la operación no esté vacía ni sean solo espacios en
                                               // blanco.
    }

    /**
     * Deriva la operación a la clase correspondiente según la clase de destino.
     * 
     * @return El resultado de la derivación.
     */
    public String derivadorDeClases() {
        String claseDestino = this.getClaseDestino(); // Obtiene el nombre de la clase de destino.
        String retorno = ""; // Almacena la respuesta de la operación derivada.

        if (claseDestino == null || claseDestino.isBlank()) {
            return "Error: claseDestino no puede estar vacío.,;,400"; // Retorna un error si la clase de destino es nula
                                                                      // o vacía.
        }

        try {
            switch (claseDestino) {
                case "Usuarios":
                    DerivarUsuarios derivadorUsuarios = new DerivarUsuarios(operacion, mensaje);
                    retorno = derivadorUsuarios.derivarUsuarios(); // Deriva la operación a la clase DerivarUsuarios.
                    break;
                case "Evaluaciones":
                    DerivarEvaluaciones derivadorEvaluaciones = new DerivarEvaluaciones(operacion, mensaje);
                    retorno = derivadorEvaluaciones.derivarEvaluaciones(); // Deriva la operación a la clase
                                                                           // DerivarEvaluaciones.
                    break;
                case "Historiales":
                    DerivarHistoriales derivadorHistoriales = new DerivarHistoriales(operacion, mensaje);
                    retorno = derivadorHistoriales.derivarHistoriales(); // Deriva la operación a la clase
                                                                         // DerivarHistoriales.
                    break;
                case "Prueba":
                    if (operacion.equals("Conexion")) {
                        retorno = "# Prueba de conexión: ok.,;,200"; // Retorna éxito en la prueba de conexión.
                    }
                    break;
                default:
                    retorno = "Error: claseDestino [" + claseDestino + "] desconocido.,;,400"; // Retorna un error si la
                                                                                               // clase de destino no es
                                                                                               // válida.
                    break;
            }
        } catch (Exception e) {
            retorno = "Error al derivar a " + claseDestino + ": " + e.getMessage() + ",;,500"; // Maneja errores durante
                                                                                               // la derivación.
        }

        return retorno; // Devuelve el resultado de la derivación.
    }
}
