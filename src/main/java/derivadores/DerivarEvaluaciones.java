package derivadores;

import logica.CompletarEspacio;
import logica.Evaluacion;
import logica.Evaluaciones;
import logica.Historial;
import logica.Historiales;
import logica.MultipleOpcion;
import logica.Pregunta;
import logica.Preguntas;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase DerivarEvaluaciones gestiona operaciones relacionadas con
 * evaluaciones,
 * incluyendo creación, eliminación, obtención y corrección. Permite realizar
 * diversas acciones en función de la operación solicitada.
 */

public class DerivarEvaluaciones {

    private String operacion;
    private String mensaje;

    /**
     * Constructor que inicializa la operación y el mensaje.
     * 
     * @param operacion La operación a realizar (e.g., "Eliminar", "Listar").
     * @param mensaje   El mensaje que contiene datos necesarios para la operación.
     */
    public DerivarEvaluaciones(String operacion, String mensaje) {
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
     * Método para derivar las operaciones sobre Evaluaciones.
     * Realiza la operación indicada en base al valor de operacion y
     * mensaje.
     * 
     * @return Una cadena con el resultado de la operación y el código de estado
     *         HTTP correspondiente.
     */
    public String derivarEvaluaciones() {
        switch (operacion) {
            case "Eliminar":
                return eliminarEvaluacion();
            case "Existencia":
                return verificarExistencia();
            case "Listar":
                return listarEvaluaciones();
            case "Alta":
                return altaEvaluacion();
            case "ObtenerPregunta":
                return obtenerPregunta();
            case "Correccion":
                return correccionEvaluacion();
            case "ObtenerCorrectas":
                return derivarObtenerRespuestas();
            case "ValorCheckboxRespuestas":
                return obtenerValorCheckboxRespuestas();
            case "ObtenerPuntajeTotal":
                return obtenerPuntajeTotalDeEvaluacion();
            case "ObtenerTituloAlAzar":
                return obtenerTituloAlAzar();
            default:
                return "Operación desconocida,;,400";
        }
    }

    /**
     * Elimina una evaluación si existe.
     * 
     * @return Resultado de la operación y código de estado HTTP.
     */
    private String eliminarEvaluacion() {
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();

        if (evaluaciones.existeEvaluacion(mensaje)) {
            try {
                evaluaciones.eliminarEvaluacion(mensaje);
                Historiales historiales = new Historiales();
                historiales.eliminarTodosLosHistorialesDeUnaEvaluacion(mensaje);
                return "Evaluación eliminada,;,200";
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DerivarEvaluaciones.class.getName()).log(Level.SEVERE, null, ex);
                return "Error al eliminar la evaluación,;,500";
            }
        } else {
            return "Evaluación NO existe,;,500";
        }
    }

    /**
     * Verifica si una evaluación existe.
     * 
     * @return Resultado de la verificación y código de estado HTTP.
     */
    private String verificarExistencia() {
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();
        return evaluaciones.existeEvaluacion(mensaje) ? "Evaluación existe,;,200" : "Evaluación NO existe,;,500";
    }

    /**
     * Lista todas las evaluaciones existentes.
     * 
     * 
     * @return Cadena con los títulos de las evaluaciones y código de estado HTTP.
     */
    private String listarEvaluaciones() {
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();
        String listaEnString = "";

        try {
            List<String> listaTitulosEvaluaciones = evaluaciones.obtenerTítulosEvaluaciones();
            for (String parte : listaTitulosEvaluaciones) {
                listaEnString += parte + ";;;";
            }
            if (listaEnString.length() > 3) { // Elimino el ;;; sobrantes
                listaEnString = listaEnString.substring(0, listaEnString.length() - 3);
            }
            return listaEnString + ",;,200";
        } catch (Exception e) {
            return "Error al acceder a las evaluaciones,;,400";
        }
    }

    /**
     * Crea una nueva evaluación.
     * 
     * @return Resultado de la creación de la evaluación y código de estado HTTP.
     */
    private String altaEvaluacion() {
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();

        String[] mensajeTokenizado = tokenizarMensaje(mensaje);
        if (evaluaciones.existeEvaluacion(mensajeTokenizado[0])) {
            return "Ya existe evaluación con ese título,;,400";
        }

        Preguntas preguntas = procesarPreguntas(mensajeTokenizado);
        Integer cantidadDePreguntas = Integer.valueOf(mensajeTokenizado[mensajeTokenizado.length - 1]);
        boolean respuestasValidas = Boolean.parseBoolean(mensajeTokenizado[mensajeTokenizado.length - 2]);

        return crearYPersistirEvaluacion(evaluaciones, mensajeTokenizado[0], preguntas, cantidadDePreguntas,
                respuestasValidas);
    }

