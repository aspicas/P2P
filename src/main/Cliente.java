package main;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * Created by david on 5/20/16.
 */

public class Cliente extends Thread{
    public Socket client = null;
    public DataOutputStream output = null;
    public DataInputStream input = null;

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
            System.out.println("La respuesta es: " + respuesta);
            output.close();
            input.close();
            client.close();
        }
        catch (IOException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SendHello(){
        String respuesta;
        try {
            output.writeUTF("Hola");
            respuesta = input.readUTF();
            System.out.println("La respuesta es: " + respuesta);
            output.close();
            input.close();
            client.close();
        }
        catch (Exception ex){
            //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se envio el hello");
        }
    }

    @Override
    public void run(){
        try {
            SendHello();
        }
        catch (Exception ex){
            System.out.println("Fallo hilo");
        }

    }
}
