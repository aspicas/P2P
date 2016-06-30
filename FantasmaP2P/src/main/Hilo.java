package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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

    public Hilo (Socket socket){
        this.socket = socket;
        try {
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
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
            String respuesta = input.readUTF();
            String[] comando = definerAction(respuesta);
            //Primero en conectarse
            if (comando[0].equals("PREDECESOR") && Main.ultConect.equals("cero")){
                output.writeUTF("PREDECESOR " + Servidor.address);
                Main.ultConect = comando[1];
                output.writeUTF("listo");
            }
            //Para los otros nodos
            else if (comando[0].equals("PREDECESOR") && !Main.ultConect.equals(comando[1])) {
                output.writeUTF("PREDECESOR " + Main.ultConect);
                Main.ultConect = comando[1];
                output.writeUTF("listo");
            }
        }
        catch (IOException ex){
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}