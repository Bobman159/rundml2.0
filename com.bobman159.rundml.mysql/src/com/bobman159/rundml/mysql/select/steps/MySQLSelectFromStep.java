package com.bobman159.rundml.mysql.select.steps;

import java.util.List;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.OrderByExpression;
import com.bobman159.rundml.jdbc.select.ITableRow;

/**
 * Second build for an SQL SELECT statement
 * Next step OrderingStep or FetchStep
 *
 */
public interface MySQLSelectFromStep {
	
	/**
	 * Specifies a GROUP BY clause to group the results by the given expression(s)
	 * @param groupByExpr expressions specifying the grouping
	 * @return the ordering build step
	 */
	MySQLSelectOrderStep groupBy(IExpression... groupByExpr);
	
	/**
	 * Specifies a LIMIT clause to limit the number of rows returned by the SELECT
	 * @param limitTerm expression specifying the maximum number of rows to return
	 * @return the ordering build step
	 */
	MySQLSelectOrderStep limit(IExpression limitTerm);
	
	/**
	 * Specifies an ORDER BY clause to sort the results by the given expression(s)
	 * @param orderByExprs expressions specifying the sort order
	 * @return the ordering build step
	 */
	MySQLSelectOrderStep orderBy(OrderByExpression... orderByExprs);
	
	/**
	 * Specifies a WHERE clause to specify what results are returned by the SELECT
	 * 
	 * @param pred predicate clause for the SELECT
	 * @return the ordering build step
	 */
	MySQLSelectOrderStep where(Predicate pred);
	
	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectFetchStep#fetch()
	 * @return the results of the select statement
	 */
	List<ITableRow> fetch();	

}
