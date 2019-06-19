/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *urickjl2023@mountunion.edu
 * JAcob Urick CSC 220 
 */
package basicwar.map;

import basicwar.BasicWar;
import basicwar.GENSTATE;
import basicwar.STATE;
import basicwar.graphics.Screen;
import basicwar.units.TestTroop;
import basicwar.units.Troop;
import basicwar.units.Unit;
import thinking.Brain;

import java.util.ArrayList;
import java.util.Random;


public class Map {
	private static final double DELTA = 0.15;

	boolean setSomeUp = false;

	private boolean ranOnce = false;
    public final int MAP_SIZE = 500;
    public final int NUMBER_OF_FACTIONS = 4;
    public ArrayList<Unit> mapList = new ArrayList<Unit>();
    public ArrayList<ArrayList<ArrayList<Unit>>> map = new ArrayList<ArrayList<ArrayList<Unit>>>() ;
    public int[][] territory = new int[MAP_SIZE][MAP_SIZE] ;
    public ArrayList<ArrayList<Territory>> territoryObjectArray = new ArrayList<ArrayList<Territory>>() ;
    public int[][] territoryColors = new int[MAP_SIZE][MAP_SIZE] ;
    public int[] territoryByFaction = new int[NUMBER_OF_FACTIONS];
    public int[] numberPerFaction = new int[NUMBER_OF_FACTIONS];
    public ArrayList<Integer> factionsAlive = new ArrayList<Integer>();
    private BasicWar bw;
	ArrayList<Unit> survivors = new ArrayList<Unit>();	

    public Map(int size, BasicWar bw){
    	this.bw = bw;
    	//Make the map ArrayList an ArrayList that contains 500 ArrayLists of 500 ArrayLists that each holds the units at that location
    	for(int i = 0; i < MAP_SIZE; i++) {
    		ArrayList<ArrayList<Unit>> toAdd = new ArrayList<ArrayList<Unit>>();
    		for(int j = 0; j < MAP_SIZE; j++) {
        		toAdd.add(new ArrayList<Unit>());
        	}
    		map.add(i, toAdd);
    	}
    		for(int i = 0; i < MAP_SIZE; i++) {
        		for(int j = 0; j < MAP_SIZE; j++) {
            		territory[i][j] = -1;
            		territoryColors[i][j] = 0;
            	}
        		
    	}
    		
    		
    		for(int i = 0; i < NUMBER_OF_FACTIONS; i++) {
        		numberPerFaction[i] = 0;
        	}
    		for(int i = 0; i <= NUMBER_OF_FACTIONS; i ++) {
    			territoryObjectArray.add(new ArrayList<Territory>());
    			
    		}
    	
    	
    }
    public void addMap(Unit unit){
        mapList.add(unit);
        numberPerFaction[unit.getFaction()]++;
        
    }
    public void removeMap(Unit unit){
        mapList.remove(unit);
        survivors.clear();
        survivors.add(unit);
        numberPerFaction[unit.getFaction()]--;

    }
    
    public void returnToCenter() {
    	for(int i = 0; i < mapList.size(); i ++){
            mapList.get(i).condense();
        }
    	
    }

    public void update(){
    	//printArray(numberPerFaction);
    	//System.out.println("working");

    	for(int i = 0; i < mapList.size(); i ++){
            mapList.get(i).update();
            
        }
    	map.clear();
    	for(int i = 0; i < MAP_SIZE; i++) {
    		ArrayList<ArrayList<Unit>> toAdd = new ArrayList<ArrayList<Unit>>();
    		for(int j = 0; j < MAP_SIZE; j++) {
    			
        		toAdd.add(new ArrayList<Unit>());
        	}
    		map.add(i, toAdd);
    		
    	}
    	
    	for(int i = 0; i < mapList.size(); i ++){
    		int x = mapList.get(i).getX();
    		int y = mapList.get(i).getY();
    		int faction = mapList.get(i).getFaction();
    		map.get(x).get(y).add(mapList.get(i));
 
    		//Who owns each pixel is decided here
    		int ownedBy = territory[x][y];
    		if(ownedBy != faction) {
    			territoryObjectArray.get(faction).add(new Territory(x,y));
    			territory[x][y] = faction;
    			territoryByFaction[faction] +=1;
    			if(ownedBy >= 0) {
    				
    				for(int j = 0; j < territoryByFaction[ownedBy]; j++) {
    					if(territoryObjectArray.get(ownedBy).get(j).x == x) {
    						if(territoryObjectArray.get(ownedBy).get(j).y == y) {
    							territoryObjectArray.get(ownedBy).remove(j);
        						break;
        					}
    						
    					}
    					
    				}
    				territoryByFaction[ownedBy] --;
    			
    			
    			};
    		}
    		
        }
    	
    	if(threeAreZero(numberPerFaction)) {
    		mapList.clear();
    		bw.genstate = GENSTATE.RESET;
    	
    	}
    }
    
