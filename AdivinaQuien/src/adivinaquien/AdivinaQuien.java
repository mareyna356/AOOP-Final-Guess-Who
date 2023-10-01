/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adivinaquien;

import java.io.*;
import java.net.*;

/**
 *
 * @author comp08j
 */
public class AdivinaQuien {

    private Menu menu;
    private Jugador jugador;
    private TableroLocal tableroLocal;
    private Tablero tablero;
    
    private Socket cliente;
    private BufferedReader r;
    private PrintWriter w;
    
    private int turno;
    private int turnoActual;
    private boolean pregunta;
    
    private ReadingThread readingThread;
    
    public AdivinaQuien() {
        turno = 0;
        pregunta = false;
        turnoActual = 0;
    }
    
    public AdivinaQuien(Jugador jugador) {
        this.jugador = jugador;
        turno = 0;
        pregunta = false;
        turnoActual = 0;
    }
    
    public void connectLocal(javax.swing.JLabel jLabel) {
        try {
            cliente = new Socket("localhost", 5555);
            r = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            w = new PrintWriter(cliente.getOutputStream(), true);
            w.println("PvE");
            while (!(r.ready())) {}
            jLabel.setText(r.readLine());
        } catch (Exception ex) {
            System.err.println("No se pudo conectar al servidor.");
            jLabel.setText("No se pudo conectar al servidor.");
        }
    }
    
    public void preguntarLocal(String pregunta, javax.swing.JTextArea jTextArea, javax.swing.JButton jButton, javax.swing.JLabel jLabel) {
        try {
            jugador.increaseNumPregs();
            jLabel.setText("Haz hecho " + jugador.getNumPregs() + " preguntas");
            w.println(pregunta);
            while (!(r.ready())) {}
            String input = r.readLine();
            jTextArea.setText(input + "\n" + r.readLine());
            if (jugador.getNumPregs() == 6) jButton.setEnabled(false);
        } catch (Exception ex) {
            System.err.println("preguntarLocal exception occurred.");
        }
    }
    
    public void adivinaLocal(int i) {
        try {
            w.println("Exit");
            w.println(i);
            while (!(r.ready())) {}
            String input = r.readLine();
            jugador.setGanador(r.readLine());
            Notice notice = new Notice(this, input);
            notice.setVisible(true);
            guardaDatos();
            w.close();
            r.close();
            cliente.close();
        } catch (Exception ex) {
            System.err.println("adivinaLocal exception occurred.");
        }
    }
    
    public void adivinaOnline(int i) {
        try {
            w.println("Adivina: \n" + i + "\n" + jugador.getPersonaje());
        } catch (Exception ex) {
            System.err.println("adivinaOnline exception occurred.");
        }
    }
    
    public void guardaDatos() {
        try {
            File file = new File("Resultados.txt");
            FileOutputStream flujo = new FileOutputStream(file, true);
            flujo.write((jugador.getNombre() + "\nPreguntas hechas: " + jugador.getNumPregs() + " - " + jugador.getGanador() + "\n\n").getBytes());
            flujo.close();
        } catch (Exception ex) {
            System.err.println("guardaDatos exception occurred.");
        }
    }
    
    public void datosServer(javax.swing.JLabel jLabel) {
        DatosDeServer datos = new DatosDeServer(this, jLabel);
        datos.setVisible(true);
    }
    
    public void connectMultiplayer(String host, int puerto, javax.swing.JLabel jLabel) {
        try {
            cliente = new Socket(host, puerto);
            menu.setEnabled(true);
            menu.setVisible(false);
            r = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            w = new PrintWriter(cliente.getOutputStream(), true);
            w.println("PvP");
            while (!(r.ready())) {}
            String turno = r.readLine();
            String pregunta = r.readLine();
            this.turno = Integer.parseInt(turno);
            this.pregunta = Boolean.parseBoolean(pregunta);
            tablero = new Tablero(this);
            tablero.setVisible(true);
            if (this.turno == 2) {
                tablero.getBotonAdivina().setEnabled(false);
                tablero.getBotonPreguntar().setEnabled(false);
                tablero.getBotonPreguntar().setText("Responder");
            }
            readingThread = new ReadingThread(this);
            readingThread.start();
        } catch (Exception ex) {
            System.err.println("No se pudo conectar a servidor.");
            jLabel.setText("No se pudo conectar a servidor.");
            menu.setEnabled(true);
        }
    }
    
