package ID3Tree;



public class Leaf extends Node {

	private String classification;
	
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
