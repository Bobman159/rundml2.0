package com.bobman159.rundml.core.model;

import com.bobman159.rundml.core.expressions.ExpressionList;
import com.bobman159.rundml.core.expressions.impl.ExpressionVisitor;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Model information for an SQL clause, expression, predicate in an SQL statement. 
 * 
 * Each instance  of
 * <code>SQLModelNode</code> represents the text of the SQL clause for the SQL 
 * statement and the "argument(s)" values for that SQL clause.
 * 
 * Combined together into a <code>SQLStatementModel</code> the SQLModelNode is used
 * to build the text of an SQL Statement will be executed.
 *
 */
public class SQLModelNode {
	
	private ModelNodeType	nodeType;
	private ISQLType	arg;

	/**
	 * Define a SQL clause with no arguments
	 * @param nodeType type of model node being defined
	 */
	public SQLModelNode(ModelNodeType nodeType) {
		this.nodeType = nodeType;
		arg = null;
	}
	
	/**
	 * Define an SQL clause with an argument
	 * @param nodeType the enumeration of an SQL clause syntax
	 * @param arg the argument of that clause
	 */
	public SQLModelNode(ModelNodeType nodeType,ISQLType arg) {
		this.nodeType = nodeType;
		this.arg = arg;
	}
	
	/**
	 * @return the node type for the current model node
	 */
	public ModelNodeType getNodeType() {
		return nodeType;
	}
	
	/**
	 * @see com.bobman159.rundml.core.model.ISQLModelNode#argToString()
	 */
//	@Override
	public String argToString() {
//	public String serialize() {
		String argText = null;
		
		if (arg == null) {
			return null;
		}
		
		if (arg instanceof String) {
			argText = (String) arg;
		} else if (arg instanceof ISQLType) {
			ISQLType expr = (ISQLType) arg;
			argText = ExpressionVisitor.getInstance().acceptSerialize(expr);
		} else if (arg instanceof Predicate) {
			Predicate pred = (Predicate) arg;
			argText = pred.serialize();
		} else if (arg instanceof ExpressionList) {
			//ASSUME: The list of objects for SELECTEXPR are ISQLType(s)
			ExpressionList list = (ExpressionList) arg;
			argText = list.toCSV();
		}
 		
		argText = argText + " ";
		return argText;
	}

	/**
	 * @see com.bobman159.rundml.core.model.ISQLModelNode#getArg()
	 */
//	@Override
//	public Object getArg() {
//		return arg;
//	}
	
}
