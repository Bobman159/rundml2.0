package com.bobman159.rundml.core.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bobman159.rundml.core.exprtypes.Column;

/**
 * A utility class for use in building the SQL statements for RunDML.
 * 
 *
 */
public class RunDMLUtils {

	/**
	 * Returns a qualified table name (schema.tableName)
	 * @param schema
	 * @param tbName
	 */
	public static String qualifiedTbName(String schema,String tbName) {
		return String.format("%s.%s", schema,tbName);
	}
	

//	public String csvList(Collection collection) {
	public static String csvList(Stream<Column> list) {
		
//		return (String) collection.stream()
//				  .map(Object::toString)
//				  .collect(Collectors.joining(","));
		
		return list.map(Object::toString)
		    .collect(Collectors.joining(","));
//		return list.collect(Collectors.joining(","));
		
//		list.forEach(col -> {
//			if list.
//			if (col.getName().equalsIgnoreCase("dfltInteger")) {
//				Assert.assertEquals("DFLTINTEGER",col.getName());
//				Assert.assertEquals(Types.INTEGER,col.getType());				
//			} else if (col.getName().equalsIgnoreCase("dfltnumber72")) {
//				Assert.assertEquals("DFLTNUMBER72",col.getName());
//				Assert.assertEquals(Types.DECIMAL,col.getType());				
//			} else if (col.getName().equalsIgnoreCase("notnullchar")) {
//				Assert.assertEquals("NOTNULLCHAR",col.getName());
//				Assert.assertEquals(Types.CHAR,col.getType());
//			} else {
//				fail("Column: " + col.getName() + 
//					 " was not found in table definition");
//			}
//		});
		
	}
}
