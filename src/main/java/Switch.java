/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
     * Deriva la operación a la clase correspondiente según el contenido del
     * msj, la clase de destino y la operación.
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
                        retorno = "# Pureba de conexión: ok.,;,200";

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
            case "CambioPassword":
                retorno = derivarCambioPassword();
                break;
            default:
                // Error para operación desconocida
                retorno = "Error: Operación desconocida.,;,404";
                break;
        }
        return retorno;

    }

    public String derivarCambioPassword() {
        Usuarios usuarios = new Usuarios();
        usuarios.cargarUsuarios();
        String retorno = "";
        String[] tokens = mensaje.split(";;;");
        String usuario = tokens[0];
        String nuevaContrasenia = tokens[1];
        if (usuarios.existeUsuario(usuario)) {
            usuarios.obtenerUsuario(usuario).setContrasenia(nuevaContrasenia);
            usuarios.perisistirUsuarios();
            retorno = "cambio con exito,;,200";
        } else {
            retorno = "NO existe usuario,;,500";
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
     * @param operacion
     * @return Una cadena con el resultado de la operación y el código de estado
     *         HTTP correspondiente.
     */
    public String derivarEvaluaciones(String operacion) {
        Persistencia persistencia = new Persistencia();
        Evaluaciones es = new Evaluaciones();
        es.setListaEvaluaciones(persistencia.cargarEvaluacionesDesdeArchivo().getEvaluaciones());
        String retorno = null;

        switch (operacion) {

            case "Eliminar":
                if (es.existeEvaluacion(mensaje)) {

                    try {
                        es.eliminarEvaluacion(mensaje);
                        retorno = "Evaluacion eliminada,;,200";
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Switch.class.getName()).log(Level.SEVERE, null, ex);
                    }

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
                String listaEnString = "";
                try {
                    List<String> listaTitulosEvaluaciones = persistencia.obtenerTitulosDeEvaluacionesDesdeArchivo();
                    for (String parte : listaTitulosEvaluaciones) {
                        listaEnString += parte + ";;;";

                    }
                    retorno = listaEnString + ",;,200";
                } catch (IOException e) {
                    // Manejo de la excepción
                    System.err.println("Ocurrió un error al leer el archivo: " + e.getMessage());
                    retorno = "Error al acceder a las evaluaciones,;,400";
                }

                break;
            case "Alta":
                /**
                 * Procesa un mensaje de alta de evaluación desde el cliente.
                 *
                 * @param mensaje El mensaje recibido desde el cliente con el
                 *                formato:
                 *                "Evaluacion1;;;Pregunta1,,,Completar,,,0,,,Respuesta1;;;Pregunta2,,,Multiple,,,0,,,Respuesta2.1,,,Respuesta2.2,,,Respuesta3.3,,,Respuesta4.4,,,Opción
                 *                2;;;Pregunta3,,,VF,,,0,,,Verdadero;;;Pregunta4,,,Completar,,,0,,,Respuesta4;;;Pregunta5,,,Multiple,,,0,,,Respuesta5.1,,,Respuesta5.2,,,Respuesta5.3,,,Respuesta5.4,,,Opción
                 *                4;;;Pregunta6,,,VF,,,3,,,Verdadero;;;6"
                 * @return Retorna un mensaje indicando el estado de la
                 *         operación.
                 */
                // Divide el mensaje en partes usando el delimitador ';;;'
                String[] mensajeTokenizado = mensaje.split(";;;");

                // Verifica si ya existe una evaluación con el mismo título
                if (!es.existeEvaluacion(mensajeTokenizado[0])) {
                    // Crear un objeto para almacenar las preguntas de la evaluación
                    Preguntas ps = new Preguntas();

                    // Itera sobre todas las preguntas, excluyendo el último token que es el total
                    for (int i = 1; i < mensajeTokenizado.length - 1; i++) { // Excluyendo el total
                        // Divide la información de cada pregunta en partes usando el delimitador ',,,'
                        String[] preguntaActual = mensajeTokenizado[i].split(",,,"); // tercer nivel
                        Pregunta p = null;
                        String enunciadoPregunta = preguntaActual[0];
                        String tipoPregunta = preguntaActual[1];
                        System.out.println("------------------------------------------------------");
                        System.out.println(enunciadoPregunta);
                        System.out.println(tipoPregunta);
                        System.out.println("------------------------------------------------------");
                        int puntajePregunta = Integer.parseInt(preguntaActual[2]); // Puntaje de la pregunta

                        // Crea la pregunta según el tipo especificado
                        switch (tipoPregunta) {
                            case "Completar":
                                // Extrae las respuestas para preguntas de tipo "Completar"
                                String[] respuestas = preguntaActual[3].split(",");
                                p = new CompletarEspacio(enunciadoPregunta, puntajePregunta, respuestas);
                                break;
                            case "Multiple":
                                // Extrae las opciones para preguntas de tipo "Multiple"
                                String[] opciones = { preguntaActual[3], preguntaActual[4], preguntaActual[5],
                                        preguntaActual[6] };
                                p = new MultipleOpcion(enunciadoPregunta, puntajePregunta, opciones, false,
                                        preguntaActual[7]);
                                break;
                            case "VF":
                                // Opciones fijas para preguntas de tipo "VF"
                                String[] opcionesVF = { "Verdadero", "Falso" }; // Opciones para VF
                                p = new MultipleOpcion(enunciadoPregunta, puntajePregunta, opcionesVF, true,
                                        preguntaActual[3]);
                                break;
                            default:
                                // Maneja un tipo de pregunta desconocido
                                continue; // O lanzar una excepción si es necesario
                        }

                        // Agrega la pregunta creada al objeto Preguntas
                        ps.agregarPregunta(p);
                    }
                    String cantidadDePreguntas = mensajeTokenizado[mensajeTokenizado.length - 1];
                    System.out.println("Cantidad de preguntas>" + cantidadDePreguntas);

                    try {
                        // Crea la evaluación y la agrega al sistema
                        Evaluacion ev = new Evaluacion(mensajeTokenizado[0], ps);
                        es.setListaEvaluaciones(persistencia.cargarEvaluacionesDesdeArchivo().getEvaluaciones());
                        es.agregarEvaluacion(ev);
                        persistencia.persistirEvaluacionesEnArchivo(es.getEvaluaciones(), cantidadDePreguntas);
                        retorno = "Evaluacion creada,;,200";
                    } catch (FileNotFoundException ex) {
                        // Maneja excepciones en caso de error al agregar la evaluación
                        Logger.getLogger(Switch.class.getName()).log(Level.SEVERE, null, ex);
                        retorno = "Error al crear la evaluación,;,500"; // Cambiar el mensaje según corresponda
                    }
                } else {
                    // Mensaje si la evaluación con ese título ya existe
                    retorno = "Ya existe evaluación con ese título,;,400";
                }
                break;
            case "ObtenerPregunta":
                String[] tokens = mensaje.split(";;;");
                retorno = obtenerPregunta(tokens[0],Integer.parseInt(tokens[1])); //titulo y numero pregunta
                break;
            case "Correccion":
                String[] tokens2 = mensaje.split(";;;");
                es.setListaEvaluaciones(persistencia.cargarEvaluacionesDesdeArchivo().getEvaluaciones());
                System.out.println(tokens2[1]);
                Evaluacion evaluacion = es.obtenerEvaluacion(tokens2[1]);
                retorno = correccion(evaluacion.getListaPreguntas());
                break;
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
        String retorno = "";
        Historiales hs = new Historiales();
        Persistencia persistencia = new Persistencia();
        hs.setListaHistorial(persistencia.cargarHistorialesDesdeArchivo().getListaHistorial());
        switch (operacion) {
            case "Ver":
                if (hs.existeHistorial(mensaje)) {
                    LinkedList<Historial> hls = hs.obtenerHistoriales(mensaje);
                    for (int i = 0; i < hls.size(); i++) {
                        Historial historial = hls.get(i);
                        retorno += historial.getCiEstudiante() + ",,," + historial.getPuntaje() + ";;;";
                    }
                    retorno = retorno.substring(0, retorno.length() - 3);
                    retorno += ",;,200";
                } else {
                    retorno = "NO existen historiales,;,500";
                }
                break;
        }
        return retorno;
    }

    /**
     * Método que permite enviar al cliente la pregunta solicitada de una evaluación.
     * @param evaluacionTitulo
     * @param indice corresponde al número de pregunta.
     * @return el mensaje a recibir por el cliente del tipo "tipo;;;enunciado;;;op1(opcional);;;op2(opcional);;;op3(opcional);;;op4(opcional);;;puntaje,;,200
     */
    public String obtenerPregunta(String evaluacionTitulo, int indice) {
        String retorno = "";
        Evaluaciones evaluaciones = new Evaluaciones();
        Persistencia persistencia = new Persistencia();
        evaluaciones.setListaEvaluaciones(persistencia.cargarEvaluacionesDesdeArchivo().getEvaluaciones());
        Evaluacion evaluacion = evaluaciones.obtenerEvaluacion(evaluacionTitulo);
        if (indice < evaluacion.getListaPreguntas().getPreguntas().size()) {
            Pregunta pregunta = evaluacion.getListaPreguntas().obtenerPregunta(indice);
            String tipoPregunta = pregunta.obtenerTipo();
            if (tipoPregunta.equals("Multiple")) {
                MultipleOpcion multiple = (MultipleOpcion) pregunta;
                retorno = tipoPregunta + ";;;" + multiple.getEnunciado() + ";;;" + multiple.getOpciones()[0] + ";;;" + multiple.getOpciones()[1] + ";;;" + multiple.getOpciones()[2] + ";;;" + multiple.getOpciones()[3] + ";;;" + multiple.getPuntaje() + ",;,200";
            } else if (tipoPregunta.equals("VF")) {
                MultipleOpcion vf = (MultipleOpcion) pregunta;
                retorno = tipoPregunta + ";;;" + vf.getEnunciado() + ";;;" + vf.getPuntaje() + ",;,200";
            } else {
                CompletarEspacio completar = (CompletarEspacio) pregunta;
                retorno = tipoPregunta + ";;;" + completar.getEnunciado() + ";;;" + completar.getPuntaje() + ",;,200";
            }
        } else { //Se fue de rango y no hay más preguntas
            retorno = "Finalizar,;,200";
        }
        return retorno;
    }
    
    /**
     * Método que calcúla la calificacion obtenida por un estudiante al realizar una evaluación.
     * @param preguntas
     * @return 
     */
    public String correccion (Preguntas preguntas){
        String retorno = "";
        String[] tokens = mensaje.split(";;;");
        int puntajeTotal = 0;
        String estudiante = tokens[0];
        String evaluacion = tokens[1];
        
        for(int i=0; i<preguntas.getPreguntas().size();i++){
            puntajeTotal += calificar(preguntas.obtenerPregunta(i),tokens[i+2]);
        }
        retorno = "puntaje total de " + puntajeTotal + " puntos,;,200";
        return retorno;
    }

    /**
     * Método que dada las preguntas individuales calcula la calificacion obtenida en cada una de ellas.
     * @param pregunta
     * @param respuesta dada por el estudiante.
     * @return
     */
    public int calificar(Pregunta pregunta, String respuesta){
        int puntaje = 0;
        if(pregunta.esCorrecta(respuesta)){
            System.out.println("Pregunta correcta");
            puntaje = pregunta.getPuntaje();
        }
        return puntaje;
    }
}
