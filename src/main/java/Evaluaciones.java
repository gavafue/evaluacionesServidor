
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
     * TODO:AGREGAR VALIDACIÓN/RESTRICCIÓN ACÁ MISMO.
     *
     * @param evaluacion
     * @throws java.io.FileNotFoundException
     */
    public void agregarEvaluacion(Evaluacion evaluacion) throws FileNotFoundException {
        listaEvaluaciones.add(evaluacion);
        persistirEvaluaciones(listaEvaluaciones);
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

    /**
     * Método que permite cargar las evaluaciones desde un archivo utilizando
     * deserialización.
     *
     * @throws java.io.FileNotFoundException
     */
    // public void cargarEvaluaciones() throws FileNotFoundException {
    // FileInputStream file = new FileInputStream("evaluaciones.txt");
    // try (ObjectInputStream ois = new ObjectInputStream(file)) {
    // listaEvaluaciones = (ArrayList<Evaluacion>) ois.readObject();
    // ois.close();
    // } catch (IOException | ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // }
    public void persistirEvaluaciones(ArrayList<Evaluacion> listaEvaluaciones) {
        Persistencia persistir = new Persistencia();
        persistir.persistirEvaluacionesEnArchivo(listaEvaluaciones);
    }

    // ----------------- en texto plano ------------------
    /**
     * Metodo que permite cargar al sistema las evaluaciones extraidas de un
     * archivo de texto.
     */
    /*
     * public void cargarEvaluaciones() {
     * try {
     * Scanner s = new Scanner(new File("evaluaciones.txt"));
     * while (s.hasNextLine()) {
     * String linea = s.nextLine();
     * String[] evaluacion = linea.split(";");
     * this.agregarEvaluacion(new Evaluacion(evaluacion[0]));
     * }
     * s.close();
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     */
    /**
     * Metodo que persiste las evaluaciones del sistema en su totalidad.
     *
     */
    /*
     * private void persistirEvaluaciones() {
     * try (BufferedWriter writer = new BufferedWriter(new
     * FileWriter("evaluaciones.txt"))) {
     * for (Evaluacion evaluacion : listaEvaluaciones) {
     * writer.write(evaluacion.getTitulo());
     * writer.newLine();
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     */
    // ....................
    public void listarEvaluaciones() {
        for (int i = 0; i < listaEvaluaciones.size(); i++) {
            System.out.println("[" + (i + 1) + "]. " + listaEvaluaciones.get(i).getTitulo());
            listaEvaluaciones.get(i).getListaPreguntas().listarPreguntas();
            System.out.println("\n\n\n");
        }
    }

    /*
     * public void cargarEvaluacionesIniciales() throws FileNotFoundException {
     * Evaluacion evaluacion1 = new Evaluacion("Primer Parcial");
     * Evaluacion evaluacion2 = new Evaluacion("Evaluacion Diagnostica");
     * 
     * String[] respuestas1 = {"Un pingüino", "una vaca", "un alien", "un perro"};
     * MultipleOpcion pregunta1 = new
     * MultipleOpcion("¿Què animal es la mascota de Linux?", 10, respuestas1, false,
     * respuestas1[0]);
     * evaluacion1.getListaPreguntas().agregarPregunta(pregunta1);
     * 
     * String[] respuestas2 = {"pinguino", "pico"};
     * CompletarEspacio pregunta2 = new
     * CompletarEspacio("El animal que es la mascota de Linux es un _ y tiene un _ naranja"
     * , 5, respuestas2);
     * evaluacion1.getListaPreguntas().agregarPregunta(pregunta2);
     * 
     * String[] respuestas3 = {"Verdadero", "Falso"};
     * MultipleOpcion pregunta3 = new
     * MultipleOpcion("La mascota de Linux es un pinguino", 10, respuestas3, true,
     * respuestas3[0]);
     * evaluacion2.getListaPreguntas().agregarPregunta(pregunta3);
     * 
     * agregarEvaluacion(evaluacion1);
     * agregarEvaluacion(evaluacion2);
     * }
     */
}
