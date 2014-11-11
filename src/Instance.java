import java.util.ArrayList;


public class Instance {

	private ArrayList<String> features;
	private String classification;
	
	public Instance(ArrayList<String> features, String classification){
		this.features = features;
		this.classification = classification;
	}
	
	public Instance(Instance org){
		this.features = new ArrayList<String>();
		for(int i = 0; i < org.getDimension(); i++)
			this.features.add(org.getFeature(i));
		this.classification = org.getClassification();
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
	
}
