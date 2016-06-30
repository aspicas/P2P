package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by david on 6/10/16.
 */
public class Servidor extends Thread{
    public int port = 2000;
    public ServerSocket server;
    public Socket client;
    public static String address;
    public DataInputStream input;
    public DataOutputStream output;

    public Servidor() {
        try {
            this.server = new ServerSocket(port);
            this.client = server.accept();
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
            findIP();
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public String getLastOctet(String str) {
        StringTokenizer st = new StringTokenizer(str, ".");
        // itera mediante el “objeto st” para obtener más tokens de él
        String token= null;
        while (st.hasMoreElements()) {
            token = st.nextElement().toString();
        }
        return token;
    }

    public String getThreeOctet(String str) {
        StringTokenizer st = new StringTokenizer(str, ".");
        // itera mediante el “objeto st” para obtener más tokens de él
        String token= null;
        String exit = "";
        while (st.hasMoreElements()) {
            token = st.nextElement().toString();
            if (st.hasMoreElements() == false)
            {
                break;
            }
            exit = exit + token + ".";
        }
        return exit;
    }

    public void findIP() throws IOException {
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces(); //Encuentra interfaz
        NetworkInterface e = n.nextElement(); // la separa de la numeracion y la coloca aparte.
        Enumeration<InetAddress> a = e.getInetAddresses(); //Encuentra la MAC
        InetAddress addr = a.nextElement(); // la separa de la numeracion y la coloca aparte.
        addr = a.nextElement(); //Seguido de la MAC esta la IP
        this.address = addr.getHostAddress();//*/
        this.address = getLastOctet(address);
    }

    public void listening(){
        try {
            while (true){
                this.client = server.accept();
                ((Hilo) new Hilo(client)).start();
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @Override
    public void run(){ listening(); }
}