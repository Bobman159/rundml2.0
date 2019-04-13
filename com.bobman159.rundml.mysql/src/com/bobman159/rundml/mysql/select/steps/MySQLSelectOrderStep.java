package com.bobman159.rundml.mysql.select.steps;

import java.util.List;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.OrderByExpression;

/**
 * The third step in the SELECT build process. Allows for ordering and grouping of
 * SELECT results.
 *
 */
public interface MySQLSelectOrderStep {
	
	/**
	 * Specifies a GROUP BY clause to group the results by the given expression(s)
	 * <p>Note: MySQL syntax doesn't appear to allow for literal number values ie 
	 * "GROUP BY 10000" but does allow for column position IE "SELECT 100000 ...
	 * GROUP BY 1"
	 * @param groupByExprs expressions specifying the grouping
	 * @return the ordering build step
	*/
	public MySQLSelectOrderStep groupBy(IExpression... groupByExprs);
		
	/**
	 * Specifies an ORDER BY clause to sort the results by the given expression(s)
	 * @param orderByExprs expressions specifying the sort order
	 * @return the ordering build step
	*/
	public MySQLSelectOrderStep orderBy(OrderByExpression... orderByExprs);
		
	/**
	 * Specifies a HAVING clause to filter the row results after a GROUP BY.
	 * This clause is specified after a groupBy()
	 * @param havingExpr expression to filter the rows.
	 * @return the ordering build step
	*/
	public MySQLSelectOrderStep having(Predicate havingExpr);
	
	/**
	 * Specifies a LIMIT clause to limit the number of rows returned by the SELECT
	 * @param limitTerm expression specifying the maximum number of rows to return
	 * @return the ordering build step
	 */
	public MySQLSelectOrderStep limit(IExpression limitTerm);
	
	/**
	 * Specifies a OFFSET clause to specify how many rows are skipped before the LIMIT clause
	 * @param offSet expression specifying how many rows to skip
	 * @return the ordering build step
	 */
	public MySQLSelectOrderStep offset(IExpression offSet);
		
	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectFetchStep#fetch()
	 * @return the results of the SELECT statement
	*/
	public List<String> fetch();

}
