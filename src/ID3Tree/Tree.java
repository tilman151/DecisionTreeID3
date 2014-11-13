package ID3Tree;
import java.util.ArrayList;


/**
 * Node for a decision tree; branches on a feature
 * 
 * @author Tilman & Tim
 *
 */
public class Tree extends Node {

	private int feature;
	private Domain domain;
	private Node[] branches;
	
	/**
	 * Default constructor for tree class
	 * 
	 */
	public Tree(){
	}

	/**
	 * Classifies an instance
	 * 
	 * @param 
	 * instance instance to be classified
	 * 
	 * @return classification of instance
	 */
	@Override
	public String decide(Instance instance) {
		String instanceFeature = instance.getFeature(feature);
		int i = 0;
		while(domain.getValue(i).compareTo(instanceFeature) != 0)
			i++;
		if(branches[i] == null)
			return null;
		return branches[i].decide(instance);
	}
	
	
	/**
	 * Builds a decision tree from a training set; splits at a random feature
	 * 
	 * @param 
	 * trainingset training set to be learned from
	 * 
	 * @return decision tree for training set
	 */
	public Node buildRandom(Trainingset trainingset){
		if(trainingset.getEntropy(trainingset.getClassCount()) == 0.0){
			return new Leaf(trainingset.getInstance(0).getClassification());
		}
		else{
			int splitAt = (int) (Math.random()*trainingset.getInstance(0).getDimension());
			ArrayList<Trainingset> split = trainingset.splitAtFeature(splitAt);
			
			feature = splitAt;
			domain = trainingset.getDomain(splitAt);
			
			branches = new Node[split.size()];
			
			for(int i = 0; i < split.size(); i++){
				if(split.get(i).isEmpty()){
					branches[i] = null;
				}
				else{
					branches[i] = (new Tree()).buildRandom(split.get(i));
				}
			}
			
			return this;
		}
	}
	
	/**
	 * Builds a decision tree from a training set; splits with ID3 strategy
	 * 
	 * @param 
	 * trainingset training set to be learned from
	 * 
	 * @return decision tree for training set
	 */
	public Node buildID3(Trainingset trainingset){
		if(trainingset.isHomogen()){
			return new Leaf(trainingset.getInstance(0).getClassification());
		}
		else{
			int splitAt = selectOptimalFeature(trainingset);
			ArrayList<Trainingset> split = trainingset.splitAtFeature(splitAt);
			
			feature = splitAt;
			domain = trainingset.getDomain(splitAt);
			
			branches = new Node[split.size()];
			
			for(int i = 0; i < split.size(); i++){
				if(split.get(i).isEmpty()){
					branches[i] = null;
				}
				else{
					branches[i] = (new Tree()).buildID3(split.get(i));
				}
			}
			
			return this;
		}
	}
	
	/**
	 * Selects the optimal feature to split at with computation of entropy
	 * @param 
	 * trainingset training set to be split
	 * 
	 * @return index of feature to be splitted on
	 */
	private int selectOptimalFeature(Trainingset trainingset){
		double entropy0 = trainingset.getEntropy(trainingset.getClassCount());
		int bestFeature = 0;
		double bestGain = 0;
		
		for(int i = 0; i < trainingset.getFeatureCount(); i++){
			ArrayList<Trainingset> split = trainingset.splitAtFeature(i);
			double entropySplitSum = 0;
			for(Trainingset t : split){
				double test = t.getEntropy(trainingset.getClassCount());
				entropySplitSum += (double)(t.size())/(double)(trainingset.size())*test;
			}
			if(entropy0 - entropySplitSum > bestGain){
				bestFeature = i;
				bestGain = entropy0 - entropySplitSum;
			}
		}
		
		return bestFeature;
	}
	
	public String toString(){
		String res = feature + " --> \n";
		for(Node n : branches){
			if(n != null)
				res = res + "\t" + n.toString();
			else
				res = res + "\t" + "null";
		}
		return res;
	}
	
}
