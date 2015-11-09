package edu.jpgm.models;

import java.util.Arrays;
import java.util.List;

import edu.jpgm.Constants;


public class Node {

	// left: parent parameters, column header: node parameters
	private double[][] distribution;
	private String name;
	private Node[] parents;
	private Node[] children;
	private List<String> parameters;
	private int[] occurances;

	//Node name, All possible values in the order as the distribution table should be constructed,
	//parents in the order of the distribution table
	public Node(String name, String[] params, Node[] parents) {
		this.name = name;
		this.parents = parents;
		this.parameters = Arrays.asList(params);
		this.children = null;
		this.distribution = null;
		int totalrows = parameters.size();
		if (parents != null)
			for (Node parent : parents)
				totalrows *= parent.getParameters().size();
		occurances = new int[totalrows];
	}

	//Assigns uniform distribution
	public void setDefaultDistribution() {
		int totalrows = 1;
		if (parents != null)
			for (Node parent : parents)
				totalrows *= parent.getParameters().size();
		if (distribution == null)
			distribution = new double[totalrows][parameters.size()];
		for (int i = 0; i < totalrows; i++) {
			for (int j = 0; j < parameters.size(); j++) 
				distribution[i][j] = 1.0 / parameters.size();
		}
	}

	public void countOccurances(List<String> headers, String[] vals) {
		int index = 0;
		int totalrows = occurances.length;
		for(int i = 0; parents != null && i < parents.length; i++){
			Node node = parents[i];
			int a = headers.indexOf(node.getName());
			int b = node.getParameters().indexOf(vals[a]);
			totalrows /= node.getParameters().size();
			index += b*totalrows;
		}
		int a = headers.indexOf(name);
		int b = parameters.indexOf(vals[a]);
		occurances[index+b]++;
	}

	public void calculateDistribution() {
		int groupCount = parents != null ? parents[parents.length-1].getParameters().size() : occurances.length;
		int li = 0;
		int j = 0;
		while(li < occurances.length){
			int total = 0;
			for(int i = li; i < li+groupCount; i++){
				distribution[j][i-li] = occurances[i];
				total += occurances[i];
			}
			for(int i = li; i < li+groupCount; i++)
				distribution[j][i-li] /= total;
			li += groupCount;
			j++;
		}
		occurances = new int[occurances.length];
	}
	
	public void mostLikelyValue(List<String> headers, String[] values){
		int totalrows = distribution.length;
		int a = headers.indexOf(name);
		if(values[a].equals(Constants.MISSING_VALUE)){
			int index = 0;
			if(parents != null){
				for(int i = 0; i < parents.length; i++){
					int a1 = headers.indexOf(parents[i].getName());
					int b1 = parents[i].getParameters().indexOf(values[a1]);
					if(values[a1].equals(Constants.MISSING_VALUE))
						return;
					else {
						totalrows /= parents[i].getParameters().size();
						index += b1*totalrows;
					}
				}
			}
			double[] row = distribution[index];
			double max = 0;
			int maxi = 0;
			for(int j = 0; j < row.length; j++){
				if(max < row[j]){
					max = row[j];
					maxi = j;
				}
			}
			values[a] = parameters.get(maxi);
		}
		if(children != null)
			for(Node node : children)
				node.mostLikelyValue(headers, values);
	}

	public List<String> getParameters() {
		return parameters;
	}

	public double[][] getDistribution() {
		return distribution;
	}

	public void setDistribution(double[][] distribution) {
		this.distribution = distribution;
	}

	public Node[] getChildren() {
		return children;
	}
	
	public String getName(){
		return name;
	}

	public void setChildren(Node[] children) {
		this.children = children;
	}
	
	public Node[] getParents(){
		return parents;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(name + " :");
		if(parents != null)
			for(Node node : parents)
				str.append(" " + node.getName());
		str.append("\n");
		for(String param : parameters)
			str.append(param + "\t");
		str.append("\n--------------\n");
		for(int i = 0; i < distribution.length; i++){
			double[] vals = distribution[i];
			for(int j = 0; j < vals.length; j++)
				str.append(vals[j] + "\t");
			str.append("\n");
		}
		return str.toString();
	}

}
