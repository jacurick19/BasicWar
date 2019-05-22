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
    
    
    
    //TODO add speed
    public abstract void render(Screen screen);
    public abstract void update();
    public abstract void die();
    public int health;
    public int x;
    public int faction;
    public int y;
    public int ammo;
    public int vitality;
    public int strength;
    public int anger;
    public int hunger;
    public int repro;
    public int agro;
    public String job;
    protected int food;
    
    public String toString(){
            return "I am a "+ job+". I am at position ("+x+", "+y+"). I have "+health+" health and my ammo supplies are: "+ammo;
    }
}
