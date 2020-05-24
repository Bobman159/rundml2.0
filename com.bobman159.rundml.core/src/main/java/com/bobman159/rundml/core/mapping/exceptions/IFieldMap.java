package com.bobman159.rundml.core.mapping.exceptions;

import com.bobman159.rundml.core.mapping.FieldMapDefinitionList;

/**
 * An interface that allows a RunDML table row class (a Java class that will 
 * contain 1 row in database table) to specify an alternative name for a field(s)
 * in the table row class.  By default, RunDML maps a column name in the table 
 * to a field name in the table row class by exact match ( where case is ignored).  
 * In situations where the column name in a table is not a desirable as field name, the 
 * table row class <b>must</b> implement this interface and provide a list of column(s) names
 * followed by the field name in the target class to contain the data from that column.
 * 
 * <ul>
 * <li>No type safety checking is done, the table row class is assumed to have defined the correct java types</li>
 * <li>Java field types can be primitive, or Wrappers (Integer,Byte,etc) as long as they match the
 * type for the table column being mapped</li>
 * </ul>
 *
 */
public interface IFieldMap {

	/**
	 * Define and return a list of <code>IAlternateField</code> entries to be used when
	 * mapping the RunDML table row class.  
	 * 
	 * @param fieldList the <code>FieldMapDefinitionList</code> for the class fields
	 * An example implementation
	 *  <pre>
	 *  {@code
		public FieldMapDefinitionList getFieldMappings(FieldMapDefinitionList fieldList) {
	
			fieldList.addDefinition("tableCol1", "classField1");
			fieldList.addDefinition("tableCol2", "classField2");
			fieldList.addDefinition("tableCol3", "classField3");
			fieldList.addDefinition("tableCol4","classField4");
			fieldList.addDefinition("tableCol5", "classField5");
			return fieldList;
		}		
	}
	</pre>
	 * In the example implementation, the first argument for the addDefinition method ("tableCol1") is
	 * the table column name in a database table.  The second argument for the addDefinition method is
	 * the java class field name for the table column data.
	 * 
	 * @return the list of alternate fields for mapping 
	 * @see com.bobman159.rundml.core.mapping.FieldMapDefinitionList
	 */
	public FieldMapDefinitionList getFieldMappings(FieldMapDefinitionList fieldList);
}
