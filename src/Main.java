import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Domain d0 = new Domain(new String[] {"fast","slow"});
		Domain d1 = new Domain(new String[] {"yes","no"});
		Domain d2 = new Domain(new String[] {"n","n*n"});
		ArrayList<Domain> domains = new ArrayList<Domain>();
		domains.add(d0);
		domains.add(d1);
		domains.add(d2);
		
		ArrayList<String> classes = new ArrayList<String>();
		classes.add("good");
		classes.add("bad");
		
		ArrayList<String> f0 = new ArrayList<String>();
		f0.add("fast");
		f0.add("no");
		f0.add("n");
		Instance i0 = new Instance(f0, "good");
		
		ArrayList<String> f1 = new ArrayList<String>();
		f1.add("fast");
		f1.add("yes");
		f1.add("n*n");
		Instance i1 = new Instance(f1, "good");
		
		ArrayList<String> f2 = new ArrayList<String>();
		f2.add("slow");
		f2.add("no");
		f2.add("n");
		Instance i2 = new Instance(f2, "bad");
		
		ArrayList<String> f3 = new ArrayList<String>();
		f3.add("fast");
		f3.add("no");
		f3.add("n*n");
		Instance i3 = new Instance(f3, "bad");
		
		ArrayList<String> f4 = new ArrayList<String>();
		f4.add("slow");
		f4.add("yes");
		f4.add("n");
		Instance i4 = new Instance(f4, "good");
		
		Trainingset t = new Trainingset();
		t.addInstance(i0);
		t.addInstance(i1);
		t.addInstance(i2);
		t.addInstance(i3);
		t.addInstance(i4);
		t.setClasses(classes);
		t.setDomains(domains);
		
		Node tree = (new Tree()).buildID3(t);
		
		ArrayList<String> ftest = new ArrayList<String>();
		ftest.add("fast");
		ftest.add("yes");
		ftest.add("n*n");
		Instance itest = new Instance(ftest, null);
		
		System.out.println(tree);
		System.out.println(tree.decide(itest));
	}

}
