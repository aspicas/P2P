package main;

/**
 * Created by david on 5/19/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 */
import java.io.IOException;
import main.Usuario;

public class Main {
    public static void main (String[] args) throws IOException
    {
        Usuario user = new Usuario();

        user.findIP();

        System.out.println(user.getAddress());
    }
}
