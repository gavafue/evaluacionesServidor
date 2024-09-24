/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import derivadores.DerivarUsuarios;
import logica.Usuario;
import logica.Usuarios;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gonzalito
 */
public class DerivarUsuariosTest {

    @Test
    public void testConstructorInicializaCorrectamente() {
        DerivarUsuarios derivador = new DerivarUsuarios("Alta", "user1;;;pass1");

        assertEquals("Alta", derivador.getOperacion());
        assertEquals("user1;;;pass1", derivador.getMensaje());
        assertNotNull(derivador.getUsuarios());
    }

    @Test
    public void testValidarDatosUsuarioVacio() {
        DerivarUsuarios derivador = new DerivarUsuarios("Login", "user1;;;pass1");
        assertFalse(derivador.validarDatos("", "password"));
    }

    @Test
    public void testValidarDatosContraseniaVacia() {
        DerivarUsuarios derivador = new DerivarUsuarios("Login", "user1;;;pass1");

        assertFalse(derivador.validarDatos("usuario", ""));
    }

    @Test
    public void testDerivarUsuariosOperacionAlta() {
        DerivarUsuarios derivador = new DerivarUsuarios("Alta", "user1;;;pass1");
        Usuarios usuarios = new Usuarios();
        usuarios.perisistirUsuarios();
        derivador.setUsuarios(usuarios);

        String resultado = derivador.derivarUsuarios();
        assertEquals("Usuario creado con éxito,;,200", resultado);
    }

    @Test
    public void testDerivarUsuariosOperacionLoginCorrecto() {
        DerivarUsuarios derivador = new DerivarUsuarios("Login", "user1;;;pass1");
        Usuarios usuarios = new Usuarios();
        usuarios.perisistirUsuarios();
        usuarios.agregarUsuario(new Usuario("user1", "pass1", "estudiante"));
        derivador.setUsuarios(usuarios);

        String resultado = derivador.derivarUsuarios();
        assertEquals("estudiante,;,200", resultado);
    }

    @Test
    public void testDerivarUsuariosOperacionLoginInvalido() {
        DerivarUsuarios derivador = new DerivarUsuarios("Login", "user1;;;wrongpass");
        Usuarios usuarios = new Usuarios();
        usuarios.perisistirUsuarios();
        usuarios.agregarUsuario(new Usuario("user1", "pass1", "estudiante"));
        derivador.setUsuarios(usuarios);

        String resultado = derivador.derivarUsuarios();
        assertEquals("Usuario y/o contraseña incorrectos,;,400", resultado);
    }

    @Test
    public void testDerivarCambioPasswordCorrecto() {
        DerivarUsuarios derivador = new DerivarUsuarios("CambioPassword", "user1;;;newpass");
        Usuarios usuarios = new Usuarios();
        usuarios.perisistirUsuarios();
        usuarios.agregarUsuario(new Usuario("user1", "oldpass", "estudiante"));
        derivador.setUsuarios(usuarios);

        String resultado = derivador.derivarUsuarios();
        assertEquals("Cambio con éxito,;,200", resultado);
        assertEquals("newpass", usuarios.obtenerUsuario("user1").getContrasenia());
    }

    @Test
    public void testDerivarCambioPasswordUsuarioNoExiste() {
        DerivarUsuarios derivador = new DerivarUsuarios("CambioPassword", "user1;;;newpass");
        Usuarios usuarios = new Usuarios(); // No se agrega el usuario
        usuarios.perisistirUsuarios();
        derivador.setUsuarios(usuarios);

        String resultado = derivador.derivarUsuarios();
        assertEquals("No existe usuario,;,500", resultado);
    }

    @Test
    public void testDerivarValidezNombreUsuarioCorrecto() {
        DerivarUsuarios derivador = new DerivarUsuarios("Validez", "12345678");

        String resultado = derivador.derivarValidezNombreUsuario();
        assertEquals("Formato de CI correcto,;,200", resultado);
    }

    @Test
    public void testDerivarValidezNombreUsuarioIncorrecto() {
        DerivarUsuarios derivador = new DerivarUsuarios("Validez", "1234ABC");

        String resultado = derivador.derivarValidezNombreUsuario();
        assertEquals("Cédula en formato incorrecto. [Deben ser 8 dígitos numéricos exactos],;,400", resultado);
    }

    @Test
    public void testDerivarExistenciaUsuarioCorrecto() {
        DerivarUsuarios derivador = new DerivarUsuarios("Existencia", "12345678");
        Usuarios usuarios = new Usuarios();
        usuarios.perisistirUsuarios();
        usuarios.agregarUsuario(new Usuario("12345678", "pass1", "estudiante"));
        derivador.setUsuarios(usuarios);

        String resultado = derivador.derivarUsuarios();
        assertEquals("Usuario existente en el sistema,;,200", resultado);
    }

    @Test
    public void testDerivarExistenciaUsuarioNoExiste() {
        DerivarUsuarios derivador = new DerivarUsuarios("Existencia", "12345678");
        Usuarios usuarios = new Usuarios(); // No se agrega el usuario
        usuarios.perisistirUsuarios();
        derivador.setUsuarios(usuarios);

        String resultado = derivador.derivarUsuarios();
        assertEquals("Usuario NO existe,;,400", resultado);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
