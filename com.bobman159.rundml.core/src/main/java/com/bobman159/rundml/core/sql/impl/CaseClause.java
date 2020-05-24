package com.bobman159.rundml.core.sql.impl;

import java.util.LinkedList;
import java.util.stream.Stream;

import com.bobman159.rundml.core.sql.ICaseStatement;
import com.bobman159.rundml.core.sql.ICaseWhenConditions;
import com.bobman159.rundml.core.sql.ISQLClause;
import com.bobman159.rundml.core.sql.impl.SQLClauses.SQLClause;
import com.bobman159.rundml.core.sql.types.ISQLType;

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

public class CaseClause implements ICaseStatement {
	
	SQLClause caseType = null;
	private ISQLType caseExpr;
	private LinkedList<ICaseWhenConditions> whenConds;
	private ISQLType whenCondition;		//holds when ISQLType for the then() method
	private ISQLType elseCondition = null;
	
	//TODO: Consider supporting Conditions (CASE COLNOTNULL CHAR = 'Abc')
	//Support for CASE, WHEN, THEN and ELSE as well as String, Math.  Think this may be
	//a YAGNI (You aint gonna need it)
	
	/**
	 * Creates a CASE WHEN expression clause
	 */
	public CaseClause() {
		this.caseExpr = null;
		whenConds = new LinkedList<>();
		caseType = SQLClause.CASE_WHEN;
	}
	
	/**
	 * Creates a CASE expression clause
	 * @param expr the <code>ISQLType</code> for the CASE clause
	 */
	public CaseClause(ISQLType expr) {
		this.caseExpr = expr;
		whenConds = new LinkedList<>();
		caseType = SQLClause.CASE_EXPR;
	}
	
	/**
	 * Creates a WHEN expression clause
	 * @param expr the <code>ISQLType</code> for the WHEN condition
	 * @return the WHEN expression
	 */
	public CaseClause when(ISQLType expr) {
		whenCondition = expr;
		return this;
	}

	/**
	 * Creates a THEN expression clause
	 * @param expr the <code>ISQLType</code> for the WHEN clause
	 * @return the THEN expression
	 */
	public CaseClause then(ISQLType expr) {
		CaseWhenThenCondition condition = new CaseWhenThenCondition(whenCondition,expr);
		whenConds.add(condition);
		return this;
	}
	
	/**
	 * Creates a ELSE expression clause
	 * @param expr the <code>ISQLType</code> for the ELSE clause
	 * @return the ELSE expression
	 */
	public CaseClause elseClause(ISQLType expr) {
		elseCondition = expr;
		return this;
	}
	
	/**
	 * Generate the END clause for the CASE
	 * @return CASE expression
	 */
	public CaseClause end() {
		//Does nothing, implemented for readability during coding
		return this;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ISQLClause#serializeClause()
	 */
//	@Override
	public String serializeClause() {
		
		String caseClause = "";
		
		if (caseType.equals(SQLClause.CASE_EXPR)) {
			caseClause = " CASE ";
		} else {
			caseClause = " CASE WHEN ";
		}
		
		return caseClause;
	}
	

	/**
	 * @see com.bobman159.rundml.core.sql.ICaseStatement#getType()
	 */
	@Override
	public ISQLClause getType() {
		return this.caseType;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseStatement#getCaseExpr()
	 */
	@Override
	public ISQLType getCaseExpr() {
		return this.caseExpr;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.ICaseStatement#getWhenConditions()
	 */
	@Override
	public Stream<ICaseWhenConditions> getWhenConditions() {
		return whenConds.stream();
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseStatement#isCaseWhen()
	 */
	@Override
	public boolean isCaseWhen() {
		
		boolean isCaseWhen = false;
		
		if (SQLClause.CASE_WHEN.equals(caseType)) {
			isCaseWhen = true;
		}
		
		return isCaseWhen;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.ICaseStatement#hasElse()
	 */
	@Override
	public boolean hasElse() {
		
		boolean hasElse = false;
		if (elseCondition != null) {
			hasElse = true;
		}
		return hasElse;
	}

	@Override
	public ISQLType getElse() {
		return this.elseCondition;
	}

}
