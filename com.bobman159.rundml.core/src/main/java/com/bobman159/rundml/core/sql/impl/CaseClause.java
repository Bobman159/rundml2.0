package com.bobman159.rundml.core.sql.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.sql.ICaseClause;
import com.bobman159.rundml.core.sql.ICaseWhenThen;
import com.bobman159.rundml.core.sql.ICaseWhenValue;
import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.types.ISQLType;

//  The first arg in these case statements appears to determine the return type 
//  of the CASE statement.  IE CASE 123 returns a number, CASE 'abc' returns a
//  String.  

/**
 * Defines a CASE expression WHEN expression THEN expression ... 
 * clause.  Math and String operations are supported for the CASE but type 
 * checking is done by the DBMS and not by rundml2.0.  Condition operations 
 * IE COLNOTNULLCHAR = 'Abc' are NOT currently supported use
 *
 */

public class CaseClause implements ICaseClause {
	
	private class CaseWhenThen implements ICaseWhenThen {
		private ICaseWhenValue	when;
		private ISQLType		then;
		
		protected CaseWhenThen(ICaseWhenValue when, ISQLType then) {
			this.when = when;
			this.then = then;
		}
		
		@Override
		public ISQLType getThenCondition() {
			return then;
		}
		
		/**
		 * @see com.bobman159.rundml.core.sql.ICaseWhenThen#isWhenValueSQLCondition()
		 */
		@Override
		public boolean isWhenValueSQLCondition() {
			boolean isSQLCond = false;
			if (when instanceof ISQLCondition) {
				isSQLCond = true;
			}
			return isSQLCond;
		}

		/**
		 * @see com.bobman159.rundml.core.sql.ICaseClause#isWhenValueSQLType(ICaseWhenValue)
		 */
		@Override
		public boolean isWhenValueSQLType() {
			boolean isSQLType = false;
			if (when instanceof ISQLType) {
				isSQLType = true;
			}
			return isSQLType;
		}

		/**
		 * @see com.bobman159.rundml.core.sql.ICaseWhenThen#getWhenValue()
		 */
		@Override
		public ISQLType getWhenType() {
			return (ISQLType) when;
		}

		@Override
		public ISQLCondition getWhenCondition() {
			return (ISQLCondition) when;
		}

			
	}

	private ISQLType caseValue;
	private LinkedList<ICaseWhenThen> whenConds;
	private ISQLType elseCondition = null;
	private boolean 	caseWhen = false;		//is this a CASE WHEN statement?
	
	//TODO: Consider supporting Conditions (CASE COLNOTNULL CHAR = 'Abc')
	//Support for CASE, WHEN, THEN and ELSE as well as String, Math.  Think this may be
	//a YAGNI (You aint gonna need it)
	
	/**
	 * Create a CASE WHEN SQL clause
	 */
	public CaseClause() {
		this.caseValue = null;
		whenConds = new LinkedList<>();
	}
	
	/**
	 * Creates a CASE 
	 * @param value the <code>ISQLType</code> for the CASE clause
	 */
	public CaseClause(ISQLType value) {
		this.caseValue = value;
		whenConds = new LinkedList<>();
	}
	
	/**
	 * Create a CASE math expression WHEN... clause
	 * @param caseExpr the math expression for the CASE clause
	 */
	public CaseClause(IExpressionNode caseExpr) {
		if (caseExpr instanceof ISQLType) {
			this.caseValue = caseExpr;
		} else {
			throw new IllegalArgumentException("mathExp must be an inherited instance of ISQLType");
		}
		whenConds = new LinkedList<>();
	}
		
	/**
	 * Add a new WHEN value THEN value to the list of WHEN clauses for the current CASE
	 * @param whenValue value for WHEN condition
	 * @param thenValue value for THEN condition
	 */
	@Override
	public void setWhenThen(ICaseWhenValue whenValue, ISQLType thenValue) {
		ICaseWhenThen tempWhenThen = new CaseWhenThen(whenValue,thenValue);
		whenConds.add(tempWhenThen);
	}

	/**
	 * @see com.bobman159.rundml.core.sql.ICaseClause#getCaseExpr()
	 */
	@Override
	public ISQLType getCaseExpr() {
		return caseValue;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseClause#getWhenThenConditions()
	 */
	@Override
	public List<ICaseWhenThen> getWhenThenConditions() {
		return Collections.unmodifiableList(whenConds);
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseClause#isCaseWhen()
	 */
	@Override
	public boolean isCaseWhen() {
		
		return caseWhen;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.ICaseClause#hasElse()
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

	/**
	 * @see com.bobman159.rundml.core.sql.ICaseClause#setElse(ISQLType)
	 */
	@Override
	public void setElse(ISQLType elseExpr) {
		elseCondition = elseExpr;
	}



}
