package com.bobman159.rundml.jdbc.mapping;

import java.util.HashMap;

/**
 * A map of class fields for mapping to a table column name using a string
 * key (column name) is not case sensitive. Key and Value types are expected
 * as Strings for this class.  <b>Only the <code>HashMap</code>
 * functions needed for storage of the fields mapping information are 
 * defined</b>
 * example
 * <pre>
* {@code
	CaseInsensitiveFieldsMap<String,String> map = new CaseInsensitiveFieldsMap<String,String>();
	map.put("columnName","nameColumn");
* }
* </pre>
 */
public class CaseInsensitiveFieldsMap<K,V> //extends HashMap<K,V> 
{

	private HashMap<String,String> fieldsMap;
	public CaseInsensitiveFieldsMap() {
		fieldsMap = new HashMap<String,String>();
	}
	
	/**
	 * @see java.util.HashMap#containsKey
	 * @return true if the fields map contains the key, false otherwise
	 */
	public boolean containsKey(String key) {
		return fieldsMap.containsKey(key.toUpperCase());
	}

	/**
	 * @see java.util.HashMap#put(Object, Object)
	 * @param key string column name in table for mapping
	 * @param value string field name in class for mapping 
	 * @return see HashMap javadoc
	 */
	public V put(String key,String value) {
		return (V) fieldsMap.put(key.toUpperCase(), value);
	}
	
	/**
	 * @see java.util.HashMap#get(Object)
	 * @param key column name for the field name to find
	 * @return see HashMap javadoc
	 */
	public String get(String key) {
		return fieldsMap.get(key.toUpperCase());
	}
	
}
