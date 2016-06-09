package main;

/**
 * Created by david on 5/19/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 */
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Main {
    public static void main (String[] args) throws IOException
    {
        /*SERVIDOR*/
        Servidor serv = new Servidor();
        serv.start();

        /*CLIENTE*/
        /*Cliente client = new Cliente();
        client.Send("Hola");/**/

        Usuario user = new Usuario();
        user.findIP();
        String address = user.getAddress();
        String host = user.getLastOctet(address);
        String net = user.getThreeOctet(address);
        for (int ip = Integer.parseInt(host); ip < 254; ip++)
        {
            System.out.println(ip);
            ((Cliente) new Cliente(net+ip)).start();
        }/**/


        /* CLIENTE
        String line;
        Scanner sc = new Scanner(System.in);
        int i = 0;
        while (true){
            Cliente client = new Cliente("192.168.11.136");
            line = sc.next();
            client.Send(line);
            i++;
            if (line.equals("EXIT"))
            {
                break;
            }
        }*/

        /*
        Usuario user = new Usuario();

        user.findIP();

        System.out.println(user.getAddress());*/
    }
}
