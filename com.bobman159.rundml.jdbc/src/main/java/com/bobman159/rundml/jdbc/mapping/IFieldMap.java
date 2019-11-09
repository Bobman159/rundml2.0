package com.bobman159.rundml.jdbc.mapping;

/**
 * An interface that allows a RunDML table row class (a Java class that will 
 * contain 1 row in database table) to specify an alternative name for a field(s)
 * in the table row class.  By default, RunDML maps a column name in the table to a field name in the
 * table row class by exact match (case insensitive).  In situations 
 * where the column name in a table is not a desirable as field name, the 
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
	 * An example implementation
	 *  <pre>
	 *  {@code
		
		public CaseInsensitiveFieldsMap<String,String> getFieldMappings() {

		CaseInsensitiveFieldsMap<String,String> fields = new CaseInsensitiveFieldsMap<String,String>();
		fields.put("SB","stolenBases");
		fields.put("CS","caughtStealing");
		fields.put("BB","baseOnBalls");		
		fields.put("SO","strikeOuts");
		fields.put("IBB","itentionalBaseBalls");
		fields.put("HBP", "hitByPitch");
		fields.put("SH", "sacBunt");
		fields.put("SF","sacFly");
		fields.put("GIDP", "groundIntoDoublePlay");

		return fields;
	
	}
	}
	</pre>
	 * @return the list of alternate fields for mapping 
	 */
	public CaseInsensitiveFieldsMap<String,String> getFieldMappings();
}