    /**
     * Divide el mensaje en partes usando el delimitador ';;;' y muestra el
     * resultado.
     * 
     * @param mensaje El mensaje a tokenizar.
     * @return Un arreglo de cadenas que representan las partes del mensaje.
     */
    private String[] tokenizarMensaje(String mensaje) {
        String[] mensajeTokenizado = mensaje.split(";;;");
        return mensajeTokenizado;
    }

    /**
     * Procesa las preguntas a partir del mensaje tokenizado.
     * 
     * @param mensajeTokenizado El mensaje tokenizado que contiene las preguntas.
     * @return Un objeto Preguntas que contiene todas las preguntas procesadas.
     */
    private Preguntas procesarPreguntas(String[] mensajeTokenizado) {
        Preguntas preguntas = new Preguntas();

        for (int i = 1; i < mensajeTokenizado.length - 2; i++) { // Excluyendo el total
            String[] preguntaActual = mensajeTokenizado[i].split(",,,");
            System.out.println("Procesando pregunta: " + Arrays.toString(preguntaActual));

            Pregunta p = crearPregunta(preguntaActual);
            if (p != null) {
                preguntas.agregarPregunta(p);
            }
        }

        return preguntas;
    }

    /**
     * Crea una pregunta a partir de los datos proporcionados.
     * 
     * @param preguntaDatos Los datos de la pregunta.
     * @return La pregunta creada o null si los datos son incorrectos.
     */
    private Pregunta crearPregunta(String[] preguntaDatos) {
        String enunciadoPregunta = preguntaDatos[0];
        String tipoPregunta = preguntaDatos[1];
        int puntajePregunta = Integer.parseInt(preguntaDatos[2]);
        Pregunta pregunta = null;

        switch (tipoPregunta) {
            case "Completar":
                String[] respuestas = preguntaDatos[3].split(",");
                pregunta = new CompletarEspacio(enunciadoPregunta, puntajePregunta, respuestas);
                break;
            case "Multiple":
                if (preguntaDatos.length < 8) {
                    System.out
                            .println("Error: Pregunta de tipo Multiple mal formada: " + Arrays.toString(preguntaDatos));
                    break;
                }
                String[] opciones = { preguntaDatos[3], preguntaDatos[4], preguntaDatos[5], preguntaDatos[6] };
                pregunta = new MultipleOpcion(enunciadoPregunta, puntajePregunta, opciones, false, preguntaDatos[7]);
                break;
            case "VF":
                if (preguntaDatos.length < 4) {
                    System.out.println("Error: Pregunta de tipo VF mal formada: " + Arrays.toString(preguntaDatos));
                    break;
                }
                String[] opcionesVF = { "Verdadero", "Falso" };
                pregunta = new MultipleOpcion(enunciadoPregunta, puntajePregunta, opcionesVF, true, preguntaDatos[3]);
                break;
            default:
                System.out.println("Error: Tipo de pregunta desconocido: " + tipoPregunta);
                break;
        }

        return pregunta;
    }

