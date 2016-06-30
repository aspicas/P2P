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
        /*SERVIDOR*//*
        Servidor serv = new Servidor();
        serv.start();/**/

        //Inicio del Cliente
        /* CLIENTE*/
        Cliente client = new Cliente("192.168.1.103");
        Usuario user = new Usuario();
        user.findIP();
        client.definePredecesor("PREDECESOR "+user.getLastOctet(user.getAddress()));
        System.out.println("Termino el cliente");
        System.out.println(predecesor);
        /**/

        /* CLIENTE*/
        /*String line = "exit";
        Scanner sc = new Scanner(System.in);
        do {
            line = sc.nextLine().toUpperCase();
            client.Send(line);
            System.out.println(line);
        } while (!line.equals("EXIT"));/**/
    }
}