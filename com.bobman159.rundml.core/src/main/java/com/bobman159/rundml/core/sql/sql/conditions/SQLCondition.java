package com.bobman159.rundml.core.sql.sql.conditions;

import com.bobman159.rundml.core.expressions.AbstractBaseExpression;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;


/**
 * Represents a condition (eg COL01 = 10, 10 &gt; 20) in an SQL statement.  
 * 
 *
 */
public class SQLCondition implements ISQLCondition {
	
	private ISQLType leftCond;
	private ISQLType rightCond;
	private Op		 operator;

	/**
	 * Partial constructor for a SQL condition
	 * @param leftExpr first operand (left) of the SQL condition
	 * @return partial instance of an SQL condition
	 */
	public static SQLCondition createSQLCondition(ISQLType leftExpr) {
		return new SQLCondition(leftExpr);
	}
	
	/**
	 * Defines an SQL condition which is evaluated as true or false.
	 * 
	 * @param condition partially defined (left expression) sql condition
	 * @param op the operator (&gt;&lt;,&lt;&gt;,=") etc.
	 * @param rhs the SQL expression on the right side of the operand
	 */
	public static SQLCondition createSQLCondition(ISQLCondition condition,Op op, ISQLType rightExpr) {
		ISQLType leftExpr = condition.getLeftCondition();
		return new SQLCondition(leftExpr, op, rightExpr);
	}
	
//	/**
//	 * Creates a complete SQL condition 
//	 * @param leftCond the left part of the condition
//	 * @param operator the operator for the condition
//	 * @param rightCond the right part of the condition
//	 * @return the completed SQL condition
//	 */
//	@Override
//	public SQLCondition createSQLCondition(ISQLType leftCond, Op operator, ISQLType rightCond) {
//		return new SQLCondition(leftCond,operator,rightCond);
//	}
	
	/*
	 * Partial constructor for a SQL condition
	 * @param leftExpr first operand (left) of the SQL condition
	 * @return partial instance of an SQL condition
	 */
	private SQLCondition(ISQLType leftCond) {
		this.leftCond = leftCond;
	}
	
	/*
	 * Defines an SQL condition which is evaluated as true or false.
	 * 
	 * @param condition partially defined (left expression) sql condition
	 * @param op the operator (&gt;&lt;,&lt;&gt;,=") etc.
	 * @param rhs the SQL expression on the right side of the operand
	 */
	private SQLCondition(ISQLType leftCond,Op op, ISQLType rightCond) {
		this.leftCond = leftCond;
		this.operator = op;
		this.rightCond = rightCond;
	}



	/**
	 * @see com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition#getLeftCondition()
	 */
	@Override
	public ISQLType getLeftCondition() {
		return leftCond;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition#getRightCondition()
	 */
	@Override
	public ISQLType getRightCondition() {
		return rightCond;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition#getOperator()
	 */
	@Override
	public Op getOperator() {
		return operator;
	}

}
