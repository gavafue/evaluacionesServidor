/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import logica.Usuario;
import logica.Usuarios;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para verificar el correcto funcionamiento de la autenticación
 * de usuarios y manejo de contraseñas dentro del sistema.
 * Se prueban varios casos de creación de usuarios, autenticación exitosa y
 * fallida,
 * actualización de contraseñas y manejo de nombres de usuarios duplicados.
 * 
 */
public class AutenticacionTest {

    /**
     * Prueba la creación de un usuario.
     * Verifica que el usuario se crea con los datos correctos.
     */
    @Test
    public void testCrearUsuario() {
        // Datos proporcionados para la prueba
        String nombreUsuario = "10101010";
        String contrasena = "password123";
        String tipo = "administrativo";

        // Crear una instancia de Usuario
        Usuario usuario = new Usuario(nombreUsuario, contrasena, tipo);

        // Verificar que el usuario se crea correctamente
        assertEquals(nombreUsuario, usuario.getIdUsuario(), "El nombre de usuario no es correcto.");
        assertEquals(contrasena, usuario.getContrasenia(), "La contraseña no es correcta.");
        assertEquals(tipo, usuario.getTipoDeUsuario(), "El tipo de usuario no es correcto.");
    }

    /**
     * Prueba la autenticación exitosa de un usuario registrado.
     * Verifica que la autenticación funcione correctamente con credenciales
     * válidas.
     */
    @Test
    public void testAutenticacionExitosa() {
        // Crear la instancia de Usuarios que gestionará los usuarios
        Usuarios usuarios = new Usuarios();
        usuarios.actualizarListaDeUsuarios();

        // Datos proporcionados para la prueba
        String nombreUsuario = "10101010";
        String contrasena = "password123";

        // Registrar un usuario en el sistema
        Usuario admin = new Usuario(nombreUsuario, contrasena, "administrativo");
        if (!usuarios.existeUsuario(admin.getIdUsuario()))
            usuarios.agregarUsuario(admin);

        // Verificar que la autenticación es exitosa con las credenciales correctas
        boolean resultado = usuarios.existeUsuarioLogin(nombreUsuario, contrasena);
        assertTrue(resultado, "La autenticación debería ser exitosa con las credenciales correctas.");
        usuarios.eliminarUsuario(nombreUsuario);
    }

    /**
     * Prueba la autenticación fallida debido a una contraseña incorrecta.
     * Verifica que un usuario no pueda autenticarse con una contraseña incorrecta.
     */
    @Test
    public void testAutenticacionFallida() {
        // Preparación del escenario
        String nombreUsuario = "10101010";
        String contrasenaIncorrecta = "wrongpassword";
        String contrasenaCorrecta = "password123";
        String tipoUsuario = "administrativo";

        // Crear el objeto de Usuario con el tipo
        Usuario usuario = new Usuario(nombreUsuario, contrasenaCorrecta, tipoUsuario);

        // Crear el sistema de usuarios y agregar el usuario
        Usuarios usuarios = new Usuarios();
        usuarios.actualizarListaDeUsuarios();
        if (!usuarios.existeUsuario(usuario.getIdUsuario())) {
            usuarios.agregarUsuario(usuario);
        }

        // Verificar que el usuario no sea autenticado con una contraseña incorrecta
        boolean resultado = usuarios.existeUsuarioLogin(nombreUsuario, contrasenaIncorrecta);
        assertFalse(resultado, "Se esperaba que la autenticación fallara con credenciales incorrectas");
        usuarios.eliminarUsuario(nombreUsuario);
    }

    /**
     * Prueba la creación de un usuario con un nombre de usuario duplicado.
     * Verifica que el sistema arroje una excepción al intentar crear un usuario
     * con un nombre ya existente.
     */
    @Test
    public void testCrearUsuarioConNombreDuplicado() {
        // Crear una instancia de Usuarios
        Usuarios usuarios = new Usuarios();
        usuarios.actualizarListaDeUsuarios();

        // Crear un usuario inicial para que exista un usuario con el nombre "admin"
        if (!usuarios.existeUsuario("10101010")) {
            usuarios.agregarUsuario(new Usuario("10101010", "password123", "administrativo"));
        }
        // Datos para la prueba
        String nombreUsuarioDuplicado = "10101010";
        String nuevaContrasenia = "newpassword123";

        // Verificar que se lanza una excepción al intentar crear un usuario con nombre
        // duplicado
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarios.agregarUsuario(new Usuario(nombreUsuarioDuplicado, nuevaContrasenia, "estudiante"));
        });

        // Verificar el mensaje de error
        String mensajeEsperado = "Un usuario con el nombre \"" + nombreUsuarioDuplicado + "\" ya existe en el sistema.";
        String mensajeRecibido = exception.getMessage();
        assertEquals(mensajeEsperado, mensajeRecibido,
                "El mensaje de error debe indicar que el nombre de usuario ya existe.");
        usuarios.eliminarUsuario(nombreUsuarioDuplicado);
    }

    /**
     * Prueba la actualización de la contraseña de un usuario.
     * Verifica que la contraseña se actualice correctamente y que el usuario no
     * pueda autenticarse con la contraseña anterior.
     */
    @Test
    public void testActualizarContrasenia() {
        // Crear una instancia de Usuarios
        Usuarios usuarios = new Usuarios();
        usuarios.actualizarListaDeUsuarios();

        // Crear un usuario existente y agregarlo al sistema
        Usuario usuarioExistente = new Usuario("10101010", "oldpassword", "administrativo");
        if (!usuarios.existeUsuario("10101010")) {
            usuarios.agregarUsuario(usuarioExistente);
        }

        // Nueva contraseña
        String nuevaContrasenia = "newadmin123";

        // Llamar al método para actualizar la contraseña del usuario "admin"
        usuarios.actualizarContrasenia("10101010", nuevaContrasenia);

        // Verificar que la contraseña es actualizada correctamente
        boolean usuarioAutenticadoConNuevaContrasenia = usuarios.existeUsuarioLogin("10101010", nuevaContrasenia);
        boolean usuarioAutenticadoConContraseniaAntigua = usuarios.existeUsuarioLogin("10101010", "oldpassword");

        // Asegurar que el usuario puede autenticarse con la nueva contraseña
        assertTrue(usuarioAutenticadoConNuevaContrasenia,
                "El usuario debería poder autenticarse con la nueva contraseña.");

        // Asegurar que el usuario no puede autenticarse con la contraseña antigua
        assertFalse(usuarioAutenticadoConContraseniaAntigua,
                "El usuario no debería poder autenticarse con la contraseña antigua.");
        usuarios.eliminarUsuario("10101010");
    }

    /**
     * Prueba la autenticación con un nombre de usuario vacío.
     * Verifica que el sistema no permita la autenticación si el nombre de usuario
     * está vacío.
     */
    @Test
    public void testAutenticacionConNombreVacio() {
        // Crear la instancia de Usuarios y agregar un usuario
        Usuarios usuarios = new Usuarios();
        usuarios.actualizarListaDeUsuarios();
        if (!usuarios.existeUsuario("usuario1")) {
            usuarios.agregarUsuario(new Usuario("usuario1", "password123", "estudiante"));
        }
        String nombreUsuario = ""; // Nombre de usuario vacío
        String contraseña = "password123";

        // Verificar que la autenticación falla
        boolean resultado = usuarios.existeUsuarioLogin(nombreUsuario, contraseña);
        assertFalse(resultado, "Se esperaba que la autenticación falle con un nombre de usuario vacío.");
        usuarios.eliminarUsuario("usuario1");
    }
}
