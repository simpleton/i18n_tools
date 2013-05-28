/**
 * 
 */
package com.i18n.file.output;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

/**
 * @author simsun
 *
 */
public interface IOutputHelper {
	public void write(HashMap<String, String> entrys) throws Exception;
	public void write(SimpleEntry<String, String> in) throws Exception; 
}
