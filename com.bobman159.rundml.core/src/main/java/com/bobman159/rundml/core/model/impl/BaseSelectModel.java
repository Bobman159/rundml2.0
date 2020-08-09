package com.bobman159.rundml.core.model.impl;
/**
 * Definition(s) for SQL SELECT statement
 *
 */

import java.util.Properties;

import com.bobman159.rundml.core.expressions.IExpressionList;
import com.bobman159.rundml.core.model.ISelectModel;
import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.IOrderByList;
import com.bobman159.rundml.core.sql.types.ISQLType;


/** Base Model class for SELECT SQL statements. 
 * Supports SELECT syntax common to SELECTs. 
 * 
 */
public class BaseSelectModel implements ISelectModel {
	
	private Properties selectProps;
	
	protected BaseSelectModel() {
		selectProps = new Properties();
	}

	/**
	 * Add a SELECT value to the select model
	 * @param selectExpr the value to be added
	 */
	public void addSelectExpression(ISQLType selectExpr) {
		IExpressionList selectExprs = getExpressionList();
		selectExprs.addExpression(selectExpr);
		putProp(SelectClause.SELECTEXPR,selectExpr);
	}
	
	/**
	 * Add a list of SELECT value, value, value... to the select model
	 * @param selectExprs the list of values to be added
	 */
	public void addSelectExpression(ISQLType... selectExprs) {
		IExpressionList selectExprList = getExpressionList();
		selectExprList.addExpressions(selectExprs);
		putProp(SelectClause.SELECTEXPR,selectExprList);
	}
	
	/**
	 * Add a list of SELECT value, value, value... to the select model
	 * @param orderByEntries the list of values to be added
	 */
	public void addOrderByEntry(IOrderByEntry... orderByEntries) {
		IOrderByList orderByList = CoreModelFactory.getInstance().createOrderByList();
		CoreModelFactory.getInstance().createOrderByList();
		orderByList.addOrderByClause(orderByEntries);
		putProp(SelectClause.ORDERBY,orderByList);
	}
	
	/***
	 * Adds an entry to the SELECT statement if it does not exist for the
	 * select clause.  Otherwise the existing entry for the clause is 
	 * replaced with the new value.
	 * @param clause the clause being added or replaced
	 * @param value the value being added or replaced
	 */
	public void putProp(SelectClause clause, Object value) {
		
		if (selectProps.containsKey(clause)) {
			selectProps.replace(clause, value);
		} else {
			selectProps.put(clause, value);
		}
	}
	
	private IExpressionList getExpressionList() {
		
		IExpressionList selectExprs = null;
		if (selectProps.containsKey(SelectClause.SELECTEXPR)) {
			selectExprs = (IExpressionList) selectProps.get(SelectClause.SELECTEXPR);
		} else {
			selectExprs = CoreModelFactory.getInstance().createExpressionList();
		}
		
		return selectExprs;

	}
	

}
