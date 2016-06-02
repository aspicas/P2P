package main;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * Created by aspicas on 5/20/16.
 */
public class Servidor extends Thread{
    public int port = 2000;
    public ServerSocket server = null;
    public Socket client = null;
    public int idSession;

    public Servidor() {
        try {
            this.server = new ServerSocket(port);
            this.idSession = 0;
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

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
