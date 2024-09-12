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
     * Obtiene el socket del servidor.
     * 
     * @return el socket del servidor utilizado para aceptar conexiones de clientes.
     */
    public ServerSocket getServidor() {
        return servidor;
    }

    /**
     * Establece el socket del servidor.
     * 
     * @param servidor el socket servidor a utilizar para aceptar conexiones.
     */
    public void setServidor(ServerSocket servidor) {
        this.servidor = servidor;
    }

    /**
     * Obtiene el socket del cliente.
     * 
     * @return el socket que representa la conexión con un cliente.
     */
    public Socket getSoc() {
        return soc;
    }

    /**
     * Establece el socket del cliente.
     * 
     * @param soc el socket cliente que representa la conexión con un cliente.
     */
    public void setSoc(Socket soc) {
        this.soc = soc;
    }

    /**
     * Obtiene el puerto en el que el servidor está escuchando.
     * 
     * @return el puerto en el que el servidor escucha por conexiones entrantes.
     */
    public int getPuerto() {
        return puerto;
    }

    // No se crea un setPuerto porque el puerto es final y no puede ser modificado

    /**
     * Obtiene el flujo de entrada de datos.
     * 
     * @return el flujo de entrada utilizado para recibir datos del cliente.
     */
    public DataInputStream getIn() {
        return in;
    }

    /**
     * Establece el flujo de entrada de datos.
     * 
     * @param in el flujo de entrada que se utilizará para recibir datos del cliente.
     */
    public void setIn(DataInputStream in) {
        this.in = in;
    }

    /**
     * Obtiene el flujo de salida de datos.
     * 
     * @return el flujo de salida utilizado para enviar datos al cliente.
     */
    public DataOutputStream getOut() {
        return out;
    }
    
    /**
     * Establece el flujo de salida de datos.
     * 
     * @param out el flujo de salida que se utilizará para enviar datos al cliente.
     */
    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    /**
     * Inicia el servidor y escucha las conexiones entrantes.
     */
    public void iniciar() {
        try {
            servidor = new ServerSocket(puerto); // Crea un socket del servidor en el puerto definido.
            System.out.println(" # Servidor iniciado #");

            boolean continuar = true;
            while (continuar) {
                // Espera hasta que un cliente se conecte al servidor.
                this.setSoc(servidor.accept());
                System.out.println("Se ha conectado un cliente");

                try {
                    this.setIn(new DataInputStream(soc.getInputStream())); 
                    this.setOut(new DataOutputStream(soc.getOutputStream()));
                    String mensaje = in.readUTF(); // Lee el mensaje recibido como un string UTF.

                    if (mensaje.equals("desconectar")) {
                        System.out.println("Servidor desconectado"); // Cierra la conexión si el cliente envía
                        // "desconectar".
                        continuar = false;
                    } else {
                        this.procesarSolicitud(mensaje);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE,
                            "Error de E/S: " + ex.getMessage(), ex); // Registra errores de entrada/salida.
                } finally {
                    try {
                        if (in != null) {
                            in.close(); // Cierra el flujo de entrada si está abierto.
                        }
                        if (out != null) {
                            out.close(); // Cierra el flujo de salida si está abierto.
                        }
                        if (soc != null) {
                            soc.close(); // Cierra el socket del cliente.
                        }
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

    /**
     * Procesa la petición del cliente.
     * @param solicitud petición del cliente.solicitud@throws IOException 
     * @throws java.io.IOException 
     */
    public void procesarSolicitud(String solicitud) throws IOException {
        String[] tokens = solicitud.split(",;,"); // Divide el mensaje recibido en partes separadas por ",;,"

        if (tokens.length < 3) {
            this.getOut().writeUTF("Formato de mensaje incorrecto"); // Envía un mensaje de error si el formato es
            // incorrecto.
        } else {
            String contenidoMensaje = tokens[0].trim(); // Extrae el contenido del mensaje.
            String claseDestino = tokens[1].trim(); // Extrae la clase destino del mensaje.
            String operacion = tokens[2].trim(); // Extrae la operación a realizar.
            Switch derivador = new Switch(contenidoMensaje, claseDestino, operacion); // Crea una instancia del
            // derivador para manejar la solicitud.
            if (derivador.validarMensaje() && derivador.validarClaseFinal()) { // Si no son vacíos
                String retorno = derivador.derivadorDeClases(); // Deriva el mensaje a la clase y operación
                // correspondiente.
                this.getOut().writeUTF(retorno); // Envía la respuesta al cliente.
                System.out.println(" < Comunicacion enviada: " + retorno); // Única impresión de la comunicación
                // enviada.
            } else {
                this.getOut().writeUTF("No existe dicha operación"); // Envía un mensaje de error si la operación no
                // existe.
            }
        }
    }
}
