package Main;
import java.util.ArrayList;

import DataTransfer.InstanceReader;
import DataTransfer.MetaDataReader;
import DataTransfer.TreeToXMLWriter;
import ID3Tree.Domain;
import ID3Tree.Trainingset;
import ID3Tree.Tree;


public class Main {

	public static void main(String[] args) {
		
		InstanceReader input = new InstanceReader("./car.data");
		Trainingset t = input.readInstances();
		
		MetaDataReader meta = new MetaDataReader("./car.c45-names");
		ArrayList<Domain> d = meta.readDomains();
		ArrayList<String> c = meta.readClasses();
		
		t.setClasses(c);
		t.setDomains(d);
		Tree tree = (Tree)(new Tree().buildID3(t));
		//System.out.println(tree);
		
		TreeToXMLWriter writer = new TreeToXMLWriter(t, "result.xml");
		writer.writeXMLFile(tree);
	}

}
