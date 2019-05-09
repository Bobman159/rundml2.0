package com.bobman159.rundml.h2.select.steps;

import java.util.List;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.OrderByExpression;
import com.bobman159.rundml.jdbc.select.ITableRow;

/**
 * Second Builder Step in the Select build steps.
 * Next Step available: OrderingStep OR FetchStep
 *
 */
public interface H2SelectFromStep {
	
	/**
	 * Specifies a GROUP BY clause to group the results by the given expression(s)
	 * @param groupByExpr expressions specifying the grouping
	 * @return the ordering build step
	 */
	H2SelectOrderStep groupBy(IExpression... groupByExpr);
	
	/**
	 * Specifies a ODRER BY clause to sort the results by the given expression(s)
	 * @param orderByExpr expressions specifying the sort order
	 * @return the ordering build step
	 */
	H2SelectOrderStep orderBy(OrderByExpression... orderByExpr);
	
	/**
	 * Specifies a LIMIT clause to limit the number of rows returned by the SELECT
	 * @param limitTerm expression specifying the maximum number of rows to return
	 * @return the ordering build step
	 */
	H2SelectOrderStep limit(IExpression limitTerm);
	
	/**
	 * Specifies a WHERE clause to specify what results are returned by the SELECT
	 * @param pred predicate clause for the SELECT
	 * @return the ordering build step
	 */
	H2SelectOrderStep where(Predicate pred);
	
	/**
	 * @see com.bobman159.rundml.h2.select.steps.H2SelectFetchStep#fetch()
	 * @return - the results of the SELECT statement
	 */
	List<ITableRow> fetch();  

}
