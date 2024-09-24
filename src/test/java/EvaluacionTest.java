
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import logica.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import persistencia.PersistirEvaluaciones;

/**
 * Clase de prueba unitaria para las funcionalidades relacionadas con la clase
 * Evaluacion.
 * Contiene varios métodos de prueba para verificar la creación, persistencia,
 * recuperación,
 * y eliminación de evaluaciones en el sistema.
 */
public class EvaluacionTest {

    /**
     * Prueba que verifica la correcta creación de una evaluación con preguntas
     * válidas.
     * Se crean varias preguntas de opción múltiple y de completar espacios, luego
     * se
     * agregan a la evaluación. Finalmente, se verifica que las respuestas correctas
     * coinciden con las esperadas.
     */
    @Test
    public void testCrearEvaluacionConPreguntasValidas() {
        // Crear preguntas
        Pregunta p1 = new MultipleOpcion("¿Cuál es la capital de Francia?", 1,
                new String[] { "París", "Berlín", "Madrid" }, false, "París");
        Pregunta p2 = new MultipleOpcion("¿Cuál es el río más largo del mundo?", 1,
                new String[] { "Amazonas", "Nilo", "Yangtsé" }, false, "Amazonas");
        Pregunta p3 = new MultipleOpcion("¿Qué continente es Australia?", 1,
                new String[] { "Asia", "Oceanía", "Europa" }, false, "Oceanía");
        Pregunta p4 = new MultipleOpcion("¿Cuál es el país más grande del mundo?", 1,
                new String[] { "China", "Estados Unidos", "Rusia" }, false, "Rusia");
        Pregunta p5 = new MultipleOpcion("¿Qué océano está al este de África?", 1,
                new String[] { "Atlántico", "Pacífico", "Índico" }, false, "Índico");
        Pregunta p6 = new CompletarEspacio("Completa: El desierto más grande del mundo es el ____.",
                1, new String[] { "Sahara" });

        // Crear objeto Preguntas utilizando el constructor vacío
        Preguntas preguntas = new Preguntas();

        // Agregar las preguntas una por una
        preguntas.agregarPregunta(p1);
        preguntas.agregarPregunta(p2);
        preguntas.agregarPregunta(p3);
        preguntas.agregarPregunta(p4);
        preguntas.agregarPregunta(p5);
        preguntas.agregarPregunta(p6);

        // Crear evaluación
        Evaluacion evaluacion = new Evaluacion("Evaluación de Geografía", preguntas);

        // Verificar título
        assertEquals("Evaluación de Geografía", evaluacion.getTitulo());

        // Verificar número de preguntas
        assertEquals(6, evaluacion.getListaPreguntas().getPreguntas().size());

        // Obtener respuestas correctas
        List<String> respuestasCorrectas = evaluacion.obtenerRespuestasCorrectas();

        // Separar las respuestas del formato "enunciado,,,respuesta"
        assertEquals("París", obtenerRespuestaCorrecta(respuestasCorrectas.get(0))); // París
        assertEquals("Amazonas", obtenerRespuestaCorrecta(respuestasCorrectas.get(1))); // Amazonas
        assertEquals("Oceanía", obtenerRespuestaCorrecta(respuestasCorrectas.get(2))); // Oceanía
        assertEquals("Rusia", obtenerRespuestaCorrecta(respuestasCorrectas.get(3))); // Rusia
        assertEquals("Índico", obtenerRespuestaCorrecta(respuestasCorrectas.get(4))); // Índico
        assertArrayEquals(new String[] { "Sahara" }, obtenerRespuestaCorrecta(respuestasCorrectas.get(5)).split("\\*")); // Sahara
    }

    /**
     * Prueba que intenta obtener una evaluación que no existe en el sistema.
     * Se espera que el resultado sea null.
     */
    @Test
    public void testObtenerEvaluacionQueNoExiste() {
        // Crear una instancia del sistema de evaluaciones
        Evaluaciones evs = new Evaluaciones();
        evs.actualizarListaEvaluaciones();

        // Intentar obtener una evaluación que no existe
        Evaluacion evaluacion = evs.obtenerEvaluacion("Evaluación Inexistente");

        // Verificar que el resultado sea null (evaluación no encontrada)
        assertNull(evaluacion, "Se esperaba que la evaluación no exista y el resultado sea null.");
    }

