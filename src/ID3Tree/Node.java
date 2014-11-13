package ID3Tree;

import java.util.HashMap;



/**
 * Abstract class for a node of a decision tree
 * 
 * @author Tilman & Tim
 *
 */
public abstract class Node {

	public HashMap<String, Integer> classMemberCount;
	public double entropy;
	public int splitFeature;
	public int splitValue;
	
	public abstract String decide(Instance instance);
	
}
