package Main;
import java.util.ArrayList;

import DataTransfer.InstanceReader;
import ID3Tree.Trainingset;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InstanceReader input = new InstanceReader("./car.data");
		Trainingset t = input.readInstances();
		System.out.println(t.getRandomInstance());
	}

}