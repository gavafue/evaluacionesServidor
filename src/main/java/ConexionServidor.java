
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para manejar la conexión del servidor
 */
public class ConexionServidor {

    private ServerSocket servidor; // Socket servidor
    private Socket soc; // Socket cliente
    private final int puerto = 6464; // Puerto que no esté en uso
    private DataInputStream in; // Flujo de datos de entrada
    private DataOutputStream out; // Flujo de datos de salida

    public void iniciar() {
        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            boolean continuar = true;

            while (continuar) {
                // Queda escuchando hasta que se realiza una conexión a este socket
                soc = servidor.accept();
                System.out.println("Se ha conectado un cliente");

                try {
                    in = new DataInputStream(soc.getInputStream());
                    out = new DataOutputStream(soc.getOutputStream());

                    String mensaje = in.readUTF(); // Se guarda la entrada en un String
                    String[] tokens = mensaje.split(",");

                    if (tokens.length < 3) {
                        out.writeUTF("Formato de mensaje incorrecto");
                        continue; // Continúa con la siguiente iteración del bucle
                    }

                    System.out.println("Mensaje recibido: " + mensaje);
                    Switch derivador = new Switch(tokens[0], tokens[1], tokens[2]);

                    if (derivador.validarMensaje() && derivador.validarTipoMensaje()) {
                        out.writeUTF(derivador.obtenerMensajeEvaluacion());
                    } else {
                        out.writeUTF("No exite dicha operacion");
                    }

                    if (mensaje.equals("desconectar")) {
                        System.out.println("Servidor desconectado");
                        continuar = false;
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE, "Error de E/S: " + ex.getMessage(), ex);
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                        if (soc != null) {
                            soc.close(); // Se desconecta al cliente
                            System.out.println("Cliente desconectado");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE, "Error al cerrar el socket: " + ex.getMessage(), ex);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE, "Error al iniciar el servidor: " + ex.getMessage(), ex);
        }
    }
}
