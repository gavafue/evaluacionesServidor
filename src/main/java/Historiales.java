import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.LinkedList;
import java.util.Scanner;

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
     * @param historial 
     */
    public void agregarHistorial(Historial historial){
        this.listaHistorial.add(historial);
        persistirHistoriales();
    }
    
    
     /**
     * Metodo que permite cargar al sistema los resultados de las evaluaciones extraidos de un
     * archivo de texto.
     */
    public void cargarHistorial() {
        try {
            Scanner s = new Scanner(new File("historiales.txt"));
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                String[] historial = linea.split(";");
                this.agregarHistorial(new Historial(historial[0],historial[1],parseInt(historial[2])));
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que persiste el puntaje obtenido por cada estudiante al realizar una evaluacion.
     */
    private void persistirHistoriales() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historiales.txt"))) {
            for (Historial historial : listaHistorial) {
                writer.write(historial.getTituloEvaluacion()+";"+historial.getCiEstudiante()+";"+historial.getPuntaje());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
}
