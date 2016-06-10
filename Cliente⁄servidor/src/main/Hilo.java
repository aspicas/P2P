package main;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.logging.*;

/**
 * Created by aspicas on 5/20/16.
 */

public class Hilo extends Thread {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private int idSession;
    public String[] sp = null;

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

    public String[] definirAccion(String str){
        StringTokenizer st = new StringTokenizer(str, " ");
        // itera mediante el “objeto st” para obtener más tokens de él
        String[] token = new String[2];
        int i = 0;
        while (st.hasMoreElements()) {
            token[i] = st.nextElement().toString();
            i++;
        }
        return token;
    }

    @Override
    public void run(){
        String accion = "";
        this.sp = new String[2];
        try {
            accion = input.readUTF();
            //System.out.println("La accion es: "+accion);
            output.writeUTF("He leido tu accion");
            String[] comando = definirAccion(accion);
            System.out.println(comando[0] + comando [1]);
            if (comando[0] == "predecesor"){
                this.sp[0] = comando[1];
                this.sp[1] = comando[2];
            }
            for (String i: sp) {
                System.out.println("hilo: "+i);
            }
        }
        catch (IOException ex){
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
        desconectar();
    }
}
