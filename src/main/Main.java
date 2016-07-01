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

    public static void Estadistica(JSONParser parser, String home) throws IOException {
        /* Buscar el archivo de estadisticas*/
        try {

            Object obj = parser.parse(new FileReader(home + "/Downloads/p2p.json"));
            JSONObject jsonObject = (JSONObject) obj;

        } catch (FileNotFoundException e) {
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
    }

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
        Cliente client = new Cliente("192.168.43.182");
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
        Scanner scanner = new Scanner(System.in);
        System.out.println(user.getThreeOctet(user.getAddress()) + predecesor);
        Estadistica(parser, home);
        do {
            Cliente cliente = new Cliente(user.getThreeOctet(user.getAddress()) + predecesor);
            /*line = scanner.nextLine().toUpperCase();
            cliente.Send(line);*/
//            System.out.println(line);
            while (command.toUpperCase() != "SALIR") {
                System.out.print("$ ");
                command = scanner.nextLine();
                List<String> items = Arrays.asList(command.split(" "));
                switch (items.get(0).toUpperCase()) {
                    case ("RECURSOS_OFRECIDOS"):
                        System.out.println("Los recursos ofrecidos son: ");
                        File directory = new File(home + "/Downloads");
                        utility.listFilesForFolder(directory);
                        break;
                    case ("BUSCAR_RECURSO"):
                        Receiver receiver = new Receiver();
                        receiver.receiveNewFile(user.getThreeOctet(user.getAddress()) + predecesor,
                                items.get(1));
                        System.out.println("Se ha iniciado la descarga.");
                        break;
                    case ("ESTADO_SOLICITUDES"):
                        break;
                    case ("ESTADO_RESPUESTAS"):
                        break;
                    case ("NUM_DESCARGAS"):
                        Long cant = new Long(-1);
                        try {
                            Object obj = parser.parse(new FileReader(home + "/Downloads/p2p.json"));
                            JSONObject jsonObject = (JSONObject) obj;
                            cant = (Long) jsonObject.get("cantdescargas");
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
                        if ( cant != -1) System.out.println("Hasta la fecha se han descargado " + cant + " archivos.");
                        break;
                    case ("SALIR"):
                        System.exit(0);
                    default:
                        System.out.println("Comando invalido. Intentelo de nuevo.");
                        break;
                }
            }
        } while (!line.equals("EXIT"));/**/
        Cliente cliente = new Cliente(user.getThreeOctet(user.getAddress()) + predecesor);
        cliente.definePredecesor("desconectar " + predecesor);
    }
}