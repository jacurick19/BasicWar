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
import thinking.Brain;


public abstract class Unit {
    public final int TROOP_HEALTH = 15;
    public final double DELTA = .15;
        
    //TODO add speed
    public abstract void render(Screen screen);
    public abstract void update();
    public abstract void die();
    public abstract void setHealth(double health);
    public abstract void changeHealth(double health);
	public abstract void returnToCenter();
    protected double health;
    protected int x;
    protected int faction;
    protected int y;
    protected int ammo;
    protected double vitality;
    protected double strength;
    protected double anger;
    protected double hunger;
    protected double repro;
    protected double agro;
    protected String job;
    protected int food;
    protected Brain brain;
    
    public int getY() {return y;}
    public  int getX() {
    	return x;
    }
    
    public String toString(){
            return "I am a "+ job+" at position ("+x+", "+y+"). I have "+health+" health and my ammo supplies are: "+ammo;
    }
	public double getStrength() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Brain getBrain() {
		return brain;
	}
	public int getFaction() {
		return faction;
	}
	
}

