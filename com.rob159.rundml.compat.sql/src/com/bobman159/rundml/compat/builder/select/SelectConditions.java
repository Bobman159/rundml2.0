package com.bobman159.rundml.compat.builder.select;

import com.bobman159.rundml.compat.builder.select.SelectClauses.SelectClause;
import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.Predicate;

public abstract class SelectConditions<T,S extends SelectConditions<T,S>>
				extends SelectOrdering<T,S>

{

	@SuppressWarnings("unchecked")
	public T where(Predicate pred) {
		model.addClause(SelectClause.WHERE,pred);
		System.out.println("SelectWhere where method");
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public S orderBy(IExpression orderByExpr) {
		model.addClause(SelectClause.ORDERBY,orderByExpr);
		System.out.println("SelectWhere orderBy method");
		return (S)this;		
	}

}
