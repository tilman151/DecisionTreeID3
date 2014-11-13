package ID3Tree;

/**
 * Domain of a feature
 * 
 * @author Tilman & Tim
 *
 */
public class Domain {

	private String name;
	private String[] values;
	
	public Domain(String name, String[] values){
		this.name = name;
		this.values = values;
	}
	
	public String getValue(int index){
		return values[index];
	}
	
	public String getName(){
		return name;
	}
	
	public int size(){
		return values.length;
	}
	
	public String toString(){
		String res = name + "(";
		for(String s : values){
			res += s + ",";
		}
		return res + ")";
	}
	
}
