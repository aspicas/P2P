package main;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * Created by aspicas on 5/20/16.
 */

public class Hilo extends Thread {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private int idSession;

    public Hilo (Socket socket, int id){
        this.socket = socket;
        this.idSession = id;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex){
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void desconectar(){
        try {
            socket.close();
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @Override
    public void run(){
        String accion = "";
        try {
            accion = input.readUTF();
            System.out.println("La accion es: "+accion);
            output.writeUTF("He leido tu accion");
        }
        catch (IOException ex){
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
        desconectar();
    }
}
