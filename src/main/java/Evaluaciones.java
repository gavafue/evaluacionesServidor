
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que permite crear una lista de evaluaciones.
 */
public class Evaluaciones {

    // Atributos
    private ArrayList<Evaluacion> listaEvaluaciones; // Siguiendo la recomendacion de evitar polimorfismo: cambio de
    // List a ArrayList la declaracion.

    // Constructor vacio
    public Evaluaciones() {
        this.listaEvaluaciones = new ArrayList<>();
    }

    // Getter
    public ArrayList<Evaluacion> getEvaluaciones() {
        return listaEvaluaciones;
    }

    // Setter
    public void setListaEvaluaciones(ArrayList<Evaluacion> listaEvaluaciones) {
        this.listaEvaluaciones = listaEvaluaciones;
    }

    /**
     * Metodo que permite agregar un evaluacion a la lista de evaluaciones.
     * Asume que no ingresara una evaluacion con un titulo ya existente.
     *
     * @param evaluacion
     * @throws java.io.FileNotFoundException
     */
    public void agregarEvaluacion(Evaluacion evaluacion) throws FileNotFoundException {
        listaEvaluaciones.add(evaluacion);
    }

    /**
     * Metodo que permite eliminar una evaluación de la lista de evaluaciones
     * dado su titulo.
     *
     * @param titulo
     */
    public void eliminarEvaluacion(String titulo) throws FileNotFoundException {
        if (existeEvaluacion(titulo)) {
            listaEvaluaciones.remove(obtenerEvaluacion(titulo));
            // Eliminar la evaluación del archivo "evaluaciones.txt"
            try (BufferedReader br = new BufferedReader(new FileReader("evaluaciones.txt"))) {
                List<String> lineas = new ArrayList<>();
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (!linea.startsWith(titulo + ";")) {
                        lineas.add(linea);
                    }
                }
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("evaluaciones.txt"))) {
                    for (String linea2 : lineas) {
                        bw.write(linea2);
                        bw.newLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo que determina si existe al menos una evaluacion dado su título.
     *
     * @param titulo
     * @return
     */
    public boolean existeEvaluacion(String titulo) {
        boolean existe = false;
        for (Evaluacion evaluacion : listaEvaluaciones) {
            if (evaluacion.getTitulo().equals(titulo)) {
                existe = true;
            }
        }
        return existe;
    }

    /**
     * Metodo que permite obtener una evaluacion dado su numero de indice.
     *
     * @param indice
     * @return
     */
    public Evaluacion obtenerEvaluacion(int indice) {
        if (indice >= 0 && indice < listaEvaluaciones.size()) {
            return listaEvaluaciones.get(indice);
        } else {
            return null;
        }
    }

    /**
     * Metodo que permite obtener una evaluacion dado su titulo.
     *
     * @param titulo
     * @return
     */
    public Evaluacion obtenerEvaluacion(String titulo) {
        Evaluacion evaluacion = null;
        if (existeEvaluacion(titulo)) {
            for (int i = 0; i < listaEvaluaciones.size(); i++) {
                if (listaEvaluaciones.get(i).getTitulo().equals(titulo)) {
                    evaluacion = listaEvaluaciones.get(i);
                }
            }
        }
        return evaluacion;
    }

    public void persistirEvaluaciones(ArrayList<Evaluacion> listaEvaluaciones) {
        Persistencia persistir = new Persistencia();
        persistir.persistirEvaluacionesEnArchivo(listaEvaluaciones);
    }

    public void actualizarListaEvaluaciones() {
        Persistencia persistir = new Persistencia();
        setListaEvaluaciones(persistir.cargarEvaluacionesDesdeArchivo().getEvaluaciones());
    }

    public List<String> obtenerTítulosEvaluaciones() {
        Persistencia persistencia = new Persistencia();
        List<String> listaTitulosEvaluaciones = new ArrayList<>();
        try {
            listaTitulosEvaluaciones = persistencia.obtenerTitulosDeEvaluacionesDesdeArchivo();
        } catch (IOException e) {
            // Manejo de la excepción: podrías registrar el error y/o lanzar una excepción
            // personalizada
            e.printStackTrace(); // Imprime la traza del error
        }
        return listaTitulosEvaluaciones;
    }

    public void listarEvaluaciones() {
        for (int i = 0; i < listaEvaluaciones.size(); i++) {
            System.out.println("[" + (i + 1) + "]. " + listaEvaluaciones.get(i).getTitulo());
            listaEvaluaciones.get(i).getListaPreguntas().listarPreguntas();
            System.out.println("\n\n\n");
        }
    }
}
