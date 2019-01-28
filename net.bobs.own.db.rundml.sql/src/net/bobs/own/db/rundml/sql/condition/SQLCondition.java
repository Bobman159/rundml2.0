package net.bobs.own.db.rundml.sql.condition;

import net.bobs.own.db.rundml.sql.expression.types.IExpression;


/**
 * Represents a condition (eg COL01 = 10, 10 < 20) in an SQL statement.  
 * 
 *
 */
public class SQLCondition implements IExpression {

	private IExpression lhs;
	private Op op;
	private IExpression rhs;
	
	/**
	 * Defines an SQL condition which is evaluated as true or false.
	 * 
	 * @param lhs - the SQL expression on the left of the operand
	 * @param op - the operator (<,>,<>,=) etc.
	 * @param rhs
	 */
	public SQLCondition(IExpression lhs,Op op, IExpression rhs) {
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
	}

	/**
	 * @see net.bobs.own.db.rundml.sql.expression.types.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		String expr = "";
		expr = lhs.serialize();
		expr = expr + op.getOperator();
		expr = expr + rhs.serialize();
		
		return expr;
	}
	
}
