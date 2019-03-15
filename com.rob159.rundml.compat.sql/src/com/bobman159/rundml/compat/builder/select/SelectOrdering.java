package com.bobman159.rundml.compat.builder.select;

import com.bobman159.rundml.compat.builder.select.SelectClauses.SelectClause;
import com.bobman159.rundml.core.exprtypes.IExpression;

public abstract class SelectOrdering<T,S extends SelectOrdering<T,S>> 
		extends SelectFetchableResults<S>
{
	@SuppressWarnings("unchecked")
	public S groupBy(IExpression groupByExpr) {
		model.addClause(SelectClause.GROUPBY,groupByExpr);
		System.out.println("SelectWhere groupBy method");
		return (S) this;
	}
		
	@SuppressWarnings("unchecked")
	public S having(IExpression havingExpr) {
		model.addClause(SelectClause.HAVING,havingExpr);
		System.out.println("SelectWhere having method");
		return (S) this;
	}

}
