package main;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

/**
 * Created by aspicas on 5/20/16.
 */

/**
 * Este es el servidor que se mantiene constantemente escuchando por peticiones y se encarga de crear nuevos Hilos para
 * el manejo de cada una
 */
public class Servidor extends Thread{
    public int port = 2000;
    public ServerSocket server = null;
    public Socket client = null;
    public int idSession;
    public String[] sp = null;

    public Servidor() {
        try {
            this.server = new ServerSocket(port);
            this.idSession = 0;
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Funcion de escucha del servidor
     */
    public void Listening(){
        try {
            while (true){
                this.client = server.accept();
                ((Hilo) new Hilo(client, idSession)).start();
                this.idSession++;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @Override
    public void run(){
        Listening();
    }
}
