import org.junit.jupiter.api.Test;

import derivadores.DerivarEvaluaciones;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import logica.*;

public class DerivarEvaluacionesTest {

    @Test
    public void testConstructor() {
        String operacion = "Listar";
        String mensaje = "Mensaje de prueba";

        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones(operacion, mensaje);

        assertEquals(operacion, derivarEvaluaciones.getOperacion());
        assertEquals(mensaje, derivarEvaluaciones.getMensaje());
        assertNotNull(derivarEvaluaciones.getEvaluaciones());
        assertNotNull(derivarEvaluaciones.getHistoriales());
    }

    @Test
    public void testDerivarEvaluacionesOperacionDesconocida() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("OperacionInvalida", "Mensaje de prueba");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Operación desconocida,;,400", resultado);
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
    public void testAltaEvaluacionYaExistente() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("Alta",
                "EvaluacionExistente;;;Pregunta1,,,Multiple,,,1,,,OpcionA,,,OpcionB,,,OpcionC,,,OpcionD,,,Respuesta;;;1;;;true");

        Evaluaciones evaluaciones = new Evaluaciones();
        try {
            evaluaciones.agregarEvaluacion(new Evaluacion("EvaluacionExistente", new Preguntas()));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        derivarEvaluaciones.setEvaluaciones(evaluaciones);
        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Ya existe evaluación con ese título,;,400", resultado);
    }

    @Test
    public void testObtenerCorrectasEvaluacionNoExistente() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("ObtenerCorrectas", "EvaluacionNoExistente");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals(",;,400", resultado);
    }

    @Test
    public void testObtenerValorCheckboxRespuestasEvaluacionNoExistente() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("ValorCheckboxRespuestas",
                "EvaluacionNoExistente");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals(",;,400", resultado);
    }

    @Test
    public void testObtenerPuntajeTotalDeEvaluacionNoExistente() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("ObtenerPuntajeTotal",
                "EvaluacionNoExistente");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Evaluación NO existe,;,500", resultado);
    }

    @Test
    public void testObtenerTituloAlAzarSinEvaluaciones() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("ObtenerTituloAlAzar", "");

        Evaluaciones evaluaciones = new Evaluaciones();
        derivarEvaluaciones.setEvaluaciones(evaluaciones);

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("No existen evaluaciones,;,500", resultado);
    }

    @Test
    public void testDerivarEvaluacionesConHistorial() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("Listar", "Mensaje de prueba");

        Historiales historiales = new Historiales();
        derivarEvaluaciones.setHistoriales(historiales);

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("200", resultado.split(",;,")[1]);
        assertNotNull(derivarEvaluaciones.getHistoriales());
    }

    @Test
    public void testEliminarEvaluacionConHistorial() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("Eliminar", "EvaluacionExistente");

        Evaluaciones evaluaciones = new Evaluaciones();
        Evaluacion evaluacionExistente = new Evaluacion("EvaluacionExistente", new Preguntas());
        try {
            evaluaciones.agregarEvaluacion(evaluacionExistente);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        derivarEvaluaciones.setEvaluaciones(evaluaciones);

        Historiales historiales = new Historiales();
        derivarEvaluaciones.setHistoriales(historiales);

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Evaluación eliminada,;,200", resultado);
        assertEquals(0, evaluaciones.getListaEvaluaciones().size());
    }

    @Test
    public void testVerificarExistenciaConTituloVacio() {
        DerivarEvaluaciones derivarEvaluaciones = new DerivarEvaluaciones("Existencia", "");

        String resultado = derivarEvaluaciones.derivarEvaluaciones();

        assertEquals("Evaluación NO existe,;,500", resultado);
    }

}
