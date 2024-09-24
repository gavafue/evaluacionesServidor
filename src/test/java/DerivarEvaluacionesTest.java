
import org.junit.jupiter.api.Test;

import derivadores.DerivarEvaluaciones;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import logica.*;

public class DerivarEvaluacionesTest {

    @Test
    public void testDerivarEvaluacionesOperacionDesconocida() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("OperacionInvalida", "Mensaje de prueba");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Operación desconocida,;,400", resultado);
    }

    @Test
    public void testAltaEvaluacionExitosa() throws FileNotFoundException {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("Alta",
                "NuevaEvaluacion;;;kk,,,Completar,,,2,,,kk;;;kk,,,Completar,,,1,,,kk;;;kk,,,Completar,,,2,,,kk;;;false;;;3");

        // Asegurarse de que no exista la evaluación previamente
        if (derivarEvaluaciones.getEvaluaciones().existeEvaluacion("NuevaEvaluacion")) {
            derivarEvaluaciones.getEvaluaciones().eliminarEvaluacion("NuevaEvaluacion");
        }

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Evaluación creada,;,200", resultado);
        derivarEvaluaciones.getEvaluaciones().eliminarEvaluacion("NuevaEvaluacion");
    }

    @Test
    public void testEliminarEvaluacionNoExistente() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("Eliminar", "EvaluacionNoExistente");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Evaluación NO existe,;,500", resultado);
    }

    @Test
    public void testVerificarExistenciaEvaluacionNoExistente() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("Existencia", "EvaluacionNoExistente");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Evaluación NO existe,;,500", resultado);
    }

    @Test
    public void testAltaEvaluacionYaExistente() throws FileNotFoundException {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("Alta",
                "EvaluacionExistente;;;Pregunta1,,,Multiple,,,1,,,OpcionA,,,OpcionB,,,OpcionC,,,OpcionD,,,Respuesta;;;1;;;true");
        try {
            derivarEvaluaciones.getEvaluaciones().agregarEvaluacion(new Evaluacion("EvaluacionExistente", new Preguntas()));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Ya existe evaluación con ese título,;,400", resultado);
        derivarEvaluaciones.getEvaluaciones().eliminarEvaluacion("EvaluacionExistente");
    }

    @Test
    public void testObtenerCorrectasEvaluacionExistente() throws FileNotFoundException {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("ObtenerCorrectas", "EvaluacionExistente");

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
        if (!derivarEvaluaciones.getEvaluaciones().existeEvaluacion("EvaluacionExistente")) {
            derivarEvaluaciones.getEvaluaciones().agregarEvaluacion(new Evaluacion("EvaluacionExistente", preguntas));
        } else {
            derivarEvaluaciones.getEvaluaciones().obtenerEvaluacion("EvaluacionExistente").setListaPreguntas(preguntas);
        }

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("¿Cuál es la capital de Francia?,,,París;;;¿Cuál es el río más largo del mundo?,,,Amazonas;;;¿Qué continente es Australia?,,,Oceanía;;;¿Cuál es el país más grande del mundo?,,,Rusia;;;¿Qué océano está al este de África?,,,Índico;;;Completa: El desierto más grande del mundo es el ____.,,,Sahara;;;,;,200", resultado); // Asegúrate de que el valor esperado sea correcto.
        derivarEvaluaciones.getEvaluaciones().eliminarEvaluacion("EvaluacionExistente");
    }

    @Test
    public void testObtenerCorrectasEvaluacionNoExistente() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("ObtenerCorrectas", "EvaluacionNoExistente");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals(",;,400", resultado);
    }

    @Test
    public void testObtenerPuntajeTotalDeEvaluacionExistente() throws FileNotFoundException {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("ObtenerPuntajeTotal", "EvaluacionExistente");

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
        if (!derivarEvaluaciones.getEvaluaciones().existeEvaluacion("EvaluacionExistente")) {
            derivarEvaluaciones.getEvaluaciones().agregarEvaluacion(new Evaluacion("EvaluacionExistente", preguntas));
        } else {
            derivarEvaluaciones.getEvaluaciones().obtenerEvaluacion("EvaluacionExistente").setListaPreguntas(preguntas);
        }

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("6,;,200", resultado); // Ajusta el valor esperado según la lógica de tu aplicación.
        derivarEvaluaciones.getEvaluaciones().eliminarEvaluacion("EvaluacionExistente");
    }

    @Test
    public void testObtenerPuntajeTotalDeEvaluacionNoExistente() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("ObtenerPuntajeTotal",
                "EvaluacionNoExistente");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Evaluación NO existe,;,500", resultado);
    }

    @Test
    public void testObtenerTituloAlAzarConEvaluaciones() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("ObtenerTituloAlAzar", "");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertTrue(resultado.split(",;,")[1].equals("200")); // Verifica que se retorne uno de los títulos existentes.
    }

    @Test
    public void testObtenerTituloAlAzarSinEvaluaciones() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("ObtenerTituloAlAzar", "");
        Evaluaciones evs = new Evaluaciones();
        derivarEvaluaciones.setEvaluaciones(evs);

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("No existen evaluaciones,;,500", resultado);
    }

    @Test
    public void testVerificarExistenciaConTituloVacio() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("Existencia", "");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Evaluación NO existe,;,500", resultado);
    }

}
