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
 * evaluaciones, incluyendo creación, eliminación, obtención y corrección.
 * Permite realizar diversas acciones en función de la operación solicitada.
 */

public class DerivarEvaluaciones {

    private String operacion;
    private String mensaje;
    private Evaluaciones evaluaciones;
    private Historiales historiales;

    /**
     * Constructor que inicializa la operación y el mensaje.
     * 
     * @param operacion La operación a realizar (e.g., "Eliminar", "Listar").
     * @param mensaje   El mensaje que contiene datos necesarios para la operación.
     */
    public DerivarEvaluaciones(String operacion, String mensaje) {
        this.operacion = operacion;
        this.mensaje = mensaje;
        this.evaluaciones = new Evaluaciones();
        this.evaluaciones.actualizarListaEvaluaciones();
        this.historiales = new Historiales();
        this.historiales.actualizarHistoriales();
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
        return mensaje;
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
        return operacion;
    }

    /**
     * Establece la colección de evaluaciones.
     * 
     * @param evaluaciones La nueva colección de evaluaciones.
     */
    public void setEvaluaciones(Evaluaciones evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    /**
     * Obtiene las evaluaciones.
     * 
     * @return La colección de evaluaciones.
     */
    public Evaluaciones getEvaluaciones() {
        return evaluaciones;
    }
    
    /**
     * Establece la colección de historiales.
     * 
     * @param historiales La nueva colección de historiales.
     */
    public void setHistoriales(Historiales historiales) {
        this.historiales = historiales;
    }

    /**
     * Obtiene los historiales.
     * 
     * @return La colección de historiales.
     */
    public Historiales getHistoriales() {
        return historiales;
    }
    
    /**
     * Divide el mensaje en partes usando el delimitador ';;;' y devuelve el
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
     * Método para derivar las operaciones sobre Evaluaciones.
     * Realiza la operación indicada en base al valor de operacion y
     * mensaje.
     * 
     * @return Una cadena con el resultado de la operación y el código de estado
     *         HTTP correspondiente.
     */
    public String derivarEvaluaciones() {
        String retorno = "";
        switch (operacion) {
            case "Eliminar":
                retorno = eliminarEvaluacion();
                break;
            case "Existencia":
                retorno = verificarExistencia();
                break;
            case "Listar":
                retorno = listarEvaluaciones();
                break;
            case "Alta":
                retorno = altaEvaluacion();
                break;
            case "ObtenerPregunta":
                retorno = obtenerPregunta();
                break;
            case "Correccion":
                retorno = correccionEvaluacion();
                break;
            case "ObtenerCorrectas":
                retorno = derivarObtenerRespuestas();
                break;
            case "ValorCheckboxRespuestas":
                retorno = obtenerValorCheckboxRespuestas();
                break;
            case "ObtenerPuntajeTotal":
                retorno = obtenerPuntajeTotalDeEvaluacion();
                break;
            case "ObtenerTituloAlAzar":
                retorno = obtenerTituloAlAzar();
                break;
            default:
                retorno = "Operación desconocida,;,400";
                break;
        }
        return retorno;
    }

    /**
     * Elimina una evaluación si existe.
     * 
     * @return Resultado de la operación y código de estado HTTP.
     */
    private String eliminarEvaluacion() {
        String retorno = "";
        if (this.getEvaluaciones().existeEvaluacion(mensaje)) { // El mensaje corresponde al título de la evaluación
            try {
                this.getEvaluaciones().eliminarEvaluacion(mensaje);
                this.getHistoriales().eliminarHistoriales(mensaje); // En memoria y en persistencia elimina los historiales asociados a la evaluación
                retorno = "Evaluación eliminada,;,200";
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DerivarEvaluaciones.class.getName()).log(Level.SEVERE, null, ex);
                retorno = "Error al eliminar la evaluación,;,500";
            }
        } else {
            retorno = "Evaluación NO existe,;,500";
        }
        return retorno;
    }

    /**
     * Verifica si una evaluación existe.
     * 
     * @return Resultado de la verificación y código de estado HTTP.
     */
    private String verificarExistencia() {
        String retorno = "";
        boolean existe = this.getEvaluaciones().existeEvaluacion(mensaje);
        if(existe){
            retorno = "Evaluación existe,;,200";
        }else{
            retorno = "Evaluación NO existe,;,500";
        }
        return retorno;
    }

