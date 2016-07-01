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

    public Receiver() {

    }

    public void receiveNewFile (String ip, String file) {
        try {
            Socket socket = new Socket(ip, 3000);
            ((HiloR) new HiloR(socket, file)).start();
        }
        catch (IOException e) {
            Logger.getLogger(HiloR.class.getName()).log(Level.SEVERE,null,e);
        }
        catch (Exception e) {

        }
    }

    @Override
    public void run() {
    }

}