    /**
     * Crea y persiste una nueva evaluación.
     * 
     * @param evaluaciones        El objeto Evaluaciones donde se agregará la nueva
     *                            evaluación.
     * @param titulo              El título de la evaluación.
     * @param preguntas           El objeto Preguntas con las preguntas a incluir.
     * @param cantidadDePreguntas La cantidad de preguntas en la evaluación.
     * @param respuestasValidas   Indica si las respuestas son válidas.
     * @return Resultado de la creación y persistencia de la evaluación y código de
     *         estado HTTP.
     */
    private String crearYPersistirEvaluacion(Evaluaciones evaluaciones, String titulo, Preguntas preguntas,
            Integer cantidadDePreguntas, boolean respuestasValidas) {
        try {
            Evaluacion evaluacion = new Evaluacion(titulo, preguntas);
            evaluacion.setCantidadDePreguntas(cantidadDePreguntas);
            evaluacion.setRespuestasValidas(respuestasValidas);
            evaluaciones.agregarEvaluacion(evaluacion);
            evaluaciones.persistirEvaluaciones(evaluaciones.getEvaluaciones());
            return "Evaluación creada,;,200";
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DerivarEvaluaciones.class.getName()).log(Level.SEVERE, null, ex);
            return "Error al crear la evaluación,;,500";
        }
    }

    /**
     * 
     * Busca la evaluación por su título y devuelve la pregunta en el índice
     * especificado.
     * 
     * @return Una cadena con la pregunta y su tipo, junto con el código de estado
     *         HTTP.
     */
    private String obtenerPregunta() {
        String[] tokens = tokenizarMensaje(mensaje);
        return obtenerPregunta(tokens[0], Integer.parseInt(tokens[1])); // título y número de pregunta
    }

    /**
     * 
     * Calcula el puntaje obtenido en la evaluación y actualiza o agrega el
     * historial del estudiante.
     * 
     * @return Resultado de la corrección y código de estado HTTP.
     */
    private String correccionEvaluacion() {
        Historiales hs = new Historiales();
        hs.actualizarHistoriales();
        String[] tokens = tokenizarMensaje(mensaje);
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();
        Evaluacion evaluacion = evaluaciones.obtenerEvaluacion(tokens[1]);
        int puntajeObtenido = correccion(evaluacion.getListaPreguntas());

        if (hs.existeHistorial(tokens[1], tokens[0])) {
            Historial h = hs.obtenerHistorial(tokens[1], tokens[0]);
            h.setPuntaje(puntajeObtenido);
            hs.persistirHistoriales();
        } else {
            hs.agregarHistorial(new Historial(tokens[1], tokens[0], puntajeObtenido));
        }
        return "Historial agregado con éxito,;,200";
    }

    /**
     * 
     * Busca la evaluación por su título y devuelve la pregunta en el índice
     * especificado.
     * 
     * @param evaluacionTitulo El título de la evaluación.
     * @param indice           El índice de la pregunta dentro de la evaluación.
     * 
     * @return Una cadena con la pregunta y su tipo, junto con el código de estado
     *         HTTP.
     */
    private String obtenerPregunta(String evaluacionTitulo, int indice) {
        String retorno = "";
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();
        Evaluacion evaluacion = evaluaciones.obtenerEvaluacion(evaluacionTitulo);

        if (indice < evaluacion.getListaPreguntas().getPreguntas().size()) {
            Pregunta pregunta = evaluacion.getListaPreguntas().obtenerPregunta(indice);
            String tipoPregunta = pregunta.obtenerTipo();

            if (tipoPregunta.equals("Multiple")) {
                MultipleOpcion multiple = (MultipleOpcion) pregunta;
                retorno = tipoPregunta + ";;;" + multiple.getEnunciado() + ";;;"
                        + multiple.getOpciones()[0] + ";;;" + multiple.getOpciones()[1] + ";;;"
                        + multiple.getOpciones()[2] + ";;;" + multiple.getOpciones()[3] + ";;;"
                        + multiple.getPuntaje() + ",;,200";
            } else if (tipoPregunta.equals("VF")) {
                MultipleOpcion vf = (MultipleOpcion) pregunta;
                retorno = tipoPregunta + ";;;" + vf.getEnunciado() + ";;;"
                        + vf.getPuntaje() + ",;,200";
            } else {
                CompletarEspacio completar = (CompletarEspacio) pregunta;
                retorno = tipoPregunta + ";;;" + completar.getEnunciado() + ";;;"
                        + completar.getPuntaje() + ",;,200";
            }
        } else {
            retorno = "Finalizar,;,200";
        }
        return retorno;
    }

    /**
     * 
     * Obtiene las respuestas correctas para la evaluación especificada en el
     * mensaje.
     * 
     * 
     * @return Una cadena con las respuestas correctas y el código de estado HTTP.
     */
    private String derivarObtenerRespuestas() {
        String retorno = "";
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();

        try {
            Evaluacion evaluacion = evaluaciones.obtenerEvaluacion(mensaje);
            ArrayList<String> respuestasCorrectas = evaluacion.obtenerRespuestasCorrectas();

            for (String preguntaYRespuesta : respuestasCorrectas) {
                retorno += preguntaYRespuesta + ";;;";
            }
            retorno += ",;,200";
        } catch (Exception e) {
            retorno = ",;,400";
        }

        return retorno;
    }

    /**
     * 
     * 
     * El método se fija si la evaluacion correspondiente tiene habilitada la opcion
     * de ver las respuestas correctas o no
     * 
     * @return Valor booleano de respuestas válidas y código de estado HTTP.
     */
    private String obtenerValorCheckboxRespuestas() {
        String retorno = "";
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();

        try {
            Evaluacion evaluacion = evaluaciones.obtenerEvaluacion(mensaje);
            Boolean respuestasValidas = evaluacion.isRespuestasValidas();
            retorno = String.valueOf(respuestasValidas) + ",;,200";
        } catch (Exception e) {
            retorno = ",;,400";
        }

        return retorno;
    }

    /**
     * Calcula el puntaje total basado en las respuestas proporcionadas por el
     * estudiante.
     * 
     * @param preguntas Lista de preguntas de la evaluación.
     * @return Puntaje total obtenido en la evaluación.
     */
    private int correccion(Preguntas preguntas) {
        String[] tokens = tokenizarMensaje(mensaje);
        int puntajeTotal = 0;
        for (int i = 0; i < preguntas.getPreguntas().size(); i++) {
            puntajeTotal += calificar(preguntas.obtenerPregunta(i), tokens[i + 2]);
        }
        return puntajeTotal;
    }

    /**
     * Califica una pregunta comparando la respuesta proporcionada con la respuesta
     * correcta.
     * 
     * @param pregunta  La pregunta a calificar.
     * @param respuesta La respuesta proporcionada por el estudiante.
     * @return El puntaje obtenido para la pregunta.
     */
    private int calificar(Pregunta pregunta, String respuesta) {
        int puntaje = 0;
        if (pregunta.esCorrecta(respuesta)) {
            puntaje = pregunta.getPuntaje();
        }
        return puntaje;
    }

    /**
     * Obtiene el puntaje total de la evaluación correspondiente al mensaje.
     *
     * @return Un string que contiene el puntaje total seguido del código de estado,
     *         en el formato "puntajeTotal,;,codigoEstado".
     */
    private String obtenerPuntajeTotalDeEvaluacion() {
        String retorno = "";
        try {
            // Crea una instancia de Evaluaciones y obtiene el puntaje total
            Evaluaciones evaluaciones = new Evaluaciones();

            // Verifica si el mensaje (título de la evaluación) es válido
            if (mensaje == null || mensaje.trim().isEmpty()) {
                throw new IllegalArgumentException("El título de la evaluación (mensaje) es nulo o vacío.");
            }

            // Obtiene el puntaje total de la evaluación correspondiente al mensaje
            int puntajeTotal = evaluaciones.obtenerPuntajeTotal(mensaje);
            String puntajeTotalEnString = String.valueOf(puntajeTotal);

            // Construye el string de retorno con el puntaje total y el código de estado
            retorno = puntajeTotalEnString + ",;,200";
        } catch (IllegalArgumentException e) {
            // Manejo de errores cuando el título de la evaluación es inválido
            System.err.println("Error al obtener el puntaje total de la evaluación: " + e.getMessage());
            retorno = "Error: " + e.getMessage() + ",;,400"; // Código de estado 400 para error de solicitud
        } catch (Exception e) {
            // Manejo de cualquier otra excepción inesperada
            System.err.println("Error inesperado al obtener el puntaje total de la evaluación: " + e.getMessage());
            retorno = "Error inesperado: " + e.getMessage() + ",;,500"; // Código de estado 500 para error interno del
                                                                        // servidor
        }
        return retorno;
    }

    /**
     * Método que obtiene un título de una evaluación aleatoria.
     * 
     * @return título de la evaluación.
     */
    private String obtenerTituloAlAzar() {
        String retorno = "";
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();

        // Verificamos si hay evaluaciones disponibles
        if (evaluaciones.getEvaluaciones().isEmpty()) {
            retorno = "No existen evaluaciones,;,500";
        } else {
            // Generamos un índice aleatorio basado en la cantidad de evaluaciones
            // disponibles
            int randomIndex = (int) (Math.random() * evaluaciones.getEvaluaciones().size());
            // Obtenemos el título de la evaluación en el índice aleatorio
            retorno = evaluaciones.getEvaluaciones().get(randomIndex).getTitulo();
            retorno += ",;,200";
        }
        return retorno;
    }
}
