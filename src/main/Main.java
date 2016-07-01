package main;

/**
 * Created by david on 5/19/16.
 * https://www.programarya.com/Cursos/Java-Avanzado/Sockets
 */
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static String predecesor = "cero";
    public static String command = "";
    public static Utils utility = new Utils();

    public static void main (String[] args) throws IOException
    {
        JSONParser parser = new JSONParser();
        String home = System.getProperty("user.home");
        //Inicio del Servidor
        /*SERVIDOR*/
        Servidor serv = new Servidor();
        serv.start();/**/


        //Inicio del Cliente
        /* CLIENTE*/
        Cliente client = new Cliente("192.168.1.100");
        Usuario user = new Usuario();
        user.findIP();
        System.out.println(user.address);
        client.definePredecesor("PREDECESOR "+user.getLastOctet(user.getAddress()));
        client.desconectar();
        System.out.println("predecesor: " + predecesor);

        /**/


        /*SENDER*/
        Sender sender = new Sender();
        sender.start();


        /* CLIENTE*/
        String line = "a";
        Scanner sc = new Scanner(System.in);
        System.out.println(user.getThreeOctet(user.getAddress()) + predecesor);
        do {
            Cliente cliente = new Cliente(user.getThreeOctet(user.getAddress()) + predecesor);
            line = sc.nextLine().toUpperCase();
            cliente.Send(line);
            System.out.println(line);
        } while (!line.equals("EXIT"));/**/
        Cliente cliente = new Cliente(user.getThreeOctet(user.getAddress()) + predecesor);
        cliente.definePredecesor("desconectar " + predecesor);/**/

        /* Buscar el archivo de estadisticas*/
        try {

            Object obj = parser.parse(new FileReader(home + "/Downloads/p2p.json"));
            JSONObject jsonObject = (JSONObject) obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JSONObject obj = new JSONObject();
            obj.put("cantdescargas", new Integer (0));
            JSONArray list = new JSONArray();
            obj.put("descargas", list);
            FileWriter file = new FileWriter(home + "/Downloads/p2p.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        while (command.toUpperCase() != "SALIR") {
            System.out.println("Ingrese el comando");
            command = scanner.next();
            List<String> items = Arrays.asList(command.split("\\s*"));
            switch (items.get(0).toUpperCase()) {
                case ("RECURSOS_OFRECIDOS"):
                    System.out.println("Los recursos ofrecidos son: ");
                    File directory = new File(home + "/Downloads");
                    utility.listFilesForFolder(directory);
                    break;
                case ("BUSCAR_RECURSO"):
                    break;
                case ("ESTADO_SOLICITUDES"):
                    break;
                case ("ESTADO_RESPUESTAS"):
                    break;
                case ("NUM_DESCARGAS"):
                    int cant = -1;
                    try {
                        Object obj = parser.parse(new FileReader(home + "/Downloads/p2p.json"));
                        JSONObject jsonObject = (JSONObject) obj;
                        cant = (int) jsonObject.get("cantdescargas");
                    } catch (FileNotFoundException e) {
                        System.out.println("No existe el archivo de control.");
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("No se puede acceder al archivo de control.");
                        e.printStackTrace();
                    } catch (ParseException e) {
                        System.out.println("Ha ocurrido un error");
                        e.printStackTrace();
                    }
                    if ( cant != -1) System.out.println("Hasta la fecha se han descargado " + cant + "archivos.");
                    break;
            }
        }
    }
}