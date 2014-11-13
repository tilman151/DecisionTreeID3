package ID3Tree;



/**
 * Abstract class for a node of a decision tree
 * 
 * @author Tilman & Tim
 *
 */
public abstract class Node {

	public abstract String decide(Instance instance);
	
}
