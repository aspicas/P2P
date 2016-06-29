package main;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;

/**
 * Created by gbsojo on 6/11/16.
 * http://www.java2s.com/Code/Java/Network-Protocol/TransferafileviaSocket.htm
 */
public class Sender extends Thread{
    public int port = 3000;
    private String path;
    public ServerSocket server = null;
    public Socket client = null;
    public int idSession;
    public DataOutputStream output = null;
    public DataInputStream input = null;
    public String file;

    /**
     * Constructor de la clase
     * @param file
     */

    public Sender(String file) {
        try {
            this.server = new ServerSocket(port);
            this.idSession = 0;
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
            this.file = file;
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Funcion de escucha del Socket
     */
    public void Listening(){
        try {
            while (true){
                this.client = server.accept();
                ((Hilo) new Hilo(client, idSession)).start();
                this.idSession++;
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Funcion para el envio de un recurso a traves del OutputStream del Socket
     * @throws IOException
     */

    public void sendFile() throws IOException {
        FileInputStream fis;
        BufferedInputStream bis = null;
        OutputStream os = new OutputStream() {
            @Override
            public void write(int i) throws IOException {

            }
        };

        try {
            File myFile = new File(this.file);
            byte[] mybytearray = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            os = client.getOutputStream();
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
        }
        finally {
            if (bis != null) bis.close();
            if (os != null) os.close();
        }
    }

    /**
     * Funcion para retornar el tamano del recurso, ya que el tamano es necesario para la creacion del ByteArray
     * en que se guardara la data transmitida.
     * @return
     */

    public int sendFileSize () {
        File myFile = new File (this.file);
        byte [] mybytearray  = new byte [(int)myFile.length()];
        return mybytearray.length;
    }

    /**
     * Funcion para mantener el Socket escuchando por una nueva peticion.
     */
    @Override
    public void run(){
        Listening();
    }

}
