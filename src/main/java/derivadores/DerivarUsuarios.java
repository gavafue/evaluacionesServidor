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
     * Método principal que gestiona la derivación de las operaciones con usuarios
     * según el valor de la operación configurada.
     * 
     * Dependiendo del valor de la operación, llama al método correspondiente para
     * llevar a cabo la acción solicitada.
     * 
     * @return El resultado de la operación correspondiente en forma de cadena.
     */
    public String derivarUsuarios() {
        String operacion = this.getOperacion();
        String retorno = "";

        switch (operacion) {
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
     * Este método valida el formato del mensaje, verifica si el usuario ya existe
     * y,
     * si no existe, lo crea con el rol de "estudiante".
     * 
     * @return Una cadena con el resultado de la operación y el código de estado
     *         HTTP correspondiente.
     */
    public String derivarCrearUsuario() {
        String retorno = "";
        try {
            String mensaje = this.getMensaje();

            if (mensaje == null || mensaje.isBlank()) {
                retorno = "Mensaje vacío,;,400";
                return retorno;
            }

            String[] tokens = mensaje.split(";;;");

            if (tokens.length != 2) {
                retorno = "Formato de mensaje incorrecto,;,400";
                return retorno;
            }

            String usuario = tokens[0];
            String contrasenia = tokens[1];

            if (usuario.isBlank() || contrasenia.isBlank()) {
                retorno = "Usuario y/o contraseña vacíos,;,400";
                return retorno;
            }

            Usuarios listaUsuarios = new Usuarios();

            if (listaUsuarios.existeUsuario(usuario)) {
                String tipoDeUsuario = listaUsuarios.obtenerUsuario(usuario).getTipoDeUsuario().trim();
                retorno = "El documento ya tiene un usuario registrado de tipo " + tipoDeUsuario + ",;,400";
            } else {
                Usuario nuevoUsuario = new Usuario(usuario, contrasenia, "estudiante");
                listaUsuarios.agregarUsuario(nuevoUsuario);
                listaUsuarios.perisistirUsuarios();
                retorno = "Usuario creado con éxito,;,200";
            }
        } catch (Exception e) {
            retorno = "Error del servidor: " + e.getMessage() + ",;,500";
        }
        return retorno;
    }

    /**
     * 
     * 
     * Este método valida el formato del mensaje y verifica las credenciales del
     * usuario
     * para realizar el login.
     * 
     * @return El resultado de la operación de login con el código de estado HTTP
     *         correspondiente.
     */
    public String derivarLogin() {
        String retorno = null;
        try {
            String msj = this.getMensaje();

            if (msj == null || msj.isEmpty()) {
                retorno = "Mensaje vacío,;,400";
                return retorno;
            }

            String[] tokens = msj.split(";;;");

            if (tokens.length != 2) {
                retorno = "Formato de mensaje incorrecto,;,400";
                return retorno;
            }

            String usuario = tokens[0];
            String contrasenia = tokens[1];

            if (usuario.isEmpty() || contrasenia.isEmpty()) {
                retorno = "Usuario y/o contraseña vacíos,;,400";
                return retorno;
            }

            Usuarios listaUsuarios = new Usuarios();
            listaUsuarios.actualizarListaDeUsuarios();

            if (listaUsuarios.existeUsuarioLogin(usuario, contrasenia)) {
                String tipoDeUsuario = listaUsuarios.obtenerUsuario(usuario).getTipoDeUsuario().trim();
                retorno = tipoDeUsuario + ",;,200";
            } else {
                retorno = "Usuario y/o contraseña incorrectos,;,400";
            }
        } catch (Exception e) {
            retorno = "Error del servidor: " + e.getMessage() + ",;,500";
        }
        return retorno;
    }

    /**
     * 
     * 
     * Este método valida el formato del mensaje y actualiza la contraseña del
     * usuario
     * si el usuario existe.
     * 
     * @return El resultado de la operación con el código de estado HTTP
     *         correspondiente.
     */
    public String derivarCambioPassword() {
        Usuarios usuarios = new Usuarios();
        usuarios.actualizarListaDeUsuarios();
        String retorno = "";
        String[] tokens = mensaje.split(";;;");
        String usuario = tokens[0];
        String nuevaContrasenia = tokens[1];

        if (usuarios.existeUsuario(usuario)) {
            usuarios.obtenerUsuario(usuario).setContrasenia(nuevaContrasenia);
            usuarios.perisistirUsuarios();
            retorno = "Cambio con éxito,;,200";
        } else {
            retorno = "No existe usuario,;,500";
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
            Usuarios us = new Usuarios();
            if (us.getListaUsuarios().containsKey(this.getMensaje())) {
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