    /**
     * Prueba que verifica la correcta eliminación de una evaluación existente en el
     * sistema.
     * Primero se agrega una evaluación y luego se verifica que ha sido eliminada.
     *
     * @throws Exception Si la eliminación falla por alguna razón inesperada.
     */
    @Test
    public void testEliminarEvaluacionExistente() throws Exception {
        // Crear una instancia de Evaluaciones
        Evaluaciones sistemaEvaluaciones = new Evaluaciones();
        sistemaEvaluaciones.actualizarListaEvaluaciones();

        // Crear una instancia de Preguntas vacía
        Preguntas preguntas = new Preguntas();
        Pregunta p1 = new MultipleOpcion("¿Cuál es la capital de Francia?", 1,
                new String[] { "París", "Berlín", "Madrid" }, false, "París");
        Pregunta p2 = new MultipleOpcion("¿Cuál es el río más largo del mundo?", 1,
                new String[] { "Amazonas", "Nilo", "Yangtsé" }, false, "Amazonas");
        Pregunta p3 = new MultipleOpcion("¿Qué continente es Australia?", 1,
                new String[] { "Asia", "Oceanía", "Europa" }, false, "Oceanía");
        Pregunta p4 = new MultipleOpcion("¿Cuál es el país más grande del mundo?", 1,
                new String[] { "China", "Estados Unidos", "Rusia" }, false, "Rusia");
        Pregunta p5 = new MultipleOpcion("¿Qué océano está al este de África?", 1,
                new String[] { "Atlántico", "Pacífico", "Índico" }, false, "Índico");
        Pregunta p6 = new CompletarEspacio("Completa: El desierto más grande del mundo es el ____.",
                1, new String[] { "Sahara" });

        // Agregar las preguntas una por una
        preguntas.agregarPregunta(p1);
        preguntas.agregarPregunta(p2);
        preguntas.agregarPregunta(p3);
        preguntas.agregarPregunta(p4);
        preguntas.agregarPregunta(p5);
        preguntas.agregarPregunta(p6);

        // Crear una evaluación con la instancia de Preguntas
        Evaluacion evaluacion = new Evaluacion("Evaluación de Matemáticas", preguntas);

        // Agregar la evaluación al sistema
        sistemaEvaluaciones.agregarEvaluacion(evaluacion);

        // Verificar que la evaluación existe antes de eliminar
        Evaluacion evaluacionAntesDeEliminar = sistemaEvaluaciones.obtenerEvaluacion("Evaluación de Matemáticas");
        assertNotNull(evaluacionAntesDeEliminar, "La evaluación debería existir antes de eliminar.");

        // Eliminar la evaluación
        sistemaEvaluaciones.eliminarEvaluacion("Evaluación de Matemáticas");

        // Verificar que la evaluación ha sido eliminada
        Evaluacion evaluacionDespuesDeEliminar = sistemaEvaluaciones.obtenerEvaluacion("Evaluación de Matemáticas");
        assertNull(evaluacionDespuesDeEliminar, "La evaluación debería haber sido eliminada.");
    }

