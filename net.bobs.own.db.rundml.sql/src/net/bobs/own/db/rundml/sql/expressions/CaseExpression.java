package net.bobs.own.db.rundml.sql.expressions;

import java.util.LinkedList;

import net.bobs.own.db.rundml.sql.expression.types.IExpression;
import net.bobs.own.db.rundml.sql.expression.types.IMathOperations;
import net.bobs.own.db.rundml.sql.expression.types.IStringOperations;

//  The first arg in these case statements appears to determine the return type 
//  of the CASE statement.  IE CASE 123 returns a number, CASE 'abc' returns a
//  String.  

/**
 * Implements a CASE expression WHEN expression THEN expression ... 
 * clause.  Math and String operations are supported for the CASE but type 
 * checking is done by the DBMS and not by rundml2.0.
 *
 */

public class CaseExpression implements IExpression, IMathOperations, IStringOperations {	
	
	private IExpression caseExpr;
	private LinkedList<CaseWhenThenCondition> whenConds;
	private IExpression whenCondition;		//holds when IExpressionType for the then() method
	private IExpression elseCondition = null;
	
	/**
	 * Creates a CASE expression clause
	 * @param expr - the <code>IExpression</code> for the CASE clause
	 */
	public CaseExpression(IExpression expr) {
		this.caseExpr = expr;
		whenConds = new LinkedList<>();
	}
	
	/**
	 * Creates a WHEN expression clause
	 * @param expr - the <code>IExpression</code> for the WHEN condition
	 */
	public CaseExpression when(IExpression expr) {
		whenCondition = expr;
		return this;
	}

	/**
	 * Creates a THEN expression clause
	 * @param expr - the <code>IExpression</code> for the WHEN clause
	 */
	public CaseExpression then(IExpression expr) {
		CaseWhenThenCondition condition = new CaseWhenThenCondition(whenCondition,expr);
		whenConds.add(condition);
		return this;
	}
	
	/**
	 * Creates a ELSE expression clause
	 * @param expr - the <code>IExpression</code> for the ELSE clause
	 */
	public CaseExpression elseClause(IExpression expr) {
		elseCondition = expr;
		return this;
	}
	
	/**
	 * Generate the END clause for the CASE
	 * @return - CASE expression
	 */
	public CaseExpression end() {
		//Does nothing, implemented for readability during coding
		return this;
	}

	/**
	 * @see net.bobs.own.db.rundml.sql.expression.types.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		
		StringBuilder expr = new StringBuilder();
		expr.append(" case ").append(caseExpr.serialize());
		whenConds.forEach(when -> expr.append(when.serialize()));
		if (elseCondition != null) {
			expr.append(elseCondition.serialize());
		}
		expr.append(" end ");
		
		return expr.toString();
	}
	
}
