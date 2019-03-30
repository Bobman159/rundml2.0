package com.bobman159.rundml.compat.select.steps;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.tabledef.TableDefinition;

/**
 * First Builder step in the SELECT build steps.  
 * Specifies the expressions to be selected for the statement and the table to be selected
 * from.
 * 
 */
public interface CompatibleSelectListStep {
	
	/**
	 * Specifies a SELECT * sql clause which returns all columns defined in a table.
	 * @return - the list build step
	 */
	public CompatibleSelectListStep selectStar();
	
	/**
	 * Specifies a SELECT sql clause
	 * return the list build step.
	 */
	public CompatibleSelectListStep select();
	
	/**
	 * Specifies a SELECT clause which will explicitly select all the columns defined in 
	 * a <code>TableDefinition</code>
	 * @return - the list build step.
	 */
	public CompatibleSelectFromStep select(TableDefinition tbDef);
	
	
	/**
	 * Specifies a ALL clause
	 * @return - the list build step
	 */
	public CompatibleSelectListStep all();
	
	/**
	 * Specifies a DISTINCT clause.
	 * @return - the list build step
	 */

	public CompatibleSelectListStep distinct();
	
	/**
	 * Specifies a select expression (eg column name or literal) in the SELECT clause.
	 * @return - the list build step
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 */

	public CompatibleSelectListStep selectExpression(IExpression expr);
	
	/**
	 * Specifies a FROM clause which specifies the schema and table name to execute the
	 * SELECT statement against.
	 * @param schema - the database schema name of the table
	 * @param tbName - the database table name
	 * @return - the list build step
	 */
	public CompatibleSelectFromStep from(String schema, String tbName);

}
