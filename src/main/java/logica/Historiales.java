package logica;


import persistencia.Persistencia;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anaju
 */
public class Historiales {

    // Atributos
    private LinkedList<Historial> listaHistorial;

    // Constructor vacio
    public Historiales() {
        this.listaHistorial = new LinkedList<Historial>();
    }

    // Getter
    public LinkedList<Historial> getListaHistorial() {
        return listaHistorial;
    }

    // Setter
    public void setListaHistorial(LinkedList<Historial> listaHistorial) {
        this.listaHistorial = listaHistorial;
    }

    /**
     * Metodo que agrega un historial a la lista de historiales.
     *
     * @param historial
     */
    public void agregarHistorial(Historial historial) {
        this.listaHistorial.add(historial);
        persistirHistoriales();
    }

    /**
     * Metodo que persiste el puntaje obtenido por cada estudiante al realizar
     * una evaluacion.
     */
    public void persistirHistoriales() {
        Persistencia persistir = new Persistencia();
        persistir.persistirHistorialesEnArchivo(listaHistorial);
    }

    /**
     * Metodo que retorna el Historial dado su posicion en la lista. Asume que
     * el numero de indice es valido.
     *
     * @param indice
     * @return Historial
     */
    public Historial obtenerHistorial(int indice) {
        return this.getListaHistorial().get(indice);
    }

    /**
     * Metodo que retorna el historial de una evaluacion dado el titulo de la misma
     * y la ci del estudiante.
     *
     * @param titulo del Historial a buscar.
     * @return el Historial encontrado en la coleccion.
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
     * Metodo que permite obtener todos los historiales de igual titulo.
     *
     * @param titulo del Historial a buscar.
     * @return las coincidencias de Historial encontrado en la coleccion.
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
     * Metodo que determina si existe al menos un Historial a partir de un
     * titulo.
     *
     * @param titulo
     * @return si existe el Historial
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
     * Metodo que determina si existe al menos un Historial asociado a un estudiante
     * a partir del
     * titulo y la ci.
     *
     * @param titulo
     * @param ci
     * @return si existe un historial asociado a la ci
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
     * Metodo que actualiza en memoria la lista de historiales a partir de un
     * archivo de texto.
     */
    public void actualizarHistoriales() {
        Persistencia persistir = new Persistencia();
        this.setListaHistorial(persistir.cargarHistorialesDesdeArchivo().getListaHistorial());
    }

    public void eliminarTodosLosHistorialesDeUnaEvaluacion(String titulo) {
        try {
            // Actualizar la lista de historiales antes de eliminar
            this.actualizarHistoriales();

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
