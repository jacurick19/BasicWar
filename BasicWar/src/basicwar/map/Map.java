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
    public int[] wins = new int[NUMBER_OF_FACTIONS];
    public ArrayList<Integer> factionsAlive = new ArrayList<Integer>();
    private ArrayList<Unit> faction0 = new ArrayList<Unit>();
    private ArrayList<Unit> faction1 = new ArrayList<Unit>();
    private ArrayList<Unit> faction2 = new ArrayList<Unit>();
    private ArrayList<Unit> faction3 = new ArrayList<Unit>();
    ArrayList<Unit> temp0 = new ArrayList<Unit>();
    ArrayList<Unit> temp1 = new ArrayList<Unit>();
    ArrayList<Unit> temp2 = new ArrayList<Unit>();
    ArrayList<Unit> temp3 = new ArrayList<Unit>();
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
        		wins[i] = 0;
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
        numberPerFaction[unit.getFaction()]--;
        if(numberPerFaction[unit.getFaction()] <= 0) {
        	ArrayList<Unit> temp = survivors;
        	for(int i= 0; i < survivors.size(); i ++) {
        		if(survivors.get(i).getFaction()==unit.getFaction()) {
        			temp.remove(i);
        		}
        	}
        	survivors = temp;
        	survivors.add(unit);
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
    	
    	if(threeAreZero(numberPerFaction)||mapList.size()==0) {
    		
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
    	if(mapList.size()>0) {
    	for(Unit u : mapList) {
    		toReturn+=u.getStrength();
    //		System.out.println(u);
    	}
    	}
    	
//		System.out.println(toReturn/mapList.size());

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
    public Unit calculateAvg(ArrayList<Unit> ar, int pla, boolean useFac, int fac) {
    	if(ar.isEmpty()) return null;
    	double strength = 0;
    	double vitality = 0;
    	double agression = 0;
    	for(Unit u : ar) {
    		
    		strength += ((u.getStrength()) + DELTA*plusMinus());
    		vitality+=(u.getVitality() +DELTA*plusMinus());
    		agression += (u.getAgression() +DELTA*plusMinus());
    	}
    	if(ar.size()>0) {
    		strength /= ar.size();
    		vitality /= ar.size();
    		agression /= ar.size();
    	}
    	Troop t;
    	
    	//TODO this should not survivors.get(pla).getBrain(). it should be based on the average of the top places
    	if(!useFac)	t = new Troop(strength, vitality, agression, survivors.get(pla).getBrain(), this);
    	
    	//brainFromList currently selects a random brain from the list
    	else t = new Troop(strength, vitality, agression, numberToFactionList(highestElementIndex(territoryByFaction)).get(0).getBrain(), this);
    	
    	//System.out.println(t);
    	return t;
    	}
    
    public Unit calculateSurvivors() {
    	return calculateAvg(survivors, -1, false, -1);
    }
    
    
    
    
    
    
    	public int plusMinus() {
    		Random random = new Random();
    	
    		int check = random.nextInt();
    		if(check%3 == 0) return 1;
    		if(check%3 ==1 ) return 0;
    		else return -1;
    	}
    
	
	public void setUp() {
		
	
    	boolean done = false;
    	if(bw.genstate == GENSTATE.FIRST_RUN && !ranOnce) {
        	System.out.println("first setup");
        	done = true;
    		ranOnce = true;
    		setSomeUp = true;
    		for(int i = 0; i < 10; i ++){
    			Troop t = new Troop(i+225,i+225,1, this);
    			addMap(t);
    			temp1.add(t);

    		}
    		for(int i = 0; i < 10; i ++){
    			Troop t = new Troop(i+275,i+225,0, this);
    			addMap(t);
    			temp0.add(t);
    		}
    		for(int i = 0; i < 10; i ++){
    			Troop t = new Troop(i+225,i+275,2, this);
    			addMap(t);
    			temp2.add(t);
    		}
    		for(int i = 0; i < 10; i ++){
    			Troop t = new Troop(i+275,i+275,3, this);
    			addMap(t);
    			temp3.add(t);
    		}
    	}

    	
    	if(bw.genstate == GENSTATE.SET_UP &&setSomeUp == false) {
    		
    		setSomeUp = true;
    		Unit parent0 = parentOfFaction(0);
    		Unit parent1 = parentOfFaction(1);
    		Unit parent2 = parentOfFaction(2);
    		Unit parent3 = parentOfFaction(3);
    		System.out.println("parent0: "+parent0);
    		System.out.println("parent1: "+parent1);
    		System.out.println("parent2: "+parent2);
    		System.out.println("parent3: "+parent3);
    		for(int i = 0; i < 10; i ++){
    			Troop t = new Troop(249,i+249,0, parent0, this);
    			addMap(t);
    			temp0 .add(t);
    		}
    		for(int i = 0; i < 10; i ++){
    			Troop t = new Troop(251,249,1, parent1, this);
    			addMap(t);
    			temp1.add(t);
    		}

    		for(int i = 0; i < 10; i ++){
    			Troop t = new Troop(251,i+251,2,parent2, this);
    			addMap(t);
    			temp2.add(t);
    		}
    		for(int i = 0; i < 10; i ++){
    			Troop t =new Troop(249,251,3,parent3, this);
    			addMap(t);
    			temp3.add(t);
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
    	if(done) {
        	faction0.clear();
        	faction1.clear();
        	faction2.clear();
        	faction3.clear();
        	faction0.addAll(temp0);
        	faction1.addAll(temp1);
        	faction2.addAll(temp2);
        	faction3.addAll(temp3);
        	survivors.clear();
    		bw.genstate = GENSTATE.WORK;
    	}
    	
    	
    }
	
	
	//Returns the index of the largest element in an array, an error occurs if the array is empty
	public int highestElementIndex(int[] ar) {
		int toReturn = 0;
		for(int i = 1; i < ar.length; i ++) {
			if(ar[i]>ar[toReturn])toReturn = i;
		}
		return toReturn;
	}
	
	//first and second stay, third place is a clone of second place, 4th is a clone of first place
    public Unit parentOfFaction(int fac) {
    	Unit toReturn = null;
    	int place =  -1;
    	for(int i = 0; i < survivors.size(); i ++) {
    		if(fac == survivors.get(i).getFaction()) place = i;
    	};
    	if(place == 0)wins[fac]++;
    	if(place == 0) toReturn = calculateAvg(numberToFactionList(fac), place, true, fac);
    	if(place == 1) toReturn = calculateAvg(numberToFactionList(fac), place, true, fac);
    	if(place == 2) toReturn = calculateAvg(numberToFactionList(survivors.get(2).getFaction()), place, true, fac);
    	if(place == 3) toReturn = calculateAvg(numberToFactionList(survivors.get(3).getFaction()), place, true, fac);
    	if(toReturn == null) {
    		System.out.println("Somehow got a null return value in parentOfFaction");
    		System.out.println("error occured at place: "+place);
    		System.out.println("survivors is: "+survivors);
    	}
    	return toReturn;
    	
    }
    
    public ArrayList<Unit> numberToFactionList(int num){
    	if(num == 0) return faction0;
    	if(num == 1) return faction1;
    	if(num == 2) return faction2;
    	if(num == 3) return faction3;
    		
    	return null;
    	
    }

    public void reset() {
    
       	System.out.println("resetting");
       	

    	//needed for setUp()
    	setSomeUp = false;
    	resetTerritory();

    	if(mapList.size()>=1) {
    	int sz = mapList.size();
    	for(int i = sz-1; i > -1; i--){
    		removeMap(mapList.get(i));
    		}
    	}
    	
    	bw.genstate = GENSTATE.SET_UP;
 
}
    
    
    
    
    
    
    public void resetTerritory() {
		for(int i = 0; i < MAP_SIZE; i++) {
    		for(int j = 0; j < MAP_SIZE; j++) {
        		territory[i][j] = -1;
        		territoryColors[i][j] = 0;
        	}
    		
	}
		territoryObjectArray.clear();
		for(int i = 0; i <= NUMBER_OF_FACTIONS; i ++) {
			territoryObjectArray.add(new ArrayList<Territory>());
			
		}
		for(int i = 0; i < NUMBER_OF_FACTIONS; i++) {
    		numberPerFaction[i] = 0;
    	}
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