package com.i18n.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
	private List<File> fileList = new ArrayList<File>();
	
	private void getFile(String root) {
		File dir = new File(root);
		File files[] = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					getFile(file.getPath());
				} else {
					fileList.add(file);
				}
			}
		}
	}

	private void getFile(String root, String suffix) {
		File dir = new File(root);
		File files[] = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					getFile(file.getPath() , suffix);
				} else {
					if (checkSuffix(file, suffix)) {
						fileList.add(file);
					} 
				}
			}
		}
		
	}
	
	public List<File> getFileList(String rootFolder) {
		fileList.clear();
		getFile(rootFolder);
		return fileList;
	}
	
	public List<File> getFileList(String rootFolder, String suffix) {
		fileList.clear();
		getFile(rootFolder, suffix);
		return fileList;
	}
	
	private boolean checkSuffix(File file, String suffix) {
		String filename = file.getName();
		int i = filename.lastIndexOf('.');
		if (suffix.equalsIgnoreCase(filename.substring(i + 1))) {
			return true;
		} else {
			return false;
		}
	} 
	
}
