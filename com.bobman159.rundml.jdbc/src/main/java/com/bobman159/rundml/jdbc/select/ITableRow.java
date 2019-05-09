package com.bobman159.rundml.jdbc.select;

/**
 * One row in a SQL <code>ResultSet</code>. Implementors of ITableRow 
 * must follow the following conventions:
 * <ul>
 * <li>By default, the class name (Person) for the implementation will be 
 * mapped the matching table name (person).  For multiple table results (JOINS), or situations where
 * or when the table name should not be used then the TableDefinition may be 
 * used to define the desired class name.  The matching for class name to table 
 * name is case insensitive.</li>
 * <li>By default, the column name in a <code>ResultSet</code> will be mapped
 * to the matching class field name (eg "DFLTBOOOLEAN" will be mapped to field
 * "dfltBoolean").  If the default is not desired, then TableDefinition 
 * addMapColumn method may be used to specify the desired field name.  In 
 * situations where a SQL expression (i.e "10 + 10", or 'abc'||'def') is 
 * selected, then the AS clause should be specified for the field name.
 * </li>
 * <li>
 * When mapping SELECT results to the mapping class, RunDML looks for a 
 * defined constructor that matches all the field types
 * </li>
 * </ul>
 *
 *	@see com.bobman159.rundml.core.tabledef.TableDefinition
 */
public interface ITableRow {

}