    /**
     * Prueba que verifica la correcta persistencia de evaluaciones en un archivo de
     * texto.
     * Se crea una evaluación, se guarda en el archivo y luego se valida que la
     * evaluación
     * se haya guardado correctamente en el archivo.
     *
     * @throws IOException Si ocurre un error al leer o escribir el archivo de
     *                     evaluaciones.
     */
    @Test
    public void testPersistirEvaluaciones() throws IOException {
        // Crear una instancia de PersistirEvaluaciones
        PersistirEvaluaciones persistirEvaluaciones = new PersistirEvaluaciones();
        Evaluaciones evaluaciones = new Evaluaciones();
        evaluaciones.actualizarListaEvaluaciones();

        // Crear una lista de evaluaciones para guardar

        Preguntas preguntas = new Preguntas();
        preguntas.agregarPregunta(new CompletarEspacio("Completar", 10, new String[] { "respuesta1", "respuesta2" }));
        preguntas.agregarPregunta(new MultipleOpcion("Multiple", 5,
                new String[] { "opcion1", "opcion2", "opcion3", "opcion4" }, false, "opcion1"));

        Evaluacion evaluacion = new Evaluacion("Evaluacion1", preguntas, true);
        evaluacion.setCantidadDePreguntas(2);
        evaluaciones.agregarEvaluacion(evaluacion);

        // Persistir las evaluaciones en el archivo
        persistirEvaluaciones.persistirEvaluacionesEnArchivo(evaluaciones.getListaEvaluaciones());
        Evaluaciones listadesdearchivo = persistirEvaluaciones.cargarEvaluacionesDesdeArchivo();
        Boolean existeEvaluacion = listadesdearchivo.existeEvaluacion("Evaluacion1");

        assertTrue(existeEvaluacion, "No se encontro la evaluación creada en el archivo");
        try {
            evaluaciones.eliminarEvaluacion("Evaluacion1");
        } catch (FileNotFoundException e) {
            fail("No se pudo eliminar la evaluacion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Prueba que verifica la correcta recuperación de una evaluación con preguntas.
     * Se simula la existencia de una evaluación con un conjunto de preguntas,
     * y se valida que las preguntas recuperadas coinciden con las esperadas.
     */
    @Test
    public void testRecuperarEvaluacionConPreguntas() {
        // Suponiendo que ya existe una evaluación con ID 1234
        String idEvaluacion = "1234";

        // Crear la evaluación y agregar preguntas
        Evaluaciones evaluaciones = new Evaluaciones();

        // Definir puntaje y opciones
        int puntajePregunta = 10; // Ejemplo de puntaje
        boolean esVerdaderoOFalso = false; // Indica que no es una pregunta de verdadero o falso

        // Crear preguntas
        MultipleOpcion pregunta1 = new MultipleOpcion("¿Cuál es la capital de Francia?", puntajePregunta,
                new String[] { "Berlín", "Madrid", "París", "Lisboa" }, esVerdaderoOFalso, "París");
        MultipleOpcion pregunta2 = new MultipleOpcion("¿Cuál es la capital de España?", puntajePregunta,
                new String[] { "Berlín", "Madrid", "París", "Lisboa" }, esVerdaderoOFalso, "Madrid");
        MultipleOpcion pregunta3 = new MultipleOpcion("¿Cuál es la capital de Italia?", puntajePregunta,
                new String[] { "Roma", "Milán", "Venecia", "Nápoles" }, esVerdaderoOFalso, "Roma");
        MultipleOpcion pregunta4 = new MultipleOpcion("¿Cuál es la capital de Alemania?", puntajePregunta,
                new String[] { "Berlín", "Múnich", "Fráncfort", "Hamburgo" }, esVerdaderoOFalso, "Berlín");

        // Crear conjunto de preguntas
        Preguntas conjuntoPreguntas = new Preguntas();
        conjuntoPreguntas.agregarPregunta(pregunta1);
        conjuntoPreguntas.agregarPregunta(pregunta2);
        conjuntoPreguntas.agregarPregunta(pregunta3);
        conjuntoPreguntas.agregarPregunta(pregunta4);

        // Agregar evaluación al sistema
        try {
            evaluaciones.agregarEvaluacion(new Evaluacion(idEvaluacion, conjuntoPreguntas));
        } catch (FileNotFoundException e) {
            fail("No se pudo agregar la evaluación: " + e.getMessage());
        }

        // Recuperar la evaluación con el ID proporcionado
        Evaluacion evaluacionRecuperada = evaluaciones.obtenerEvaluacion(idEvaluacion);

        // Validar que la evaluación recuperada no sea null
        assertNotNull(evaluacionRecuperada, "Se esperaba una evaluación con el ID proporcionado.");

        // Validar que las preguntas sean las esperadas
        List<Pregunta> listaPreguntasRecuperadas = evaluacionRecuperada.getListaPreguntas().getPreguntas();
        assertEquals(4, listaPreguntasRecuperadas.size(), "Se esperaban 4 preguntas en la evaluación.");

        // Validar preguntas individuales
        assertEquals(pregunta1.getEnunciado(), listaPreguntasRecuperadas.get(0).getEnunciado());
        assertEquals(pregunta2.getEnunciado(), listaPreguntasRecuperadas.get(1).getEnunciado());
        assertEquals(pregunta3.getEnunciado(), listaPreguntasRecuperadas.get(2).getEnunciado());
        assertEquals(pregunta4.getEnunciado(), listaPreguntasRecuperadas.get(3).getEnunciado());

        try {
            evaluaciones.eliminarEvaluacion(idEvaluacion);
        } catch (FileNotFoundException e) {
            fail("No se pudo eliminar la evaluacion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método auxiliar para obtener la respuesta correcta a partir de una cadena
     * formateada.
     * El formato esperado es "enunciado,,,respuesta", por lo que se divide usando
     * ",,,".
     *
     * @param respuesta Enunciado y respuesta en un formato específico.
     * @return La respuesta correcta extraída de la cadena proporcionada.
     */
    private String obtenerRespuestaCorrecta(String respuesta) {
        return respuesta.split(",,,")[1];
    }
}
