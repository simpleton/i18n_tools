package com.i18n.main;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.i18n.file.FileHelper;

public class main {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ParseException, IOException {
		FileInputStream in = new FileInputStream("test/testcase.java");
		FileHelper file_help = new FileHelper();
		List<File> fileList = file_help.getFileList("src/", "java");
		for (File file : fileList) {
			System.out.println(file.getAbsolutePath());
		}
        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }

        // visit and print the methods names
        new MethodVisitor().visit(cu, null);
        
        
	}
	
	private static class MethodVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this 
            // CompilationUnit, including inner class methods
            System.out.println(n.getName());
            super.visit(n, arg);
        }
        
        @Override
        public void visit(StringLiteralExpr n, Object arg) {
        	System.out.println(n.getValue());
        	super.visit(n, arg);
        }
    }

}
