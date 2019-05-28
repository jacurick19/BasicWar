
package basicwar.units;

import java.util.ArrayList;
import java.util.Random;

import basicwar.graphics.Screen;
import basicwar.map.Map;


// TODO look into trading


public class Troop extends Unit {
    private Map map;
   private double drive = 0;
	Random random = new Random();
	int time = 0;
	int dir = -1;
	public int plusMinus() {
		if(random.nextInt()%2 == 0) return 1;
		else return -1;

	}
    public Troop(){
        faction = -1;
    }
    
    public double reproFunction() {
    	if(repro + vitality > 250 )  drive+=1;
    	return drive;
    	
    }
    
    
    
    public Troop(int x, int y, Unit parent, Map map){
    	random = new Random();
    	  dir = random.nextInt(4);
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
  
    public Troop(int x, int y, int faction, Map map){
    	random = new Random();
        this.ammo = 5;
         dir = random.nextInt(4);
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
    	//Die of natural causes
    	if(time > (strength*50)) {
    		die();
    	}
    	//die if you have no health
    	if(health<0) die();
    	
    	//Starve
    	if(hunger>10) { 
    	hunger+=10;
    	health-=1;
    	}
    	
    	//Decide what they want to do
    	double a,b,c;
    	a = anger* agro + 1;
    	b = (Math.exp(hunger-strength));
    	//TODO make sure this is increasing
    	if(vitality>0)c= reproFunction();
    	else c =0;

    	double t = Math.max(a, b);
    	double d = Math.max(t, c);
    	
    	if(d == a) move();
    	if(d == c) reproduceRandomly();
    	if(d == b && food >0) eat();
    	else move();
    	
    	
    }

    public void move(){
    	
    	if(time %4 != 0) {
    	  dir = random.nextInt(4);
    	}
        if(dir == 0 && x<498) x++;
        if(dir == 1 && x>0) x--;
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
            map.addMap(new Troop(x, y, this, map));
            drive = 0;
        }
        
    }

    
    //If someone else is in the same spot as you, battle
    @Override
    public void render(Screen screen) {
        if(!screen.renderIsEmpty(this.x,this.y,faction)) battle(map.map, x, y);
     
        ;
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
    	winner.health -= health*(loser.strength / winner.strength);
    	return winner;
    	
    }
    
    public void changeHealth(double hea) {
    	health += hea;
    	
    }
    
    public void setHealth(double hea) {
    	health = hea;
    	
    }

    
    public void battle(ArrayList<ArrayList<ArrayList<Unit>>> ar, int x, int y) {
    	for(int i = 0; i <(ar.get(x).get(y)).size(); i++) {
    		if(ar.get(x).get(y).get(i).faction != faction) {
    			battle(this, ar.get(x).get(y).get(i));
    		
    			;
    		}
    		
    		
    		
    	}
		
    	
    }
    
    
    public void reproduceRandomly() {
    	 if((health * 1.0)/(TROOP_HEALTH)>.75 && food > 5 && repro >0){
    		 int rand = random.nextInt(map.territoryObjectArray.get(faction).size());
             food-=10;
             repro--;;
             map.addMap(new Troop(map.territoryObjectArray.get(faction).get(rand).x, map.territoryObjectArray.get(faction).get(rand).y, this, map));
             drive = 0;
         }
    	
    }
	@Override
	public void die() {
		map.removeMap(this);
		
	}
}
