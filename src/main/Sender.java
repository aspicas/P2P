package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by gbsojo on 6/11/16.
 * http://www.java2s.com/Code/Java/Network-Protocol/TransferafileviaSocket.htm
 */
public class Sender extends Thread{
    public int port = 3000;
    public ServerSocket server = null;
    public Socket client = null;
    public int idSession;
    public DataOutputStream output = null;
    public DataInputStream input = null;

    /**
     * Constructor de la clase
     *
     */

    public Sender() {
        try {
            this.server = new ServerSocket(port);
            this.idSession = 0;
        }
        catch (IOException ex) {
            Logger.getLogger(HiloD.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Funcion de escucha del Socket
     */
    public void Listening(){
        try {
            while (true){
                this.client = server.accept();
                ((HiloD) new HiloD(client)).start();
                this.idSession++;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Funcion para mantener el Socket escuchando por una nueva peticion.
     */
    @Override
    public void run(){
        Listening();
    }

}
