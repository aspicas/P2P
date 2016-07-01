package main;

/**
 * Created by david on 5/19/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static String predecesor = "cero";

    public static void main (String[] args) throws IOException
    {
        JSONParser parser = new JSONParser();
        String home = System.getProperty("user.home");
        //Inicio del Servidor
        /*SERVIDOR*//*
        Servidor serv = new Servidor();
        serv.start();/**/

        /* Buscar el archivo de estadisticas*/
        try {

            Object obj = parser.parse(new FileReader(home + "/Downloads"));
            JSONObject jsonObject = (JSONObject) obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JSONObject obj = new JSONObject();
            obj.put("cantdescargas", new Integer (0));
            JSONArray list = new JSONArray();
            obj.put("descargas", list);
            FileWriter file = new FileWriter(home + "/Downloads");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Inicio del Cliente
        /* CLIENTE*/
        Cliente client = new Cliente("192.168.1.103");
        Usuario user = new Usuario();
        user.findIP();
        client.definePredecesor("PREDECESOR "+user.getLastOctet(user.getAddress()));
        System.out.println("Termino el cliente");
        System.out.println(predecesor);
        /**/


        /*SENDER*/
        Sender sender = new Sender();
        sender.start();


        /* CLIENTE*/
        /*String line = "exit";
        Scanner sc = new Scanner(System.in);
        do {
            line = sc.nextLine().toUpperCase();
            client.Send(line);
            System.out.println(line);
        } while (!line.equals("EXIT"));/**/
    }
}