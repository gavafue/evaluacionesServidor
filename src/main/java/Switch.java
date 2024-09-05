/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                DerivarEvaluaciones derivadorEvaluaciones = new DerivarEvaluaciones(operacion, mensaje)
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

   
   
  



   

   

    /**
     * Metodo que gestiona la consulta de obtencion de respuestas de una
     * evaluacion dada.
     */
    public String derivarObtenerRespuestas() {
        String retorno = "";
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();

        try {
            Evaluacion evaluacion = evaluaciones.obtenerEvaluacion(mensaje);
            ArrayList<String> respuestasCorrectas = evaluacion.obtenerRespuestasCorrectas();

            for (String preguntaYRespuesta : respuestasCorrectas) {
                retorno += preguntaYRespuesta + ";;;";
            }
            retorno += ",;,200"; // Código de éxito 200
        } catch (Exception e) {
            // Si ocurre alguna excepción, retorna un código de error 400
            retorno = ",;,400";
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
        hs.actualizarHistoriales();
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
     * Método que permite enviar al cliente la pregunta solicitada de una
     * evaluación.
     *
     * @param evaluacionTitulo
     * @param indice           corresponde al número de pregunta.
     * @return el mensaje a recibir por el cliente del tipo
     *         "tipo;;;enunciado;;;op1(opcional);;;op2(opcional);;;op3(opcional);;;op4(opcional);;;puntaje,;,200
     */
    public String obtenerPregunta(String evaluacionTitulo, int indice) {
        String retorno = "";
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();
        Evaluacion evaluacion = evaluaciones.obtenerEvaluacion(evaluacionTitulo);
        if (indice < evaluacion.getListaPreguntas().getPreguntas().size()) {
            Pregunta pregunta = evaluacion.getListaPreguntas().obtenerPregunta(indice);
            String tipoPregunta = pregunta.obtenerTipo();
            if (tipoPregunta.equals("Multiple")) {
                MultipleOpcion multiple = (MultipleOpcion) pregunta;
                retorno = tipoPregunta + ";;;" + multiple.getEnunciado() + ";;;" + multiple.getOpciones()[0] + ";;;"
                        + multiple.getOpciones()[1] + ";;;" + multiple.getOpciones()[2] + ";;;"
                        + multiple.getOpciones()[3] + ";;;" + multiple.getPuntaje() + ",;,200";
            } else if (tipoPregunta.equals("VF")) {
                MultipleOpcion vf = (MultipleOpcion) pregunta;
                retorno = tipoPregunta + ";;;" + vf.getEnunciado() + ";;;" + vf.getPuntaje() + ",;,200";
            } else {
                CompletarEspacio completar = (CompletarEspacio) pregunta;
                retorno = tipoPregunta + ";;;" + completar.getEnunciado() + ";;;" + completar.getPuntaje() + ",;,200";
            }
        } else { // Se fue de rango y no hay más preguntas
            retorno = "Finalizar,;,200";
        }
        return retorno;
    }

    /**
     * Método que calcúla la calificacion obtenida por un estudiante al realizar
     * una evaluación.
     *
     * @param preguntas
     * @return
     */
    public int correccion(Preguntas preguntas) {
        String[] tokens = mensaje.split(";;;");
        int puntajeTotal = 0;
        String estudiante = tokens[0];
        String evaluacion = tokens[1];
        for (int i = 0; i < preguntas.getPreguntas().size(); i++) {
            puntajeTotal += calificar(preguntas.obtenerPregunta(i), tokens[i + 2]);
        }
        return puntajeTotal;
    }

    /**
     * Método que dada las preguntas individuales calcula la calificacion
     * obtenida en cada una de ellas.
     *
     * @param pregunta
     * @param respuesta dada por el estudiante.
     * @return
     */
    public int calificar(Pregunta pregunta, String respuesta) {
        int puntaje = 0;
        if (pregunta.esCorrecta(respuesta)) {
            // System.out.println("Pregunta correcta");
            puntaje = pregunta.getPuntaje();
        } else {
            // System.out.println("Incorrecto");
        }
        return puntaje;
    }

    public String obtenerValorCheckboxRespuestas() {
        String retorno = "";
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();

        try {
            Evaluacion evaluacion = evaluaciones.obtenerEvaluacion(mensaje);
            Boolean respuestasValidas = evaluacion.isRespuestasValidas();
            String respuesta = String.valueOf(respuestasValidas);

            retorno = respuesta;
            retorno += ",;,200"; // Código de éxito 200
        } catch (Exception e) {
            // Si ocurre alguna excepción, retorna un código de error 400
            retorno = ",;,400";
        }

        return retorno;
    }
}