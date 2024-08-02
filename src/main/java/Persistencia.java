/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gabriel
 */
public class Persistencia {
    public void persistirListaDeUsuariosEnArchivo(HashMap<String, Usuario> hashUsuarios) {
        try {
            FileWriter fw = new FileWriter("passwords.txt");
            for (Map.Entry<String, Usuario> entry : hashUsuarios.entrySet()) { // CODIFICACION SUGERIDA EN LA
                                                                               // DOCUMENTACION OFICIAL DE JAVA.
                fw.write(entry.getKey() + ";" + entry.getValue().getContrasenia() + ";"
                        + entry.getValue().getTipoDeUsuario() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace(); // @todo, despues tenemos que redirigir estos errores a un log.
        }
    }
}
