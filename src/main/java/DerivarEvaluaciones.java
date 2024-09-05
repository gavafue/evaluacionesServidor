/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gabriel
 */
public class DerivarEvaluaciones {

    private String operacion;
     private String mensaje;
 
     /**
      * Constructor que inicializa la operación y el mensaje.
      * 
      * @param operacion La operación a realizar (e.g., "Alta", "Login").
      * @param mensaje El mensaje que contiene datos necesarios para la operación.
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
     *
     * @param operacion
     * @return Una cadena con el resultado de la operación y el código de estado
     *         HTTP correspondiente.
     */
    public String derivarEvaluaciones() {
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();
        String retorno = null;

        switch (operacion) {

            case "Eliminar":
                if (evaluaciones.existeEvaluacion(mensaje)) {

                    try {
                        evaluaciones.eliminarEvaluacion(mensaje);
                        retorno = "Evaluacion eliminada,;,200";
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Switch.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    retorno = "Evaluacion NO existe,;,500";
                }
                break;
            case "Existencia":

                if (evaluaciones.existeEvaluacion(mensaje)) {
                    retorno = "Evaluacion existe,;,200";
                } else {
                    retorno = "Evaluacion NO existe,;,500";

                }
                break;
            case "Listar":
                String listaEnString = "";
                try {
                    List<String> listaTitulosEvaluaciones = evaluaciones.obtenerTítulosEvaluaciones();
                    for (String parte : listaTitulosEvaluaciones) {
                        listaEnString += parte + ";;;";
                    }
                    retorno = listaEnString + ",;,200";
                } catch (Exception e) {
                    // Manejo de la excepción
                    // System.err.println("Ocurrió un error al leer el archivo: " + e.getMessage());
                    retorno = "Error al acceder a las evaluaciones,;,400";
                }
                break;
            case "Alta":
                // Divide el mensaje en partes usando el delimitador ';;;'
                String[] mensajeTokenizado = mensaje.split(";;;");
                System.out.println("Mensaje tokenizado: " + Arrays.toString(mensajeTokenizado));

                // Verifica si ya existe una evaluación con el mismo título
                if (!evaluaciones.existeEvaluacion(mensajeTokenizado[0])) {
                    // Crear un objeto para almacenar las preguntas de la evaluación
                    Preguntas ps = new Preguntas();

                    // Itera sobre todas las preguntas, excluyendo el último token que es el total
                    for (int i = 1; i < mensajeTokenizado.length - 2; i++) { // Excluyendo el total
                        // Divide la información de cada pregunta en partes usando el delimitador ',,,'
                        String[] preguntaActual = mensajeTokenizado[i].split(",,,");
                        System.out.println("Procesando pregunta: " + Arrays.toString(preguntaActual));

                        Pregunta p = null;
                        String enunciadoPregunta = preguntaActual[0];
                        String tipoPregunta = preguntaActual[1];
                        int puntajePregunta = Integer.parseInt(preguntaActual[2]);

                        // Crea la pregunta según el tipo especificado
                        switch (tipoPregunta) {
                            case "Completar":
                                String[] respuestas = preguntaActual[3].split(",");
                                p = new CompletarEspacio(enunciadoPregunta, puntajePregunta, respuestas);
                                break;
                            case "Multiple":
                                if (preguntaActual.length < 8) {
                                    System.out.println("Error: Pregunta de tipo Multiple mal formada: "
                                            + Arrays.toString(preguntaActual));
                                    continue;
                                }
                                String[] opciones = { preguntaActual[3], preguntaActual[4], preguntaActual[5],
                                        preguntaActual[6] };
                                p = new MultipleOpcion(enunciadoPregunta, puntajePregunta, opciones, false,
                                        preguntaActual[7]);
                                break;
                            case "VF":
                                if (preguntaActual.length < 4) {
                                    System.out.println("Error: Pregunta de tipo VF mal formada: "
                                            + Arrays.toString(preguntaActual));
                                    continue;
                                }
                                String[] opcionesVF = { "Verdadero", "Falso" };
                                p = new MultipleOpcion(enunciadoPregunta, puntajePregunta, opcionesVF, true,
                                        preguntaActual[3]);
                                break;
                            default:
                                continue;
                        }

                        ps.agregarPregunta(p);
                    }
                    Integer cantidadDePreguntas = Integer.valueOf(mensajeTokenizado[mensajeTokenizado.length - 1]);
                    String respuestasValidasStr = mensajeTokenizado[mensajeTokenizado.length - 2];
                    boolean respuestasValidas = respuestasValidasStr.equals("true");

                    try {
                        Evaluacion ev = new Evaluacion(mensajeTokenizado[0], ps);
                        ev.setCantidadDePreguntas(cantidadDePreguntas);
                        ev.setRespuestasValidas(respuestasValidas);
                        evaluaciones.actualizarListaEvaluaciones();
                        evaluaciones.agregarEvaluacion(ev);
                        evaluaciones.persistirEvaluaciones(evaluaciones.getEvaluaciones());

                        retorno = "Evaluacion creada,;,200";
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Switch.class.getName()).log(Level.SEVERE, null, ex);
                        retorno = "Error al crear la evaluación,;,500";
                    }
                } else {
                    retorno = "Ya existe evaluación con ese título,;,400";
                }
                break;

            case "ObtenerPregunta":
                String[] tokens = mensaje.split(";;;");
                retorno = obtenerPregunta(tokens[0], Integer.parseInt(tokens[1])); // titulo y numero pregunta
                break;
            case "Correccion":
                Historiales hs = new Historiales();
                hs.actualizarHistoriales();
                String[] tokens2 = mensaje.split(";;;");
                evaluaciones.actualizarListaEvaluaciones();
                Evaluacion evaluacion = evaluaciones.obtenerEvaluacion(tokens2[1]);
                int puntajeObtenido = correccion(evaluacion.getListaPreguntas());
                if (hs.existeHistorial(tokens2[1], tokens2[0])) {
                    Historial h = hs.obtenerHistorial(tokens2[1], tokens2[0]);
                    h.setPuntaje(puntajeObtenido);
                    hs.persistirHistoriales();
                } else {
                    hs.agregarHistorial(new Historial(tokens2[1], tokens2[0], puntajeObtenido));
                }
                retorno = "Historial agregado con exito,;,200";
                break;
            case "ObtenerCorrectas":
                retorno = derivarObtenerRespuestas();
                break;
            case "ValorCheckboxRespuestas":
                retorno = obtenerValorCheckboxRespuestas();
                break;
        }
        return retorno;
    }
}
