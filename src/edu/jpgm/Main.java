package edu.jpgm;

import java.util.Arrays;
import java.util.List;

import edu.jpgm.models.BayesianNetwork;
import edu.jpgm.models.Node;

public class Main {

	public static void main(String[] args) {
		BayesianNetwork net = new BayesianNetwork(createGraph());
		//training data path
		net.setDataPath("");
		net.iterate();
	}
	
	public static List<Node> createGraph(){
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
