package main;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * Created by david on 5/20/16.
 */

public class Cliente {
    public Socket client = null;
    public DataOutputStream output = null;
    public DataInputStream input = null;
    public int id = 0;

    public Cliente(int id) throws IOException {
        this.id = id;
        this.client = new Socket("192.168.11.136",2000);
        this.input = new DataInputStream(client.getInputStream());
        this.output = new DataOutputStream(client.getOutputStream());
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
}
