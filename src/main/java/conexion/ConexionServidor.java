package conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para manejar la conexión del servidor. Este servidor escucha en un
 * puerto específico y procesa mensajes de los clientes.
 */
public class ConexionServidor {

    private ServerSocket servidor; // Socket servidor utilizado para aceptar conexiones de clientes.
    private Socket soc; // Socket cliente que representa la conexión con un cliente.
    private final int puerto = 6464; // Puerto en el que el servidor escucha por conexiones entrantes.
    private DataInputStream in; // Flujo de entrada para recibir datos del cliente.
    private DataOutputStream out; // Flujo de salida para enviar datos al cliente.

    /**
     * Inicia el servidor y escucha las conexiones entrantes. Procesa mensajes de
     * los clientes y los deriva a clases y operaciones específicas.
     */
    public void iniciar() {
        try {
            servidor = new ServerSocket(puerto); // Crea un socket del servidor en el puerto definido.
            System.out.println(" # Servidor iniciado #");

            boolean continuar = true;

            while (continuar) {
                // Espera hasta que un cliente se conecte al servidor.
                soc = servidor.accept();
                System.out.println("Se ha conectado un cliente");

                try {
                    in = new DataInputStream(soc.getInputStream()); // Obtiene el flujo de entrada del socket cliente.
                    out = new DataOutputStream(soc.getOutputStream()); // Obtiene el flujo de salida del socket cliente.

                    String mensaje = in.readUTF(); // Lee el mensaje recibido como un string UTF.
                    String[] tokens = mensaje.split(",;,"); // Divide el mensaje recibido en partes separadas por ",;,"
                    String contenidoMensaje = tokens[0].trim(); // Extrae el contenido del mensaje.
                    String claseDestino = tokens[1].trim(); // Extrae la clase destino del mensaje.
                    String operacion = tokens[2].trim(); // Extrae la operación a realizar.

                    if (tokens.length < 3) {
                        out.writeUTF("Formato de mensaje incorrecto"); // Envía un mensaje de error si el formato es
                                                                       // incorrecto.
                        continue; // Continúa con la siguiente conexión.
                    }

                    Switch derivador = new Switch(contenidoMensaje, claseDestino, operacion); // Crea una instancia del
                                                                                              // derivador para manejar
                                                                                              // el mensaje.

                    if (derivador.validarMensaje() && derivador.validarClaseFinal()) { // Si no son vacíos
                        String retorno = derivador.derivadorDeClases(); // Deriva el mensaje a la clase y operación
                                                                        // correspondiente.
                        out.writeUTF(retorno); // Envía la respuesta al cliente.
                        System.out.println(" < Comunicacion enviada: " + retorno); // Única impresión de la comunicación
                                                                                   // enviada.
                    } else {
                        out.writeUTF("No existe dicha operación"); // Envía un mensaje de error si la operación no
                                                                   // existe.
                    }

                    if (mensaje.equals("desconectar")) {
                        System.out.println("Servidor desconectado"); // Cierra la conexión si el cliente envía
                                                                     // "desconectar".
                        continuar = false;
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE,
                            "Error de E/S: " + ex.getMessage(), ex); // Registra errores de entrada/salida.
                } finally {
                    try {
                        if (in != null)
                            in.close(); // Cierra el flujo de entrada si está abierto.
                        if (out != null)
                            out.close(); // Cierra el flujo de salida si está abierto.
                        if (soc != null)
                            soc.close(); // Cierra el socket del cliente.
                        System.out.println("Cliente desconectado"); // Mensaje cuando un cliente se desconecta.
                    } catch (IOException ex) {
                        Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE,
                                "Error al cerrar el socket: " + ex.getMessage(), ex); // Registra errores al cerrar los
                                                                                      // recursos.
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE,
                    "Error al iniciar el servidor: " + ex.getMessage(), ex); // Registra errores al iniciar el servidor.
        }
    }
}
