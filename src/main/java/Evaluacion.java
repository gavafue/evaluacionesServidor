import java.io.Serializable;
import javax.swing.JPanel;

/**
 * Esta clase permite crear una evaluacion con sus respectivas preguntas.
 */
public class Evaluacion implements Serializable {

    /**
     * Agrego el ID fijo para evitar problemas de compatibilidad
     * al realizar cambios en la clase después de serializar objetos.
     * Por defecto se genera un id automatico que cambia al modificar algun metodo
     * de la clase
     */
    private static final long serialVersionUID = 903578866662717088L;

    // Atributos
    private String titulo;
    private Preguntas listaPreguntas;

    // Constructor comun
    public Evaluacion(String titulo) {
        this.titulo = titulo;
        this.listaPreguntas = new Preguntas();

    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public Preguntas getListaPreguntas() {
        return listaPreguntas;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setListaPreguntas(Preguntas listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }
}
// Carga las preguntas en ventanas. Dependiendo el tipo se muestra de una u otra
// forma.

// public void cargarEnGui(int posicion) {
// JPanel multiple;
// JPanel espacios;
// FramePregunta pregunta;

// pregunta=new FramePregunta(null,this);
// pregunta.setLocationRelativeTo(null);
// multiple=pregunta.getPanelMultiple();
// espacios=pregunta.getPanelRespuesta();//Este panel se utiliza si la pregutna
// es VF o para completar
// pregunta.getPanelEnunciado().setVisible(false);//Solo visible al crear la
// pregunta
// pregunta.setVisible(true);
// Pregunta aMostrar = listaPreguntas.obtenerPregunta(posicion);

// if (aMostrar instanceof MultipleOpcion){

// if (!((MultipleOpcion) aMostrar).getEsVerdaderoOFalso()) {
// String[] opciones = ((MultipleOpcion) aMostrar).getOpciones();

// pregunta.getLblEnunciadoMultiple().setText(aMostrar.getEnunciado());//Se
// carga el enunciado en Label
// pregunta.getTxtRespuesta().setVisible(true);
// pregunta.getCboxVerdaderoOFalso().setVisible(false);
// pregunta.getPanel().setVisible(false);
// espacios.setVisible(false);
// multiple.setVisible(true);
// //Aparecen las opciones y el puntaje pero sin posibilidad de editar
// pregunta.getspnPuntajeMultiple().setEnabled(false);
// pregunta.getspnPuntajeMultiple().setValue(aMostrar.getPuntaje());
// pregunta.getTxtOpc1().setText(opciones[0]);
// pregunta.getTxtOpc2().setText(opciones[1]);
// pregunta.getTxtOpc3().setText(opciones[2]);
// pregunta.getTxtOpc4().setText(opciones[3]);

// pregunta.getTxtOpc1().setEnabled(false);
// pregunta.getTxtOpc2().setEnabled(false);
// pregunta.getTxtOpc3().setEnabled(false);
// pregunta.getTxtOpc4().setEnabled(false);
// pregunta.getBtnFinalizarMultiple().setText("Siguiente");

// }else { //Es una pregunta VF

// pregunta.getTxtEnunciadoVF().setText(aMostrar.getEnunciado());//Se carga el
// enunciado en el txtArea
// espacios.setVisible(true);
// multiple.setVisible(false);
// pregunta.getTxtRespuesta().setVisible(false);
// pregunta.getPanel().setVisible(true);
// pregunta.getCboxVerdaderoOFalso().setVisible(true);
// pregunta.getLblTipo().setText("True/False");
// pregunta.getspnPuntaje().setEnabled(false);
// pregunta.getspnPuntaje().setValue(aMostrar.getPuntaje());
// pregunta.getBtnFinalizar().setText("Siguiente");

// }

// } else { //Es una pregunta de completar

// pregunta.getTxtEnunciadoVF().setText(aMostrar.getEnunciado());//Se carga el
// enunciado en el txtArea
// pregunta.getCboxVerdaderoOFalso().setVisible(false);
// pregunta.getTxtRespuesta().setVisible(true);
// espacios.setVisible(true);
// pregunta.getPanel().setVisible(true);
// pregunta.getspnPuntaje().setEnabled(false);
// pregunta.getspnPuntaje().setValue(aMostrar.getPuntaje());
// pregunta.getLblTipo().setText("Respuesta/s");
// pregunta.getBtnFinalizar().setText("Siguiente");

// }

// }

// }

/*
 * public int realizarEvaluacion(String[] respuestas) {
 * //Se le puede pasar un arreglo con todas las respuestas en orden y
 * //comparar con la colección de preguntas. Si es correcta, sumar el puntaje
 * asignado a esa pregunta…
 * //es solamente una idea
 * int puntajeTotal = 0;
 * for (int i = 0; i < listaPreguntas.getPreguntas().size(); i++) {
 * Pregunta pregunta = listaPreguntas.getPreguntas().get(i);
 * String respuesta = respuestas[i];
 * 
 * if (pregunta.esCorrecta(respuesta)) {
 * puntajeTotal += pregunta.getPuntaje();
 * }
 * }
 * 
 * return puntajeTotal;
 * }
 */
