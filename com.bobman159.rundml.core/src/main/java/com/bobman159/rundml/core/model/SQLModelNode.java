package com.bobman159.rundml.core.model;

import com.bobman159.rundml.core.expressions.ExpressionList;
import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.ISQLEnum;

/**
 * Information for an SQL clause in an SQL statement. Each instance  of
 * <code>SQLModelNode</code> represents the text of the SQL clause for the SQL 
 * statement and the "argument(s)" values for that SQL clause.
 * 
 * Combined together into a <code>SQLStatementModel</code> the SQLModelNode is used
 * to build the text of an SQL Statement will be executed.
 *
 */
class SQLModelNode implements ISQLModelNode {
	
	private ISQLEnum 	enumClause;
	private Object 		arg;

	/**
	 * Define a SQL clause with no arguments
	 * @param enumeration the enumeration of an SQL clause syntax
	 */
	public SQLModelNode(ISQLEnum enumeration) {
		enumClause = enumeration;
		arg = null;
	}
	
	/**
	 * Define an SQL clause with an argument
	 * @param enumeration the enumeration of an SQL clause syntax
	 * @param arg the argument of that clause
	 */
	public SQLModelNode(ISQLEnum enumeration,Object arg) {
		enumClause = enumeration;
		this.arg = arg;
	}
	
	/**
	 * @see com.bobman159.rundml.core.model.ISQLModelNode#argToString()
	 */
	@Override
	public ISQLEnum getEnum() {
		return enumClause;
	}
	
	/**
	 * @see com.bobman159.rundml.core.model.ISQLModelNode#argToString()
	 */
	@Override
	public String argToString() {
		String argText = null;
		
		if (arg == null) {
			return null;
		}
		
		
		if (arg instanceof String) {
			argText = (String) arg;
		} else if (arg instanceof IExpression) {
			IExpression expr = (IExpression) arg;
			argText = expr.serialize();
		} else if (arg instanceof Predicate) {
			Predicate pred = (Predicate) arg;
			argText = pred.serialize();
		} else if (arg instanceof ExpressionList) {
			//ASSUME: The list of objects for SELECTEXPR are IExpression(s)
			ExpressionList list = (ExpressionList) arg;
			argText = list.toCSV();
		}
 		
		argText = argText + " ";
		return argText;
	}

	/**
	 * @see com.bobman159.rundml.core.model.ISQLModelNode#getArg()
	 */
	@Override
	public Object getArg() {
		return arg;
	}
	
}