    /**
     * Lista todas las evaluaciones existentes.
     * 
     * 
     * @return Cadena con los títulos de las evaluaciones y código de estado HTTP.
     */
    private String listarEvaluaciones() {
        String listaEnString = "";
        try {
            List<String> listaTitulosEvaluaciones = this.getEvaluaciones().obtenerTítulosEvaluaciones();
            for (String parte : listaTitulosEvaluaciones) {
                listaEnString += parte + ";;;";
            }
            if (listaEnString.length() > 3) { // Elimino el ;;; sobrante
                listaEnString = listaEnString.substring(0, listaEnString.length() - 3);
            }
            return listaEnString + ",;,200";
        } catch (Exception e) {
            return "Error al acceder a las evaluaciones,;,400";
        }
    }

    /**
     * Método que desemboca la creación de una nueva evaluación.
     * 
     * @return Resultado de la creación de la evaluación y código de estado
     * HTTP.
     */
    private String altaEvaluacion() {
        String retorno = "";
        String[] stringPreguntas = tokenizarMensaje(mensaje);
        if (this.getEvaluaciones().existeEvaluacion(stringPreguntas[0])) {
            retorno = "Ya existe evaluación con ese título,;,400";
        } else {
            Preguntas preguntas = procesarPreguntas(stringPreguntas);
            Integer cantidadDePreguntas = Integer.valueOf(stringPreguntas[stringPreguntas.length - 1]);
            boolean verRespuestas = Boolean.parseBoolean(stringPreguntas[stringPreguntas.length - 2]); // Si está habilitado o no ver las respuestas correctas luego de realizar la evaluación
            retorno = crearEvaluacion(stringPreguntas[0], preguntas, cantidadDePreguntas,
                    verRespuestas);
        }
        return retorno;
    }


    /**
     * Procesa las preguntas a partir del mensaje tokenizado.
     * 
     * @param mensajeTokenizado El mensaje tokenizado que contiene las preguntas.
     * @return Un objeto Preguntas que contiene todas las preguntas procesadas.
     */
    private Preguntas procesarPreguntas(String[] mensajeTokenizado) {
        Preguntas preguntas = new Preguntas();
        for (int i = 1; i < mensajeTokenizado.length - 2; i++) { // Excluyendo el total de preguntas
            String[] preguntaActual = mensajeTokenizado[i].split(",,,");
            Pregunta pregunta = crearPregunta(preguntaActual);
            if (pregunta != null) {
                preguntas.agregarPregunta(pregunta);
            }
        }
        return preguntas;
    }

    /**
     * Crea una pregunta a partir de los datos proporcionados.
     * 
     * @param datosPregunta Los datos de la pregunta.
     * @return La pregunta creada o null si los datos son incorrectos.
     */
    private Pregunta crearPregunta(String[] datosPregunta) {
        Pregunta pregunta = null;
        String enunciado = datosPregunta[0];
        String tipo = datosPregunta[1];
        int puntaje = Integer.parseInt(datosPregunta[2]);

        switch (tipo) {
            case "Completar":
                String[] respuestas = datosPregunta[3].split(",");
                pregunta = new CompletarEspacio(enunciado, puntaje, respuestas);
                break;
            case "Multiple":
                if (datosPregunta.length < 8) {
                    System.out.println("Error: Pregunta de tipo Multiple mal formada: " + Arrays.toString(datosPregunta));
                }else{
                    String[] opciones = {datosPregunta[3], datosPregunta[4], datosPregunta[5], datosPregunta[6] };
                    String respuesta =  datosPregunta[7];
                    pregunta = new MultipleOpcion(enunciado, puntaje, opciones, false, respuesta);
                }
                break;
            case "VF":
                if (datosPregunta.length < 4) {
                    System.out.println("Error: Pregunta de tipo VF mal formada: " + Arrays.toString(datosPregunta));
                }else{
                    String[] opcionesVF = { "Verdadero", "Falso" };
                    String respuesta = datosPregunta[3];
                    pregunta = new MultipleOpcion(enunciado, puntaje, opcionesVF, true, respuesta);
                }
                break;
            default:
                System.out.println("Error: Tipo de pregunta desconocido: " + tipo);
                break;
        }
        return pregunta;
    }

