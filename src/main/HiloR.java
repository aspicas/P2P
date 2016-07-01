package main;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by gbsojo on 7/1/16.
 */
public class HiloR extends Thread {
    public Socket socket;
    public DataInputStream input;
    public DataOutputStream output;
    public String file;

    public HiloR (Socket socket, String file)
    {
        this.file = file;
        this.socket = socket;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex){
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void desconectar(){
        try {
            socket.close();
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void requestFile(String file){
        try {
            output.writeUTF(file);
            receiveFile();
        }
        catch (IOException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveFile () throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] mybytearray = new byte[1024];
            InputStream is = socket.getInputStream();
            fos = new FileOutputStream(this.file);
            bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            int current = bytesRead;
            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, bytesRead);
            bos.flush();
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (socket != null) socket.close();
        }
    }

    @Override
    public void run(){
        requestFile(file);
        desconectar();
    }
}
