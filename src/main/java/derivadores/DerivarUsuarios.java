package derivadores;

import logica.Usuario;
import logica.Usuarios;

/**
 * La clase DerivarUsuarios gestiona operaciones relacionadas con usuarios,
 * incluyendo alta, login, validación de identificadores, existencia y cambio de
 * contraseña.
 * 
 * Permite derivar diferentes operaciones según la solicitud recibida, manejando
 * las acciones correspondientes para cada tipo de operación sobre usuarios.
 * 
 * 
 */
public class DerivarUsuarios {

    private String operacion;
    private String mensaje;
    private Usuarios usuarios;

    /**
     * Constructor que inicializa la operación y el mensaje de la clase
     * DerivarUsuarios.
     * 
     * 
     * @param operacion La operación a realizar, como "Alta", "Login", etc.
     * @param mensaje   El mensaje que contiene los datos necesarios para la
     *                  operación.
     */
    public DerivarUsuarios(String operacion, String mensaje) {
        this.operacion = operacion;
        this.mensaje = mensaje;
        this.usuarios = new Usuarios();
        usuarios.actualizarListaDeUsuarios();
    }

    /**
     * Establece un nuevo mensaje en la instancia de DerivarUsuarios.
     * 
     * 
     * @param mensaje El nuevo mensaje que contiene datos necesarios para la
     *                operación.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el mensaje actual almacenado en la instancia de DerivarUsuarios.
     * 
     * @return El mensaje actual que puede ser utilizado para las operaciones.
     */
    public String getMensaje() {
        return this.mensaje;
    }

    /**
     * Establece una nueva operación en la instancia de DerivarUsuarios.
     * 
     * La operación define el tipo de acción a realizar, como alta de usuario,
     * login, etc.
     * 
     * @param operacion La nueva operación que se desea realizar.
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    /**
     * Obtiene la operación actual almacenada en la instancia de DerivarUsuarios.
     * 
     * @return La operación actual que está programada para ser ejecutada.
     */
    public String getOperacion() {
        return this.operacion;
    }
    
    /**
     * Establece la nueva lista de usuarios.
     * 
     * @param usuarios a nueva lista a usar.
     */
    public void setUsuarios(Usuarios usuarios){
        this.usuarios = usuarios;
    }
    
    /**
     * Obtiene la lista de usuarios del sistema.
     * 
     * @return lista de usuarios.
     */
    public Usuarios getUsuarios(){
        return usuarios;
    }

    /**
     * Valida que el usuario y contraseña no sean vacíos.
     * @param usuario el usuario.
     * @param contrasenia la contraseña del usuario.
     * @return true si son válidos y false en caso contrario.
     */
    public boolean validarDatos(String usuario, String contrasenia){
        boolean esValido = false;
        if((!usuario.isBlank())&&(!contrasenia.isBlank())){
            esValido = true;
        }
        return esValido;
    }
    
    /**
     * Método principal que gestiona la derivación de las operaciones con usuarios
     * según el valor de la operación configurada.
     * 
     * Dependiendo del valor de la operación, llama al método correspondiente para
     * llevar a cabo la acción solicitada.
     * 
     * @return El resultado de la operación correspondiente en forma de cadena.
     */
    public String derivarUsuarios() {
        String operacionActual = this.getOperacion();
        String retorno = "";

        switch (operacionActual) {
            case "Alta":
                retorno = derivarCrearUsuario();
                break;
            case "Login":
                retorno = derivarLogin();
                break;
            case "Validez":
                retorno = derivarValidezNombreUsuario();
                break;
            case "Existencia":
                retorno = derivarExistenciaUsuario();
                break;
            case "CambioPassword":
                retorno = derivarCambioPassword();
                break;
            default:
                retorno = "Error: Operación desconocida.,;,400";
                break;
        }
        return retorno;
    }

    /**
     *
     * Este método valida el formato del mensaje, verifica si el usuario ya
     * existe y, si no existe, lo crea con el rol de "estudiante".
     *
     * @return Una cadena con el resultado de la operación y el código de estado
     * HTTP correspondiente.
     */
    public String derivarCrearUsuario() { // Llegado este punto sabemos que el mensaje no es vacío.
        String retorno = "";
        try {
            String mensajeActual = this.getMensaje();
            String[] tokens = mensajeActual.split(";;;");

            if (tokens.length != 2) { // Si el formato es incorrecto
                retorno = "Formato de mensaje incorrecto,;,400";
            } else { // Si el formato no es incorrecto
                String usuario = tokens[0];
                String contrasenia = tokens[1];

                if (!this.validarDatos(usuario, contrasenia)) {
                    retorno = "Usuario y/o contraseña vacíos,;,400";
                } else {
                    if (this.getUsuarios().existeUsuario(usuario)) {
                        retorno = "El documento ya tiene un usuario registrado,;,400";
                    } else { // Solo se hace alta de estudiantes
                        Usuario nuevoUsuario = new Usuario(usuario, contrasenia, "estudiante");
                        this.getUsuarios().agregarUsuario(nuevoUsuario); // En memoria y en persistencia
                        retorno = "Usuario creado con éxito,;,200";
                    }
                }
            }
        } catch (Exception e) {
            retorno = "Error del servidor: " + e.getMessage() + ",;,500";
        }
        return retorno;
    }

