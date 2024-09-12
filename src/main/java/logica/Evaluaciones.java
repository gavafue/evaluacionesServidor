package logica;

import persistencia.PersistirEvaluaciones;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase Evaluaciones permite gestionar una colección de evaluaciones.
 *
 * Proporciona métodos para agregar, eliminar, obtener y verificar evaluaciones,
 * así como para persistirlas en un archivo y actualizar la lista de
 * evaluaciones desde el archivo.
 *
 */
public class Evaluaciones {

    private ArrayList<Evaluacion> listaEvaluaciones;

    /**
     * Constructor vacío de la clase Evaluaciones.
     *
     */
    public Evaluaciones() {
        this.listaEvaluaciones = new ArrayList<>();
    }

    /**
     * Obtiene la lista de evaluaciones.
     *
     * @return La lista de evaluaciones.
     */
    public ArrayList<Evaluacion> getListaEvaluaciones() {
        return listaEvaluaciones;
    }

    /**
     * Establece la lista de evaluaciones.
     *
     * @param listaEvaluaciones La nueva lista de evaluaciones.
     */
    public void setListaEvaluaciones(ArrayList<Evaluacion> listaEvaluaciones) {
        this.listaEvaluaciones = listaEvaluaciones;
    }

    /**
     * Agrega una evaluación a la lista de evaluaciones.
     *
     * @param evaluacion La evaluación que se desea agregar.
     * @throws FileNotFoundException Si ocurre un error al manejar el archivo.
     */
    public void agregarEvaluacion(Evaluacion evaluacion) throws FileNotFoundException {
        this.getListaEvaluaciones().add(evaluacion);
        this.persistirEvaluaciones(this.getListaEvaluaciones());
    }

    /**
     * Elimina una evaluación de la lista de evaluaciones dado su título.
     *
     * @param titulo El título de la evaluación que se desea eliminar.
     * @throws FileNotFoundException Si ocurre un error al manejar el archivo.
     */
    public void eliminarEvaluacion(String titulo) throws FileNotFoundException {
        if (existeEvaluacion(titulo)) {
            this.getListaEvaluaciones().remove(obtenerEvaluacion(titulo));
            this.persistirEvaluaciones(this.getListaEvaluaciones());
        }
    }

    /**
     * Determina si existe al menos una evaluación con el título dado.
     *
     * @param titulo El título de la evaluación a verificar.
     * @return true si existe una evaluación con el título dado, false en caso
     * contrario.
     */
    public boolean existeEvaluacion(String titulo) {
        for (Evaluacion evaluacion : this.getListaEvaluaciones()) {
            if (evaluacion.getTitulo().equals(titulo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene una evaluación dado su número de índice en la lista de
     * evaluaciones.
     *
     * @param indice El índice de la evaluación en la lista.
     * @return La evaluación en el índice dado, o null si el índice está fuera
     * de rango.
     */
    public Evaluacion obtenerEvaluacion(int indice) {
        if (indice >= 0 && indice < this.getListaEvaluaciones().size()) {
            return this.getListaEvaluaciones().get(indice);
        } else {
            return null;
        }
    }

    /**
     * Obtiene una evaluación dado su título.
     *
     * @param titulo El título de la evaluación a obtener.
     * @return La evaluación con el título dado, o null si no existe.
     */
    public Evaluacion obtenerEvaluacion(String titulo) {
        if (existeEvaluacion(titulo)) {
            for (Evaluacion evaluacion : this.getListaEvaluaciones()) {
                if (evaluacion.getTitulo().equals(titulo)) {
                    return evaluacion;
                }
            }
        }
        return null;
    }

    /**
     * Obtiene una lista de títulos de todas las evaluaciones.
     *
     * @return Una lista de títulos de evaluaciones.
     */
    public List<String> obtenerTítulosEvaluaciones() {
        PersistirEvaluaciones persistir = new PersistirEvaluaciones();
        List<String> listaTitulosEvaluaciones = new ArrayList<>();
        try {
            listaTitulosEvaluaciones = persistir.obtenerTitulosDeEvaluacionesDesdeArchivo();
        } catch (IOException e) {
            // Manejo de la excepción: podrías registrar el error y/o lanzar una excepción
            // personalizada
            e.printStackTrace(); // Imprime la traza del error
        }
        return listaTitulosEvaluaciones;
    }

    /**
     * Actualiza la lista de evaluaciones desde el archivo de almacenamiento.
     */
    public void actualizarListaEvaluaciones() {
        PersistirEvaluaciones persistir = new PersistirEvaluaciones();
        setListaEvaluaciones(persistir.cargarEvaluacionesDesdeArchivo().getListaEvaluaciones());
    }

    /**
     * Persiste la lista de evaluaciones en un archivo.
     *
     * @param listaEvaluaciones La lista de evaluaciones que se desea persistir.
     */
    public void persistirEvaluaciones(ArrayList<Evaluacion> listaEvaluaciones) {
        PersistirEvaluaciones persistir = new PersistirEvaluaciones();
        persistir.persistirEvaluacionesEnArchivo(listaEvaluaciones);
    }

    /**
     * Obtiene el puntaje total de una evaluación dada su título.
     *
     * @param titulo El título de la evaluación de la cual se desea obtener el
     * puntaje total.
     * @return El puntaje total de la evaluación. Si la evaluación no existe o
     * tiene preguntas inválidas, devuelve 0.
     */
    public int obtenerPuntajeTotal(String titulo) {
        int puntajeTotal = 0;
        try {
            Evaluacion evaluacion = this.obtenerEvaluacion(titulo);

            // Verifica si la evaluación es null
            if (evaluacion == null) {
                throw new IllegalArgumentException("La evaluación con el título '" + titulo + "' no existe.");
            } else {
                List<Pregunta> preguntas = evaluacion.getListaPreguntas().getPreguntas();
                if (preguntas == null || preguntas.isEmpty()) {
                    throw new IllegalStateException("La evaluación '" + titulo + "' no tiene preguntas asignadas.");
                } else {
                    // Suma los puntajes de todas las preguntas
                    for (Pregunta pregunta : preguntas) {
                        if (pregunta == null) {
                            System.err.println("Advertencia: Pregunta nula encontrada en la evaluación '" + titulo + "'.");
                            continue;
                        }
                        puntajeTotal += pregunta.getPuntaje();
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            // Manejo de errores cuando la evaluación no existe
            System.err.println("Error al obtener puntaje total: " + e.getMessage());
        } catch (IllegalStateException e) {
            // Manejo de errores cuando la evaluación no tiene preguntas asignadas
            System.err.println("Error al calcular puntaje total: " + e.getMessage());
        } catch (Exception e) {
            // Manejo de cualquier otra excepción inesperada
            System.err.println("Error inesperado al obtener el puntaje total: " + e.getMessage());
        }
        return puntajeTotal;
    }
}
