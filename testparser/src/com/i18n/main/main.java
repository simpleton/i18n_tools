package com.i18n.main;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.i18n.file.FileHelper;
import com.i18n.file.XmlHelper;

public class main {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws TransformerException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 */
	public static void main(final String[] args) throws ParseException, IOException, ParserConfigurationException, TransformerException, SAXException {
		
		FileHelper file_help = new FileHelper();
		List<File> fileList = file_help.getFileList("test/", "java");
		for (File file : fileList) {
			System.out.println("-->parsing:" + file.getAbsolutePath());
			parserFile(file.getAbsolutePath());
		}
		XmlHelper xmlhelper= new XmlHelper.Builder()
							.setDebug(false)
							.setFilePath("out/string.xml").build();
		xmlhelper.write(new SimpleEntry<String, String>("name", "你好啦"));
		
	}
	
	private static class StringVisitor extends VoidVisitorAdapter {
        
        @Override
        public void visit(StringLiteralExpr n, Object arg) {
        	System.out.println(n.getValue());
        	super.visit(n, arg);
        }
    }
	
	private static void parserFile(String filepath) throws ParseException, IOException {
		FileInputStream in = new FileInputStream(filepath);
		CompilationUnit cu ;
		try {
			cu = JavaParser.parse(in);
		} finally {
			in.close();
		}
		
		new StringVisitor().visit(cu, null);
		
	}

}
