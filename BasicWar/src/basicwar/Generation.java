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
		
	}
	
	
	
	public void runGeneration(Map map) {
	
		
	}
	public void finishGeneration(Generation g) {
	//	map.returnToCenter();
	}
	

	


public void setUp(Generation parent) {
    
	   

	}
	
}
    	
