/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import logica.CompletarEspacio;
import logica.MultipleOpcion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gonzalito
 */
public class PreguntaTest {

    @Test
    public void testCrearPreguntaMultipleOpcion() {
        // Datos proporcionados para la prueba
        String enunciado = "¿Cuál es la capital de Italia?";
        String[] opciones = { "Roma", "Londres", "Berlín" };
        String respuestaCorrecta = "Roma";

        // Crear una instancia de MultipleOpcion
        MultipleOpcion pregunta = new MultipleOpcion(enunciado, 1, opciones, false, respuestaCorrecta);

        // Verificar que la pregunta se almacena correctamente
        assertEquals(enunciado, pregunta.getEnunciado(), "El enunciado de la pregunta no es correcto.");
        assertEquals(opciones[0], pregunta.getOpciones()[0], "La primera opción no es correcta.");
        assertEquals(opciones[1], pregunta.getOpciones()[1], "La segunda opción no es correcta.");
        assertEquals(opciones[2], pregunta.getOpciones()[2], "La tercera opción no es correcta.");
        assertEquals(respuestaCorrecta, pregunta.getRespuestaCorrecta(), "La respuesta correcta no es correcta.");
    }

    @Test
    public void testCrearPreguntaCompletarEspacio() {
        // Datos proporcionados para la prueba
        String enunciado = "La capital de España es _____.";
        String[] respuestasCorrectas = { "Madrid" };

        // Crear una instancia de CompletarEspacio
        CompletarEspacio pregunta = new CompletarEspacio(enunciado, 1, respuestasCorrectas);

        // Verificar que la pregunta se almacena correctamente
        assertEquals(enunciado, pregunta.getEnunciado(), "El enunciado de la pregunta no es correcto.");
        assertEquals(respuestasCorrectas.length, pregunta.getRespuestasCorrectas().length,
                "El número de respuestas correctas no es correcto.");
        for (int i = 0; i < respuestasCorrectas.length; i++) {
            assertEquals(respuestasCorrectas[i], pregunta.getRespuestasCorrectas()[i],
                    "Respuesta correcta en la posición " + i + " no es correcta.");
        }
    }

}
