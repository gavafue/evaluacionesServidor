
import derivadores.DerivarEvaluaciones;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import logica.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.PersistirEvaluaciones;

public class EvaluacionTest {

    @Test
    public void testCrearEvaluacionConPreguntasValidas() {
        // Crear preguntas
        Pregunta p1 = new MultipleOpcion("¿Cuál es la capital de Francia?", 1,
                new String[]{"París", "Berlín", "Madrid"}, false, "París");
        Pregunta p2 = new MultipleOpcion("¿Cuál es el río más largo del mundo?", 1,
                new String[]{"Amazonas", "Nilo", "Yangtsé"}, false, "Amazonas");
        Pregunta p3 = new MultipleOpcion("¿Qué continente es Australia?", 1,
                new String[]{"Asia", "Oceanía", "Europa"}, false, "Oceanía");
        Pregunta p4 = new MultipleOpcion("¿Cuál es el país más grande del mundo?", 1,
                new String[]{"China", "Estados Unidos", "Rusia"}, false, "Rusia");
        Pregunta p5 = new MultipleOpcion("¿Qué océano está al este de África?", 1,
                new String[]{"Atlántico", "Pacífico", "Índico"}, false, "Índico");
        Pregunta p6 = new CompletarEspacio("Completa: El desierto más grande del mundo es el ____.",
                1, new String[]{"Sahara"});

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
        assertEquals("París", obtenerRespuestaCorrecta(respuestasCorrectas.get(0)));  // París
        assertEquals("Amazonas", obtenerRespuestaCorrecta(respuestasCorrectas.get(1)));  // Amazonas
        assertEquals("Oceanía", obtenerRespuestaCorrecta(respuestasCorrectas.get(2)));  // Oceanía
        assertEquals("Rusia", obtenerRespuestaCorrecta(respuestasCorrectas.get(3)));  // Rusia
        assertEquals("Índico", obtenerRespuestaCorrecta(respuestasCorrectas.get(4)));  // Índico
        assertArrayEquals(new String[]{"Sahara"}, obtenerRespuestaCorrecta(respuestasCorrectas.get(5)).split("\\*"));  // Sahara
    }

    @Test
    public void testObtenerEvaluacionQueNoExiste() {
        // Crear una instancia del sistema de evaluaciones
        Evaluaciones evs = new Evaluaciones();

        // Intentar obtener una evaluación que no existe
        Evaluacion evaluacion = evs.obtenerEvaluacion("Evaluación Inexistente");

        // Verificar que el resultado sea null (evaluación no encontrada)
        assertNull(evaluacion, "Se esperaba que la evaluación no exista y el resultado sea null.");
    }

