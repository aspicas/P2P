package main;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.*;

/**
 * Created by aspicas on 5/20/16.
 */


/**
 * Clase para el manejo de Hilos
 */
public class Hilo extends Thread {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private int idSession;
    public String[] sp = null;
    public String path;

    /**
     * Constructor de la clase
     * @param socket
     * @param id
     */
    public Hilo (Socket socket, int id){
        String home = System.getProperty("user.home");
        Path dwPath = Paths.get(home + "/Downloads");
        if (Files.exists(dwPath)) setPath(home + "/Downloads");
        else {
            try {
                new File(home + "/Downloads").mkdirs();
                setPath(home + "/Downloads");
            }
            catch (SecurityException se) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, "No existe ni se pudo crear el directorio Downloads.");
            }
        }
        this.socket = socket;
        this.idSession = id;
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex){
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Funcion para la desconexion del socket
     */
    public void desconectar(){
        try {
            socket.close();
        }
        catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    /**
     * Funcion para separar el mensaje recibido en diferentes palabras
     * @param str
     * @return
     */
    public List<String> definirAccion(String str){
        StringTokenizer st = new StringTokenizer(str, " ");
        // itera mediante el “objeto st” para obtener más tokens de él
        String[] token = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreElements()) {
            token[i] = st.nextElement().toString();
            i++;
        }
        return Arrays.asList(token);
    }

    /**
     * Funcion para el listado de archivos del directorio definido para compartir recursos.
     * @param folder
     */
    public void listFilesForFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                try {
                    output.writeUTF(fileEntry.getName());
                }
                catch (Exception e) {

                }
            }
        }
    }

    public void setPath (String path) { this.path = path; }

    /**
     * Funcion donde se identifica el comando recibido y se realizan las acciones pertinentes
     */
    @Override
    public void run(){
        String accion = "";
        List<String> sp = definirAccion(accion);
        try {
            accion = input.readUTF();
            //System.out.println("La accion es: "+accion);
            output.writeUTF("He leido tu accion");
            List<String> comando = definirAccion(accion);
            System.out.println(comando.get(0) + comando.get(1));

            for (String i: sp) {
                System.out.println("hilo: "+i);
            }
        }
        catch (IOException ex){
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE,null,ex);
        }
        desconectar();
    }
}
