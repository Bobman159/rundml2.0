package com.bobman159.rundml.core.sql;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.sql.OrderByEnums.OrderByNullsEnum;
import com.bobman159.rundml.core.sql.OrderByEnums.OrderBySortEnum;

/**
 * Defines an ORDER BY clause expression (column, integer etc.)
 *
 */
public class OrderByExpression implements IExpression {
	
	IExpression orderByExpr;
	OrderBySortEnum sortDir;
	OrderByNullsEnum orderByNull;
	
	/**
	 * Create an Order By Expression
	 * @param orderByExpr the SQL ORDER BY expression
	 */
	public OrderByExpression(IExpression orderByExpr) {
		this.orderByExpr = orderByExpr;
		sortDir = null;
		orderByNull = null;
	}
	
	/**
	 * Specify a sort order of ascending for this expression
	 * @return the ORDER BY expression
	 */
	public OrderByExpression asc() {
		this.sortDir = OrderBySortEnum.ASC;
		return this;
	}
	
	/**
	 * Specify a sort order of descending for this expression
	 * @return the ORDER BY expression
	 */
	public OrderByExpression desc() {
		this.sortDir = OrderBySortEnum.DESC;
		return this;
	}
	
	/**
	 * Specify NULLS FIRST for this expression
	 * <p>NOTE: Not all DBMS will allow the NULLS FIRT clause in an ORDER BY,
	 * review the documentation for your specific DBMS to determine if the clause is 
	 * supported.
	 * @return the ORDER BY expression
	 */
	public OrderByExpression nullsFirst() {
		orderByNull = OrderByNullsEnum.NULLS_FIRST;
		return this;
	}
	
	/**
	 * Specify NULLS LAST for this expression.
	 * <p>NOTE: Not all DBMS will allow the NULLS LAST clause in an ORDER BY,
	 *  review the documentation for your specific DBMS to determine if the clause is 
	 *  supported.
	 * @return the ORDER BY expression*  
	 */
	public OrderByExpression nullsLast() {
		orderByNull = OrderByNullsEnum.NULLS_LAST;
		return this;
	}

	/**
	 * @see com.bobman159.rundml.core.exprtypes.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		
		StringBuilder builder = new StringBuilder();
		builder.append(orderByExpr.serialize( )).append(" ");
		if (sortDir != null) {
			builder.append(sortDir.serializeClause()).append(" ");
		}
		if (orderByNull != null) {
			builder.append(orderByNull.serializeClause()).append(" ");
		}
		return builder.toString().trim();
	}

}
