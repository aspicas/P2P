package main;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by gbsojo on 7/1/16.
 */
public class HiloR extends Thread {
    public Socket socket;
    public DataInputStream input;
    public DataOutputStream output;
    public String file;
    public String home;

    public HiloR (Socket socket, String file)
    {
        this.home = System.getProperty("user.home");
        System.out.println("Se creo el hilo de recepcion");
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
            System.out.println("Se solicita el archivo: " + file);
            output.writeUTF(file);
            receiveFile();
            addDownload();
        }
        catch (IOException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        }


    public void receiveFile () throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        System.out.println("Se comienza a recibir el archivo");
        try {
            byte[] mybytearray = new byte[1024];
            InputStream is = socket.getInputStream();
            fos = new FileOutputStream(this.home + "/Downloads/" + this.file);
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

    public void addDownload () {
        JSONParser parser = new JSONParser();
        System.out.println("Se actualiza la estadistica de descargas.");
        try {
            Object obj = parser.parse(new FileReader(home + "/Downloads/p2p.json"));
            JSONObject jsonObject = (JSONObject) obj;
            int cant = (int) jsonObject.get("cantdescargas");
            cant ++;
            jsonObject.put("cantdescargas", cant);
            FileWriter file = new FileWriter(home + "/Downloads/p2p.json");
            file.write(jsonObject.toJSONString());
            System.out.println("Se actualizo el archivo");
            file.flush();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        System.out.println("Comienza a correr el hilo");
        requestFile(file);
        desconectar();
    }
}
