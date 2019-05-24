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
    public Map(int size){
    	for(int i = 0; i < MAP_SIZE; i++) {
    		ArrayList<ArrayList<Unit>> toAdd = new ArrayList<ArrayList<Unit>>();
    		for(int j = 0; j < MAP_SIZE; j++) {
        		toAdd.add(new ArrayList<Unit>());
        	}
    		map.add(i, toAdd);
    		
    	}
    }
    public void addMap(Unit unit){
        mapList.add(unit);
        
    }
    public void removeMap(Unit unit){
        mapList.remove(unit);
    }
    public void add(int x, int y, Unit unit){
       // map[x][y] = unit;
    }
    
    public void remove(int x, int y){
     //   map[x][y] = null;
    }
    
    public void update(){
    	map.clear();
    	for(int i = 0; i < MAP_SIZE; i++) {
    		ArrayList<ArrayList<Unit>> toAdd = new ArrayList<ArrayList<Unit>>();
    		for(int j = 0; j < MAP_SIZE; j++) {
        		toAdd.add(new ArrayList<Unit>());
        	}
    		map.add(i, toAdd);
    		
    	}
    	for(int i = 0; i < mapList.size(); i ++){
    		
    		map.get(mapList.get(i).x).get(mapList.get(i).y).add(mapList.get(i));
    		System.out.println(map.get(105).get(100));
        }
        for(int i = 0; i < mapList.size(); i ++){
            mapList.get(i).update();
            
        }
    }
    
    public void render(Screen screen){
        for(int i = 0; i < mapList.size(); i ++){
            mapList.get(i).render(screen);
        }
    }

}

