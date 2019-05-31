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
        
    //TODO add speed
    public abstract void render(Screen screen);
    public abstract void update();
    public abstract void die();
    public abstract void setHealth(double health);
    public abstract void changeHealth(double health);
    private double health;
    private int x;
    private int faction;
    private int y;
    private int ammo;
    private double vitality;
    private double strength;
    private double anger;
    private double hunger;
    private double repro;
    private double agro;
    private String job;
    protected int food;
    
    public  int getX() {
    	return x;
    }
    
    public String toString(){
            return "I am a "+ job+" at position ("+x+", "+y+"). I have "+health+" health and my ammo supplies are: "+ammo;
    }
}
