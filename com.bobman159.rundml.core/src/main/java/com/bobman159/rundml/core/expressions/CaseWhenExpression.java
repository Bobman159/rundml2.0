package com.bobman159.rundml.core.expressions;

import java.util.LinkedList;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.exprtypes.IMathOperations;
import com.bobman159.rundml.core.exprtypes.IStringOperations;
import com.bobman159.rundml.core.exprtypes.NumericValue;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.sql.conditions.SQLCondition;

//For case when the return type of the case when is based on either the THEN 
//or the ELSE.  If the WHEN condition is true the return type is the type of
//the THEN.  If the WHEN condition is false the return type is the type of the 
//ELSE

/**
 * Implements a CASE WHEN expression THEN expression ... 
 * clause.  Math and String operations are supported for the CASE but type 
 * checking is done by the DBMS and not by rundml2.0.
 *
 */
public class CaseWhenExpression implements IExpression, IMathOperations, IStringOperations {

	private LinkedList<CaseWhenThenCondition> whenConds;
	private IExpression whenCondition;		//holds when IExpressionType for the then() method
	private IExpression elseCondition = null;
	
	/**
	 * Creates a CASE WHEN clause.
	 */
	public CaseWhenExpression() {
		whenConds = new LinkedList<>();		
	}
	
	/**
	 * Creates a WHEN expression condition between two numerical values
	 * @param lhs the number to the of the operator
	 * @param op the condition operator (EQ,GT,LTE, etc)
	 * @param rhs the number to the right of the operator
	 * @see com.bobman159.rundml.core.sql.sql.conditions.Op
	 * @return the CASE WHEN Expression
	 */
	public CaseWhenExpression when(Number lhs, Op op, Number rhs) {
	
		NumericValue lhsNumber = Expression.number(lhs);
		NumericValue rhsNumber = Expression.number(rhs);
		whenCondition = new SQLCondition(lhsNumber,op,rhsNumber);
		return this;
	}
	
	/**
	 * Creates a CASE WHEN condition between two expressions 
	 * @param lhs the expression to the left of the condition
	 * @param op  the condition operator (EQ,GT,LTE, etc)
	 * @param rhs the expression to the right of the condition
	 * @see com.bobman159.rundml.core.sql.sql.conditions.Op
	 * @return the CASE WHEN expression
	 */
 	public CaseWhenExpression when(IExpression lhs, Op op, IExpression rhs) {
 		whenCondition = new SQLCondition(lhs,op,rhs);
		return this;
	}
 	
 	/**
 	 * Create a THEN expression clause
 	 * @param then expression for the THEN clause
 	 * @return the CASE WHEN expression
 	 */
	public CaseWhenExpression then(IExpression then) {
		CaseWhenThenCondition condition = new CaseWhenThenCondition(whenCondition,then);
		whenConds.add(condition);
		return this;
	}
	
	/**
	 * Creates a ELSE expression clause
	 * @param expr the <code>IExpression</code> for the ELSE clause
	 * @return the CASE expression
	 */
	public CaseWhenExpression elseClause(IExpression expr) {
		elseCondition = expr;
		return this;
	}
	
	/**
	 * Generate the END clause for the CASE
	 * @return the CASE expression
	 */
	public CaseWhenExpression end() {
		//Does nothing, implemented for readability during coding
		return this;
	}
	
 	/**
 	 * @see com.bobman159.rundml.core.exprtypes.IExpression#serialize()
 	 */
	@Override
	public String serialize() {
		
		StringBuilder expr = new StringBuilder();
		expr.append(" case ");
		whenConds.forEach(when -> expr.append(when.serialize()));
		expr.append(" end ");
		
		return expr.toString();

	}
	
}
