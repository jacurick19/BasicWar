package basicwar;

import java.util.ArrayList;

import basicwar.map.Map;
import basicwar.units.Unit;

public class Generation {
	private int generationNumber;
	private Map map;
	public Generation(int red, int green, int yellow, int blue, Map map) {
		generationNumber = 0;
		this.map = map;

	}

	public Generation(int red, int green, int yellow, int blue, ArrayList<Unit> winner, ArrayList<Unit> second, ArrayList<Unit> third, int generationNumber, Map map) {
		this.generationNumber = generationNumber;
		map = this.map;
	}
	
	public void runGeneration() {
		
	}
	public void finishGeneration() {
		map.returnToCenter();
	}
	
}
