package com.i18n.main;

import japa.parser.ParseException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.i18n.file.output.IOutputHelper;
import com.i18n.file.output.XmlHelper;
import com.i18n.keymaker.pinyinHelper;
import com.i18n.parser.StringFilterType;
import com.i18n.parser.StringParser;

public class Main {
	
	XmlHelper xmlhelper;
	
	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws TransformerException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 */
	public static void main(final String[] args) throws ParseException, IOException, ParserConfigurationException, TransformerException, SAXException {
		IOutputHelper out_helper = new XmlHelper.Builder()
												.setDebug(false)
												.setFilePath("out/string.xml").build();
		StringParser parser = new StringParser.Builder().setDebug(true)
														.setInputFolder("test/")
														.setOutputFolder("out/")
														.setFilterType(StringFilterType.CH)
														.setKeyMakerHelper(new pinyinHelper())
														.setOutputClient(out_helper).build();
		parser.run();
	}
}
