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
    public String address;
    public DataInputStream input;
    public DataOutputStream output;
    public String[] sp;
    public String predecesor;

    public Servidor() {
        try {
            this.server = new ServerSocket(port);
            this.address = null;
            this.client = server.accept();
            this.input = new DataInputStream(client.getInputStream());
            this.output = new DataOutputStream(client.getOutputStream());
            this.sp = null;
            findIP();
            this.predecesor = getLastOctet(address);
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
        address = addr.getHostAddress();//*/
    }

    public void listening(){
        try {
            while (true){
                findIP();
                this.client = server.accept();
                ((Hilo) new Hilo(client, getLastOctet(address))).start();
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public String[] definerAction(String str){
        StringTokenizer st = new StringTokenizer(str, " ");
        // itera mediante el “objeto st” para obtener más tokens de él
        String[] token = new String[2];
        int i = 0;
        while (st.hasMoreElements()) {
            token[i] = st.nextElement().toString();
            i++;
        }
        return token;
    }

    @Override
    public void run(){
        listening();
        /*try {
            String respuesta = input.readUTF();
            String[] comando = definerAction(respuesta);
            if (comando[0].equals("predecesor") && sp == null) {
                sp = new String[2];
                sp[0] = comando[1]; //Predecesor
                sp[1] = comando[1]; //Sucesor
                output.writeUTF("predecesor " + predecesor);
            }
            else if(comando[0].equals("predecesor")) {
                Socket cambio = new Socket(getThreeOctet(address)+sp[1],2000);
                DataOutputStream salida = new DataOutputStream(cambio.getOutputStream());
                salida.writeUTF("predecesor " + predecesor);
                cambio.close();
                output.writeUTF("predecesor " + predecesor);
                output.writeUTF("sucesor " + sp[1]);
                sp[1] = comando[1];
                client.close();
            }

            output.writeUTF("He recibido tu mensaje");
        }
        catch (IOException ex){
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }*/
    }
}
