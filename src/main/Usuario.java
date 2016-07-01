package main;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.StringTokenizer;

/**
 * Created by david on 5/18/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 * http://stackoverflow.com/questions/19476872/java-get-local-ip
 */

public class Usuario {

    public String address;
    private String downloadPath;
    private String uploadPath;

    public Usuario() throws IOException {
        address = "";
    }

    /**
     * Funcion para obtener la direccion IP del nodo
     * @throws IOException
     */
    public void findIP() throws IOException {
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces(); //Encuentra interfaz

        /**/
        NetworkInterface e = n.nextElement(); // la separa de la numeracion y la coloca aparte.
        Enumeration<InetAddress> a = e.getInetAddresses(); //Encuentra la MAC
        InetAddress addr = a.nextElement(); // la separa de la numeracion y la coloca aparte.
        if (a.hasMoreElements()) addr = a.nextElement(); //Seguido de la MAC esta la IP
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

    /**
     * Funcion para obtener el ultimo octeto de la direccion IP que recibe
     * @param str direccion IP de tipo String
     * @return
     */
    public String getLastOctet(String str) {
        StringTokenizer st = new StringTokenizer(str, ".");
        // itera mediante el “objeto st” para obtener más tokens de él
        String token = null;
        while (st.hasMoreElements()) {
            token = st.nextElement().toString();
        }
        return token;
    }

    /**
     * Funcion para obtener los primeros 3 octetos de la direccion IP
     * @param str direccion IP de tipo String
     * @return
     */
    public String getThreeOctet(String str) {
        StringTokenizer st = new StringTokenizer(str, ".");
        // itera mediante el “objeto st” para obtener más tokens de él
        String token = null;
        String exit = "";
        while (st.hasMoreElements()) {
            token = st.nextElement().toString();
            if (st.hasMoreElements() == false) {
                break;
            }
            exit = exit + token + ".";
        }
        return exit;
    }
}