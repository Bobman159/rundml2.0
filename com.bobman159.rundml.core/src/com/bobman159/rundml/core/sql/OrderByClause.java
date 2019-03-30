package com.bobman159.rundml.core.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.sql.SQLClauses.SQLClause;

/**
 * Implementation for an SQL ORDER BY clause and the expression(s) defined in 
 * the ORDER BY
 */
public class OrderByClause implements IExpression {
	
	private List<OrderByExpression> orderByExprs;
	
	/**
	 * Creates the ORDER BY implementation 
	 */
	public OrderByClause() {
		orderByExprs = new ArrayList<>();
	}
	
	/**
	 * Add an expression to the ORDER BY clause
	 * @param orderByExpr - the expression for the ORDER BY
	 */
	public OrderByClause addExpression(OrderByExpression orderByExpr) {
		orderByExprs.add(orderByExpr);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.core.exprtypes.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		String orderByText = "";
		
		orderByText = SQLClause.ORDERBY.serializeClause() + " ";		
		orderByText = orderByText + orderByExprs.stream()
						   .map(orderByExpr -> orderByExpr.serialize())	//NOSONAR
						   .collect(Collectors.joining(","));

		return orderByText;
	}

}
