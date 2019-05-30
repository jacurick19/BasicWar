package thinking;

import java.util.ArrayList;
import java.util.Random;

import basicwar.units.Action;

public class Brain {
	//MAkes an 8x7x7x6 neural network with random weights and biases
	
	private ArrayList<ArrayList<ArrayList<Double>>> weights = new ArrayList<ArrayList<ArrayList<Double>>>();
	private ArrayList<ArrayList<Double>> biases = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> activation = new ArrayList<ArrayList<Double>>();
	private Random random = new Random();
	
	public Brain() {
		//These will be dummy arraylists to fill the activation arraylist
		ArrayList<Double> temp_7 = new ArrayList<Double>();
		ArrayList<Double> temp_6 = new ArrayList<Double>();
		
		//fills temp_7 and temp_6 with 7 and 6 null doubles, respectively
		for(int i = 0; i < 7; i ++) temp_7.add(i, null);
		for(int i = 0; i < 6; i ++) temp_6.add(i, null);

		activation.add(0, temp_7);
		activation.add(1, temp_7);
		activation.add(2, temp_6);
		activation.add(3, temp_6);


		biases.add(0, randomArrayList(7));	
		
		
		for(int i = 1; i < 3; i ++) {
			biases.add(i, randomArrayList(6));	
		
		}
		
		for(int i = 0; i < 3; i ++) {
			ArrayList<ArrayList<Double>> toAdd = new ArrayList<ArrayList<Double>>();
			for(int j = 0; j < activation.get(i+1).size(); j++) {
				ArrayList temp = randomArrayList(activation.get(i).size());
				toAdd.add(temp);

			}
			weights.add(i, toAdd);
			
		}
		System.out.println(weights.get(1).get(0).size());
		
		
	}
	
	public void loadData(int up, int right, int down, int left, double anger, double drive, double hunger) {
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.add((double) up);
		temp.add((double) right);
		temp.add((double) down);
		temp.add((double) left);
		
		temp.add(anger);
		temp.add(drive);
		temp.add(hunger);
		activation.set(0, temp);
	}
	
	public int plusMinus() {
		int temp = random.nextInt(1);
		if (temp <1)  return 1;
		else return 1;
		
	}
	
	public double sigmoid(double d) {
		
		return Math.exp(d)/(1+Math.exp(d));
		
	}
	
	public ArrayList<Double> randomArrayList(int size){
		ArrayList<Double> toReturn = new ArrayList<>();
		for(int i = 0 ; i < size; i ++) {
			double rand = plusMinus()*random.nextDouble();
			toReturn.add(rand);
		}
		return toReturn;
	}
	
	public Action think() {
		Action a;
		int temp = -1;
		if(activation.get(0).isEmpty())System.out.println("There is a tremendous problem. Somehow think() is being called before loadData()");
		
		
		return(intToAction(temp));
		
	}
	
	
	public Action intToAction(int input) {
		Action a = null;
		if(input < 0 ) a = null;
		if(input == 0) a = Action.MOVE_UP;
		if(input == 1) a = Action.MOVE_RIGHT;
		if(input == 2) a = Action.MOVE_DOWN;
		if(input == 3) a = Action.MOVE_LEFT;
		if(input == 4) a = Action.REPRODUCE;
		if(input == 5) a = Action.EAT;
		return a;
	}
	

}
