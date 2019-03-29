package com.bobman159.rundml.compat.select.steps;

import java.util.List;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.OrderByExpression;

/**
 * Second build for an SQL SELECT statement
 * Next Step OrderingStep OR FetchStep
 * 
 */
public interface CompatibleSelectFromStep {

	/**
	 * Specifies a GROUP BY clause to group the results by the given expression(s)
	 * @param groupByExpr - expressions specifying the grouping
	 * @return - the ordering build step
	 */
	CompatibleSelectOrderStep groupBy(IExpression... groupByExpr);
	
	/**
	 * Specifies an ORDER BY clause to sort the results by the given expression(s)
	 * @param orderByExpr - expressions specifying the sort order
	 * @return - the ordering build step
	 */
	CompatibleSelectOrderStep orderBy(OrderByExpression... orderByExprs);
	
	/**
	 * Specifies a WHERE clause to specify what results are returned by the SELECT
	 * 
	 * @param pred - predicate clause for the SELECT
	 * @return - the ordering build step
	 */
	CompatibleSelectOrderStep where(Predicate pred);
	
	/**
	 * @see com.bobman159.rundml.compat.select.steps.CompatibleSelectFetchStep#fetch()
	 * @return
	 */
	List<String> fetch();
	
}
