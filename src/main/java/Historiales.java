import java.util.LinkedList;

/**
 *
 * @author anaju
 */
public class Historiales {

    //Atributos
    private LinkedList<Historial> listaHistorial;

    //Constructor vacio
    public Historiales() {
        this.listaHistorial = new LinkedList();
    }

    //Getter
    public LinkedList<Historial> getListaHistorial() {
        return listaHistorial;
    }

    //Setter
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
    private void persistirHistoriales() {
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
     * Metodo que retorna el HIstorial dado su título.
     *
     * @param titulo del Historial a buscar.
     * @return el Historial encontrado en la coleccion.
     */
    public Historial obtenerHistorial(String titulo) {
        Historial encontrado = null;
        for (Historial h : this.getListaHistorial()) {
            if (h.getTituloEvaluacion().equals(titulo)) {
                encontrado = h;
            }
        }
        return encontrado; //Puede haber mas de una con el mismo titulo?
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
     * Metodo que actualiza en memoria la lista de historiales a partir de un archivo de texto.
     */
    public void actualizarHistoriales() {
        Persistencia persistir = new Persistencia();
        this.setListaHistorial(persistir.cargarHistorialesDesdeArchivo().getListaHistorial());
    }
}
