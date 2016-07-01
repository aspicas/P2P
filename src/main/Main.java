package main;

/**
 * Created by david on 5/19/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 */
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static String predecesor = "cero";

    public static void main (String[] args) throws IOException
    {
        //Inicio del Servidor
        /*SERVIDOR*/
        Servidor serv = new Servidor();
        serv.start();/**/

        //Inicio del Cliente
        /* CLIENTE*/
        Cliente client = new Cliente("192.168.1.100");
        Usuario user = new Usuario();
        user.findIP();
        client.definePredecesor("PREDECESOR "+user.getLastOctet(user.getAddress()));
        client.desconectar();
        System.out.println("predecesor: " + predecesor);
        /**/


        /*SENDER*/
        Sender sender = new Sender();
        sender.start();


        /* CLIENTE*/
        String line = "a";
        Scanner sc = new Scanner(System.in);
        do {
            Cliente cliente = new Cliente(user.getThreeOctet(user.getAddress()) + predecesor);
            line = sc.nextLine().toUpperCase();
            cliente.Send(line);
            System.out.println(line);
        } while (!line.equals("EXIT"));/**/
        Cliente cliente = new Cliente(user.getThreeOctet(user.getAddress()) + predecesor);
        cliente.definePredecesor("desconectar " + predecesor);/**/
    }
}