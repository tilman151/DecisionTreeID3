
public class Domain {

	private String[] values;
	
	public Domain(String[] values){
		this.values = values;
	}
	
	public String getValue(int index){
		return values[index];
	}
	
	public int size(){
		return values.length;
	}
	
}
