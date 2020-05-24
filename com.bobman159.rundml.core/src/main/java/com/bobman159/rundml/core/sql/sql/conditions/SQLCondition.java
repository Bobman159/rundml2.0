package com.bobman159.rundml.core.sql.sql.conditions;

import com.bobman159.rundml.core.expressions.AbstractBaseExpression;
import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;


/**
 * Represents a condition (eg COL01 = 10, 10 &gt; 20) in an SQL statement.  
 * 
 *
 */
public class SQLCondition extends AbstractBaseExpression {

	/**
	 * Defines an SQL condition which is evaluated as true or false.
	 * 
	 * @param lhs the SQL expression on the left of the operand
	 * @param op the operator (&gt;&lt;,&lt;&gt;,=") etc.
	 * @param rhs the SQL expression on the right side of the operand
	 */
	public SQLCondition(ISQLType leftExpr,Op op, ISQLType rightExpr) {
		super(leftExpr,op,rightExpr);
	}

	@Override
	public AbstractBaseExpression createExpressionNode(ISQLType leftExpr, Op operator, ISQLType rightExpr) {
		return new SQLCondition(leftExpr,operator,rightExpr);
	}

	@Override
	public SQLType getType() {
		return SQLType.PREDICATE;
	}

}