    /**
     * Crea una nueva evaluación.
     * 
     * @param titulo              El título de la evaluación.
     * @param preguntas           El objeto Preguntas con las preguntas a incluir.
     * @param cantidadDePreguntas La cantidad de preguntas en la evaluación.
     * @param respuestasValidas   Indica si las respuestas son válidas.
     * @return Resultado de la creación y persistencia de la evaluación y código de
     *         estado HTTP.
     */
    private String crearEvaluacion(String titulo, Preguntas preguntas, Integer cantidadDePreguntas, boolean respuestasValidas) {
        String retorno = "";
        try {
            Evaluacion evaluacion = new Evaluacion(titulo, preguntas);
            evaluacion.setCantidadDePreguntas(cantidadDePreguntas);
            evaluacion.setRespuestasValidas(respuestasValidas);
            this.getEvaluaciones().agregarEvaluacion(evaluacion); // En memoria y en persistencia
            retorno = "Evaluación creada,;,200";
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DerivarEvaluaciones.class.getName()).log(Level.SEVERE, null, ex);
            retorno = "Error al crear la evaluación,;,500";
        }
        return retorno;
    }

    /**
     * 
     * Calcula el puntaje obtenido en la evaluación y actualiza o agrega el
     * historial del estudiante.
     * 
     * @return Resultado de la corrección y código de estado HTTP.
     */
    private String correccionEvaluacion() {
        String[] tokens = tokenizarMensaje(mensaje);
        Evaluacion evaluacion = this.getEvaluaciones().obtenerEvaluacion(tokens[1]);
        int puntajeObtenido = correccion(evaluacion.getListaPreguntas());

        if (this.getHistoriales().existeHistorial(tokens[1], tokens[0])) {
            Historial historial = this.getHistoriales().obtenerHistorial(tokens[1], tokens[0]);
            historial.setPuntaje(puntajeObtenido);
            this.getHistoriales().persistirHistoriales();
        } else {
            this.getHistoriales().agregarHistorial(new Historial(tokens[1], tokens[0], puntajeObtenido)); // En memoria y en persistencia
        }
        return "Historial agregado o modificado con éxito,;,200";
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
        String retorno = "";
        String[] tokens = tokenizarMensaje(mensaje);
        String titulo =  tokens[0]; // Título de la evaluación
        int indice = Integer.parseInt(tokens[1]); // El índice de la pregunta dentro de la evaluación
   
        Evaluacion evaluacion = this.getEvaluaciones().obtenerEvaluacion(titulo);
        if (indice < evaluacion.getListaPreguntas().getPreguntas().size()) { // Si existe el número de pregunta
            Pregunta pregunta = evaluacion.getListaPreguntas().obtenerPregunta(indice);
            String tipoPregunta = pregunta.obtenerTipo();
            if (tipoPregunta.equals("Multiple")) { // Si e multiple opción
                MultipleOpcion multiple = (MultipleOpcion) pregunta;
                retorno = tipoPregunta + ";;;" + multiple.getEnunciado() + ";;;"
                        + multiple.getOpciones()[0] + ";;;" + multiple.getOpciones()[1] + ";;;"
                        + multiple.getOpciones()[2] + ";;;" + multiple.getOpciones()[3] + ";;;"
                        + multiple.getPuntaje() + ",;,200";
            } else if (tipoPregunta.equals("VF")) { // Si es verdadero o falso
                MultipleOpcion vf = (MultipleOpcion) pregunta;
                retorno = tipoPregunta + ";;;" + vf.getEnunciado() + ";;;"
                        + vf.getPuntaje() + ",;,200";
            } else { // Si es completar espacios en blanco
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

        try {
            Evaluacion evaluacion = this.getEvaluaciones().obtenerEvaluacion(mensaje);
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

        try {
            Evaluacion evaluacion = this.getEvaluaciones().obtenerEvaluacion(mensaje);
            Boolean habilitado = evaluacion.respuestasHabilitadas();
            retorno = String.valueOf(habilitado) + ",;,200";
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
            int puntajeTotal = this.getEvaluaciones().obtenerPuntajeTotal(mensaje);
            String puntajeTotalEnString = String.valueOf(puntajeTotal);
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
        // Verificamos si hay evaluaciones disponibles
        if (this.getEvaluaciones().getListaEvaluaciones().isEmpty()) {
            retorno = "No existen evaluaciones,;,500";
        } else {
            // Generamos un índice aleatorio basado en la cantidad de evaluaciones
            // disponibles
            int randomIndex = (int) (Math.random() * this.getEvaluaciones().getListaEvaluaciones().size());
            // Obtenemos el título de la evaluación en el índice aleatorio
            retorno = this.getEvaluaciones().getListaEvaluaciones().get(randomIndex).getTitulo();
            retorno += ",;,200";
        }
        return retorno;
    }
}
