package ID3Tree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Trainingset {

	private ArrayList<Instance> instances;
	private ArrayList<Domain> domains;
	private ArrayList<String> classes;
	
	public Trainingset(){
		instances = new ArrayList<Instance>();
		domains = new ArrayList<Domain>();
		classes = new ArrayList<String>();
	}
	
	public Trainingset(ArrayList<Domain> domains, ArrayList<String> classes){
		this.domains = domains;
		this.classes = classes;
		instances = new ArrayList<Instance>();
	}
	
	public void addInstance(Instance i){
		instances.add(i);
	}
	
	public void setDomains(ArrayList<Domain> domains){
		this.domains = domains;
	}
	
	public void setClasses(ArrayList<String> classes){
		this.classes = classes;
	}
	
	public int getClassCount(){
		return classes.size();
	}
	
	public int getFeatureCount(){
		return domains.size();
	}
	
	public Domain getDomain(int i){
		return domains.get(i);
	}
	
	public Instance getInstance(int i){
		return instances.get(i);
	}
	
	public Instance getRandomInstance(){
		return instances.get((int) (Math.random()*instances.size()));
	}
	
	public boolean isEmpty(){
		return instances.isEmpty();
	}
	
	public int size(){
		return instances.size();
	}
	
	public boolean isHomogen(){
		String classification = instances.get(0).getClassification();
		for(int i = 0; i < instances.size(); i++){
			if(classification != instances.get(i).getClassification())
				return false;
		}
		return true;
	}
	
	public double getEntropy(int classCount){
		HashMap<String,Integer> classes = new HashMap<String,Integer>();
		for(Instance i : instances){
			if(classes.containsKey(i.getClassification())){
				classes.put(i.getClassification(), classes.get(i.getClassification()) + 1);
			}
			else
				classes.put(i.getClassification(), 1);
		}
		
		double entropy = 0;
		Collection<Integer> values = classes.values();
		for(Integer value : values){
			double proportion = (double)(value)/(double)(instances.size());
			entropy += -1*proportion*Math.log(proportion)/Math.log(classCount);
		}
		
		return entropy;
	}
	
	public ArrayList<Trainingset> splitAtFeature(int feature){
		ArrayList<Trainingset> res = new ArrayList<Trainingset>();
		for(int i = 0; i < domains.get(feature).size(); i++)
			res.add(new Trainingset(domains, classes));
		
		for(Instance i : instances){
			String instanceFeature = i.getFeature(feature);
			int index = 0;
			while(domains.get(feature).getValue(index) != instanceFeature)
				index++;
			res.get(index).addInstance(i);
		}
		
		return res;
	}
	
}