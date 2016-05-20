package main;

import java.io.IOException;
import java.net.*;

/**
 * Created by david on 5/18/16.
 */

public class Usuario {

    public int puerto = 50000;
    public Socket cs; //Cliente socket
    public ServerSocket ss; //Server socket

    public Usuario() throws IOException {
        ss = new ServerSocket(puerto); //Crea el socket para el puerto 50000
        cs = new Socket(); //Socket para el cliente
    }
}
