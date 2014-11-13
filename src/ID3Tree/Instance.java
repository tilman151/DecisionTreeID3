package ID3Tree;
import java.util.ArrayList;


/**
 * Tuple of features and an optional classification
 * 
 * @author Tilman & Tim
 *
 */
public class Instance {

	private ArrayList<String> features;
	private String classification;
	
	public Instance(ArrayList<String> features, String classification){
		this.features = features;
		this.classification = classification;
	}
	
	public String getFeature(int index){
		return features.get(index);
	}
	
	public String removeFeature(int index){
		return features.remove(index);
	}
	
	public int getDimension(){
		return features.size();
	}
	
	public String getClassification(){
		return classification;
	}
	
	public String toString(){
		String res = "Instance( ";
		for(String s : features)
			res += s + ", ";
		res += ") class: " + classification;
		return res;
	}
	
}
