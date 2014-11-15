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
import ID3Tree.Tree;

public class TreeToXMLWriter {

	private Document doc;
	private Transformer transformer;
	private StreamResult result;

	public TreeToXMLWriter(String destination) {

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
	}

	public void writeXMLFile(Tree tree) {

		System.out.println("Writing decision tree to file...");

		iterateNodes(null, null, tree);

		try {
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
		} catch (TransformerException te) {
			System.out.println("Oops! Something went wrong!");
			te.printStackTrace();
		}

		System.out.println("All went smoothly! XML file written.");

	}

	private void iterateNodes(Element parent, Tree parentNode, Node node) {

		Element element = transformNode(parent, parentNode, node);

		if (node instanceof Leaf)
			return;

		for (Node n : ((Tree) node).getBranches()) {
			iterateNodes(element, (Tree) node, n);
		}

	}

	private Element transformNode(Element parentElement, Tree parentNode, Node n) {

		Element element;
		if (parentElement == null)
			element = doc.createElement("tree");
		else
			element = doc.createElement("node");

		element.setAttribute("classes",
				parseHashMapToString(n.classMemberCount));
		element.setAttribute("entropy", String.valueOf(n.entropy));

		if (n instanceof Leaf)
			element.setTextContent(n.toString());

		if (parentNode != null) {
			// find domain of parent node
			Domain d = parentNode.getDomain();
			element.setAttribute(d.getName(), d.getValue(n.splitValue));
		}

		if (parentElement == null)
			doc.appendChild(element);
		else
			parentElement.appendChild(element);

		return element;
	}

	private static String parseHashMapToString(
			HashMap<String, Integer> classMemberCount) {

		String classes = "";

		for (Entry<String, Integer> entry : classMemberCount.entrySet()) {
			classes += entry.getKey() + ":" + entry.getValue() + ",";
		}
		// remove last ','
		classes = classes.substring(0, classes.length() - 1);

		return classes;
	}
}
