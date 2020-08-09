package com.bobman159.rundml.core.sql;

import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Builder for SQL CASE clause
 *
 */
public interface ICaseBuilder {

	/**
	 * Create a CASE value WHEN value/condition SQL clause
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder caseClause();
	
	/**
	 * Create a CASE value WHEN value/condition SQL clause
	 * @param casevalue the value for CASE clause
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder caseClause(ISQLType casevalue);
	
	/**
	 * Create a CASE expression SQL clause
	 * @param caseExpr the value for CASE clause
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder caseClause(IExpressionNode caseExpr);
	
	/**
	 * Create a CASE WHEN value/condition SQL clause
	 * @return an <code>ICaseBuilder</code> instance
	 * 
	 */
	public ICaseBuilder caseWhenClause();
	
	/**
	 * Create a WHEN value/condition SQL clause in a CASE statement
	 * @param whenValue the value or condition for the WHEN clause
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder when(ISQLType whenValue);
	
	/**
	 * Create a WHEN expression SQL clause in a CASE statement
	 * @param whenExpr the mathExpression for the WHEN clause
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder when(IExpressionNode whenExpr);
	
	/**
	 * Create a WHEN SQL condition (eg value = value) SQL clause in a CASE statement
	 * @param lhsValue the left side of the condition for the WHEN clause
	 * @param oper the operator (&lt;,&#61;,&lt;&gt;,etc)
	 * @param rhsValue the right side of the condition
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder when(ISQLType lhsValue,Op oper, ISQLType rhsValue);
	
	/**
	 * Create a WHEN SQL condition (eg value = value) SQL clause in a CASE statement
	 * @param lhsValue the left side of the condition for the WHEN clause
	 * @param oper the operator (&lt;,&#61;,&lt;&gt;,etc)
	 * @param rhsValue the right side of the condition
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder when(IExpressionNode lhsValue,Op oper, ISQLType rhsValue);
	
	/**
	 * Create a WHEN SQL condition (eg value = value) SQL clause in a CASE statement
	 * @param lhsValue the left side of the condition for the WHEN clause
	 * @param oper the operator (&lt;,&#61;,&lt;&gt;,etc)
	 * @param rhsValue the right side of the condition
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder when(IExpressionNode lhsValue,Op oper, IExpressionNode rhsValue);
	
	
	/**
	 * Create a THEN value/condition SQL clause in a CASE statement
	 * @param thenValue the value or condition for the WHEN clause
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder then(ISQLType thenValue);
	
	/**
	 * Create a THEN expression SQL clause in a CASE statement
	 * @param thenExpr the math expression for the WHEN clause
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder then(IExpressionNode thenExpr);
	
	/**
	 * Create a ELSE SQL clause in a CASE statement
	 * @param elseValue the value or condition for the WHEN clause
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder elseClause(ISQLType elseValue);
	
	/**
	 * Create a ELSE expression SQL clause in a CASE statement
	 * @param elseExpr the math expression for the WHEN clause
	 * @return an <code>ICaseBuilder</code> instance
	 */
	public ICaseBuilder elseClause(IExpressionNode elseExpr);

	/**
	 * Create a END SQL clause in a CASE statement
	 * @return the completed CASE statement
	 */
	public ICaseClause end();
	
}
