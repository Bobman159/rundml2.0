package com.bobman159.rundml.core.expressions;

import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * The representation of an SQL expressions such as mathematical (ex: 10 + 10) and string expressions
 * ex ('Abc' || (concat) 'Def').  Expressions are defined with a left side, an operation and 
 * a right side. Any class(es) intended for the implementation of SQL expressions meeting these
 * conditions <b>must</b> implement this interface.
 *
 */
public interface IExpressionNode extends ISQLType {
	
	/**
	 * Get the expression value on the left of the operation
	 * @return the left expression value
	 */
	public ISQLType getLeftExpr();
	
	/**
	 * Get the expression value on the left of the operation
	 * @return the right expression value
	 */
	public ISQLType getRightExpr();
	
	/**
	 * Get the operation value
	 * @return the expression operator
	 */
	public Op				getOperator();
	
	/**
	 * Set the right expression value
	 * @param rightExpr the new value to be defined
	 */
	public void	setRightExpr(ISQLType rightExpr);

}