    @Test
    public void testEliminarEvaluacionExistente() throws Exception {
        // Crear una instancia de Evaluaciones
        Evaluaciones sistemaEvaluaciones = new Evaluaciones();

        // Crear una instancia de Preguntas vacía
        Preguntas preguntas = new Preguntas();

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

    // Ruta del archivo de persistencia para las pruebas
    private static final String RUTA_ARCHIVO = "/home/gonzalito/Documentos/GitHub/evaluacionesServidor/evaluaciones.txt";

    @Test
    public void testPersistirEvaluaciones() throws IOException {
        // Crear una instancia de PersistirEvaluaciones
        PersistirEvaluaciones persistirEvaluaciones = new PersistirEvaluaciones();

        // Crear una lista de evaluaciones para guardar
        List<Evaluacion> listaEvaluaciones = new ArrayList<>();
        Preguntas preguntas = new Preguntas();
        preguntas.agregarPregunta(new CompletarEspacio("Completar", 10, new String[]{"respuesta1", "respuesta2"}));
        preguntas.agregarPregunta(new MultipleOpcion("Multiple", 5, new String[]{"opcion1", "opcion2", "opcion3", "opcion4"}, false, "opcion1"));

        Evaluacion evaluacion = new Evaluacion("Evaluacion1", preguntas, true);
        evaluacion.setCantidadDePreguntas(2);
        listaEvaluaciones.add(evaluacion);

        // Persistir las evaluaciones en el archivo
        persistirEvaluaciones.persistirEvaluacionesEnArchivo(listaEvaluaciones);

        // Leer y validar el contenido del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            boolean contieneEvaluacion1 = false;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea); // Imprimir el contenido del archivo para depuración
                if (linea.contains("Evaluacion1")) {
                    contieneEvaluacion1 = true;
                    break;
                }
            }
            assertTrue(contieneEvaluacion1, "El archivo no contiene la evaluación esperada.");
        }
    }

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
        MultipleOpcion pregunta1 = new MultipleOpcion("¿Cuál es la capital de Francia?", puntajePregunta, new String[]{"Berlín", "Madrid", "París", "Lisboa"}, esVerdaderoOFalso, "París");
        MultipleOpcion pregunta2 = new MultipleOpcion("¿Cuál es la capital de España?", puntajePregunta, new String[]{"Berlín", "Madrid", "París", "Lisboa"}, esVerdaderoOFalso, "Madrid");
        MultipleOpcion pregunta3 = new MultipleOpcion("¿Cuál es la capital de Italia?", puntajePregunta, new String[]{"Roma", "Milán", "Venecia", "Nápoles"}, esVerdaderoOFalso, "Roma");
        MultipleOpcion pregunta4 = new MultipleOpcion("¿Cuál es la capital de Alemania?", puntajePregunta, new String[]{"Berlín", "Múnich", "Fráncfort", "Hamburgo"}, esVerdaderoOFalso, "Berlín");
        MultipleOpcion pregunta5 = new MultipleOpcion("¿Cuál es la capital de Portugal?", puntajePregunta, new String[]{"Lisboa", "Oporto", "Coímbra", "Braga"}, esVerdaderoOFalso, "Lisboa");
        MultipleOpcion pregunta6 = new MultipleOpcion("¿Cuál es la capital de Bélgica?", puntajePregunta, new String[]{"Bruselas", "Amberes", "Gante", "Brujas"}, esVerdaderoOFalso, "Bruselas");

        // Agregar preguntas a la evaluación
        Evaluacion evaluacion = new Evaluacion(idEvaluacion);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta1);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta2);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta3);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta4);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta5);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta6);

        try {
            evaluaciones.agregarEvaluacion(evaluacion);
        } catch (FileNotFoundException e) {
            fail("No se pudo agregar la evaluación: " + e.getMessage());
        }

        // Intentar recuperar la evaluación
        Evaluacion recuperada = evaluaciones.obtenerEvaluacion(idEvaluacion);

        // Verificar que todas las preguntas se carguen correctamente
        assertNotNull(recuperada, "La evaluación no debe ser nula");
        assertEquals(6, recuperada.getListaPreguntas().getPreguntas().size(), "La cantidad de preguntas debe ser 6");

        // Verificar que las preguntas sean las esperadas
        assertEquals("¿Cuál es la capital de Francia?", recuperada.getListaPreguntas().obtenerPregunta(0).getEnunciado());
        assertEquals("¿Cuál es la capital de España?", recuperada.getListaPreguntas().obtenerPregunta(1).getEnunciado());
        assertEquals("¿Cuál es la capital de Italia?", recuperada.getListaPreguntas().obtenerPregunta(2).getEnunciado());
        assertEquals("¿Cuál es la capital de Alemania?", recuperada.getListaPreguntas().obtenerPregunta(3).getEnunciado());
        assertEquals("¿Cuál es la capital de Portugal?", recuperada.getListaPreguntas().obtenerPregunta(4).getEnunciado());
        assertEquals("¿Cuál es la capital de Bélgica?", recuperada.getListaPreguntas().obtenerPregunta(5).getEnunciado());
    }

    @Test
    public void testCrearEvaluacionConPreguntasDeDiferentesTipos() {
        // Suponiendo que el usuario ya está autenticado
        String nombreEvaluacion = "Evaluación Mixta";
        Evaluaciones evaluaciones = new Evaluaciones();

        // Definir puntaje
        int puntajePregunta = 10; // Ejemplo de puntaje

        // Crear preguntas de diferentes tipos
        MultipleOpcion pregunta1 = new MultipleOpcion("¿Cuál es la capital de Francia?", puntajePregunta,
                new String[]{"Berlín", "Madrid", "París", "Lisboa"}, false, "París");
        CompletarEspacio pregunta2 = new CompletarEspacio("La capital de España es ____.", puntajePregunta, new String[]{"Madrid"});
        MultipleOpcion pregunta3 = new MultipleOpcion("¿Cuál es la capital de Italia?", puntajePregunta,
                new String[]{"Roma", "Milán", "Venecia", "Nápoles"}, false, "Roma");
        MultipleOpcion pregunta4 = new MultipleOpcion("¿Es el sol una estrella?", puntajePregunta,
                new String[]{"Sí", "No"}, true, "Sí");
        CompletarEspacio pregunta5 = new CompletarEspacio("La capital de Alemania es ____.", puntajePregunta, new String[]{"Berlín"});
        MultipleOpcion pregunta6 = new MultipleOpcion("¿Es el agua H2O?", puntajePregunta,
                new String[]{"Sí", "No"}, true, "Sí");

        // Crear una evaluación y agregar preguntas
        Evaluacion evaluacion = new Evaluacion(nombreEvaluacion);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta1);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta2);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta3);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta4);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta5);
        evaluacion.getListaPreguntas().agregarPregunta(pregunta6);

        try {
            // Agregar evaluación a la lista de evaluaciones
            evaluaciones.agregarEvaluacion(evaluacion);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EvaluacionTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Intentar recuperar la evaluación
        Evaluacion recuperada = evaluaciones.obtenerEvaluacion(nombreEvaluacion);

        // Verificar que la evaluación fue creada correctamente
        assertNotNull(recuperada, "La evaluación no debe ser nula");
        assertEquals(nombreEvaluacion, recuperada.getTitulo(), "El nombre de la evaluación debe ser el correcto");
        assertEquals(6, recuperada.getListaPreguntas().getPreguntas().size(), "La cantidad de preguntas debe ser 6");

        // Verificar las preguntas
        assertEquals("¿Cuál es la capital de Francia?", recuperada.getListaPreguntas().obtenerPregunta(0).getEnunciado());
        assertEquals("La capital de España es ____.", recuperada.getListaPreguntas().obtenerPregunta(1).getEnunciado());
        assertEquals("¿Cuál es la capital de Italia?", recuperada.getListaPreguntas().obtenerPregunta(2).getEnunciado());
        assertEquals("¿Es el sol una estrella?", recuperada.getListaPreguntas().obtenerPregunta(3).getEnunciado());
        assertEquals("La capital de Alemania es ____.", recuperada.getListaPreguntas().obtenerPregunta(4).getEnunciado());
        assertEquals("¿Es el agua H2O?", recuperada.getListaPreguntas().obtenerPregunta(5).getEnunciado());
    }

    // Método auxiliar para extraer la respuesta correcta del formato completo
    private String obtenerRespuestaCorrecta(String formatoCompleto) {
        // Separar por ",,," para obtener la respuesta
        String[] partes = formatoCompleto.split(",,,");
        return partes.length > 1 ? partes[1] : partes[0];
    }
}
