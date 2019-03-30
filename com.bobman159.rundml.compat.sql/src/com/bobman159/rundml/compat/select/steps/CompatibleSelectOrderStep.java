package com.bobman159.rundml.compat.select.steps;

import java.util.List;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.OrderByExpression;

/**
 * The third step in the SELECT build process. Allows for ordering and grouping of
 * SELECT results.
 *
 */
public interface CompatibleSelectOrderStep {

	/**
	 * Specifies a GROUP BY clause to group the results by the given expression(s)
	 * @param groupByExpr - expressions specifying the grouping
	 * @return - the ordering build step
	 */
	public CompatibleSelectOrderStep groupBy(IExpression... groupByExprs);
	
	/**
	 * Specifies an ORDER BY clause to sort the results by the given expression(s)
	 * @param orderByExpr - expressions specifying the sort order
	 * @return - the ordering build step
	 */
	public CompatibleSelectOrderStep orderBy(OrderByExpression... orderByExprs);
	
	/**
	 * Specifies a HAVING clause to filter the row results after a GROUP BY.
	 * This clause is specified after a groupBy()
	 * @param havingExpr - expression to filter the rows.
	 * @return - the ordering build step
	 */
	public CompatibleSelectOrderStep having(Predicate havingExpr);
	
	/**
	 * @see com.bobman159.rundml.compat.select.steps.CompatibleSelectFetchStep#fetch()
	 * @return
	 */
	public List<String> fetch();
}
