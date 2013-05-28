/**
 * 
 */
package com.i18n.file.output;

import java.io.IOException;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

/**
 * @author simsun
 *
 */
public interface IOutputHelper {
	public void write(HashMap<String, String> entrys) throws Exception;
	public void write(SimpleEntry<String, String> in) throws Exception; 
}
