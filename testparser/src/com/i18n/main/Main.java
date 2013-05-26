package com.i18n.main;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.xml.sax.SAXException;

import com.i18n.file.FileHelper;
import com.i18n.file.XmlHelper;
import com.i18n.file.pinyinHelper;

public class Main {
	private static final String IN_FOLDER = "test/";
	private static final String OUT_FOLDER = "out/";
	HashMap<String, String> strMap = new HashMap<String, String>();
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
		Main aa = new Main();
	}
	
	public Main() throws ParseException, IOException, ParserConfigurationException, TransformerException, SAXException {
		FileHelper file_help = new FileHelper();
		List<File> fileList = file_help.getFileList(IN_FOLDER, "java");
		xmlhelper = new XmlHelper.Builder()
							.setDebug(false)
							.setFilePath(OUT_FOLDER + "string.xml").build();
		for (File file : fileList) {
			System.out.println("-->parsing:" + file.getAbsolutePath());
			parserFile(file);
		}
		
	}
	
	private class StringVisitor extends VoidVisitorAdapter {
        private static final String prefix = "getContext().getString(R.id.";
        private static final String suffix = ")";
        @Override
        public void visit(StringLiteralExpr n, Object arg) {
        	String str = n.getValue();
        	String key = null;
        	
        	try {
        		key = pinyinHelper.converterEname(str);
				strMap.put(key, str);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
        	
        	n.setValue(new StringBuilder().append(prefix).append(key).append(suffix).toString());
        	super.visit(n, arg);
        }
    }
	
	private void parserFile(File file) throws ParseException, IOException, ParserConfigurationException, TransformerException, SAXException {
		FileInputStream in = new FileInputStream(file.getAbsoluteFile());
		CompilationUnit cu ;
		try {
			cu = JavaParser.parse(in);
		} finally {
			in.close();
		}
		
		// find all hard code string, and put them to strMap
		new StringVisitor().visit(cu, null);
		
		//write to xml file
		xmlhelper.write(strMap);
		//write to java file
		File out = new File(new StringBuilder().append(OUT_FOLDER).append(file.getPath()).toString());
		if (!out.getParentFile().exists()) {
			out.getParentFile().mkdirs();
		}
		if (!out.exists()) {
			out.createNewFile();
		}
		FileOutputStream fw = new FileOutputStream(out);
		try {
			fw.write(cu.toString().getBytes());
		} finally {
			fw.close();
		}
	}

}
