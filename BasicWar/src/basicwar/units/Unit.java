/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *urickjl2023@mountunion.edu
 * Jacob Urick CSC 220 
 */
package basicwar.units;

import basicwar.graphics.Screen;


public abstract class Unit {
    public final int TROOP_HEALTH = 15;
    public final double DELTA = .15;
    
    //NOTE WELL: faction must be greater than 0 or the UNIT will not render
    
    //TODO add speed
    public abstract void render(Screen screen);
    public abstract void update();
    public abstract void die();
    public abstract void setHealth(double health);
    public abstract void changeHealth(double health);
    public double health;
    public int x;
    public int faction;
    public int y;
    public int ammo;
    public double vitality;
    public double strength;
    public double anger;
    public double hunger;
    public double repro;
    public double agro;
    public String job;
    protected int food;
    
    public String toString(){
            return "I am a "+ job+". I am at position ("+x+", "+y+"). I have "+health+" health and my ammo supplies are: "+ammo;
    }
}
