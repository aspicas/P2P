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
public class Servidor extends Thread{
    public int port = 2000;
    public ServerSocket server = null;
    public Socket client = null;
    public int idSession;
    public String[] sp = null;
    public String path;

    public Servidor() {
        String home = System.getProperty("user.home");
        Path dwPath = Paths.get(home + "/Downloads");
        if (Files.exists(dwPath)) setPath(home + "/Downloads");
        else {
            try {
                new File(home + "/Downloads").mkdirs();
                setPath(home + "/Downloads");
            }
            catch (SecurityException se) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, "No existe ni se pudo crear el directorio Downloads.");
            }
        }
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

    public void setPath (String path) { this.path = path; }
}
