package DataTransfer;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ID3Tree.Domain;
import ID3Tree.Leaf;
import ID3Tree.Node;
import ID3Tree.Trainingset;
import ID3Tree.Tree;

public class TreeToXMLWriter {

	private Document doc;
	private Transformer transformer;
	private StreamResult result;
	// TODO find better solution
	private Trainingset t;

	public TreeToXMLWriter(Trainingset t, String destination) {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			transformer = transformerFactory.newTransformer();

		} catch (ParserConfigurationException pce) {
			System.out.println("Oops! Something went wrong!");
			pce.printStackTrace();
		} catch (TransformerConfigurationException tce) {
			System.out.println("Oops! Something went wrong!");
			tce.printStackTrace();
		}

		result = new StreamResult(new File(destination));

		this.t = t;
	}

	public void writeXMLFile(Tree tree) {

		System.out.println("Writing decision tree to file...");

		iterateNodes(null, tree);

		try {
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
		} catch (TransformerException te) {
			System.out.println("Oops! Something went wrong!");
			te.printStackTrace();
		}

		System.out.println("All went smoothly! XML file written.");

	}

	private void iterateNodes(Element parent, Node node) {

		Element element = transformNode(parent, node);

		if (node instanceof Leaf)
			return;

		for (Node n : ((Tree) node).getBranches()) {
			iterateNodes(element, n);
		}

	}

	private Element transformNode(Element parent, Node n) {

		Element element;
		if (parent == null)
			element = doc.createElement("tree");
		else
			element = doc.createElement("node");

		element.setAttribute("classes",
				parseHashMapToString(n.classMemberCount));
		element.setAttribute("entropy", String.valueOf(n.entropy));

		if (n instanceof Leaf)
			element.setTextContent(n.toString());
		else
			element.setAttribute("attr1", ((Tree) n).getDomain().getName());

		if (parent != null) {
			// find domain of parent node
			Domain d = t.getDomain(n.splitFeature);
			element.setAttribute("attr2", d.getValue(n.splitValue));
		}

		if (parent == null)
			doc.appendChild(element);
		else
			parent.appendChild(element);

		return element;
	}

	private static String parseHashMapToString(
			HashMap<String, Integer> classMemberCount) {
		// TODO remove
		if (classMemberCount == null)
			return "Dummy";

		String classes = "";

		for (Entry<String, Integer> entry : classMemberCount.entrySet()) {
			classes += entry.getKey() + ":" + entry.getValue() + ",";
		}
		// remove last ','
		classes = classes.substring(0, classes.length() - 1);

		return classes;
	}
}
