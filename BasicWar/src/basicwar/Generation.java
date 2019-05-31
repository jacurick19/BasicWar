package basicwar;

import java.util.ArrayList;

import basicwar.map.Map;
import basicwar.units.TestTroop;
import basicwar.units.Troop;
import basicwar.units.Unit;

public class Generation {
	private int generationNumber;
	private Map map;
	private int red;
	private int green;
	private int blue; 
	private int yellow;
	public Generation(int red, int green, int yellow, int blue, Map map) {
		generationNumber = 0;
		this.map = map;
		this.red = red;
		this.blue = blue;
		this.green = green;
		this.yellow = yellow;
	}

	public Generation(int red, int green, int yellow, int blue, ArrayList<Unit> winner, ArrayList<Unit> second, ArrayList<Unit> third, int generationNumber, Map map) {
		this.generationNumber = generationNumber;
		map = this.map;
		this.red = red;
		this.yellow = yellow; 
		this.blue = blue; 
		this.green = green;
	}
	
	public void startGeneration(Map map) {
		//Generation g = new Generation(red, green, yellow, blue, null, null, null, generationNumber+1, map);
		
	}
	
	
	
	public void runGeneration(Map map) {
	//	map.update();
	//	if(threeAreZero(map.factionsAlive))g.finishGeneration(g);
		
	}
	public void finishGeneration(Generation g) {
	//	map.returnToCenter();
	}
	
	//Returns true if 3 of the values of the input array are 0
	public boolean threeAreZero(int[] ar) {
		boolean toReturn = false;
		int temp = 0;
		for(int i : ar)if(i == 0) temp++;
		if(temp >2) toReturn= true;
		return toReturn;
	}
	


public void setUp(Generation parent) {
    
	   
	for(int i = 0; i < 10; i ++){
		map.addMap(new Troop(i+225,i+225,1, map));
	}
	
	for(int i = 0; i < 10; i ++){
		map.addMap(new Troop(i+275,i+225,0, map));

	}
	
	
	for(int i = 0; i < 10; i ++){
		map.addMap(new Troop(i+225,i+275,2, map));
	}
	
	for(int i = 0; i < 10; i ++){
		map.addMap(new Troop(i+275,i+275,3, map));

	}
	
}
    	
}
