package com.bobman159.rundml.h2.select.steps;

import java.util.List;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.OrderByExpression;

/**
 * The third step in the SELECT build process.  Allows for ordering and grouping of 
 * SELECT results.  
 * 
 * Consult the H2 documentation for more detailed information on specific H2 SELECT syntax.
 *
 */
public interface H2SelectOrderStep {
	
	/**
	 * Specifies a GROUP BY clause to group the results by the given expression(s)
	 * @param groupByExpr expressions specifying the grouping
	 * @return the ordering build step
	 */
	public H2SelectOrderStep groupBy(IExpression... groupByExpr);
	
	/**
	 * Specifies a ODRER BY clause to sort the results by the given expression(s)
	 * @param orderByExpr expressions specifying the sort order
	 * @return the ordering build step
	 */

	public H2SelectOrderStep orderBy(OrderByExpression... orderByExpr);

	/**
	 * Specifies a HAVING clause to filter the row results after a GROUP BY. 
	 * This clause is specified after a groupBy() 
	 * @param havingPred expression(s) to filter the rows.
	 * @return the ordering build step
	 */
	public H2SelectOrderStep having(Predicate havingPred);
	
	/**
	 * Specifies a LIMIT clause to limit the number of rows returned by the SELECT
	 * @param limitTerm expression specifying the maximum number of rows to return
	 * @return the ordering build step
	 */
	public H2SelectOrderStep limit(IExpression limitTerm);
	
	/**
	 * Specifies a OFFSET clause to specify how many rows are skipped before the LIMIT clause
	 * @param offSet expression specifying how many rows to skip
	 * @return the ordering build step
	 */
	public H2SelectOrderStep offset(IExpression offSet);
	
	/**
	 * @see com.bobman159.rundml.h2.select.steps.H2SelectFetchStep#fetch()
	 * @return the results from the SELECT statement
	 */
	public List<String> fetch();

}
