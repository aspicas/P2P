package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;

/**
 * Created by gbsojo on 6/11/16.
 * http://www.java2s.com/Code/Java/Network-Protocol/TransferafileviaSocket.htm
 * http://www.rgagnon.com/javadetails/java-0542.html
 */
public class Receiver extends Thread{
    public Socket socket = null;
    public int idSession;
    public String file;

    public Receiver(String file) {
        this.file = file;
        try {
            this.socket = new Socket("127.0.0.1", 3000);
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
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
    public void run() {
    }

}
