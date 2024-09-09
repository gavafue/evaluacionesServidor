package logica;


import logica.MultipleOpcion;
import logica.Pregunta;
import logica.Preguntas;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Esta clase permite crear una evaluacion con sus respectivas preguntas.
 */
public class Evaluacion implements Serializable {

    /**
     * Agrego el ID fijo para evitar problemas de compatibilidad al realizar
     * cambios en la clase después de serializar objetos. Por defecto se genera
     * un id automatico que cambia al modificar algun metodo de la clase
     */
    private static final long serialVersionUID = 903578866662717088L;

    // Atributos
    private String titulo;
    private Preguntas listaPreguntas;
    private int cantidadDePreguntas;
    private boolean respuestasValidas;

    // Constructor comun
    public Evaluacion(String titulo) {
        this.titulo = titulo;
        this.listaPreguntas = new Preguntas();

    }

    /**
     * Constructor
     *
     * @param titulo
     * @param listaPreguntas
     */
    public Evaluacion(String titulo, Preguntas listaPreguntas) {
        this.titulo = titulo;
        this.listaPreguntas = listaPreguntas;
        this.respuestasValidas = false;
    }

    public Evaluacion(String titulo, Preguntas listaPreguntas, boolean respuestasValidas) {
        this.titulo = titulo;
        this.listaPreguntas = listaPreguntas;
        this.respuestasValidas = respuestasValidas;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public Preguntas getListaPreguntas() {
        return listaPreguntas;
    }

    public int getCantidadDePreguntas() {
        return cantidadDePreguntas;
    }

    public boolean isRespuestasValidas() {
        return respuestasValidas;
    }

    // Setters
    public void setCantidadDePreguntas(int cantidadDePreguntas) {
        this.cantidadDePreguntas = cantidadDePreguntas;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setListaPreguntas(Preguntas listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    public void setRespuestasValidas(boolean respuestasValidas) {
        this.respuestasValidas = respuestasValidas;
    }

    public ArrayList<String> obtenerRespuestasCorrectas() {
        ArrayList<String> enunciadosConRespuestas = new ArrayList<String>();
        for (Pregunta pregunta : this.getListaPreguntas().getPreguntas()) {
            String enunciado = pregunta.getEnunciado();
            String respuesta = "";
            if (pregunta instanceof CompletarEspacio) {
                CompletarEspacio ce = (CompletarEspacio) pregunta;
                String[] respuestas = ce.getRespuestasCorrectas();
                respuesta = String.join("*", respuestas);
            } else if (pregunta instanceof MultipleOpcion) {
                MultipleOpcion mo = (MultipleOpcion) pregunta;
                if (mo.getRespuestaCorrecta().matches("[1-4]")) {
                    int indice = Integer.parseInt(mo.getRespuestaCorrecta()) - 1;
                    respuesta = "Opcion " + mo.getRespuestaCorrecta() + ": " + mo.getOpciones()[indice];
                } else {
                    respuesta = mo.getRespuestaCorrecta();
                }
            }
            enunciadosConRespuestas.add(enunciado + ",,," + respuesta);
        }
        return enunciadosConRespuestas;
    }

}
