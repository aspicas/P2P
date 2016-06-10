package main;

/**
 * Created by david on 5/19/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 */
import java.io.IOException;

public class Main {
    public static void main (String[] args) throws IOException
    {
        /*SERVIDOR*/
        Servidor serv = new Servidor();
        serv.start();/**/

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
    }
}
