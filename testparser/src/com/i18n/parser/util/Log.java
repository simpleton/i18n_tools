/**
 * 
 */
package com.i18n.parser.util;

/**
 * @author simsun
 *
 */
public class Log implements ILog{
	private boolean debug ;
	
	public Log(boolean debug) {
		this.debug = debug;
	}
	
	public void d(String log) {
		String pref = "[Log]\t time: \t" + System.currentTimeMillis() +"\t\t";
		if (debug) 
			System.out.println(pref + log);
	}
}
