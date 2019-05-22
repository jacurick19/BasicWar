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

import basicwar.graphics.Screen;


public class Troop extends Unit {
    
    public Troop(){
        faction = -1;
    }
  
    public Troop(int x, int y, int faction){
        this.ammo = 5;
        this.health=TROOP_HEALTH;
        this.faction = faction;
        this.job = "troop";
        
        this.x = x;
        this.y = y;
    }
    
    public void act(){
        
    }

    public void move(String dir, int ammount){
        if(dir.equals("x")) x+=ammount;
        if(dir.equals("y")) y+=ammount;
        food-= ammount+1;
    }
    
    public void eat(){
        food-=10;
        health+=5;
    }
    
    public void reproduce(){
        if((health * 1.0)/(TROOP_HEALTH)>.75 && food > 5){
            food-=5;
            
        }
    }

    @Override
    public void render(Screen screen) {
        screen.render(this.x,this.y,faction);
    }

    @Override
    public void update() {
        move("x", 1);
        
    }
}
