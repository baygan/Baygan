package config;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tools.Pair;

/**
 * This class contains are information and properties that Baygan needs to be
 * able to run successfully. These information contain:<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;(*) Components classes &nbsp;&nbsp;&nbsp; =>
 * &nbsp;&nbsp;&nbsp; conf/BDefaultConfig.xml<br>
 * 
 * @author Salim
 * 
 */
public class BConfiguration {
	public static final String DEFAULT_CONFIG_FILE = "conf/BDefaultConfig.xml";
	/**
	 * Keeps Pairs of String. First objects shows the name of the components and
	 * the second is the class path of its class.
	 */
	private final ArrayList<Pair<String, String>> components;

	public BConfiguration() {
		components = new ArrayList<Pair<String, String>>();
		parseConfig(DEFAULT_CONFIG_FILE);
	}

	/**
	 * Applies the new Config file along side the old one. it replaces the new
	 * configs in the new file with the old ones.
	 * 
	 * @param configAdd
	 */
	public BConfiguration(String configAdd) {
		this();
		applyConfig(configAdd);
	}

	/**
	 * Parses config files and reads informations needed to set parameters and
	 * components and etc.
	 */
	public void parseConfig(String address) {
		/*
		 * contains the root note of the xml's DOM.
		 */
		Document dom = null;
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parse using builder to get DOM representation of the XML file
			dom = db.parse(address);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		readConfigs(dom);
	}

	/**
	 * Applies configurations mentioned in the new file. if duplicated with
	 * elder configs it will be replaced with the old one, if not it will be
	 * added to proper list.<br>
	 * <br>
	 * <b>CAUTION:</b><br>
	 * If the config's Type is of an undefined or user defined type it will not
	 * be added to any lists and it will simply be igonred.
	 * 
	 * @param address
	 */
	public void applyConfig(String address) {
		parseConfig(address);
	}

	private void readConfigs(Document dom) {
		Element docEle = dom.getDocumentElement();
		ArrayList<Node> props = getChildNodes(docEle, "Config");
		for (Node node : props) {
			if (getFirstChildNode(node, "Type").getTextContent().equals(
					"ComponentClass")) {
				addComponentConfig(new Pair<String, String>(getFirstChildNode(
						node, "Name").getTextContent(), getFirstChildNode(node,
						"Class").getTextContent()));
			}
		}
	}

	@SuppressWarnings("unused")
	private Node getChildNode(ArrayList<Node> nodes, String name) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getNodeName().equals(name))
				return nodes.get(i);
		}
		return null;
	}

	/**
	 * Finds child nodes of a DOM node which are neither comments nor texts.
	 * 
	 * @param elelement
	 * @return
	 */
	@SuppressWarnings("unused")
	private ArrayList<Node> getValidChilNodes(Node element) {
		ArrayList<Node> nodes = new ArrayList<Node>(4);
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (!childNodes.item(i).getNodeName().equals("#text")) {
				nodes.add(childNodes.item(i));
			}

		}
		return nodes;
	}

	/**
	 * Finds first child node of a DOM node which has input String as name.s
	 * 
	 * @param elelement
	 * @return
	 */
	private Node getFirstChildNode(Node element, String childName) {
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i).getNodeName().equals(childName))
				return childNodes.item(i);
		}
		return null;
	}

	/**
	 * Finds all child nodes of a DOM node which have input String as name.s
	 * 
	 * @param elelement
	 * @return
	 */
	private ArrayList<Node> getChildNodes(Node el, String childName) {
		ArrayList<Node> nodes = new ArrayList<Node>(3);
		NodeList childNodes = el.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i).getNodeName().equals(childName))// found
																	// match
				nodes.add(childNodes.item(i));
		}
		return nodes;
	}

	public ArrayList<Pair<String, String>> getComponents() {
		return components;
	}

	/**
	 * Returns the class path of the component. it will return null if there
	 * were no config read from config files with the input component name.
	 * 
	 * @param compName
	 * @return
	 */
	public String getComponentClassPath(String compName) {
		for (Pair<String, String> comp : components) {
			if (comp.getFirst().equals(compName))
				return comp.getSecond();
		}
		return null;
	}

	/**
	 * Avoids duplicate configs in the component's list. Checks if the the
	 * config already exists it simply removes the old one. <br>
	 * it is used in readCondigs function in BConfiguration.java class.
	 * 
	 * @param compPair
	 */
	private void addComponentConfig(Pair<String, String> compPair) {
		int index = -1;
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).getFirst().equals(compPair.getFirst())) {
				// Found and older config in list
				index = i;
				break;
			}
		}

		if (index != -1)// There exists an older config in list
			components.remove(index);// removes the old one
		components.add(compPair);
	}

}