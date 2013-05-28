/**
 * 
 */
package com.i18n.parser;

/**
 * @author simsun
 *
 */
public enum StringFilterType {
	/**
	 * filter all string
	 */
	ALL("*"),
	/**
	 * only filter chinese character 
	 */
	CH("[\u4e00-\u9fa5]"),
	/**
	 * only filter english character
	 */
	EN("\\w");
	
	private String regx;
	
	private StringFilterType(String reg) {
		this.regx  = reg;
	}
	
	public String getRegex() {
		return this.regx;
	}	
}
