package conexion;


import derivadores.DerivarUsuarios;
import derivadores.DerivarHistoriales;
import derivadores.DerivarEvaluaciones;
import logica.Evaluaciones;

/**
 * Clase para procesar mensajes y derivar operaciones según el contenido del
 * mensaje, la clase de destino y la operación especificada. Se espera que la
 * consulta del cliente (el mensaje) tenga la estructura:
 * [contenidoMensaje,;,ClaseDestino,;,Operacion].
 *
 * Esta clase incluye métodos para validar los mensajes y derivar operaciones.
 *
 * La respuesta del servidor puede incluir uno de los siguientes codigos:
 * <ul>
 * <li>200: consulta con exito.</li>
 * <li>400: error en la consulta realizada por el cliente.</li>
 * <li>500: error en el servidor.</li>
 * </ul>
 * La respuesta del servidor tiene la estructura: [respuesta,;,codigo].
 *
 *
 *
 */
public class Switch {

    private String mensaje; // Contenido del msj
    private String claseDestino; // Clase de destino a la cual se debe derivar la operación
    private String operacion; // Operación que se debe realizar
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
     * Establece el contenido del msj.
     *
     * @param mensaje El nuevo contenido del msj.
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
     * Obtiene el contenido del msj.
     *
     * @return El contenido del msj.
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
     * Valida si el contenido del msj no está vacío.
     *
     * @return true si el msj no está vacío, false de lo contrario.
     */
    public Boolean validarMensaje() {
        Boolean valido = false;
        if (!this.getMensaje().isBlank()) { // Verifica que el msj no esté vacío ni sean solo espacios en blanco.
            valido = true;
        }
        return valido;
    }

    /**
     * Valida si la clase de destino no está vacía.
     *
     * @return true si la clase de destino no está vacía, false de lo contrario.
     */
    public Boolean validarClaseFinal() {
        Boolean valido = false;
        if (!this.getClaseDestino().isBlank()) { // Verifica que la clase no esté vacía ni sean solo espacios en blanco.
            valido = true;
        }
        return valido;
    }

    /**
     * Valida si la operación no está vacía.
     *
     * @return true si la operación no está vacía, false de lo contrario.
     */
    public Boolean validarOperacion() {
        Boolean valido = false;
        if (!this.getOperacion().isBlank()) { // Verifica que la operacion no esté vacía ni sean solo espacios en
            // blanco.
            valido = true;
        }
        return valido;
    }

    /**
     * Deriva la operación a la clase correspondiente según la clase de destino.
     *
     * @return El resultado de la derivación.
     */
    public String derivadorDeClases() {
        // Obtiene el nombre de la clase de destino
        String claseDestino = this.getClaseDestino();
        String retorno = "";

        // Verificación de que claseDestino no es nulo o vacío
        if (claseDestino == null || claseDestino.isBlank()) {
            // ############################################
            retorno = "Error: claseDestino no puede estar vacío.,;,400";
            return retorno;
        }

        try {
            // Selección de la clase de destino
            switch (claseDestino) {
                case "Usuarios":
                    DerivarUsuarios derivadorUsuarios = new DerivarUsuarios(operacion, mensaje);
                    retorno = derivadorUsuarios.derivarUsuarios();
                    break;
                case "Evaluaciones":
                    DerivarEvaluaciones derivadorEvaluaciones = new DerivarEvaluaciones(operacion, mensaje);
                    retorno = derivadorEvaluaciones.derivarEvaluaciones();
                    break;
                case "Historiales":
                    DerivarHistoriales derivadorHistoriales = new DerivarHistoriales(operacion, mensaje);
                    retorno = derivadorHistoriales.derivarHistoriales();
                    break;

                case "Prueba":
                    if (operacion.equals("Conexion")) {
                        retorno = "# Pureba de conexión: ok.,;,200";

                    }
                    break;
                default:
                    // Error para clase de destino desconocida
                    retorno = "Error: claseDestino [" + claseDestino + "] desconocido.,;,400";
                    // System.err.println(retorno);
                    break;
            }
        } catch (Exception e) {
            // Manejo de excepciones durante la derivación
            retorno = "Error al derivar a " + claseDestino + ": " + e.getMessage() + ",;,500";
            // System.err.println(retorno);
        }

        // Devuelve el resultado de la derivación
        return retorno;
    }

}