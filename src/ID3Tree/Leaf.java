package ID3Tree;



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
	public Leaf(String classification){
		this.classification = classification;
	}

	@Override
	public String decide(Instance instance) {
		return classification;
	}
	
	public String toString(){
		return classification;
	}

}
