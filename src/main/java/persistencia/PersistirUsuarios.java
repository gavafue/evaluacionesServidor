package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import logica.Usuario;
import logica.Usuarios;

/**
 * La clase {@code PersistirUsuarios} maneja la persistencia de usuarios en un
 * archivo de texto.
 * Permite guardar la lista de usuarios y cargar usuarios desde el archivo.
 * 
 */
public class PersistirUsuarios {

    /**
     * Persiste la lista de usuarios en un archivo de texto.
     * 
     * @param hashUsuarios Un {@code HashMap} que contiene los usuarios a persistir.
     */
    public void persistirListaDeUsuariosEnArchivo(HashMap<String, Usuario> hashUsuarios) {
        try (FileWriter fw = new FileWriter("passwords.txt")) {
            for (Map.Entry<String, Usuario> entry : hashUsuarios.entrySet()) {
                fw.write(entry.getKey() + ";" + entry.getValue().getContrasenia() + ";"
                        + entry.getValue().getTipoDeUsuario() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga los usuarios desde un archivo de texto y los devuelve en un
     * {@code HashMap}.
     * 
     * @return Un {@code HashMap} que contiene los usuarios cargados desde el
     *         archivo.
     */
    public HashMap<String, Usuario> cargarUsuariosDesdeArchivo() {
        Usuarios listaUsuarios = new Usuarios();
        try (Scanner s = new Scanner(new File("passwords.txt"))) {
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                String[] usuario = linea.split(";"); // de momento dos posiciones. Podria haber un tercer
                // atributo que identifique tipo de usuario.
                listaUsuarios.agregarUsuario(new Usuario(usuario[0], usuario[1], usuario[2]));
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        return listaUsuarios.getListaUsuarios();
    }
}
