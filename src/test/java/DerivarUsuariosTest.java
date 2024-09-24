/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import derivadores.DerivarUsuarios;
import logica.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gonzalito
 */
public class DerivarUsuariosTest {

    @Test
    public void testValidarDatosUsuarioVacio() {
        DerivarUsuarios derivador = new DerivarUsuarios("Login", "77777777;;;pass1");

        assertFalse(derivador.validarDatos("", "password"));
    }

    @Test
    public void testValidarDatosContraseniaVacia() {
        DerivarUsuarios derivador = new DerivarUsuarios("Login", "77777777;;;pass1");

        assertFalse(derivador.validarDatos("usuario", ""));
    }

    @Test
    public void testDerivarUsuariosOperacionAlta() {
        DerivarUsuarios derivador = new DerivarUsuarios("Alta", "77777777;;;pass1");

        if (derivador.getUsuarios().existeUsuario("77777777")) {
            derivador.getUsuarios().eliminarUsuario("77777777");
        }
        String resultado = derivador.derivarUsuarios();
        assertEquals("Usuario creado con éxito,;,200", resultado);
    }

    @Test
    public void testDerivarUsuariosOperacionLoginCorrecto() {
        DerivarUsuarios derivador = new DerivarUsuarios("Login", "77777777;;;pass1");
        if (!derivador.getUsuarios().existeUsuario("77777777")) {
            derivador.getUsuarios().agregarUsuario(new Usuario("77777777", "pass1", "estudiante"));
        }

        String resultado = derivador.derivarUsuarios();
        assertEquals("estudiante,;,200", resultado);
        derivador.getUsuarios().eliminarUsuario("77777777");
    }

    @Test
    public void testDerivarUsuariosOperacionLoginInvalido() {
        DerivarUsuarios derivador = new DerivarUsuarios("Login", "77777777;;;wrongpass");
        if (!derivador.getUsuarios().existeUsuario("77777777")) {
            derivador.getUsuarios().agregarUsuario(new Usuario("77777777", "pass1", "estudiante"));
        }

        String resultado = derivador.derivarUsuarios();
        assertEquals("Usuario y/o contraseña incorrectos,;,400", resultado);
        derivador.getUsuarios().eliminarUsuario("77777777");
    }

    @Test
    public void testDerivarCambioPasswordCorrecto() {
        DerivarUsuarios derivador = new DerivarUsuarios("CambioPassword", "77777777;;;newpass");
        if (!derivador.getUsuarios().existeUsuario("77777777")) {
            derivador.getUsuarios().agregarUsuario(new Usuario("77777777", "oldpass", "estudiante"));
        }

        String resultado = derivador.derivarUsuarios();
        assertEquals("Cambio con éxito,;,200", resultado);
        assertEquals("newpass", derivador.getUsuarios().obtenerUsuario("77777777").getContrasenia());
        derivador.getUsuarios().eliminarUsuario("77777777");
    }

    @Test
    public void testDerivarCambioPasswordUsuarioNoExiste() {
        DerivarUsuarios derivador = new DerivarUsuarios("CambioPassword", "77777777;;;newpass");

        if (derivador.getUsuarios().existeUsuario("77777777")) {
            derivador.getUsuarios().eliminarUsuario("77777777");
        }

        String resultado = derivador.derivarUsuarios();
        assertEquals("No existe usuario,;,500", resultado);
    }

    @Test
    public void testDerivarValidezNombreUsuarioCorrecto() {
        DerivarUsuarios derivador = new DerivarUsuarios("Validez", "77777777");

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
    public void testDerivarExistenciaUsuarioExiste() {
        DerivarUsuarios derivador = new DerivarUsuarios("Existencia", "77777777");

        if (!derivador.getUsuarios().existeUsuario("77777777")) {
            derivador.getUsuarios().agregarUsuario(new Usuario("77777777", "pass1", "estudiante"));
        }

        String resultado = derivador.derivarUsuarios();
        assertEquals("Usuario existente en el sistema,;,200", resultado);
        derivador.getUsuarios().eliminarUsuario("77777777");
    }

    @Test
    public void testDerivarExistenciaUsuarioNoExiste() {
        DerivarUsuarios derivador = new DerivarUsuarios("Existencia", "77777777");

        if (derivador.getUsuarios().existeUsuario("77777777")) {
            derivador.getUsuarios().eliminarUsuario("77777777");
        }

        String resultado = derivador.derivarUsuarios();
        assertEquals("Usuario NO existe,;,400", resultado);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
