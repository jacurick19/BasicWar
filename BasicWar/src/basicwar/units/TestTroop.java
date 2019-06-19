
package basicwar.units;

import java.util.ArrayList;
import java.util.Random;

import basicwar.graphics.Screen;
import basicwar.map.Map;



//TODO look into trading

public class TestTroop extends Unit {
    private Map map;
   private double drive = 0;
	Random random;
	int time = 0;
	
	public int plusMinus() {
		if(random.nextInt()%2 == 0) return 1;
		else return -1;

	}
    public TestTroop(){
        faction = -1;
    }
    
    public double reproFunction() {
    	if(repro + vitality > 250 )  drive+=1;
    	return drive;
    	
    }
    
    
    
    public TestTroop(int x, int y, Unit parent, Map map){
    	random = new Random();
        this.ammo = 5;
        this.health=TROOP_HEALTH;
        this.faction = parent.faction;
        this.job = "troop";
        this.map = map;
        anger = 2;
        hunger = 0;
        repro = 1;
        
        food = 5;
        
        vitality = parent.vitality + (plusMinus()*(random.nextInt(2) * DELTA));
        strength = parent.strength + (plusMinus()*(random.nextInt(2) * DELTA));
        agro = parent.agro + (plusMinus()*(random.nextInt(2) * DELTA));;
        
        this.x = x;
        this.y = y;
    }
  
    public TestTroop(int x, int y, int faction, Map map){
    	random = new Random();
        this.ammo = 5;
        this.health=TROOP_HEALTH;
        this.faction = faction;
        this.job = "troop";
        this.map = map;
        anger = 2;
        hunger = 0;
        
        
        vitality = random.nextInt(5);
        strength = 1+random.nextInt(7);
        agro = random.nextInt(5)+5;
        repro = vitality;
        
        this.x = x;
        this.y = y;
    }
    
    public void act(){
    	
    	move();
    	
    	
    }

    public void move(){
       int dir = faction;
       
       if(dir == 0 && x>0) x--;
        if(dir == 1 && x<498) x++; 
        if(dir == 2 && y< 498) y++;
        if(dir == 3 && y>0) y--;
        food+=10;
        hunger+= 1;
       
    }
    
    public void eat(){
        food-=10;
        hunger = 0;
        if(health < 10) health+=5;
        else health = TROOP_HEALTH;
       
    }
    
    public void reproduce(){
        if((health * 1.0)/(TROOP_HEALTH)>.75 && food > 5 && repro >0){
            food-=10;
            repro--;;
            map.addMap(new TestTroop(x, y, this, map));
            drive = 0;
        }
        
    }

    @Override
    public void render(Screen screen) {
    	
    	//If someone else is in the same spot as you, battle
    	if(!(screen.renderIsEmpty(this.x,this.y,faction))) {
        	battle(map.map);
        }
    }

    @Override
    public void update() {
    	act();
        time++;
        hunger++;
        drive++;
    }
    
    public Unit battle(Unit a, Unit b) {
    	Unit winner;
    	Unit loser;
    	if(a.strength>= b.strength) { winner = a;
    	loser = b;}
    	else {winner =b;
    	
    	loser = a;}
    	
    	loser.die();

    	return winner;
    	
    }
    
    public void changeHealth(double hea) {
    	health += hea;
    	
    }
    
    public void setHealth(double hea) {
    	health = hea;
    	
    }

    
    public void battle(ArrayList<ArrayList<ArrayList<Unit>>> ar) {
    	
    	for(int i = 0; i <(ar.get(x).get(y)).size(); i++) {
        	

    		if(ar.get(x).get(y).get(i).faction != faction) {
    			battle(this, ar.get(x).get(y).get(i));
    		
    			;
    		}
    	}
		
    	
    }
	@Override
	public void die() {
		map.removeMap(this);
		
	}
	@Override
	public void disperse() {
		// TODO Auto-generated method stub
		
	}


}
