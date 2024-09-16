package logica;

import persistencia.PersistirUsuarios;
import java.util.HashMap;

/**
 * La clase {@code Usuarios} gestiona un conjunto de usuarios, permitiendo
 * agregar, eliminar, obtener y verificar usuarios, así como actualizar contraseñas y
 * persistir la información de los usuarios en un archivo.
 * 
 */
public class Usuarios {
    
    private HashMap<String, Usuario> hashUsuarios; // Mapa que almacena los usuarios con su id de usuario como clave

    /**
     * Constructor de la clase {@code Usuarios}.
     * Inicializa el {@code HashMap} de usuarios.
     */
    public Usuarios() {
        this.hashUsuarios = new HashMap<String, Usuario>();
    }

    /**
     * Obtiene el {@code HashMap} de usuarios.
     * 
     * @return El {@code HashMap} de usuarios.
     */
    public HashMap<String, Usuario> getHashUsuarios() {
        return hashUsuarios;
    }

    /**
     * Establece el {@code HashMap} de usuarios.
     * 
     * @param hashUsuarios El {@code HashMap} de usuarios a asignar.
     */
    public void setHashUsuarios(HashMap<String, Usuario> hashUsuarios) {
        this.hashUsuarios = hashUsuarios;
    }

    /**
     * Agrega un usuario al {@code HashMap} de usuarios y al archivo de texto
     * correspondiente.
     *
     * @param usuario El usuario a agregar.
     */
    public void agregarUsuario(Usuario usuario) {
        this.getHashUsuarios().put(usuario.getIdUsuario(), usuario);
        this.perisistirUsuarios();
    }

    /**
     * Elimina un usuario del {@code HashMap} dado su nombre de usuario y del
     * archivo de texto correspondiente.
     *
     * @param nombreUsuario El nombre del usuario a eliminar.
     */
    public void eliminarUsuario(String nombreUsuario) {
        this.getHashUsuarios().remove(nombreUsuario);
        this.perisistirUsuarios();
    }

    /**
     * Obtiene un objeto {@code Usuario} dado su nombre de usuario.
     * 
     * @param nombreUsuario El nombre del usuario.
     * @return El objeto {@code Usuario} asociado al nombre dado.
     */
    public Usuario obtenerUsuario(String nombreUsuario) {
        return this.getHashUsuarios().get(nombreUsuario);
    }

    /**
     * Verifica si existe un usuario con el nombre dado.
     * 
     * @param nombreUsuario El nombre del usuario a verificar.
     * @return {@code true} si el usuario existe, {@code false} en caso contrario.
     */
    public boolean existeUsuario(String nombreUsuario) {
        return getHashUsuarios().containsKey(nombreUsuario);
    }

    /**
     * Verifica si existe un usuario con el nombre y la contraseña proporcionados.
     * Se utiliza para la autenticación de usuarios.
     * 
     * @param nombreUsuario El nombre del usuario a verificar.
     * @param password      La contraseña del usuario.
     * @return {@code true} si el usuario existe y la contraseña es correcta,
     *         {@code false} en caso contrario.
     */
    public boolean existeUsuarioLogin(String nombreUsuario, String password) {
        return getHashUsuarios().containsKey(nombreUsuario)
                && getHashUsuarios().get(nombreUsuario).getContrasenia().equals(password);
    }

    /**
     * Actualiza la contraseña de un usuario identificado por su nombre de usuario.
     * 
     * @param nombre           El nombre del usuario cuya contraseña se va a
     *                         actualizar.
     * @param nuevaContrasenia La nueva contraseña para el usuario.
     */
    public void actualizarContrasenia(String nombre, String nuevaContrasenia) {
        obtenerUsuario(nombre).setContrasenia(nuevaContrasenia);
        perisistirUsuarios();
    }
    
    /**
     * Actualiza la colección de usuarios cargando los datos desde un archivo.
     */
    public void actualizarListaDeUsuarios() {
        PersistirUsuarios persistirUsuarios = new PersistirUsuarios();
        this.setHashUsuarios(persistirUsuarios.cargarUsuariosDesdeArchivo());
    }

    /**
     * Persiste la colección completa de usuarios en un archivo.
     * 
     */
    public void perisistirUsuarios() {
        PersistirUsuarios persistir = new PersistirUsuarios();
        persistir.persistirUsuariosDesdeArchivo(this.getHashUsuarios());
    }
}
