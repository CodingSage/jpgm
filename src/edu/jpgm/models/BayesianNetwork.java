package edu.jpgm.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import edu.jpgm.Constants;

public class BayesianNetwork {

	private Map<String, Node> nodes;
	private String dataPath;
	private String tempPath;

	public BayesianNetwork(List<Node> nodes) {
		this.nodes = new HashMap<String, Node>(); 
		for(Node node : nodes)
			this.nodes.put(node.getName(), node);
	}
	
	public void iterate(int loopCount){
		for(int i = 0; i < loopCount; i++)
			iterate();
	}

	public void iterate(){
		eStep();
		mStep();
	}

	private void eStep() {
		boolean done = false;
		for(String nodeName : nodes.keySet()){
			Node node = nodes.get(nodeName);
			if(node.getDistribution() == null){
				node.setDefaultDistribution();
				done = true;
			}
		}
		if(done) return;
		BufferedReader reader = getReader(tempPath);
		String line;
		try {
			line = reader.readLine();
			List<String> headers = Arrays.asList(line.split(Constants.DELIMITER));
			while((line = reader.readLine()) != null){
				for(Node node : nodes.values()){
					String[] vals = line.split(Constants.DELIMITER);
					node.countOccurances(headers, vals);
				}
			}
			for(Node node : nodes.values())
				node.calculateDistribution();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void mStep() {
		try {
			BufferedReader reader = getReader(dataPath);
			PrintWriter writer = getWriter(tempPath);
			String line = reader.readLine();
			List<String> headers = Arrays.asList(line.split(Constants.DELIMITER));
			writer.write(line+"\n");
			while((line = reader.readLine()) != null){
				String[] vals = line.split(Constants.DELIMITER);
				for(Node node : nodes.values())
					node.mostLikelyValue(headers, vals);
				StringJoiner str = new StringJoiner(",");
				for(String val : vals)
					str.add(val);
				writer.write(str.toString()+"\n");
			}
			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private PrintWriter getWriter(String path){
		try {
			return new PrintWriter(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private BufferedReader getReader(String path){
		try {
			return new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Node> getNodes() {
		return new ArrayList<Node>(nodes.values());
	}

	public void setDataPath(String path) {
		dataPath = path;
		File file = new File(dataPath);
		tempPath = file.getParent().toString() + File.separator + "temp.txt";
	}
	
	public void printDetails(){
		for(Node node : nodes.values())
			System.out.println(node);
	}

}
