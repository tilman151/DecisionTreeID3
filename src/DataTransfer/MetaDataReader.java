package DataTransfer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ID3Tree.Domain;

/**
 * Reads the possible classes and the domains of the features from a file
 * 
 * @author Tilman & Tim
 *
 */
public class MetaDataReader {

	String name;

	public MetaDataReader() {

	}

	public MetaDataReader(String name) {
		this.name = name;
	}

	public void selectFile(String name) {
		this.name = name;
	}

	/**
	 * Reads the classes from file
	 * 
	 * @return List of classes
	 */
	public ArrayList<String> readClasses() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(name));

			ArrayList<String> res = null;

			try {
				String str;
				while ((str = reader.readLine()) != null) {
					if (str.compareTo("| class values") == 0) {
						reader.readLine();
						res = stringToClasses(reader.readLine());
						break;
					}
				}
				reader.close();
			} catch (IOException e) {
				System.err.println("Error while reading file");
				return null;
			}
			return res;
		} catch (IOException e) {
			System.err.println("No such file: " + name);
			return null;
		}
	}

	private ArrayList<String> stringToClasses(String str) {
		ArrayList<String> res = new ArrayList<String>();

		String[] split = str.split(",");
		res.add(split[0]);
		for (int i = 1; i < split.length; i++)
			res.add(split[i].substring(1));
		return res;
	}

	/**
	 * Reads domains from file
	 * 
	 * @return list of domains
	 */
	public ArrayList<Domain> readDomains() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(name));

			ArrayList<Domain> res = new ArrayList<Domain>();

			try {
				String str;
				while ((str = reader.readLine()) != null) {
					if (str.compareTo("| attributes") == 0) {
						reader.readLine();
						while ((str = reader.readLine()) != null) {
							res.add(stringToDomain(str));
						}
						break;
					}
				}
				reader.close();
			} catch (IOException e) {
				System.err.println("Error while reading file");
				return null;
			}

			return res;
		} catch (IOException e) {
			System.err.println("No such file: " + name);
			return null;
		}
	}

	private Domain stringToDomain(String str) {
		
		String name = str.substring(0, str.indexOf(':')+1).replace(':', ' ').trim();
		String[] split = str.substring(str.indexOf(':')+1).replace('.', ' ').trim().split(",");
		for(int i = 1; i < split.length; i++)
			split[i] = split[i].substring(1);
		return new Domain(name, split);
	}

}
