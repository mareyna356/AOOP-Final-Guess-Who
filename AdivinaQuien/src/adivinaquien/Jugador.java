/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adivinaquien;

/**
 *
 * @author Marco
 */
public class Jugador {
    
    String nombre;
    int numPregs;
    int personaje;
    String ganador;

    public Jugador(String nombre) {
        this.nombre = nombre;
        numPregs = 0;
        personaje = 0;
    }
    
    public void increaseNumPregs() {
        numPregs++;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumPregs() {
        return numPregs;
    }

    public void setNumPregs(int numPregs) {
        this.numPregs = numPregs;
    }

    public int getPersonaje() {
        return personaje;
    }

    public void setPersonaje(int personaje) {
        this.personaje = personaje;
    }
    
    public String getGanador() {
        return ganador;
    }
    
    public void setGanador(String ganador) {
        this.ganador = ganador;
    }
}
