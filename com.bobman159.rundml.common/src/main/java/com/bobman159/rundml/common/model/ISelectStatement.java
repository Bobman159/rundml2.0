package com.bobman159.rundml.common.model;

import java.util.List;

import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.predicates.IPredicatesList;
import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.Table;

/**
 * Define an SQL SELECT statement
 */
public interface ISelectStatement extends ISQLStatement {

	/**
	 * Specify an ALL clause for the SELECT statement
	 */
	public void setAll();
	
	/**
	 * 
	 * @return true if the SELECT has an ALL clause, false otherwise
	 */
	public boolean hasAll();
	
	/**
	 * Specify a DISTINCT clause for the SELECT statement
	 */
	public void setDistinct();	
	
	/**
	 * 
	 * @return true if the SELECT has a DISTINCT clause, false otherwise
	 */
	public boolean hasDistinct();

	/**
	 * Add a list of table columns for the SELECT statement.  The table 
	 * column names are derived from the field names defined in a Java 
	 * class.
	 * @param clazz the class to derive the column names from
	 * @throws RunDMLException if no matching class field definition is found
	 */
	public void addSelectExpressions(Class<?> clazz) throws RunDMLException; 

	/**
	 * Add a SELECT value to the select model
	 * @param selectExpr the value to be added
	 */
	public void addSelectExpression(ISQLType selectExpr);
	
	/**
	 * Add a list of SQL types (expression or value) for the list of SELECT expressions
	 * @param selectExprs the list of values to be added
	 */
	public void addSelectExpression(ISQLType... selectExprs);

	/**
	 * Add a list of string values to be used as table column names in the list of 
	 * SELECT expression. 
	 * @param columns one or more column names as string values
	 */
	public void addSelectExpression(String... columns);

	/**
	 * 
	 * @return the list of expressions for the SELECT
	 */
	public List<ISQLType> getSelectExpressions();
	
	/**
	 * Add a table name for the FROM clause of the SELECT
	 * @param tableName the table name
	 */
	public void addFrom(Table tableName);
	
	/**
	 * 
	 * @return the table name for the FROM clause
	 */
	public Table getFrom();
	
	/**
	 * Add a list of Predicates (WHERE, AND, OR clauses) for the WHERE clause of the
	 * SELECT
	 * @param predList list of predicates
	 */
	public void setWhere(IPredicatesList predList);	
	
	/**
	 * 
	 * @return the list of predicates in the WHERE clause for the SELECT,
	 * an empty list if no predicates specified
	 */
	public List<IPredicate> getWhere();
	
	/**
	 * Add a list of predicates for the HAVING clause for the SELECT
	 * @param havingList the list of predicates in the HAVING clause
	 */
	public void setHaving(IPredicatesList havingList);	
	
	/**
	 *
	 * @return the list of predicates for the HAVING clause, an empty list if no HAVING clause specified
	 */
	public List<IPredicate> getHavingPredicates();
 	
	/**
	 * Add a list of SELECT value, value, value... to the select model
	 * @param orderByEntries the list of values to be added
	 */
	public void addOrderByEntry(IOrderByEntry... orderByEntries);
	
	/**
	 * 
	 * @return the list of ORDER BY clauses for the select, 
	 * an empty list if no ORDER BY clause specified
	 */
	public List<IOrderByEntry> getOrderBys();
	
	/**
	 * Add a list of values for a GROUP BY clause for the SELECT
	 * @param groupByValues one or more values for the GROUP BY clause
	 */
	public void addGroupByExpression(ISQLType... groupByValues);	
	
	/**
	 * 
	 * @return the list of values in the GROUP BY clause
	 * an empty list if no GROUP BY clause specified
	 */
	public List<ISQLType>getGroupByValues();

}
