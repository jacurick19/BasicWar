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
import basicwar.units.Troop;
import basicwar.units.Unit;
import java.util.ArrayList;


public class Map {
    public final int MAP_SIZE = 500;
    private ArrayList<Unit> mapList = new ArrayList<Unit>();
    private Unit[][] map = new Unit[MAP_SIZE][MAP_SIZE];
    public Map(int size){

    }
    public void addMap(Unit unit){
        mapList.add(unit);
    }
    public void removeMap(Unit unit){
        mapList.remove(unit);
    }
    public void add(int x, int y, Unit unit){
        map[x][y] = unit;
    }
    
    public void remove(int x, int y){
        map[x][y] = null;
    }
    
    public void update(){
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

