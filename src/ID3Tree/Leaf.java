package ID3Tree;

import java.util.HashMap;

import javax.print.attribute.HashAttributeSet;



/**
 * Node class for a decision tree; contains a classification
 * 
 * @author Tilman & Tim
 *
 */
public class Leaf extends Node {

	private String classification;
	
	/**
	 * Constructor
	 * 
	 * @param 
	 * classification classification for instance, which reach this leaf
	 */
	public Leaf(String classification, int splitFeature, int splitValue, double entropy, HashMap<String, Integer> classMemberCount){
		this.classification = classification;
		this.splitFeature = splitFeature;
		this.splitValue = splitValue;
		this.entropy = entropy;
		this.classMemberCount = classMemberCount;
	}

	@Override
	public String decide(Instance instance) {
		return classification;
	}
	
	public String toString(){
		return classification;
	}

}
