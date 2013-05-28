/**
 * 
 */
package com.i18n.keymaker;

/**
 * @author simsun
 *
 */
public interface IKeyMakerHelper {
	/**
	 * convert key to unify strategy
	 * @param key original key
	 * @return converted key
	 */
	public String convert(String key) throws Exception;
}