    public void enviar(String mensaje, javax.swing.JButton botonPreguntar, javax.swing.JButton botonAdivina) {
        if (!pregunta) {
            w.println("R: " + mensaje);
            pregunta = true;
            botonPreguntar.setText("Preguntar");
            botonAdivina.setEnabled(true);
        } else {
            w.println("P: " + mensaje);
            pregunta = false;
            botonPreguntar.setText("Responder");
            botonPreguntar.setEnabled(false);
            botonAdivina.setEnabled(false);
            jugador.increaseNumPregs();
        }
    }
    
    public void terminarJugador1() {
        try {
            String input = r.readLine();
            if (input.endsWith(".Ganó")) {
                jugador.setGanador("Ganó");
                input = input.substring(0, 29); //Para eliminar el "Ganó."
            } else {
                jugador.setGanador("Perdió");
                input = input.substring(0, input.length()-6); //Para eliminar el "Perdió."
            }
            Notice notice = new Notice(this, input);
            notice.setVisible(true);
            guardaDatos();
        } catch (Exception ex) {
            System.err.println("terminarJugador1 exception caught.");
        }
    }
    
    public void terminarJugador2() {
        try {
            int suAtine = Integer.parseInt(r.readLine());
            int suPersonaje = Integer.parseInt(r.readLine());
            String leAtino;
            String[] nombres = {"Alex", "Alfred", "Anita", "Anne", "Bernard", "Bill", "Charles", "Claire", "David", "Eric", "Frans", "George", "Herman", "Joe", "Maria", "Max", "Paul", "Peter", "Philip", "Richard", "Robert", "Sam", "Susan", "Tom"};
            if (jugador.getPersonaje() == suAtine) {
                leAtino = "Tu oponente adivinó correctamente tu personaje! El suyo era " + nombres[suPersonaje] + ".";
                jugador.setGanador("Perdió");
                w.println("Regresa: ");
                w.println("Le atinaste! Eres el ganador.Ganó");
            } else {
                leAtino = "Tu oponente adivinó incorrectamente tu personaje! Ganas por default. El suyo era " + nombres[suPersonaje] + ".";
                jugador.setGanador("Ganó");
                w.println("Regresa: ");
                w.println("No le atinaste! Su personaje era " + nombres[jugador.getPersonaje()] + ".Perdió");
            }
            Notice notice = new Notice(this, leAtino);
            notice.setVisible(true);
            guardaDatos();
        } catch (Exception ex) {
            System.err.println("terminarJugador2 exception caught.");
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public TableroLocal getTableroLocal() {
        return tableroLocal;
    }

    public void setTableroLocal(TableroLocal tableroLocal) {
        this.tableroLocal = tableroLocal;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public Socket getCliente() {
        return cliente;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }

    public BufferedReader getR() {
        return r;
    }

    public void setR(BufferedReader r) {
        this.r = r;
    }

    public PrintWriter getW() {
        return w;
    }

    public void setW(PrintWriter w) {
        this.w = w;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public boolean isPregunta() {
        return pregunta;
    }

    public void setPregunta(boolean pregunta) {
        this.pregunta = pregunta;
    }
    
    
}

class ReadingThread extends Thread {
    
    AdivinaQuien controlador;
    
    public ReadingThread(AdivinaQuien controlador) {
        this.controlador = controlador;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                while (!(controlador.getR().ready())) {}
                String input = controlador.getR().readLine();
                if (input.equals("Adivina: ")) {
                    controlador.terminarJugador2();
                    break;
                } else if (input.equals("Regresa: ")) {
                    controlador.terminarJugador1();
                    break;
                } else {
                    controlador.getTablero().getJTextField2().setText(input);
                    if (input.startsWith("P:")) controlador.getTablero().getBotonPreguntar().setEnabled(true);
                }
            }
        } catch (Exception ex) {
            System.err.println("ReadingThread exception caught.");
        }
        System.out.println("ReadingThread terminado");
    }
}