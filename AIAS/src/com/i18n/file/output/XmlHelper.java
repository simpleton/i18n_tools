package com.i18n.file.output;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHelper implements IOutputHelper{
	private static final String RESOURCES = "resources";
	private static final String STRING_KEY = "string";
	private static final String STRING_ATTR = "name";
	
	private volatile boolean debug;
	private final String outFilePath;
	
	private Set<String> key_set = new HashSet<String>();
	private XmlHelper(String filepath, boolean debug) throws ParserConfigurationException, TransformerException, IOException {
		this.outFilePath = filepath;
		this.debug = debug;
		
		checkXmlFile(filepath);
	}
	private boolean checkXmlFile(String filepath) throws ParserConfigurationException, TransformerException, IOException {
		File file = new File(filepath);
		if (file.exists()) {
			
			return true;
		} else {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			// resources elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(RESOURCES);
			doc.appendChild(rootElement);
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result;
			File out_file = new File(outFilePath);
			File parent = new File(out_file.getParent());
			if (!parent.exists()) {
				parent.mkdirs();
			}
			if (!out_file.exists()) {
				out_file.createNewFile();
			}
			result = new StreamResult(out_file);		
			
					 
			transformer.transform(source, result);
		}
		return true;
	}
	
	private boolean key_exist(String key_name) throws ParserConfigurationException, SAXException, IOException {
		if (key_set.isEmpty()) {
			load_xml_key();
		}
		if (key_set.contains(key_name)) {
			return true;
		}
		return false;
	}
	
	private void load_xml_key() throws ParserConfigurationException, SAXException, IOException {
		Document doc = parseDoc();
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
	 
		NodeList nList = doc.getElementsByTagName(STRING_KEY);
		for (int i = 0; i < nList.getLength(); i++) {
			
			Node nNode = nList.item(i);
			if (nNode instanceof Element) {
				String name = ((Element) nNode).getAttribute(STRING_ATTR);
				key_set.add(name);
			}
		}
	}
	
	public void write(SimpleEntry<String, String> in) throws ParserConfigurationException, TransformerException, SAXException, IOException {
		if (!key_exist(in.getKey())) {
			key_set.add(in.getKey());
		
			Document doc = parseDoc();
			
			Node resources = doc.getFirstChild();
		
			Element string = doc.createElement(STRING_KEY);
			string.setAttribute(STRING_ATTR, in.getKey());
			string.appendChild(doc.createTextNode(in.getValue()));
			resources.appendChild(string);		
			
			// write the content into xml file
			writeDoc(doc);
		}
	}
	
	public void write(HashMap<String, String> entrys) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Document doc = parseDoc();
		Node resources = doc.getFirstChild();
		
		for (Map.Entry<String, String> entry : entrys.entrySet()) {
			if (!key_exist(entry.getKey())) {
				key_set.add(entry.getKey());
				Element string = doc.createElement(STRING_KEY);
				string.setAttribute(STRING_ATTR, entry.getKey());
				string.appendChild(doc.createTextNode(entry.getValue()));
				resources.appendChild(string);
			}
		}
		
		writeDoc(doc);
	}
	
	private void writeDoc(Document doc)
			throws TransformerFactoryConfigurationError,TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result;
		if (debug) {
			result = new StreamResult(System.out);			
		} else {
			result = new StreamResult(new File(outFilePath));	
		}		
		 
		transformer.transform(source, result);
	}
	private Document parseDoc() throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc = docBuilder.parse(outFilePath);
		return doc;
	}

	
	public static class Builder {
		private boolean debug;
		private String filepath;
		
		public Builder setDebug(boolean debug) {
			this.debug = debug;
			return this;
		}
		
		public Builder setFilePath(String path) {
			this.filepath = path;
			return this;
					
		}
		
		public XmlHelper build() throws ParserConfigurationException, TransformerException, IOException {
			ensureSaneDefaults();
			return new XmlHelper(filepath, debug);
		}
		
		private void ensureSaneDefaults() {
			if (filepath == null) {
				filepath = "./string.xml";
			}
		}
	}
}
