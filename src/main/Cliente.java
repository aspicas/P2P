package main;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.logging.*;

/**
 * Created by david on 5/20/16.
 */

public class Cliente {
    public Socket client = null;
    public DataOutputStream output = null;
    public DataInputStream input = null;
    public String[] sp = null;

    public Cliente() throws IOException {
        this.client = new Socket("localhost",2000);
        this.input = new DataInputStream(client.getInputStream());
        this.output = new DataOutputStream(client.getOutputStream());
    }

    public Cliente(String ip) throws IOException {
        try {
            this.client = new Socket(ip,2000);
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
        }
        catch (Exception ex){
            System.out.println("No se encontro el servidor");
        }

    }

    public void Send(String msj){
        String respuesta;
        try {
            output.writeUTF(msj);
            respuesta = input.readUTF();
            //System.out.println("La respuesta es: " + respuesta);
            output.close();
            input.close();
            client.close();
        }
        catch (IOException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
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

    public void desconectar() throws Exception{
        this.input.close();
        this.output.close();
        this.client.close();
    }

    public void definePredecesor(String msj){
        String respuesta;
        try{

            output.writeUTF(msj); //El msj sera el predecesor mas la ip ej: predecesor 192.168.11.host
            respuesta = input.readUTF();
            System.out.println(respuesta);
            if (sp == null){
                this.sp = new String[2];
                this.sp[0] = respuesta; //Sucesor
                this.sp[1] = respuesta; //Predecesor
            }
            for (String i:sp) {
                System.out.println("cliente : "+i);
            }
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
}