    //Returns the faction with the most territory
    public int mostTerritory() {
    	int toReturn = 0;
    	for(int i = 1 ; i< territoryByFaction.length; i ++) {
    		if(territoryByFaction[i] > territoryByFaction[toReturn]) toReturn = i;
    		
    	}
    	return toReturn;
    }
    
    public double averageStrength() {
    	double toReturn = 0;
    	for(Unit u : mapList) {
    		toReturn+=u.getStrength();
    		
    	}
    	
    	
    	return toReturn/mapList.size();
    	
    }
    
    public void render(Screen screen){
        for(int i = 0; i < mapList.size(); i ++){
            mapList.get(i).render(screen);
        }
    }

    private int factionToHex(int faction) {
    	int d = 0;
    	if(faction ==0) d = 0x0DD757;
    	if(faction ==1) d = 0xEE1414;
    	if(faction ==2) d = 0x4286f4;
    	if(faction ==3) d = 0xefe81f;
    	return d;
    }
    
    public void renderSolid(Screen screen){
    	
    	 for(int i = 0; i < mapList.size(); i ++){
             mapList.get(i).render(screen);
         }
    	for(int i = 0; i < MAP_SIZE; i++) {
    		for(int j = 0; j < MAP_SIZE; j++) {
    			territoryColors[i][j] = factionToHex(territory[i][j]);
    		}
    		
    	}
            screen.renderSolid(territoryColors);
        
    }
    
    
    public void setUp(STATE s, boolean tr) {
    	if(s == STATE.RUNNING && !ranOnce) {
    		ranOnce = true;
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(i+225,i+225,1, this));
    		}
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(i+275,i+225,0, this));
    		}
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(i+225,i+275,2, this));
    		}
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(i+275,i+275,3, this));
    		}
    		
    	}
    	if(s== STATE.TESTING && !ranOnce ) {
    		ranOnce = true;
    		addMap(new TestTroop(100,100,1, this));
    		addMap(new TestTroop(104,100,0, this));
    		bw.setSimSpeed(1);
    	}
    }
    
    
    
    
    
    //takes an arraylist of units and creates an average unit from it, randomly mutated
    public Unit calculateAvg(ArrayList<Unit> ar) {
    	double strength = 0;
    	double vitality = 0;
    	double agression = 0;
    	for(Unit u : ar) {
    		strength += ((u.getStrength()) + DELTA*plusMinus());
    		vitality+=(u.getVitality() +DELTA*plusMinus());
    		agression += (u.getAgression() +DELTA*plusMinus());
    	}
    	strength /= ar.size();
    	vitality /= ar.size();
    	agression /= ar.size();
    	//TODO this should not be new Brain(). it should be based on the average
    	return new Troop(strength, vitality, agression, new Brain(), this);
    	}
    
    
    
    
    
    
    
    
    	public int plusMinus() {
    		Random random = new Random();
    	
    		int check = random.nextInt();
    		if(check%3 == 0) return 1;
    		if(check%3 ==1 ) return 0;
    		else return -1;
    	}
    
	
	public void setUp(Unit unit) {
    	boolean done = false;
    	if(bw.genstate == GENSTATE.FIRST_RUN && !ranOnce) {
        	System.out.println("first setup");
        	done = true;
    		ranOnce = true;
    		setSomeUp = true;
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(i+225,i+225,1, this));
    		}
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(i+275,i+225,0, this));
    		}
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(i+225,i+275,2, this));
    		}
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(i+275,i+275,3, this));
    		}
    	}
    	
    	if(bw.genstate == GENSTATE.SET_UP &&setSomeUp == false) {
        	System.out.println("second setup");

    		setSomeUp = true;
    		for(int i = 0; i < 10; i ++){
    			System.out.println("survivor: " + survivors);
    			addMap(new Troop(251,249,1, survivors.get(0), this));
    		}
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(249,i+249,0, survivors.get(0), this));
    		}
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(251,i+251,2,survivors.get(0), this));
    		}
    		for(int i = 0; i < 10; i ++){
    			addMap(new Troop(249,251,3,survivors.get(0), this));
    		}
    	}
    	
    	
    	
    	if(setSomeUp == true && done == false) {
    		for(int i = 0; i < mapList.size(); i ++){
                mapList.get(i).disperse();
            }
    	}
    	done = true;
    	for (int i = 0; i < mapList.size(); i ++) {
    		done = (mapList.get(i).getDispersed() && done);

    	}
    	if(done)bw.genstate = GENSTATE.WORK;
    	
    	
    }

    public void reset() {
    
       	System.out.println("resetting");
    	//needed for setUp()
    	setSomeUp = false;
    	for(int i = 0; i < mapList.size(); i ++){
    		survivors.add(mapList.get(i));
            mapList.get(i).condense();
        }
    	bw.genstate = GENSTATE.SET_UP;
    	setUp(calculateAvg(survivors));
    	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	//Returns true if 3 of the values of the input array are 0
	public boolean threeAreZero(int[] ar) {
		boolean toReturn = false;
		int temp = 0;
		for(int i : ar)if(i == 0) temp++;
		if(temp >2) toReturn= true;
		return toReturn;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

