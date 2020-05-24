package com.bobman159.rundml.core.sql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bobman159.rundml.core.sql.impl.SQLClauses.SQLClause;
import com.bobman159.rundml.core.sql.types.SQLType;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Implementation for an SQL ORDER BY clause and the expression(s) defined in 
 * the ORDER BY
 */
public class OrderByClause implements ISQLType {
	
	private List<OrderByExpression> orderByExprs;
	
	/**
	 * Creates the ORDER BY implementation 
	 */
	public OrderByClause() {
		orderByExprs = new ArrayList<>();
	}
	
	/**
	 * Add an expression to the ORDER BY clause
	 * @param orderByExpr the expression for the ORDER BY
	 * @return the ORDER BY clause
	 */
	public OrderByClause addExpression(OrderByExpression orderByExpr) {
		orderByExprs.add(orderByExpr);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.types.ISQLType#serialize()
	 */
//	@Override
//	public String serialize() {
//		String orderByText = "";
//		
//		orderByText = SQLClause.ORDERBY.serializeClause() + " ";		
//		orderByText = orderByText + orderByExprs.stream()
//						   .map(orderByExpr -> orderByExpr.serialize())	//NOSONAR
//						   .collect(Collectors.joining(","));
//
//		return orderByText;
//	}

	/**
	 * @see com.bobman159.rundml.core.sql.types.ISQLType#getExpressionType()
	 */
	@Override
	public SQLType getType() {
		return SQLType.ORDER_BY;
	}

}
