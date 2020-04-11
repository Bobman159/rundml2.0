package com.bobman159.rundml.core.expressions;

import java.util.LinkedList;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.exprtypes.IMathOperations;
import com.bobman159.rundml.core.exprtypes.IStringOperations;
import com.bobman159.rundml.core.exprtypes.ParmMarker;

//  The first arg in these case statements appears to determine the return type 
//  of the CASE statement.  IE CASE 123 returns a number, CASE 'abc' returns a
//  String.  

/**
 * Implements a CASE expression WHEN expression THEN expression ... 
 * clause.  Math and String operations are supported for the CASE but type 
 * checking is done by the DBMS and not by rundml2.0.  Condition operations 
 * IE COLNOTNULLCHAR = 'Abc' are NOT currently supported use
 *
 */

public class CaseExpression implements IExpression, IMathOperations, IStringOperations {	
	
	private IExpression caseExpr;
	private LinkedList<CaseWhenThenCondition> whenConds;
	private IExpression whenCondition;		//holds when IExpressionType for the then() method
	private IExpression elseCondition = null;
	
	//TODO: Consider supporting Conditions (CASE COLNOTNULL CHAR = 'Abc')
	//Support for CASE, WHEN, THEN and ELSE as well as String, Math.  Think this may be
	//a YAGNI (You aint gonna need it)
	
	/**
	 * Creates a CASE expression clause
	 * @param expr the <code>IExpression</code> for the CASE clause
	 */
	public CaseExpression(IExpression expr) {
		this.caseExpr = expr;
		whenConds = new LinkedList<>();
	}
	
	/**
	 * Creates a WHEN expression clause
	 * @param expr the <code>IExpression</code> for the WHEN condition
	 * @return the WHEN expression
	 */
	public CaseExpression when(IExpression expr) {
		whenCondition = expr;
		return this;
	}

	/**
	 * Creates a THEN expression clause
	 * @param expr the <code>IExpression</code> for the WHEN clause
	 * @return the THEN expression
	 */
	public CaseExpression then(IExpression expr) {
		CaseWhenThenCondition condition = new CaseWhenThenCondition(whenCondition,expr);
		whenConds.add(condition);
		return this;
	}
	
	/**
	 * Creates a ELSE expression clause
	 * @param expr the <code>IExpression</code> for the ELSE clause
	 * @return the ELSE expression
	 */
	public CaseExpression elseClause(IExpression expr) {
		elseCondition = expr;
		return this;
	}
	
	/**
	 * Generate the END clause for the CASE
	 * @return CASE expression
	 */
	public CaseExpression end() {
		//Does nothing, implemented for readability during coding
		return this;
	}

	/**
	 * @see com.bobman159.rundml.core.exprtypes.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		
		StringBuilder expr = new StringBuilder();
		expr.append(" case ").append(caseExpr.serialize());
		doAppendSpace(expr);
		whenConds.forEach(when -> expr.append(when.serialize()));
		doAppendSpace(expr);
		if (elseCondition != null) {
			expr.append("else ");
			expr.append(elseCondition.serialize());
			doAppendSpace(expr);
		}
		expr.append("end");
		
		return expr.toString();
	}
	
	//Appends a space to the expression if there is not one there already
	private void doAppendSpace(StringBuilder expr) {
		if (expr.charAt(expr.length() - 1) != ' ') {
			expr.append(' ');
		}
	}
	
}
