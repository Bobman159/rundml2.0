package com.bobman159.rundml.h2.select.steps;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.tabledef.TableDefinition;

	/**
	 * First Builder Step the Select build steps
	 * Next Step available: FromStep 
	 * 
	 */
public interface H2SelectListStep {

	/**
	 * Specifies a SELECT * sql clause which returns all columns defined in a table.
	 * @return the list build step
	 */
	public H2SelectListStep selectStar();
	
	/**
	 * Specifies a SELECT sql clause.
	 * @return the list build step
	 */
	public H2SelectListStep select();
	
	/**
	 * Specifies a SELECT clause which will explicitly select all the columns defined in a 
	 * <code>TableDefinition</code>.
	 * @param tbDef  the table defintion 
	 * @return the list build step
	 */
	public H2SelectFromStep select(TableDefinition tbDef);
	
	/**
	 * Specifies a ALL clause.
	 * @return the list build step
	 */
	public H2SelectListStep all();
	
	/**
	 * Specifies a DISTINCT clause.
	 * @return the list build step
	 */
	public H2SelectListStep distinct();
	
	/**
	 * Specifies a TOP clause.
	 * @param topExpr an expression that limits the number of results returned
	 * @return the list build step
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 */
	public H2SelectListStep top(IExpression topExpr);
	
	/**
	 * Specifies a select expression (eg. column name or literal) in the SELECT clause.
	 * @param expr an expression to include in the select statement
	 * @return the list build step
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 */
	public H2SelectListStep selectExpression(IExpression expr);
    
	/**
	 * Specifies a FROM clause which specifies the schema and table name to execute 
	 * the SELECT statement against.
	 * @param schema the database schema name of the table
	 * @param tbName the database table name

	 * @return the list build step
	 */
	public H2SelectFromStep from(String schema, String tbName);

}
