package com.bobman159.rundml.core.expressions;

import com.bobman159.rundml.core.expressions.impl.MathExpression;
import com.bobman159.rundml.core.expressions.impl.StringExpression;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;

/**
 * Abstract base class for represents an expression in an SQL expression. Both RunDML supported
 * type (Number, String, Column name, parameter marker etc) in an expression and
 * expressions (String and Mathematical) are supported
 *
 */
public abstract class AbstractBaseExpression implements IExpressionNode {

	private ISQLType	leftExpr;
	private ISQLType	rightExpr;
	private Op			operator;

	/**
	 * Create of a new <code>ISQLType> instance
	 * @param leftExpr the left expression value
	 * @param operator the expression operator
	 * @param rightExpr the right expression value 
	 * @return a new instance of an <code>ISQLType</code>
	 */
	public abstract IExpressionNode createExpressionNode(ISQLType leftExpr,Op operator, 
														 ISQLType rightExpr);
	
	/**
	 * Create an SQL expression 
	 * @param nodeType the type of the expression
	 * @param expr the left value in the expression
	 * @see com.bobman159.rundml.core.expressions.ExpressionNodeEnum
	 */
	protected AbstractBaseExpression(ISQLType expr) {
		leftExpr = expr;
		rightExpr = null;
	}
	
	/**
	 * Create an SQL expression
	 * @param leftExpr the left value in the expression
	 * @param operator the operator for the expression
	 * @param rightExpr the right value in the expression
	 */
	protected AbstractBaseExpression(ISQLType leftExpr,Op operator,ISQLType rightExpr) {	
		this.leftExpr = leftExpr;
		this.rightExpr = rightExpr;
		this.operator = operator;
	}
	
	protected IExpressionNode addToExpression(IExpressionNode nodeHead,Op operator,ISQLType value) {
		
		IExpressionNode	lastNode = null;	
		IExpressionNode wkNode = null;
		
		lastNode = doFindLastNode(nodeHead);
		//ASSUME: rightExpr == null only when the IExpressionFactory is incomplete (right Side has not yet been processed) 
		if (nodeHead.getRightExpr() == null) {
			//Instantiate a new (completed) expression
			wkNode = createExpressionNode(lastNode.getLeftExpr(),operator,value);
			nodeHead = wkNode;			//Reset the head to new (completed) expression
			/* Sanity check to make sure the last node right side is a IExprType */
		} else if (!(isExpressionNode(lastNode.getRightExpr()))) {
			ISQLType wkValue = lastNode.getRightExpr();
			wkNode = createExpressionNode(wkValue,operator,value);
			lastNode.setRightExpr(wkNode);
		}
		
		return nodeHead;
	}
	
	/**
	 * @see com.bobman159.rundml.core.expressions.IExpressionNode#getLeftExpr()
	 */
	@Override
	public ISQLType getLeftExpr() {
		return leftExpr;
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IExpressionNode#getOperator()
	 */
	@Override
	public Op getOperator() {
		return operator;
	}
	
	/**
	 * @see com.bobman159.rundml.core.expressions.IExpressionNode#getRightExpr()
	 */
	@Override
	public ISQLType getRightExpr() {
		return rightExpr;
	}	
	
	/**
	 * @see com.bobman159.rundml.core.sql.types.ISQLType#getType()
	 */
	@Override
	public SQLType getType() {
		return SQLType.EXPRESSION;
	}
	
	/**
	 * @see com.bobman159.rundml.core.expressions.IExpressionNode#setRightExpr(ISQLType)
	 */
	@Override
	public void setRightExpr(ISQLType rightExpr) {
		this.rightExpr = rightExpr;
	}
	
	/*
	 * Finds the last ISQLType on the right side 
	 * @param exprHead
	 * @return
	 */
	private IExpressionNode doFindLastNode(ISQLType exprHead) {
		
		/*
		 *	IF node != ISQLType OR
		 *	   node -> rightSide == null THEN
		 *		ASSUME: we are finished searching
		 */
		if ((isExpressionNode(exprHead) &&
			(((IExpressionNode) exprHead).getRightExpr() == null) ||
			  (!(isExpressionNode(((IExpressionNode) exprHead).getRightExpr()))))) {
				//The right side is null OR the rightExpr is not an ISQLType should be done....
				return (IExpressionNode) exprHead;
		}
					
		//Evaluate the next node in the expression
		return doFindLastNode(((IExpressionNode)exprHead).getRightExpr());	
	}
	
	/*
	 * Verifies if the specified node is 1) an ISQLType and 2) a MATH_EXPR or STRING_EXPR
	 * node type 
	 * @param node the node to check
	 * @return true if both conditions are met, false otherwise
	 */
	private boolean isExpressionNode(ISQLType node) {
		
		boolean isExprNode = false;
		if (node instanceof IExpressionNode) {
			
			IExpressionNode exprNode = (IExpressionNode) node;
			
			if ((exprNode.getRightExpr() != null) &&
				(exprNode instanceof MathExpression) ||
				(exprNode instanceof StringExpression)) {
				isExprNode = true;
			} 
		}
		
		return isExprNode;
	}
	

}
