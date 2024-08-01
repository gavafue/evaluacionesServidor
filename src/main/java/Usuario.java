/**
 * Esta clase permite crear un usuario con su respectivo nombre de usuario y
 * contraseña.
 *
 * @author
 * @since version 2
 */
public class Usuario {

    // Atributos
    private String nombreUsuario;
    private String contrasenia;
    private String tipo;

    // Constructor comun
    public Usuario(String nombreUsuario, String contrasenia, String tipo) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
    }

    // Getters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getTipoDeUsuario() {
        return tipo;
    }

    // Setters
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setTipoDeUsuario(String tipo) {
        this.tipo = tipo;
    }
}
