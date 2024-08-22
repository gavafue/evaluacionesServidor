/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Switch para procesar mensajes y derivar operaciones a diferentes clases
 * según el contenido del mensaje, la clase de destino y la operación
 * especificada. Se espera que el mensaje tenga la estructura:
 * [contenidoMensaje,;,ClaseDestino,;,Operacion].
 *
 * Esta clase incluye métodos para validar los mensajes y derivar operaciones.
 *
 * Autor: Gabriel
 */
public class Switch {

    private String mensaje; // Contenido del msj
    private String claseDestino; // Clase de destino a la cual se debe derivar la operación
    private String operacion; // Operación que se debe realizar

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
        if (!this.getMensaje().isEmpty()) { // Verifica que el msj no esté vacío
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
        if (!this.getClaseDestino().isEmpty()) { // Verifica que la clase de destino no esté vacía
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
        if (!this.getOperacion().isEmpty()) { // Verifica que la operación no esté vacía
            valido = true;
        }
        return valido;
    }

    /**
     * Deriva la operación a la clase correspondiente según el contenido del
 msj, la clase de destino y la operación.
     *
     * @return El resultado de la derivación.
     */
    public String derivadorDeClases() {
        // Obtiene el nombre de la clase de destino
        String claseDestino = this.getClaseDestino();
        String retorno = "";

        // Verificación de que claseDestino no es nulo o vacío
        if (claseDestino == null || claseDestino.isEmpty()) { // PODRIA SER .isBlank para contemplar espacios vacios.
                                                              // ############################################
            retorno = "Error: claseDestino no puede estar vacío.";
            // Imprime el error en la consola
            System.err.println(retorno);
            return retorno;
        }

        try {
            // Selección de la clase de destino
            switch (claseDestino) {
                case "Usuarios":
                    retorno = derivarUsuarios();
                    break;
                case "Evaluaciones":
                    retorno = derivarEvaluaciones(operacion);
                    break;
                case "Historiales":
                    retorno = derivarHistoriales(operacion);
                    break;

                case "Prueba":
                    if (operacion.equals("Conexion")) {
                        retorno = "# Pureba de conexión: ok.,;,200 #";

                    }
                    break;
                default:
                    // Error para clase de destino desconocida
                    retorno = "Error: claseDestino [" + operacion + "] desconocido.,;,404";
                    System.err.println(retorno);
                    break;
            }
        } catch (Exception e) {
            // Manejo de excepciones durante la derivación
            retorno = "Error al derivar a " + claseDestino + ": " + e.getMessage(); // ESTO HAY QUE PASARLO A CODIGO 500
            System.err.println(retorno);
        }

        // Devuelve el resultado de la derivación
        return retorno;
    }

    public String derivarUsuarios() {
        String operacion = this.getOperacion();
        String retorno = "";
        switch (operacion) {
            case "Alta":
                retorno = derivarCrearUsuario();
                break;
            case "Login":
                retorno = derivarLogin();
                break;
            default:
                // Error para operación desconocida
                retorno = "Error: Operación desconocida.,;,404";
                break;
        }
        return retorno;

    }

    /**
     * Deriva la operación de login para los usuarios.
     *
     * @return El resultado de la operación de login.
     */
    public String derivarLogin() {
        String retorno = null;
        try {
            String msj = this.getMensaje();

            // Validar que el msj no esté vacío
            if (msj == null || msj.isEmpty()) {
                retorno = "Mensaje vacío,;,400";
                return retorno;
            }

            String[] tokens = mensaje.split(";;;"); // Divide el mensaje en tokens usando ";;;" como delimitador --Juan:
                                                    // ¿No debería ser ",;,"?

            // Validar que el msj contenga los dos tokens necesarios
            if (tokens.length != 2) {
                retorno = "Formato de mensaje incorrecto,;,400";
                return retorno;
            }

            String usuario = tokens[0];
            String contrasenia = tokens[1];

            // Validar que el usuario y la contraseña no estén vacíos
            if (usuario.isEmpty() || contrasenia.isEmpty()) {
                retorno = "Usuario y/o contraseña vacíos,;,400";
                return retorno;
            }

            Usuarios listaUsuarios = new Usuarios();
            listaUsuarios.cargarUsuarios(); // Carga la lista de usuarios desde una fuente de datos

            // Validar la existencia del usuario y la contraseña
            if (listaUsuarios.existeUsuarioLogin(usuario, contrasenia)) {
                String tipoDeUsuario = listaUsuarios.obtenerUsuario(usuario).getTipoDeUsuario().trim();
                retorno = tipoDeUsuario + ",;,200";
            } else {
                retorno = "Usuario y/o contraseña incorrectos,;,404";
            }
        } catch (Exception e) {
            // Manejar cualquier excepción no controlada
            retorno = "Error del servidor: " + e.getMessage() + ",;,500";
        }
        return retorno;
    }

    /**
     * Método para derivar la creación de un usuario.
     *
     * @return Una cadena con el resultado de la operación y el código de estado
     *         HTTP correspondiente.
     */
    public String derivarCrearUsuario() {
        String retorno = "";
        try {
            String mensaje = this.getMensaje();

            // Validar que el msj no esté vacío
            if (mensaje == null || mensaje.isEmpty()) {
                retorno = "Mensaje vacío,;,400";
                return retorno;
            }

            // Divide el msj en tokens usando ";;;" como delimitador
            String[] tokens = mensaje.split(";;;");

            // Validar que el msj contenga los dos tokens necesarios
            if (tokens.length != 2) {
                retorno = "Formato de mensaje incorrecto,;,400";
                return retorno;
            }

            String usuario = tokens[0];
            String contrasenia = tokens[1];

            // Validar que el usuario y la contraseña no estén vacíos
            if (usuario.isEmpty() || contrasenia.isEmpty()) {
                retorno = "Usuario y/o contraseña vacíos,;,400";
                return retorno;
            }

            Usuarios listaUsuarios = new Usuarios();// Carga la lista de usuarios desde un archivo

            // Validar la existencia del usuario
            if (listaUsuarios.existeUsuario(usuario)) {
                String tipoDeUsuario = listaUsuarios.obtenerUsuario(usuario).getTipoDeUsuario().trim();
                retorno = "El documento ya tiene un usuario registrado de tipo " + tipoDeUsuario + ",;,409";
            } else {
                // Intentar agregar un nuevo usuario
                Usuario nuevoUsuario = new Usuario(usuario, contrasenia, "estudiante");
                listaUsuarios.agregarUsuario(nuevoUsuario);
                listaUsuarios.perisistirUsuarios();
                retorno = "Usuario creado con éxito,;,200";
            }
        } catch (Exception e) {
            // Manejar cualquier otra excepción no controlada
            retorno = "Error del servidor: " + e.getMessage() + ",;,500";
        }
        return retorno;
    }

    /**
     * Método para derivar las operaciones sobre Evaluaciones.
     *
     * @return Una cadena con el resultado de la operación y el código de estado
     *         HTTP correspondiente.
     */
    public String derivarEvaluaciones(String operacion) {
        Evaluaciones es = new Evaluaciones();
        String retorno = "";

        try {
            switch (operacion) {

                case "Eliminar":
                    if (es.existeEvaluacion(mensaje)) {
                        es.eliminarEvaluacion(mensaje);
                        retorno = "Evaluacion eliminada,;,200";
                    } else {
                        retorno = "Evaluacion NO existe,;,500";
                    }
                    break;

                case "Existencia":
                    if (es.existeEvaluacion(mensaje)) {
                        retorno = "Evaluacion existe,;,200";
                    } else {
                        retorno = "Evaluacion NO existe,;,500";
                    }
                    break;

                case "Listar":
                    if (!es.getEvaluaciones().isEmpty()) {
                        for (Evaluacion ev : es.getEvaluaciones()) {
                            retorno += ev.getTitulo() + ";;;";
                        }
                        retorno += ",;,200";
                    } else {
                        retorno = "NO existen Evaluaciones guardadas,;,400";
                    }
                    break;

                case "Alta":
                    String[] mensajeTokenizado = mensaje.split(";;;");

                    if (!es.existeEvaluacion(mensajeTokenizado[0])) {
                        // CREAR EVALUACION
                        Evaluacion ev;
                        Preguntas ps = new Preguntas();

                        for (int i = 1; i < mensajeTokenizado.length - 2; i++) {
                            String[] preguntaActual = mensajeTokenizado[i].split(",,,");
                            Pregunta p = null;

                            if (preguntaActual[1].equals("Completar")) {
                                p = new CompletarEspacio(preguntaActual[0], Integer.parseInt(preguntaActual[2]),
                                        preguntaActual[3].split(" "));

                            } else if (preguntaActual[1].equals("MultipleOpcion")) {
                                String[] opciones = { preguntaActual[3], preguntaActual[4], preguntaActual[5],
                                        preguntaActual[6] };
                                p = new MultipleOpcion(preguntaActual[0], Integer.parseInt(preguntaActual[2]), opciones,
                                        false, preguntaActual[7]);

                            } else if (preguntaActual[1].equals("VerdaderoFalso")) {
                                String[] opciones = { "", "" }; // Verifica si estas opciones son correctas
                                p = new MultipleOpcion(preguntaActual[0], Integer.parseInt(preguntaActual[2]), opciones,
                                        true, preguntaActual[3]);
                            }

                            if (p != null) {
                                ps.agregarPregunta(p);
                            } else {
                                retorno = "Error al crear la pregunta,;,400";
                                break;
                            }
                        }

                        if (!retorno.contains("Error")) {
                            ev = new Evaluacion(mensajeTokenizado[0], ps);
                            es.agregarEvaluacion(ev);
                            retorno = "Evaluacion creada,;,200";
                        }

                    } else {
                        retorno = "Ya existe evaluacion con ese titulo,;,400";
                    }
                    break;

                default:
                    retorno = "Operacion no reconocida,;,400";
                    break;
            }
        } catch (FileNotFoundException ex) {
            retorno = "Error al realizar la operación,;,500";
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    /**
     * Método para derivar las operaciones sobre Historiales.
     *
     * @return Una cadena con el resultado de la operación y el código de estado
     *         HTTP correspondiente.
     */
    public String derivarHistoriales(String operacion) {
        String retorno = null;
        Historiales hs = new Historiales();
        switch (operacion) {
            case "Ver":
                if (hs.existeHistorial(mensaje)) {

                    LinkedList<Historial> hls = hs.obtenerHistoriales(mensaje);
                    for (Historial h : hls) {

                        retorno += h.getCiEstudiante() + ",,," + h.getPuntaje() + ";;;";
                    }
                    retorno += ",:,200";

                } else {
                    retorno = "Evaluacion NO existe,;,500";
                }

                break;

        }

        return retorno;
    }
public String realizarEvaluacion(String evaluacionTitulo, int preguntaIndex, String respuestaCliente) {
    Evaluaciones es = new Evaluaciones();
    String retorno = "";

    if (es.existeEvaluacion(evaluacionTitulo)) {
        Evaluacion evaluacion = es.obtenerEvaluacion(evaluacionTitulo);
        List<Pregunta> preguntas = evaluacion.getListaPreguntas().getPreguntas();
        
        if (preguntaIndex < preguntas.size()) {
            Pregunta preguntaActual = preguntas.get(preguntaIndex);
            
            if (respuestaCliente == null) {  // Enviar la pregunta al cliente
                retorno = preguntaActual.getEnunciado() + ";;;,;,200";
            } else {  // Recibir la respuesta del cliente y validar
                boolean resultadoCorrecto = preguntaActual.esCorrecta(respuestaCliente);
                
                if (resultadoCorrecto) {
                    retorno = "Respuesta correcta,;;;,;,200";
                } else {
                    retorno = "Respuesta incorrecta,;;;,;,500";
                }

                if (preguntaIndex + 1 < preguntas.size()) {
                    retorno += ";;Siguiente pregunta";
                } else {
                    retorno += ";;Evaluacion completada";
                }
            }
        } else {
            retorno = "Indice de pregunta fuera de rango,;,400";
        }
    } else {
        retorno = "Evaluacion NO existe,;,500";
    }

    return retorno;
}

}
