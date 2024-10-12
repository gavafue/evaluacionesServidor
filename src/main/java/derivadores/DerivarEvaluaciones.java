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
import java.util.LinkedList;
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
            case "CompararRespuestas":
                retorno = compararRespuestas();
                break;
            case "ObtenerCorrectas":
                retorno = derivarObtenerRespuestasCorrectas();
                break;
            case "ObtenerRespuestas":
                retorno = derivarObtenerRespuestasEstudiante();
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
            case "CalcularPorcentaje":
                retorno = calcularPorcentajeRespuestasCorrectas();
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
                this.getHistoriales().eliminarHistoriales(mensaje); // En memoria y en persistencia elimina los
                                                                    // historiales asociados a la evaluación
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
        if (existe) {
            retorno = "Evaluación existe,;,200";
        } else {
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

            // Comprobación si la lista está vacía
            if (listaTitulosEvaluaciones.isEmpty()) {
                return "No existen evaluaciones,;,500"; // Retorno si no hay evaluaciones
            }

            for (String parte : listaTitulosEvaluaciones) {
                listaEnString += parte + ";;;";
            }

            // Elimino el ;;; sobrante
            if (listaEnString.length() > 3) {
                listaEnString = listaEnString.substring(0, listaEnString.length() - 3);
            }

            return listaEnString + ",;,200"; // Retorno si hay evaluaciones
        } catch (Exception e) {
            return "Error al acceder a las evaluaciones,;,400"; // Retorno en caso de error
        }
    }

    /**
     * Método que desemboca la creación de una nueva evaluación.
     * 
     * @return Resultado de la creación de la evaluación y código de estado
     *         HTTP.
     */
    private String altaEvaluacion() {
        String retorno = "";
        String[] stringPreguntas = tokenizarMensaje(mensaje);
        if (this.getEvaluaciones().existeEvaluacion(stringPreguntas[0])) {
            retorno = "Ya existe evaluación con ese título,;,400";
        } else {
            Preguntas preguntas = procesarPreguntas(stringPreguntas);
            Integer cantidadDePreguntas = Integer.valueOf(stringPreguntas[stringPreguntas.length - 1]);
            boolean verRespuestas = Boolean.parseBoolean(stringPreguntas[stringPreguntas.length - 2]);
            /// Si esta realizar la evaluación
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
                    System.out
                            .println("Error: Pregunta de tipo Multiple mal formada: " + Arrays.toString(datosPregunta));
                } else {
                    String[] opciones = { datosPregunta[3], datosPregunta[4], datosPregunta[5], datosPregunta[6] };
                    String respuesta = datosPregunta[7];
                    pregunta = new MultipleOpcion(enunciado, puntaje, opciones, false, respuesta);
                }
                break;
            case "VF":
                if (datosPregunta.length < 4) {
                    System.out.println("Error: Pregunta de tipo VF mal formada: " + Arrays.toString(datosPregunta));
                } else {
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
    private String crearEvaluacion(String titulo, Preguntas preguntas, Integer cantidadDePreguntas,
            boolean respuestasValidas) {
        String retorno = "";
        try {
            Evaluacion evaluacion = new Evaluacion(titulo, preguntas);
            evaluacion.setCantidadDePreguntas(cantidadDePreguntas);
            evaluacion.setVerRespuestasHabilitado(respuestasValidas);
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
        try {
            // Verificar que el mensaje no sea nulo o vacío
            if (mensaje == null || mensaje.trim().isEmpty()) {
                throw new IllegalArgumentException("El mensaje proporcionado está vacío o es nulo.");
            }

            // Tokenizar el mensaje
            String[] tokens = tokenizarMensaje(mensaje);

            // Verificar que se hayan recibido suficientes tokens
            if (tokens.length < 3) {
                throw new IllegalArgumentException("El formato del mensaje es incorrecto, faltan tokens.");
            }

            // Obtener la evaluación con el ID proporcionado en tokens[1]
            Evaluacion evaluacion = this.getEvaluaciones().obtenerEvaluacion(tokens[1]);
            if (evaluacion == null) {
                throw new IllegalArgumentException(
                        "No se encontró una evaluación con el ID proporcionado: " + tokens[1]);
            }

            // Corregir las respuestas y calcular el puntaje obtenido
            int puntajeObtenido = correccion(evaluacion.getListaPreguntas());

            // Excluir los últimos dos elementos para obtener el tamaño correcto de las
            // respuestas
            int size = tokens.length - 2;
            String[] respuestas = new String[size];

            // Copiar las respuestas desde tokens[2] hasta tokens[tokens.length - 2]
            for (int i = 0; i < size; i++) {
                respuestas[i] = tokens[i + 2]; // 'i + 2' para empezar desde tokens[2]
            }

            // Verificar si ya existe un historial para el estudiante en la evaluación dada
            if (this.getHistoriales().existeHistorial(tokens[1], tokens[0])) {
                // Si existe, obtener el historial y actualizarlo
                Historial historial = this.getHistoriales().obtenerHistorial(tokens[1], tokens[0]);
                historial.setPuntaje(puntajeObtenido);
                historial.setRespuestas(respuestas);
                this.getHistoriales().persistirHistoriales(); // Persistir los cambios
            } else {
                // Si no existe, crear un nuevo historial y agregarlo
                Historial nuevoHistorial = new Historial(tokens[1], tokens[0], puntajeObtenido, respuestas);
                this.getHistoriales().agregarHistorial(nuevoHistorial); // Agregar en memoria y persistencia
            }

            return "Historial agregado o modificado con éxito,;,200";
        } catch (IllegalArgumentException e) {
            // Manejo de errores específicos de argumentos inválidos
            System.err.println("Error: " + e.getMessage());
            return "Error en los datos proporcionados: " + e.getMessage() + ",;,400";
        } catch (Exception e) {
            // Capturar cualquier otro error inesperado
            System.err.println("Error inesperado: " + e.getMessage());
            return "Error inesperado al procesar la corrección: " + e.getMessage() + ",;,500";
        }
    }

    /**
     * Compara las respuestas correctas de una evaluación con las respuestas
     * proporcionadas por el estudiante
     * y devuelve una cadena indicando si cada respuesta es "Correcto" o
     * "Incorrecto". Si las respuestas
     * no coinciden en número o el estudiante no tiene historial, devuelve un
     * mensaje de error.
     * 
     * La cadena devuelta tiene el siguiente formato:
     * - Si la comparación es exitosa: "Correcto,,,Correcto,,,Incorrecto,;,200"
     * - Si hay un error: "La cantidad de respuestas no coincide,;,500" o "NO
     * existen respuestas,;,500"
     * 
     * El método sigue los siguientes pasos:
     * 1. Obtiene las respuestas correctas de la evaluación.
     * 2. Obtiene el historial del estudiante con base en su identificación (CI).
     * 3. Compara las respuestas del historial con las respuestas correctas.
     * 4. Devuelve los resultados de la comparación en una
     * cadena.
     *
     * Datos que recibe el método: -
     * titulo;;;22222222,;,Evaluaciones,;,CompararRespuestas" - La primera parte
     * ("Evaluacion de prueba de null en completar espacios") es el
     * identificador de la evaluación. - "22222222" es el número de
     * identificación del estudiante (CI). - "Evaluaciones" indica la acción a
     * realizar, en este caso trabajar con evaluaciones. - "CompararRespuestas"
     * es el comando que activa este método de comparación de respuestas.
     *
     * @return Una cadena que indica el resultado de la comparación, en formato
     *         "Correcto,,,Incorrecto,;,200" o un mensaje de error con código
     *         correspondiente.
     */
    private String compararRespuestas() {
        String retorno = "";
        String[] tokens = tokenizarMensaje(mensaje);

        Evaluacion evaluacion = this.getEvaluaciones().obtenerEvaluacion(tokens[0]);
        Preguntas preguntas = evaluacion.getListaPreguntas();
        LinkedList<Historial> historialesEvaluacion = this.getHistoriales().obtenerHistoriales(tokens[0]);
        ArrayList<String> resultados = new ArrayList<>(); // Lista para almacenar "Correcto" o "Incorrecto"

        boolean existe = false;

        for (Historial historial : historialesEvaluacion) {
            if (historial.getCiEstudiante().equals(tokens[1])) {
                existe = true;
                String[] respuestas = historial.getRespuestas();
                if (preguntas.getPreguntas().size() != respuestas.length) {
                    retorno = "La cantidad de respuestas no coincide,;,500";
                } else {
                    for (int i = 0; i < preguntas.getPreguntas().size(); i++) {
                        if (preguntas.obtenerPregunta(i).esCorrecta(respuestas[i])) {
                            resultados.add("Correcto");
                        } else {
                            resultados.add("Incorrecto");
                        }
                    }
                }
            }
        }
        if (!existe) {
            retorno = "NO existen respuestas,;,500";
        } else {
            // Se convierte la lista de resultados en un string
            retorno = String.join(",,,", resultados);
            retorno += ",;,200";
        }
        return retorno;
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
        String titulo = tokens[0]; // Título de la evaluación
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
    private String derivarObtenerRespuestasCorrectas() {
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
     * Método que retorna las respuestas dadas por un estudiante al realizar una
     * evaluación.
     * 
     * @return respuestas del estudiantante.
     */
    private String derivarObtenerRespuestasEstudiante() {
        String retorno = "";
        String[] datos = this.tokenizarMensaje(mensaje);
        String tituloEvaluacion = datos[0];
        String ciEstudiante = datos[1];
        boolean existe = false;
        // Obtener el historial de la lista
        Evaluacion evaluacion = this.getEvaluaciones().obtenerEvaluacion(tituloEvaluacion);
        LinkedList<Historial> historialesEvaluacion = this.getHistoriales().obtenerHistoriales(tituloEvaluacion);
        for (Historial historial : historialesEvaluacion) {
            // Verificamos si el CI del estudiante coincide
            if (historial.getCiEstudiante().equals(ciEstudiante)) {
                existe = true;
                // Concatenamos las respuestas separadas por ",,,"
                String[] respuestas = historial.getRespuestas();
                for (int i = 0; i < respuestas.length; i++) {
                    retorno += evaluacion.obtenerEnunciadoPregunta(i) + ",,," + respuestas[i] + ";;;";
                }
                // Elimino el ;;; sobrante
                if (retorno.length() > 3) {
                    retorno = retorno.substring(0, retorno.length() - 3);
                }
            }
        }
        if (existe == true) {
            retorno += ",;,200";
        } else {
            retorno = "NO existen respuestas,;,500";
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
            Boolean habilitado = evaluacion.getVerRespuestasHabilitado();
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
        String retorno; // Variable para almacenar el resultado

        try {
            // Verifica si la evaluación existe antes de intentar obtener el puntaje
            if (!this.getEvaluaciones().existeEvaluacion(mensaje)) {
                retorno = "Evaluación NO existe,;,500"; // Mensaje de error para evaluación no existente
            } else {
                int puntajeTotal = this.getEvaluaciones().obtenerPuntajeTotal(mensaje);
                String puntajeTotalEnString = String.valueOf(puntajeTotal);
                retorno = puntajeTotalEnString + ",;,200"; // Mensaje de éxito con puntaje
            }
        } catch (IllegalArgumentException e) {
            // Manejo de errores cuando el título de la evaluación es inválido
            System.err.println("Error al obtener el puntaje total de la evaluación: " + e.getMessage());
            retorno = "Error: " + e.getMessage() + ",;,400"; // Código de estado 400 para error de solicitud
        } catch (Exception e) {
            // Manejo de cualquier otra excepción inesperada
            System.err.println("Error inesperado al obtener el puntaje total de la evaluación: " + e.getMessage());
            retorno = "Error inesperado: " + e.getMessage() + ",;,500"; // Código de estado 500 para error interno
        }

        return retorno; // Retorna el resultado al final
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

    /**
     * Llama al método compararRespuestas y calcula el porcentaje de respuestas
     * correctas,
     * redondeando al entero más cercano. Devuelve el porcentaje en formato de
     * cadena.
     * 
     * El porcentaje se calcula en base a la cantidad de respuestas correctas
     * comparadas
     * con el total de respuestas proporcionadas. Si hay un error (por ejemplo, si
     * las
     * respuestas no coinciden en cantidad), devuelve un mensaje de error.
     * 
     * @return Una cadena con el porcentaje de respuestas correctas, redondeado al
     *         entero más cercano,
     *         o un mensaje de error si ocurre algún problema.
     */
    private String calcularPorcentajeRespuestasCorrectas() {
        // Llama al método compararRespuestas para obtener el resultado
        String resultado = compararRespuestas();
        String[] indicadores = resultado.split(",;,");

        // Si el resultado contiene un mensaje de error, devuelve el error como cadena
        if (resultado.contains("500")) {
            return "La cantidad de respuestas no coincide,;,500";
        }

        // Divide la cadena de respuestas en base a los separadores ";;;" y ",,,"
        String[] respuestas = indicadores[0].split(",,,");

        // Cuenta las respuestas correctas
        int correctas = 0;
        for (String respuesta : respuestas) {
            if (respuesta.equals("Correcto")) {
                correctas++;
            }
        }

        // Calcula el porcentaje de respuestas correctas
        int totalRespuestas = respuestas.length;

        // Redondea el porcentaje al entero más cercano
        int porcentaje = Math.round((correctas * 100f) / totalRespuestas);

        // Devuelve el porcentaje en formato de cadena
        return porcentaje + ",;,200";
    }

}
