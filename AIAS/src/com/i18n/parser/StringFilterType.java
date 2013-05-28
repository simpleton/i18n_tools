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
	CH("*"),
	/**
	 * only filter english character
	 */
	EN("");
	
	private String regx;
	
	private StringFilterType(String reg) {
		this.regx  = reg;
	}
	
	public String getReg() {
		return this.regx;
	}	
}
