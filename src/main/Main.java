package main;

/**
 * Created by david on 5/19/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 */
import java.io.IOException;
import java.net.*;

public class Main {
    public static void main (String[] args) throws IOException
    {
        InetAddress address = InetAddress.getLocalHost();
        System.out.print(address);
    }
}
