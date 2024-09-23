/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import logica.Usuario;
import logica.Usuarios;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gonzalito
 */
public class AutenticacionTest {

    @Test
    public void testCrearUsuario() {
        // Datos proporcionados para la prueba
        String nombreUsuario = "10101010";
        String contrasena = "admin123";
        String tipo = "admin";

        // Crear una instancia de Usuario
        Usuario usuario = new Usuario(nombreUsuario, contrasena, tipo);

        // Verificar que el usuario se crea correctamente
        assertEquals(nombreUsuario, usuario.getIdUsuario(), "El nombre de usuario no es correcto.");
        assertEquals(contrasena, usuario.getContrasenia(), "La contraseña no es correcta.");
        assertEquals(tipo, usuario.getTipoDeUsuario(), "El tipo de usuario no es correcto.");
    }

    @Test
    public void testAutenticacionExitosa() {
        // Crear la instancia de Usuarios que gestionará los usuarios
        Usuarios usuarios = new Usuarios();

        // Datos proporcionados para la prueba
        String nombreUsuario = "10101010";
        String contrasena = "admin123";

        // Registrar un usuario en el sistema
        Usuario admin = new Usuario(nombreUsuario, contrasena, "admin");
        usuarios.agregarUsuario(admin);

        // Verificar que la autenticación es exitosa con las credenciales correctas
        boolean resultado = usuarios.existeUsuarioLogin(nombreUsuario, contrasena);
        assertTrue(resultado, "La autenticación debería ser exitosa con las credenciales correctas.");
    }

    @Test
    public void testAutenticacionFallida() {
        // Preparación del escenario
        String nombreUsuario = "10101010";
        String contrasenaIncorrecta = "wrongpassword";
        String contrasenaCorrecta = "admin123";
        String tipoUsuario = "admin"; // Proporcionar el tipo de usuario

        // Crear el objeto de Usuario con el tipo
        Usuario usuario = new Usuario(nombreUsuario, contrasenaCorrecta, tipoUsuario);

        // Crear el sistema de usuarios y agregar el usuario
        Usuarios usuarios = new Usuarios();
        usuarios.agregarUsuario(usuario);

        // Verificar que el usuario no sea autenticado con una contraseña incorrecta
        boolean resultado = usuarios.existeUsuarioLogin(nombreUsuario, contrasenaIncorrecta);
        assertFalse(resultado, "Se esperaba que la autenticación fallara con credenciales incorrectas");
    }

    @Test
    public void testCrearUsuarioConNombreDuplicado() {
        // Crear una instancia de Usuarios
        Usuarios usuarios = new Usuarios();

        // Crear un usuario inicial para que exista un usuario con el nombre "admin"
        usuarios.agregarUsuario(new Usuario("10101010", "password123", "admin"));

        // Datos para la prueba
        String nombreUsuarioDuplicado = "10101010";
        String nuevaContrasenia = "newpassword123";

        // Verificar que se lanza una excepción al intentar crear un usuario con nombre duplicado
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarios.agregarUsuario(new Usuario(nombreUsuarioDuplicado, nuevaContrasenia, "estudiante"));
        });

        // Verificar el mensaje de error
        String mensajeEsperado = "Un usuario con el nombre \"" + nombreUsuarioDuplicado + "\" ya existe en el sistema.";
        String mensajeRecibido = exception.getMessage();
        assertEquals(mensajeEsperado, mensajeRecibido, "El mensaje de error debe indicar que el nombre de usuario ya existe.");
    }

    @Test
    public void testActualizarContrasenia() {
        // Crear una instancia de Usuarios
        Usuarios usuarios = new Usuarios();

        // Crear un usuario existente y agregarlo al sistema
        Usuario usuarioExistente = new Usuario("10101010", "oldpassword", "admin");
        usuarios.agregarUsuario(usuarioExistente);

        // Nueva contraseña
        String nuevaContrasenia = "newadmin123";

        // Llamar al método para actualizar la contraseña del usuario "admin"
        usuarios.actualizarContrasenia("10101010", nuevaContrasenia);

        // Verificar que la contraseña es actualizada correctamente
        // Verificar que el usuario puede autenticarse con la nueva contraseña
        boolean usuarioAutenticadoConNuevaContrasenia = usuarios.existeUsuarioLogin("10101010", nuevaContrasenia);

        // Verificar que el usuario no puede autenticarse con la contraseña antigua
        boolean usuarioAutenticadoConContraseniaAntigua = usuarios.existeUsuarioLogin("10101010", "oldpassword");

        // Asegurar que el usuario puede autenticarse con la nueva contraseña
        assertTrue(usuarioAutenticadoConNuevaContrasenia, "El usuario debería poder autenticarse con la nueva contraseña.");

        // Asegurar que el usuario no puede autenticarse con la contraseña antigua
        assertFalse(usuarioAutenticadoConContraseniaAntigua, "El usuario no debería poder autenticarse con la contraseña antigua.");
    }

    @Test
    public void testAutenticacionConNombreVacio() {
        // Crear la instancia de Usuarios y agregar un usuario
        Usuarios usuarios = new Usuarios();
        usuarios.agregarUsuario(new Usuario("usuario1", "password123", "estudiante"));

        String nombreUsuario = ""; // Nombre de usuario vacío
        String contraseña = "password123";

        // Verificar que la autenticación falla
        boolean resultado = usuarios.existeUsuarioLogin(nombreUsuario, contraseña);
        assertFalse(resultado, "Se esperaba que la autenticación falle con un nombre de usuario vacío.");
    }
}
