
import conexion.ConexionServidor;
import conexion.Switch;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para manejar la conexión del servidor.
 * Este servidor escucha en un puerto específico y procesa mensajes de los clientes.
 */

/**
 *
 * @author Gabriel
 */
public class Main {
    
    private ServerSocket servidor; // Socket servidor
    private Socket soc; // Socket cliente
    private final int puerto = 6464; // Puerto que no esté en uso
    private DataInputStream in; // Flujo de datos de entrada
    private DataOutputStream out; // Flujo de datos de salida

    /**
     * Inicia el servidor y escucha las conexiones entrantes.
     * Se espera que los mensajes de los clientes tengan la estructura:
     * [contenidoMensaje,;,ClaseDestino,;,Operacion]
     */
    public void iniciar() {
        try {
            servidor = new ServerSocket(puerto);
            System.out.println(" # Servidor iniciado #");

            boolean continuar = true;

            while (continuar) {
                // Queda escuchando hasta que se realiza una conexión a este socket
                soc = servidor.accept();
                System.out.println("Se ha conectado un cliente");

                try {
                    in = new DataInputStream(soc.getInputStream());
                    out = new DataOutputStream(soc.getOutputStream());

                    String mensaje = in.readUTF(); // Se guarda la entrada en un String
                    String[] tokens = mensaje.split(",;,");
                    String contenidoMensaje = tokens[0].trim();
                    String claseDestino = tokens[1].trim();
                    String operacion = tokens[2].trim();
                    //System.out.println("contenido: " + contenidoMensaje);
                    //System.out.println("clase: " + claseDestino);
                    //System.out.println("operacion: " + operacion);
                    System.out.println(" > Comunicacion recibida: " + mensaje);

                    if (tokens.length < 3) {
                        out.writeUTF("Formato de mensaje incorrecto");
                        continue; // Continúa con la siguiente iteración del bucle
                    }

                    Switch derivador = new Switch(contenidoMensaje, claseDestino, operacion);

                    if (derivador.validarMensaje() && derivador.validarClaseFinal()) {
                        String retorno = derivador.derivadorDeClases();
                        out.writeUTF(retorno);
                        System.out.println(" < Comunicacion enviada: " + retorno);//UNICA IMPRESION DE LAS COMUNICACIONES. ELIMINAR EL RESTO DE LOS PRINT.
                    } else {
                        out.writeUTF("No existe dicha operación");
                    }

                    if (mensaje.equals("desconectar")) {
                        System.out.println("Servidor desconectado");
                        continuar = false;
                    }

                } catch (IOException ex) {
                    Logger.getLogger(conexion.ConexionServidor.class.getName()).log(Level.SEVERE,
                            "Error de E/S: " + ex.getMessage(), ex);
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
                        Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE,
                                "Error al cerrar el socket: " + ex.getMessage(), ex);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE,
                    "Error al iniciar el servidor: " + ex.getMessage(), ex);
        }
    }

  public static void main(String[] args) {
        
      ConexionServidor server = new ConexionServidor();
      server.iniciar();
             
   
    }
}
