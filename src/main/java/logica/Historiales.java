package logica;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import persistencia.PersistirHistoriales;

/**
 * La clase Historiales gestiona una colección de objetos de tipo
 * {@link Historial},
 * permitiendo realizar operaciones sobre los historiales de evaluaciones de los
 * estudiantes.
 * 
 */
public class Historiales {

    // Atributos
    private LinkedList<Historial> listaHistorial; // Lista de historiales de evaluaciones

    /**
     * Constructor de la clase Historiales que inicializa la lista de historiales.
     */
    public Historiales() {
        this.listaHistorial = new LinkedList<Historial>();
    }

    // Getter y Setter

    /**
     * Obtiene la lista de historiales.
     * 
     * @return La lista de historiales.
     */
    public LinkedList<Historial> getListaHistorial() {
        return listaHistorial;
    }

    /**
     * Establece la lista de historiales.
     * 
     * @param listaHistorial La nueva lista de historiales.
     */
    public void setListaHistorial(LinkedList<Historial> listaHistorial) {
        this.listaHistorial = listaHistorial;
    }

    /**
     * Agrega un historial a la lista de historiales y persiste los cambios.
     * 
     * @param historial El historial a agregar.
     */
    public void agregarHistorial(Historial historial) {
        this.listaHistorial.add(historial);
        persistirHistoriales();
    }

    /**
     * Persiste el puntaje obtenido por cada estudiante al realizar una evaluación
     * en un archivo.
     */
    public void persistirHistoriales() {
        PersistirHistoriales persistir = new PersistirHistoriales();
        persistir.persistirHistorialesEnArchivo(listaHistorial);
    }

    /**
     * Obtiene un historial dado su posición en la lista.
     * 
     * @param indice El índice del historial en la lista.
     * @return El historial en la posición indicada.
     */
    public Historial obtenerHistorial(int indice) {
        return this.getListaHistorial().get(indice);
    }

    /**
     * Obtiene el historial de una evaluación dado el título de la evaluación
     * y el número de cédula de identidad del estudiante.
     * 
     * @param titulo El título de la evaluación.
     * @param ci     El número de cédula de identidad del estudiante.
     * @return El historial correspondiente o null si no se encuentra.
     */
    public Historial obtenerHistorial(String titulo, String ci) {
        Historial encontrado = null;
        for (Historial h : this.getListaHistorial()) {
            if (h.getTituloEvaluacion().equals(titulo) && h.getCiEstudiante().equals(ci)) {
                encontrado = h;
            }
        }
        return encontrado;
    }

    /**
     * Obtiene todos los historiales de una evaluación dada su título.
     * 
     * @param titulo El título de la evaluación.
     * @return Una lista de historiales que corresponden al título especificado.
     */
    public LinkedList<Historial> obtenerHistoriales(String titulo) {
        LinkedList<Historial> encontrados = new LinkedList<Historial>();
        for (Historial h : this.getListaHistorial()) {
            if (h.getTituloEvaluacion().equals(titulo)) {
                encontrados.add(h);
            }
        }
        return encontrados;
    }

    /**
     * Determina si existe al menos un historial con el título especificado.
     * 
     * @param titulo El título del historial a buscar.
     * @return True si existe al menos un historial con el título especificado,
     *         false en caso contrario.
     */
    public boolean existeHistorial(String titulo) {
        boolean existe = false;
        for (Historial actual : this.getListaHistorial()) {
            if (actual.getTituloEvaluacion().equals(titulo)) {
                existe = true;
            }
        }
        return existe;
    }

    /**
     * Determina si existe un historial asociado a un estudiante dado el título de
     * la evaluación
     * y el número de cédula de identidad.
     * 
     * @param titulo El título de la evaluación.
     * @param ci     El número de cédula de identidad del estudiante.
     * @return True si existe un historial asociado, false en caso contrario.
     */
    public boolean existeHistorial(String titulo, String ci) {
        boolean existe = false;
        for (Historial actual : this.getListaHistorial()) {
            if (actual.getTituloEvaluacion().equals(titulo) && actual.getCiEstudiante().equals(ci)) {
                existe = true;
            }
        }
        return existe;
    }

    /**
     * Actualiza en memoria la lista de historiales a partir de un archivo de texto.
     */
    public void actualizarHistoriales() {
        PersistirHistoriales persistir = new PersistirHistoriales();
        this.setListaHistorial(persistir.cargarHistorialesDesdeArchivo().getListaHistorial());
    }

    /**
     * Elimina todos los historiales asociados a una evaluación dada su título.
     * 
     * @param titulo El título de la evaluación cuyas historiales se desean
     *               eliminar.
     */
    public void eliminarHistoriales(String titulo) {
        try {
            // Usar un iterador para evitar problemas al modificar la lista mientras se
            // recorre
            Iterator<Historial> iterador = this.getListaHistorial().iterator();
            boolean historialEliminado = false;

            while (iterador.hasNext()) {
                Historial actual = iterador.next();
                if (actual.getTituloEvaluacion().equals(titulo)) {
                    iterador.remove(); // Elimina el historial de forma segura
                    historialEliminado = true;
                }
            }

            // Persistir los cambios en los historiales
            this.persistirHistoriales();
            if (!historialEliminado) {
                Logger.getLogger(Historiales.class.getName()).log(Level.INFO,
                        "No se encontraron historiales asociados a la evaluación: {0}", titulo);
            }
        } catch (Exception ex) {
            Logger.getLogger(Historiales.class.getName()).log(Level.SEVERE,
                    "Error inesperado al eliminar los historiales de la evaluación: " + titulo, ex);
            throw new RuntimeException("Error inesperado al eliminar los historiales.", ex);
        }
    }
}
