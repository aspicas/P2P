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

    /**
     * Constructor de la clase
     * @param ip
     * @throws IOException
     */
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
    /**
     * Funcion para el envio de mensajes
     * @param msj
     */
    public void Send(String msj){
        try {
            output.writeUTF(msj);
            String respuesta = input.readUTF();
            System.out.println("Respuesta del server: " + respuesta);
            desconectar();
        }
        catch (IOException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Funcion para separar n String en un arreglo de palabras.
     * @param str
     * @return
     */
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

    /**
     * Funcion para cerrar la conexion del Socket
     */
    public void desconectar(){
        try {
            client.close();
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    /**
     * Funcion para definir el predecesor del nodo en el anillo
     * @param msj
     */
    public void definePredecesor(String msj){
        try{
            output.writeUTF(msj); //El msj sera el predecesor mas la ip ej: predecesor 192.168.11.host
            String respuesta = input.readUTF();
            String[] comando = definerAction(respuesta);
            if (comando[0].equals("PREDECESOR")){
                Main.predecesor = comando[1];
                //System.out.println("predecesor");
            }
            else if (comando[0].equals("desconectar")) {
                System.out.println("salida");
                desconectar();
            }
        }
        catch (Exception ex){
            System.out.println(ex+" definirPredecesor");
        }
    }
}
