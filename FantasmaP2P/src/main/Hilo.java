package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by david on 6/10/16.
 */
public class Hilo extends Thread{
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    public String[] sp;
    public String predecesor;

    public Hilo (Socket socket, String predecesor){
        this.socket = socket;
        try {
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
            this.predecesor = predecesor;
            this.sp = null;
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

    public String[] definerAction(String str){
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
        try {
            boolean a = true;
            while (a) {
                String respuesta = input.readUTF();
                String[] comando = definerAction(respuesta);
                if (comando[0].equals("predecesor") && sp == null) {
                    sp = new String[2];
                    sp[0] = comando[1]; //Predecesor
                    sp[1] = comando[1]; //Sucesor
                    output.writeUTF("predecesor " + predecesor);
                }
                System.out.println(respuesta);
                output.writeUTF("He recibido tu mensaje");
                if (respuesta == "EXIT")
                {
                    a = false;
                }
            }
            desconectar();
        }
        catch (IOException ex){
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}
