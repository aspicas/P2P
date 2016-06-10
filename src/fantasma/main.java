package fantasma;

/**
 * Created by david on 5/19/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 */

import main.Servidor;

import java.io.IOException;

class Main {
    public static void main (String[] args) throws IOException
    {
        /*SERVIDOR*/
        Servidor serv = new Servidor();
        serv.start();
    }
}