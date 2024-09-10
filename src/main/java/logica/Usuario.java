package logica;

/**
 * La clase {@code Usuario} representa un usuario con un nombre de usuario,
 * contraseña y tipo de usuario.
 * 
 */
public class Usuario {

    // Atributos
    private String nombreUsuario; // Nombre de usuario del usuario
    private String contrasenia; // Contraseña del usuario
    private String tipo; // Tipo de usuario (por ejemplo, admin, estudiante, etc.)

    /**
     * Constructor de la clase {@code Usuario}.
     * 
     * @param nombreUsuario El nombre de usuario del usuario.
     * @param contrasenia   La contraseña del usuario.
     * @param tipo          El tipo de usuario.
     */
    public Usuario(String nombreUsuario, String contrasenia, String tipo) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
    }

    // Getters

    /**
     * Obtiene el nombre de usuario.
     * 
     * @return El nombre de usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return La contraseña del usuario.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Obtiene el tipo de usuario.
     * 
     * @return El tipo de usuario.
     */
    public String getTipoDeUsuario() {
        return tipo;
    }

    // Setters

    /**
     * Establece el nombre de usuario.
     * 
     * @param nombreUsuario El nombre de usuario a asignar.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Establece la contraseña del usuario.
     * 
     * @param contrasenia La contraseña a asignar.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Establece el tipo de usuario.
     * 
     * @param tipo El tipo de usuario a asignar.
     */
    public void setTipoDeUsuario(String tipo) {
        this.tipo = tipo;
    }
}
