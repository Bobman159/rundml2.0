package com.bobman159.rundml.core.sql.impl;

import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.expressions.IMathExpression;
import com.bobman159.rundml.core.sql.ICaseBuilder;
import com.bobman159.rundml.core.sql.ICaseClause;
import com.bobman159.rundml.core.sql.ICaseWhenValue;
import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.sql.conditions.SQLCondition;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Create an SQL CASE statement for use in SQL statements and clauses
 *
 */
public class CaseClauseBuilder implements ICaseBuilder {
	
	private ICaseClause caseClause;		//temp storage for CASE model object
	private ICaseWhenValue   tempWhenValue;			//temp storage for WHEN value
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilderFactory#caseClause()
	 */
	public CaseClauseBuilder() {
		caseClause();
		tempWhenValue = null;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilderFactory#caseClause(ISQLType)
	 */
	public CaseClauseBuilder(ISQLType caseValue) {
		caseClause(caseValue);
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilderFactory#caseClause(IMathExpression)
	 */
	public CaseClauseBuilder(IExpressionNode mathExpr) {
		if (mathExpr instanceof IExpressionNode) {
			caseClause((ISQLType) mathExpr);
		} else {
			throw new IllegalArgumentException("IMathExpression implementation must be sub class of IExpressionNode");
		}
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#caseClause()
	 */
	@Override
	public ICaseBuilder caseClause() {
		caseClause = new CaseClause();
		return this;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#caseClause(ISQLType)
	 */
	@Override
	public ICaseBuilder caseClause(ISQLType caseValue) {
		caseClause = new CaseClause(caseValue);
		return this;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#caseClause(IMathExpression)
	 */
	@Override
	public ICaseBuilder caseClause(IExpressionNode mathExpr) {
		caseClause = new CaseClause(mathExpr);
		return this;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#caseWhenClause()
	 */
	@Override
	public ICaseBuilder caseWhenClause() {		//NOSONAR
		caseClause = new CaseClause();			
		return this;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#when(ISQLType)
	 */
	@Override
	public ICaseBuilder when(ISQLType whenValue) {
		tempWhenValue = whenValue;
		return this;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#when(IMathExpression)
	 */
	@Override
	public ICaseBuilder when(IExpressionNode whenExpr) {
		if (whenExpr instanceof ISQLType) {
			tempWhenValue = whenExpr;
		} else {
			throw new IllegalArgumentException("mathExpr must be inherited from ISQLType");
		}
		return this;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#when(ISQLType, Op, ISQLType)
	 */
	@Override
	public ICaseBuilder when(ISQLType lhsValue, Op oper, ISQLType rhsValue) {
		ISQLCondition tempCond = SQLCondition.createSQLCondition(lhsValue);
		tempWhenValue = SQLCondition.createSQLCondition(tempCond, oper, rhsValue);
		return this;
	}

	@Override
	public ICaseBuilder when(IExpressionNode lhsValue, Op oper, ISQLType rhsValue) {
		ISQLCondition tempCond = SQLCondition.createSQLCondition(lhsValue);
		tempWhenValue = SQLCondition.createSQLCondition(tempCond, oper, rhsValue);
		return this;
	}

	@Override
	public ICaseBuilder when(IExpressionNode lhsValue, Op oper, IExpressionNode rhsValue) {
		ISQLCondition tempCond = SQLCondition.createSQLCondition(lhsValue);
		tempWhenValue = SQLCondition.createSQLCondition(tempCond, oper, rhsValue);
		return this;
	}


	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#then(ISQLType)
	 */
	@Override
	public ICaseBuilder then(ISQLType thenValue) {
		caseClause.setWhenThen(tempWhenValue,thenValue);
		return this;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#then(IMathExpression)
	 */
	@Override
	public ICaseBuilder then(IExpressionNode thenExpr) {
		if (thenExpr instanceof ISQLType) {
			caseClause.setWhenThen(tempWhenValue, (ISQLType) thenExpr);
		} else {
			throw new IllegalArgumentException("thenExpr must be inherited from ISQLType");
		}
		return this;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#elseClause(ISQLType)
	 */
	@Override
	public CaseClauseBuilder elseClause(ISQLType elseExpr) {
		caseClause.setElse(elseExpr);
		return this;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#elseClause(IMathExpression)
	 */
	@Override
	public CaseClauseBuilder elseClause(IExpressionNode elseExpr) {
		if (elseExpr instanceof ISQLType) {
			caseClause.setElse((ISQLType) elseExpr);
		} else {
			throw new IllegalArgumentException("elseExpr must be inherited from ISQLType");
		}
		return this;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.ICaseBuilder#end()
	 */
	public ICaseClause end() {
		//Does nothing, implemented for readability during coding
		return caseClause;
	}

}
