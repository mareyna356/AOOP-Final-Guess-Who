/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Marco
 */
public class Server {

    ServerSocket server;
    Socket cliente;
    BufferedReader r;
    PrintWriter w;
    
    public Server(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            System.err.println("IOException: No se pudo crear el servidor.");
        }
    }
    
    public void serve() {
        try {
            while (true) {
                System.out.println("Esperando cliente.");
                cliente = server.accept();
                System.out.println("Cliente aceptado.");
                r = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                w = new PrintWriter(cliente.getOutputStream(), true);
                while (!(r.ready())) {}
                String clienteInput = r.readLine();
                if (clienteInput.equals("PvE")) {
                    System.out.println("Iniciando juego contra servidor.");
                    w.println(" ");
                    singlePlayer();
                } else {
                    System.out.println("Enviando clientes a juego multijugador.");
                    Socket oponente;
                    BufferedReader rOponente;
                    PrintWriter wOponente;
                    do {
                        System.out.println("Esperando oponente.");
                        oponente = server.accept();
                        System.out.println("Oponente recibido.");
                        rOponente = new BufferedReader(new InputStreamReader(oponente.getInputStream()));
                        wOponente = new PrintWriter(oponente.getOutputStream(), true);
                        while (!(rOponente.ready())) {}
                        clienteInput = rOponente.readLine();
                        if (clienteInput.equals("PvE")) {
                            wOponente.println("Preparación PvP en progreso.");
                            rOponente.close();
                            wOponente.close();
                            oponente.close();
                        }
                    } while (!(clienteInput.equals("PvP")));
                    System.out.println("Fuera de loop.");
                    Handler handler = new Handler(cliente, r, w, rOponente, wOponente);
                    Handler handlerOponente = new Handler(oponente, rOponente, wOponente, r, w);
                    w.println("1\nTRUE");
                    wOponente.println("2\nFALSE");
                    handler.start();
                    handlerOponente.start();
                    System.out.println("Juego empezado.");
                }
            }
        } catch (Exception ex) {
            System.err.println("No se pudo contactar al cliente.");
        }
    }
    
    public void singlePlayer() {
        try {
            Random random = new Random();
            int miPersonaje = random.nextInt(24);
            String[] nombres = {"Alex", "Alfred", "Anita", "Anne", "Bernard", "Bill", "Charles", "Claire", "David", "Eric", "Frans", "George", "Herman", "Joe", "Maria", "Max", "Paul", "Peter", "Philip", "Richard", "Robert", "Sam", "Susan", "Tom"};
            System.out.println("Mi personaje es " + nombres[miPersonaje]);
            String clienteInput;
            while (true) {
                while (!(r.ready())) {}
                clienteInput = r.readLine();
                if (clienteInput.equals("Exit")) break;
                w.println(clienteInput);
                switch (clienteInput) {
                    case "¿Tiene pelo negro?":
                        if (miPersonaje == 0 || miPersonaje == 3 || miPersonaje == 4 || miPersonaje == 15 || miPersonaje == 18 || miPersonaje == 23) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene pelo anaranjado?":
                        if (miPersonaje == 1 || miPersonaje == 5 || miPersonaje == 7 || miPersonaje == 10 || miPersonaje == 12) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene pelo rubio?":
                        if (miPersonaje == 2 || miPersonaje == 6 || miPersonaje == 8 || miPersonaje == 9 || miPersonaje == 13) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene pelo blanco?":
                        if (miPersonaje == 11 || miPersonaje == 16 || miPersonaje == 17 || miPersonaje == 21 || miPersonaje == 22) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene pelo café?":
                        if (miPersonaje == 14 || miPersonaje == 19 || miPersonaje == 20) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Está calvo?":
                        if (miPersonaje == 5 || miPersonaje == 12 || miPersonaje == 19 || miPersonaje == 21 || miPersonaje == 23) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene lentes?":
                        if (miPersonaje == 7 || miPersonaje == 13 || miPersonaje == 16 || miPersonaje == 21 || miPersonaje == 23) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene bigote?":
                        if (miPersonaje == 0 || miPersonaje == 1 || miPersonaje == 6 || miPersonaje == 15 || miPersonaje == 19) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene barba?":
                        if (miPersonaje == 5 || miPersonaje == 8 || miPersonaje == 18 || miPersonaje == 19) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene gorra?":
                        if (miPersonaje == 4 || miPersonaje == 7 || miPersonaje == 9 || miPersonaje == 11 || miPersonaje == 14) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene boca grande?":
                        if (miPersonaje == 0 || miPersonaje == 6 || miPersonaje == 9 || miPersonaje == 15 || miPersonaje == 17 || miPersonaje == 22) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene nariz grande?":
                        if (miPersonaje == 3 || miPersonaje == 4 || miPersonaje == 12 || miPersonaje == 15 || miPersonaje == 17 || miPersonaje == 20) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene mejillas rojas?":
                        if (miPersonaje == 2 || miPersonaje == 5 || miPersonaje == 18 || miPersonaje == 20 || miPersonaje == 22) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene aretes?":
                        if (miPersonaje == 3 || miPersonaje == 14) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene pelo chino?":
                        if (miPersonaje == 0 || miPersonaje == 3 || miPersonaje == 10 || miPersonaje == 12 || miPersonaje == 13 || miPersonaje == 15 || miPersonaje == 18) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene pelo largo?":
                        if (miPersonaje == 1 || miPersonaje == 2 || miPersonaje == 14 || miPersonaje == 22) w.println("Sí");
                        else w.println("No");
                        break;
                    case "¿Tiene ojos azules?":
                        if (miPersonaje == 1 || miPersonaje == 2 || miPersonaje == 17 || miPersonaje == 20 || miPersonaje == 23) w.println("Sí");
                        else w.println("No");
                        break;
                    default:
                        break;
                }
            }
            while (!(r.ready())) {}
            clienteInput = r.readLine();
            if (Integer.parseInt(clienteInput) == miPersonaje) w.println("Adivinaste!!!!\nGanó");
            else w.println("Te equivocaste, mi personaje es " + nombres[miPersonaje] + "\nPerdió");
            r.close();
            w.close();
            cliente.close();
        } catch (Exception ex) {
            System.err.println("Exception occurred.");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server(5555);
        server.serve();
    }
    
}

class Handler extends Thread {
    
    private Socket cliente;
    private BufferedReader r;
    private PrintWriter w;
    private BufferedReader rOponente;
    private PrintWriter wOponente;
    
    public Handler(Socket cliente, BufferedReader r, PrintWriter w, BufferedReader rOponente, PrintWriter wOponente) {
        this.cliente = cliente;
        this.r = r;
        this.w = w;
        this.rOponente = rOponente;
        this.wOponente = wOponente;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                while (!(r.ready())) {}
                String input = r.readLine();
                wOponente.println(input);
                if (input.equals("Adivina: ")) {
                    wOponente.println(r.readLine());
                    wOponente.println(r.readLine());
                    break;
                } else if (input.equals("Regresa: ")) {
                    String string = r.readLine();
                    wOponente.println(string);
                    w.close();
                    r.close();
                    cliente.close();
                    wOponente.close();
                    rOponente.close();
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Handler exception occurred.");
        }
        System.out.println("Se cerró Handler.");
    }
}