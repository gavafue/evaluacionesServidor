/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gabriel
 */
public class Switch {

    private String mensaje;
    private String tipoMensaje;
    private String operacion;

    public Switch(String mensaje, String tipoMensaje, String operacion) {
        this.mensaje = mensaje;
        this.tipoMensaje = tipoMensaje;
        this.operacion = operacion;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public Boolean validarMensaje() {
        Boolean valido = false;
        if (!this.getMensaje().isEmpty()) { // Usar isEmpty() en lugar de comparar con una cadena vacía
            valido = true;
        }
        return valido;
    }

    public Boolean validarTipoMensaje() {
        Boolean valido = false;
        if ("evaluacion".equals(this.tipoMensaje)) { // Usar equals() para comparar el contenido de las cadenas
            valido = true;
        }
        return valido;
    }

    public String obtenerMensajeEvaluacion() {
        Evaluacion ev = new Evaluacion();
        return ev.getMensaje();
    }

}
