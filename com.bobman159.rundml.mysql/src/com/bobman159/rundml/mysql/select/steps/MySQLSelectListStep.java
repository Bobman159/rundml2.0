package com.bobman159.rundml.mysql.select.steps;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.tabledef.TableDefinition;

/**
 * First Builder step in the SELECT build steps.  
 * Specifies the expressions to be selected for the statement and the table to be selected
 * from.
 * 
 * Consult the MySQL documentation for more information regarding the specific MySQL
 * syntax.
 * 
 */
public interface MySQLSelectListStep {
	
	/**
	 * Specifies a SELECT * sql clause which returns all columns defined in a table.
	 * @return - the list build step
	 */
	public MySQLSelectListStep selectStar();
	
	/**
	 * Specifies a SELECT sql clause
	 * return the list build step.
	 */
	public MySQLSelectListStep select();
	
	/**
	 * Specifies a SELECT clause which will explicitly select all the columns defined in 
	 * a <code>TableDefinition</code>
	 * @return - the list build step.
	 */
	public MySQLSelectFromStep select(TableDefinition tbDef);
	
	/**
	 * Specifies a ALL clause
	 * @return - the list build step
	 */
	public MySQLSelectListStep all();
	
	/**
	 * Specifies a DISTINCT clause.
	 * @return - the list build step
	 */

	public MySQLSelectListStep distinct();
	
	/**
	 * Specifies SQL_SMALL_RESULT clause in the SQL SELECT statement.
	 * From the MySQL SELECT documentation this tells the optimizer that the result 
	 * set has a small number of rows respectively.
	 * 
	 * @return - the select list step builder
	 */
	public MySQLSelectListStep smallResult();
	
	/**
	 * Specifies SQL_BIG_RESULT clause in the SQL SELECT statement.
	 * From the MySQL SELECT documentation this tells the optimizer that the result set
	 * has a large number of rows respectively.
	 * 
	 * @return - the select list step builder
	 */
	public MySQLSelectListStep bigResult();
	
	/**
	 * Specifies SQL_BUFFER_RESULT clause in the SQL SELECT statement.
	 * From the MySQL SELECT SELECT documentation this forces the result to be put into
	 * a temporary table.  This helps MySQL free the table locks early and helps in 
	 * cases where it sends a long time to send the result set to the client.
	 * 
	 * @return - select list step builder
	 */
	public MySQLSelectListStep bufferResult();
		
	/**
	 * Specifies a select expression (eg column name or literal) in the SELECT clause.
	 * @return - the list build step
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 */

	public MySQLSelectListStep selectExpression(IExpression expr);
	
	/**
	 * Specifies a FROM clause which specifies the schema and table name to execute the
	 * SELECT statement against.
	 * @param schema - the database schema name of the table
	 * @param tbName - the database table name
	 * @return - the list build step
	 */
	public MySQLSelectFromStep from(String schema, String tbName);
	


}
