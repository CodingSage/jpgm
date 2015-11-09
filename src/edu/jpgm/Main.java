package edu.jpgm;

import java.util.Arrays;
import java.util.List;

import edu.jpgm.models.BayesianNetwork;
import edu.jpgm.models.Node;

public class Main {

	public static void main(String[] args) {
		//BayesianNetwork net = new BayesianNetwork(createGraph1());
		BayesianNetwork net = new BayesianNetwork(createGraph2());
		//training data path
		net.setDataPath("/home/vinayak/Projects/jpgm/resources/sprinkler_test.dat");
		net.iterate();
		net.printDetails();
	}
	
	//Sprinkler model
	private static List<Node> createGraph2() {
		Node winter = new Node("Winter", new String[]{"1", "0"}, null);
		Node sprinkler = new Node("Sprinkler", new String[]{"1", "0"}, new Node[]{winter});
		Node rain = new Node("Rain", new String[]{"1", "0"}, new Node[]{winter});
		Node grass = new Node("WetGrass", new String[]{"1",  "0"}, new Node[]{sprinkler, rain});
		Node road = new Node("SlipperyRoad", new String[]{"1", "0"}, new Node[]{rain});
		winter.setChildren(new Node[]{sprinkler, rain});
		sprinkler.setChildren(new Node[]{grass});
		rain.setChildren(new Node[]{grass, road});
		return Arrays.asList(new Node[]{winter, sprinkler, rain, grass, road});
	}
	
	//Student model
	public static List<Node> createGraph1(){
		Node difficulty = new Node("Difficulty", new String[]{"Easy", "Hard"}, null);
		Node intelligence = new Node("Intelligence", new String[]{"High", "Low"}, null);
		Node grade = new Node("Grade", new String[]{"A", "B", "C"}, new Node[]{difficulty, intelligence});
		Node sat = new Node("SAT", new String[]{"High",  "Low"}, new Node[]{intelligence});
		Node letter = new Node("Letter", new String[]{"Good", "Bad"}, null);
		difficulty.setChildren(new Node[]{grade});
		intelligence.setChildren(new Node[]{grade, sat});
		grade.setChildren(new Node[]{letter});
		return Arrays.asList(new Node[]{difficulty, intelligence, grade, sat, letter});
	}
	
}
