package logica;

/**
 * La clase {@code Usuario} representa un usuario con su id de usuario,
 * contraseña y tipo.
 * 
 */
public class Usuario {

    // Atributos
    private String idUsuario; // Id de usuario, correspondiente a la CI en el caso del estudiante y predefinido para rol docente y administrativo.
    private String contrasenia; // Contraseña del usuario
    private String tipo; // Tipo de usuario (por ejemplo, admin, estudiante, etc.)

    /**
     * Constructor de la clase {@code Usuario}.
     * 
     * @param idUsuario El id de usuario del usuario.
     * @param contrasenia   La contraseña del usuario.
     * @param tipo          El tipo de usuario.
     */
    public Usuario(String idUsuario, String contrasenia, String tipo) {
        this.idUsuario = idUsuario;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
    }

    /**
     * Obtiene el id de usuario.
     * 
     * @return El id de usuario.
     */
    public String getIdUsuario() {
        return idUsuario;
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

    /**
     * Establece el id de usuario.
     * 
     * @param idUsuario El id de usuario a asignar.
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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
