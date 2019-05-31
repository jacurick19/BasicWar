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

import basicwar.graphics.Screen;
import basicwar.units.Unit;
import java.util.ArrayList;


public class Map {
    public final int MAP_SIZE = 500;
    public final int NUMBER_OF_FACTIONS = 4;
    public ArrayList<Unit> mapList = new ArrayList<Unit>();
    public ArrayList<ArrayList<ArrayList<Unit>>> map = new ArrayList<ArrayList<ArrayList<Unit>>>() ;
    public int[][] territory = new int[MAP_SIZE][MAP_SIZE] ;
    public ArrayList<ArrayList<Territory>> territoryObjectArray = new ArrayList<ArrayList<Territory>>() ;
    public int[][] territoryColors = new int[MAP_SIZE][MAP_SIZE] ;
    public int[] territoryByFaction = new int[NUMBER_OF_FACTIONS];
    public int[] numberPerFaction = new int[NUMBER_OF_FACTIONS];
    
    public Map(int size){
    	
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
        numberPerFaction[unit.getFaction()]--;

    }

    public void update(){
    	//printArray(numberPerFaction);
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
}

