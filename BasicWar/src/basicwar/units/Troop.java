/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *urickjl2023@mountunion.edu
 * JAcob Urick CSC 220 
 */
package basicwar.units;

import java.util.Random;

import basicwar.graphics.Screen;
import basicwar.map.Map;


public class Troop extends Unit {
    private Map map;
	Random random;
	int time = 0;
    public Troop(){
        faction = -1;
    }
  
    public Troop(int x, int y, int faction, Map map){
    	random = new Random();
        this.ammo = 5;
        this.health=TROOP_HEALTH;
        this.faction = faction;
        this.job = "troop";
        this.map = map;
        anger = 0;
        hunger = 0;
        repro = 0;
        
        vitality = random.nextInt(5);
        strength = 3+random.nextInt(7);
        agro = random.nextInt(5)+5;
        
        this.x = x;
        this.y = y;
    }
    
    public void act(){
    	health--;
    	if(health<0) die();
    	if(food<0) hunger+=10;
    	health-=1;
    	double a,b,c;
    	a = anger* agro + 1;
    	b = (Math.exp(hunger-strength));
    	if(vitality>0)c= repro % vitality;
    	else c =0;

    	double t = Math.max(a, b);
    	double d = Math.max(t, c);
    	
    	if(d == a) move();
    	//if(d == b) eat();
    	if(d == c) reproduce();
    
    }

    public void move(){
        int dir = random.nextInt(3);
        if(dir == 0) x++;
        if(dir == 1) x--;
        if(dir == 2) y++;
        if(dir == 3) y--;
        food-= 1;
    }
    
    public void eat(){
        food-=10;
        health+=5;
    }
    
    public void reproduce(){
        if((health * 1.0)/(TROOP_HEALTH)>.75 && food > 5 && time%15==0){
            food-=5;
            repro=0;
        }
        
    }

    @Override
    public void render(Screen screen) {
        screen.render(this.x,this.y,faction);
    }

    @Override
    public void update() {
    	act();
        time++;
        hunger++;
        repro++;
    }

	@Override
	public void die() {
		System.out.println(toString());
		map.removeMap(this);
		
	}
}
