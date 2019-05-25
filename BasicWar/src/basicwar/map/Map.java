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
    public ArrayList<Unit> mapList = new ArrayList<Unit>();
   // public ArrayList<ArrayList<Unit>> map = new ArrayList<ArrayList<Unit>>();
    public ArrayList<ArrayList<ArrayList<Unit>>> map = new ArrayList<ArrayList<ArrayList<Unit>>>() ;
    public int[][] territory = new int[MAP_SIZE][MAP_SIZE] ;
    
    //This must be instantiated with the number of factions + 1
    public int[] territoryByFaction = new int[4];
    
    
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
            	}
        		
    	}
    	
    	
    }
    public void addMap(Unit unit){
        mapList.add(unit);
        
    }
    public void removeMap(Unit unit){
        mapList.remove(unit);
    }

    public void update(){
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
    		int x = mapList.get(i).x;
    		int y = mapList.get(i).y;
    		int faction = mapList.get(i).faction;
    		map.get(x).get(y).add(mapList.get(i));
 
    		//Who owns each pixel is decided here
    		int ownedBy = territory[x][y];
    		if(ownedBy != faction) {
    			territory[x][y] = faction;
    			territoryByFaction[faction] +=1;
    			if(ownedBy >= 0) territoryByFaction[ownedBy] --;
    		}
    		
        }
    }
    
    //Returns the faction with the most territory
    public int mostTerritory() {
    	int toReturn = territoryByFaction[0];
    	for(int i = 1 ; i< territoryByFaction.length; i ++) {
    		if(territoryByFaction[i] > toReturn) toReturn = i;
    		
    	}
    	return toReturn;
    }
    
    
    //Prints the ammount of territory a faction has
    private void printArray(int[] ar) {
    	for(int i = 0; i < ar.length; i++)
    	System.out.print(" Faction: "+i+" has "+ar[i] +" *****");
    	System.out.println();
    }
    public void render(Screen screen){
        for(int i = 0; i < mapList.size(); i ++){
            mapList.get(i).render(screen);
        }
    }

}

