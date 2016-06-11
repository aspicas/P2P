package main;

/**
 * Created by david on 5/19/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 */
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException
    {
        /*SERVIDOR*/
        Servidor serv = new Servidor();
        serv.start();/**/

        /* CLIENTE*/
        Cliente client = new Cliente("192.168.11.1");
        Usuario user = new Usuario();
        user.findIP();
        client.definePredecesor("predecesor "+user.getLastOctet(user.getAddress()));
        /**/

        /* CLIENTE*/
        /*String line;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            Cliente client = new Cliente("192.168.11.1");
            line = sc.nextLine();
            client.Send(line);
            if (line.equals("EXIT"))
            {
                break;
            }
        }/**/
    }
}
