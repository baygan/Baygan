package config;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DocUtils {

	

	public static Node getChildNode(ArrayList<Node> nodes, String name) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getNodeName().equals(name))
				return nodes.get(i);
		}
		return null;
	}

	public static ArrayList<Node> getValidChilNodes(Node el) {
		ArrayList<Node> nodes = new ArrayList<Node>(4);
		NodeList childNodes = el.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (!childNodes.item(i).getNodeName().equals("#text")) {
				nodes.add(childNodes.item(i));
			}

		}
		return nodes;
	}

	public static Node getFirstChildNode(Node el, String childName) {
		NodeList childNodes = el.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i).getNodeName().equals(childName))
				return childNodes.item(i);
		}
		return null;
	}

	public static ArrayList<Node> getChildNodes(Node el, String childName) {
		ArrayList<Node> nodes = new ArrayList<Node>(3);
		NodeList childNodes = el.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i).getNodeName().equals(childName))
				nodes.add(childNodes.item(i));
		}
		return nodes;
	}
}