    /**
     * 
     * Este método valida el formato del mensaje y verifica las credenciales del
     * usuario
     * para realizar el login.
     *
     * @return El resultado de la operación de login con el código de estado
     * HTTP correspondiente.
     */
    public String derivarLogin() {
        String retorno = "";
        try {
            String msj = this.getMensaje();
            String[] tokens = msj.split(";;;");

            if (tokens.length != 2) { // Si el formato es incorrecto
                retorno = "Formato de mensaje incorrecto,;,400";
            } else {
                String usuario = tokens[0];
                String contrasenia = tokens[1];

                if (!this.validarDatos(usuario, contrasenia)) { // Si usuario y contraseña no son válidos
                    retorno = "Usuario y/o contraseña vacíos,;,400";
                } else {
                    if (this.getUsuarios().existeUsuarioLogin(usuario, contrasenia)) {
                        String tipoDeUsuario = this.getUsuarios().obtenerUsuario(usuario).getTipoDeUsuario().trim();
                        retorno = tipoDeUsuario + ",;,200";
                    } else {
                        retorno = "Usuario y/o contraseña incorrectos,;,400";
                    }
                }
            }
        } catch (Exception e) {
            retorno = "Error del servidor: " + e.getMessage() + ",;,500";
        }
        return retorno;
    }

    /**
     *
     * Este método valida el formato del mensaje y actualiza la contraseña del
     * usuario si el usuario existe.
     *
     * @return El resultado de la operación con el código de estado HTTP
     * correspondiente.
     */
    public String derivarCambioPassword() {
        String retorno = "";
        try {
            String msj = this.getMensaje();
            String[] tokens = msj.split(";;;");

            if (tokens.length != 2) { // Si el formato es incorrecto
                retorno = "Formato de mensaje incorrecto,;,400";
            } else {
                String usuario = tokens[0];
                String nuevaContrasenia = tokens[1];
                if (!this.validarDatos(usuario, nuevaContrasenia)) { // Si usuario y contraseña no son válidos
                    retorno = "Usuario y/o contraseña vacíos,;,400";
                } else {
                    if (this.getUsuarios().existeUsuario(usuario)) {
                        this.getUsuarios().obtenerUsuario(usuario).setContrasenia(nuevaContrasenia);
                        this.getUsuarios().perisistirUsuarios();
                        retorno = "Cambio con éxito,;,200";
                    } else {
                        retorno = "No existe usuario,;,500";
                    }
                }
            }
        } catch (Exception e) {
            retorno = "Error del servidor: " + e.getMessage() + ",;,500";
        }
        return retorno;
    }

    /**
     * 
     * Este método valida el formato del mensaje y verifica si el usuario está
     * registrado
     * en la base de datos.
     * 
     * @return El resultado de la operación con el código de estado HTTP
     *         correspondiente.
     */
    public String derivarExistenciaUsuario() {
        String retorno = "";

        if (!this.derivarValidezNombreUsuario().contains("400")) {
            if (this.getUsuarios().getHashUsuarios().containsKey(this.getMensaje())) {
                retorno = "Usuario existe,;,200";
            } else {
                retorno = "Usuario NO existe,;,400";
            }
        } else {
            retorno = "Cédula en formato incorrecto.,;,400";
        }
        return retorno;
    }

    /**
     * 
     * Este método verifica si el identificador cumple con el formato esperado, el
     * cual debe
     * ser exactamente 8 dígitos.
     * 
     * @return El resultado de la validación con el código de estado HTTP
     *         correspondiente.
     */
    public String derivarValidezNombreUsuario() {
        String retorno = "";

        if (!this.getMensaje().isBlank() && this.getMensaje().length() == 8) {
            retorno = "Formato de CI correcto,;,200";
        } else {
            retorno = "Cédula en formato incorrecto. [Deben ser 8 dígitos exactos],;,400";
        }

        return retorno;
    }
}
