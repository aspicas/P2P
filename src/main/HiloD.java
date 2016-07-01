package main;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by gbsojo on 7/1/16.
 */
public class HiloD extends Thread {

    public Socket socket;
    public DataInputStream input;
    public DataOutputStream output;
    public String file;
    public String home;

    public HiloD (Socket socket) {
        this.home = System.getProperty("user.home");
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

    public void sendFile() throws IOException {
        FileInputStream fis;
        BufferedInputStream bis = null;
        OutputStream os = new OutputStream() {
            @Override
            public void write(int i) throws IOException {

            }
        };

        try {
            File myFile = new File(home+file);
            byte[] mybytearray = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            os = socket.getOutputStream();
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
        }
        finally {
            if (bis != null) bis.close();
            if (os != null) os.close();
        }
    }

    @Override
    public void run(){
        try {
            this.file = input.readUTF();
            sendFile();
        }
        catch (Exception e) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,e);
        }
        desconectar();
    }
}
