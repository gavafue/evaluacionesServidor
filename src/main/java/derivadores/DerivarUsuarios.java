package derivadores;


import logica.Usuario;
import logica.Usuarios;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


 /**
  * La clase <code>DerivarUsuarios</code> gestiona operaciones relacionadas con usuarios,
  * incluyendo alta, login, validación, existencia y cambio de contraseña.
  * 
  * <p>Esta clase permite derivar diferentes operaciones según la solicitud recibida.</p>
  * 
  * @author Gabriel, Anna, Santiago, Juan y Gonzalo
  */
 public class DerivarUsuarios {
 
     private String operacion;
     private String mensaje;
 
     /**
      * Constructor que inicializa la operación y el mensaje.
      * 
      * @param operacion La operación a realizar (e.g., "Alta", "Login").
      * @param mensaje El mensaje que contiene datos necesarios para la operación.
      */
     public DerivarUsuarios(String operacion, String mensaje) {
         this.operacion = operacion;
         this.mensaje = mensaje;
     }
 
     /**
      * Establece el mensaje.
      * 
      * @param mensaje El nuevo mensaje.
      */
     public void setMensaje(String mensaje) {
         this.mensaje = mensaje;
     }
 
     /**
      * Obtiene el mensaje.
      * 
      * @return El mensaje actual.
      */
     public String getMensaje() {
         return this.mensaje;
     }
 
     /**
      * Establece la operación.
      * 
      * @param operacion La nueva operación.
      */
     public void setOperacion(String operacion) {
         this.operacion = operacion;
     }
 
     /**
      * Obtiene la operación.
      * 
      * @return La operación actual.
      */
     public String getOperacion() {
         return this.operacion;
     }
 
     /**
      * Método que gestiona la derivación de las operaciones con usuarios según el valor de <code>operacion</code>.
      * 
      * @return El resultado de la operación correspondiente.
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
      * Método para derivar la creación de un usuario.
      * 
      * <p>Este método valida el formato del mensaje, verifica si el usuario ya existe,
      * y si no existe, lo crea con el rol "estudiante".</p>
      * 
      * @return Una cadena con el resultado de la operación y el código de estado HTTP correspondiente.
      */
     public String derivarCrearUsuario() {
         String retorno = "";
         try {
             String mensaje = this.getMensaje();
 
             // Validar que el mensaje no esté vacío ni sea solo espacios.
             if (mensaje == null || mensaje.isBlank()) {
                 retorno = "Mensaje vacío,;,400";
                 return retorno;
             }
 
             // Divide el mensaje en tokens usando ";;;" como delimitador.
             String[] tokens = mensaje.split(";;;");
 
             // Validar que el mensaje contenga dos tokens.
             if (tokens.length != 2) {
                 retorno = "Formato de mensaje incorrecto,;,400";
                 return retorno;
             }
 
             String usuario = tokens[0];
             String contrasenia = tokens[1];
 
             // Validar que el usuario y la contraseña no estén vacíos.
             if (usuario.isBlank() || contrasenia.isBlank()) {
                 retorno = "Usuario y/o contraseña vacíos,;,400";
                 return retorno;
             }
 
             Usuarios listaUsuarios = new Usuarios(); // Carga la lista de usuarios desde un archivo
 
             // Validar la existencia del usuario.
             if (listaUsuarios.existeUsuario(usuario)) {
                 String tipoDeUsuario = listaUsuarios.obtenerUsuario(usuario).getTipoDeUsuario().trim();
                 retorno = "El documento ya tiene un usuario registrado de tipo " + tipoDeUsuario + ",;,400";
             } else {
                 // Intentar agregar un nuevo usuario.
                 Usuario nuevoUsuario = new Usuario(usuario, contrasenia, "estudiante");
                 listaUsuarios.agregarUsuario(nuevoUsuario);
                 listaUsuarios.perisistirUsuarios();
                 retorno = "Usuario creado con éxito,;,200";
             }
         } catch (Exception e) {
             // Manejar cualquier otra excepción no controlada.
             retorno = "Error del servidor: " + e.getMessage() + ",;,500";
         }
         return retorno;
     }
 
     /**
      * Deriva la operación de login para los usuarios.
      * 
      * <p>Este método valida el formato del mensaje y verifica las credenciales del usuario.</p>
      * 
      * @return El resultado de la operación de login con el código de estado HTTP correspondiente.
      */
     public String derivarLogin() {
         String retorno = null;
         try {
             String msj = this.getMensaje();
 
             // Validar que el mensaje no esté vacío.
             if (msj == null || msj.isEmpty()) {
                 retorno = "Mensaje vacío,;,400";
                 return retorno;
             }
 
             String[] tokens = msj.split(";;;"); // Divide el mensaje en tokens usando ";;;" como delimitador
 
             // Validar que el mensaje contenga dos tokens.
             if (tokens.length != 2) {
                 retorno = "Formato de mensaje incorrecto,;,400";
                 return retorno;
             }
 
             String usuario = tokens[0];
             String contrasenia = tokens[1];
 
             // Validar que el usuario y la contraseña no estén vacíos.
             if (usuario.isEmpty() || contrasenia.isEmpty()) {
                 retorno = "Usuario y/o contraseña vacíos,;,400";
                 return retorno;
             }
 
             Usuarios listaUsuarios = new Usuarios();
             listaUsuarios.cargarUsuarios(); // Carga la lista de usuarios desde una fuente de datos
 
             // Validar la existencia del usuario y la contraseña.
             if (listaUsuarios.existeUsuarioLogin(usuario, contrasenia)) {
                 String tipoDeUsuario = listaUsuarios.obtenerUsuario(usuario).getTipoDeUsuario().trim();
                 retorno = tipoDeUsuario + ",;,200";
             } else {
                 retorno = "Usuario y/o contraseña incorrectos,;,400";
             }
         } catch (Exception e) {
             // Manejar cualquier excepción no controlada.
             retorno = "Error del servidor: " + e.getMessage() + ",;,500";
         }
         return retorno;
     }
 
     /**
      * Método que gestiona el cambio de contraseña de los usuarios.
      * 
      * <p>Este método valida el formato del mensaje y actualiza la contraseña del usuario.</p>
      * 
      * @return El resultado de la operación con el código de estado HTTP correspondiente.
      */
     public String derivarCambioPassword() {
         Usuarios usuarios = new Usuarios();
         usuarios.cargarUsuarios();
         String retorno = "";
         String[] tokens = mensaje.split(";;;");
         String usuario = tokens[0];
         String nuevaContrasenia = tokens[1];
 
         // Verificar si el usuario existe y actualizar la contraseña.
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
      * Método que responde si un usuario existe dado un identificador.
      * 
      * <p>Este método valida el formato del mensaje y verifica si el usuario está registrado.</p>
      * 
      * @return El resultado de la operación con el código de estado HTTP correspondiente.
      */
     public String derivarExistenciaUsuario() {
         String retorno = "";
 
         // Validar el formato del mensaje.
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
      * Método que valida el formato de un identificador de usuario.
      * 
      * <p>Este método verifica si el identificador cumple con el formato esperado (debe ser exactamente 8 dígitos).</p>
      * 
      * @return El resultado de la validación con el código de estado HTTP correspondiente.
      */
     public String derivarValidezNombreUsuario() {
         String retorno = "";
 
         // Verificar si el mensaje no está vacío y tiene exactamente 8 caracteres.
         if (!this.getMensaje().isBlank() && this.getMensaje().length() == 8) {
             retorno = "Formato de CI correcto,;,200";
         } else {
             retorno = "Cédula en formato incorrecto. [Deben ser 8 dígitos exactos],;,400";
         }
 
         return retorno;
     }
 }
 