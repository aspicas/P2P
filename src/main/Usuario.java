package main;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by david on 5/18/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 * http://stackoverflow.com/questions/19476872/java-get-local-ip
 */

public class Usuario {

    public String address;

    public Usuario() throws IOException {
        address = "";
    }

    public void findIP() throws IOException {
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces(); //Encuentra interfaz

        /**/NetworkInterface e = n.nextElement(); // la separa de la numeracion y la coloca aparte.
        Enumeration<InetAddress> a = e.getInetAddresses(); //Encuentra la MAC
        InetAddress addr = a.nextElement(); // la separa de la numeracion y la coloca aparte.
        addr = a.nextElement(); //Seguido de la MAC esta la IP
        address = addr.getHostAddress();//*/

        /*
        for (; n.hasMoreElements();)
        {
            NetworkInterface e = n.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements();)
            {
                InetAddress addr = a.nextElement();
                System.out.println("  " + addr.getHostAddress());
            }
        }//*/
    }

    public String getAddress() {
        return address;
    }
}
