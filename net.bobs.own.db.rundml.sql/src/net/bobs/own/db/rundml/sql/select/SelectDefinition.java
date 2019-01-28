package net.bobs.own.db.rundml.sql.select;

import java.util.Properties;

import net.bobs.own.db.rundml.sql.expressions.ExpressionList;


/**
 * Defines the metadata/information to create an SQL SELECT statement.  Based on the 
 * idea of the DefaultQueryMetaData class in QueryDSL.  Used by <code>SelectStatement</code> 
 * to identify which clauses were specified for a SELECT statement
 * 
 * @author Robert Anderson
 *
 */
class SelectDefinition {

	Properties selectProps;
	ExpressionList selectExpressions;
	
	/**
	 * Create the defintion object for an SQL SELECT statement.
	 */
	public SelectDefinition() {
		
		selectProps = new Properties();
		selectExpressions = new ExpressionList();
		selectProps.put(SQLStatementConstants.SELECT_EXPR, selectExpressions);
		
	}
	
	/**
	 * 
	 * Set an option for the SQL Select statement.
	 * 
	 * @param id - identifier for the option
	 * @param obj - the option to be specified
	 */
	public void setOption(String id, Object obj) {
		
		selectProps.put(id, obj);
	}
	
	/**
	 * Return the object for the specified key value.
	 * @see java.util.Hashtable#get
	 * @param id - the key identifier
	 * @return - the object for the identifer, null if no value present
	 */
	public Object getOption(String id) {
		
		Object returnObj = null;
		
		if (selectProps.containsKey(id)) {
			returnObj = selectProps.get(id);
		}
		
		return returnObj;
	}
	
	
	
}
