package thinking;

import java.util.ArrayList;
import java.util.Random;

import basicwar.units.Action;
import basicwar.units.Unit;

public class Brain {
	//Makes an 8x7x7x6 neural network with random weights and biases
	
	private ArrayList<ArrayList<ArrayList<Double>>> weights = new ArrayList<ArrayList<ArrayList<Double>>>();
	private ArrayList<ArrayList<Double>> biases = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> activation = new ArrayList<ArrayList<Double>>();
	private Random random = new Random();
	private final Double DELTA = 0.85;
	public Brain() {
		//These will be dummy arraylists to fill the activation arraylist
		ArrayList<Double> temp_7 = new ArrayList<Double>();
		ArrayList<Double> temp_6 = new ArrayList<Double>();
		
		//fills temp_7 and temp_6 with 7 and 6 0's, respectively
		for(int i = 0; i < 7; i ++) temp_7.add(i, 0.0);
		for(int i = 0; i < 6; i ++) temp_6.add(i, 0.0);

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
				ArrayList<Double> temp = randomArrayList(activation.get(i).size());
				toAdd.add(temp);

			}
			weights.add(i, toAdd);
			
		}		
		
	}
	
	//TODO make it avg with variation, not random
	public Brain brainFromList(ArrayList<Unit> ar) {
		
		ArrayList<Brain> br = new ArrayList<Brain>();
		for(Unit u : ar) {
			br.add(u.getBrain());
		}
		
		int r = random.nextInt(br.size());
		return br.get(r);
		
		
	}
	
	public Brain(Brain parent) {
		//These will be dummy arraylists to fill the activation arraylist
		ArrayList<Double> temp_7 = new ArrayList<Double>();
		ArrayList<Double> temp_6 = new ArrayList<Double>();
		
		//fills temp_7 and temp_6 with 7 and 6 0's, respectively
		for(int i = 0; i < 7; i ++) temp_7.add(i, 0.0);
		for(int i = 0; i < 6; i ++) temp_6.add(i, 0.0);

		activation.add(0, temp_7);
		activation.add(1, temp_7);
		activation.add(2, temp_6);
		activation.add(3, temp_6);


		biases.add(0, varyRandomly(parent.getBiases().get(0)));	
		
		
		for(int i = 1; i < 3; i ++) {
			biases.add(i, varyRandomly(parent.getBiases().get(i)));	
		}
		
		
		
		
		for(int i = 0; i < 3; i ++) {
			ArrayList<ArrayList<Double>> toAdd = new ArrayList<ArrayList<Double>>();
			for(int j = 0; j < activation.get(i+1).size(); j++) {
				ArrayList<Double> temp = varyRandomly(parent.getWeights().get(i).get(j));
				toAdd.add(temp);
			}
			weights.add(i, toAdd);
			
		}		
		
	}
	
	public ArrayList<Double> varyRandomly(ArrayList<Double> ar ) {
		ArrayList<Double> toReturn = new ArrayList<Double>();
		for(int i = 0; i < ar.size(); i ++) {
			toReturn.add(ar.get(i) + random.nextDouble()*plusMinus()*DELTA);
			
		}
		return toReturn;
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
	
	private int plusMinus() {
		int temp = random.nextInt(2);
		if (temp <1)  return 1;
		else return -1;
		
	}
	
	public ArrayList<ArrayList<ArrayList<Double>>> averageWeights(ArrayList<Unit> units){
		return weights;
	}
	
	public double sigmoid(double d) {
		
		return Math.exp(d)/(1+Math.exp(d));
		
	}
	
	private ArrayList<Double> randomArrayList(int size){
		ArrayList<Double> toReturn = new ArrayList<>();
		for(int i = 0 ; i < size; i ++) {
			double rand = plusMinus()*random.nextDouble();
			toReturn.add(rand);
		}
		return toReturn;
	}
	
	public Double differenceBetweenArrayLists(ArrayList<Double> one, ArrayList<Double> two) {
		double toReturn = 0;
		toReturn = sumOfArrayList(one) - sumOfArrayList(two);
		return toReturn;
	}
	
	public Action think() {		
		//checks to see if the input layer is empty
		if(activation.get(0).isEmpty())System.out.println("There is a tremendous problem. Somehow think() is being called before loadData()");
		
		//feeds the data through the neural network
		for(int i = 1; i < 4; i ++) {
			for(int j = 0; j < activation.get(i).size(); j ++) {
				
				//apply weights
				activation.get(i).set(j, weightedSumOfArrayList(activation.get(i-1), weights.get(i-1).get(j)));
				
				//apply biases
				activation.get(i).set(j, activation.get(i).get(j) + biases.get(i-1).get(j));
				
				//normalize data with sigmoid function
				activation.get(i).set(j, sigmoid(activation.get(i).get(j)));

			}
			
		}
		
		
		//returns the index of the largest activation
		return(intToAction(biggestMember(activation.get(3))));
		
	}
	
	public ArrayList<ArrayList<Double>> getBiases(){
		return biases;
	}
	
	public ArrayList<ArrayList<ArrayList<Double>>> getWeights(){
		return weights;
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
	
	public Double sumOfArrayList(ArrayList<Double> ar) {
		Double toReturn = 0.0;
		for(Double d : ar) {
			toReturn += d;
			
		}
		return toReturn;
	}
	
	//Returns the index of the largest value in an array of doubles
	public int biggestMember(ArrayList<Double> ar) {
		int toReturn = 0;
		for(int i = 1; i < ar.size(); i ++) if(ar.get(i) > ar.get(toReturn)) toReturn = i;
		return toReturn;
	}
	
	private Double weightedSumOfArrayList(ArrayList<Double> ar, ArrayList<Double> weights) {
		Double toReturn = 0.0;
		for(int i = 0; i < ar.size(); i++) {
			
			toReturn += ar.get(i) * weights.get(i);
			
		}	
		return toReturn;
	}

	public void loadData(ArrayList<Unit> unitsUp, ArrayList<Unit> unitsRight, ArrayList<Unit> unitsDown,
			ArrayList<Unit> unitsLeft, double anger, double drive, double hunger) {
		ArrayList<Double> temp = new ArrayList<Double>();
		int up = unitsUp.size();
		int down = unitsDown.size();
		int right = unitsRight.size();
		int left = unitsLeft.size();
		temp.add((double) up);
		temp.add((double) right);
		temp.add((double) down);
		temp.add((double) left);
		
		temp.add(anger);
		temp.add(drive);
		temp.add(hunger);
		activation.set(0, temp);
	}
		
	
	public String toString() {
		return "these are my weights: "+weights;
	}

}
