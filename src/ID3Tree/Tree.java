package ID3Tree;
import java.util.ArrayList;


public class Tree extends Node {

	private int feature;
	private Domain domain;
	private Node[] branches;
	
	public Tree(){
	}

	@Override
	public String decide(Instance instance) {
		String instanceFeature = instance.getFeature(feature);
		int i = 0;
		while(domain.getValue(i) != instanceFeature)
			i++;
		if(branches[i] == null)
			return null;
		return branches[i].decide(instance);
	}
	
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
	
	private int selectOptimalFeature(Trainingset trainingset){
		double entropy0 = trainingset.getEntropy(trainingset.getClassCount());
		int bestFeature = 0;
		double bestGain = 0;
		
		for(int i = 0; i < trainingset.getFeatureCount(); i++){
			ArrayList<Trainingset> split = trainingset.splitAtFeature(i);
			double entropySplitSum = 0;
			for(Trainingset t : split){
				double test = t.getEntropy(trainingset.getClassCount());
				entropySplitSum += test;
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
