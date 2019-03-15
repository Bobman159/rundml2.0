package com.bobman159.rundml.sql.select.steps;

import java.util.List;

import com.bobman159.rundml.core.exprtypes.IExpression;

public interface H2SelectOrderStep {
	
	public H2SelectOrderStep orderBy(IExpression orderByExpr);
	public H2SelectOrderStep groupBy(IExpression groupByExpr);		
	public H2SelectOrderStep having(IExpression havingExpr);
	public H2SelectOrderStep limit(IExpression limitTerm);
	public List<String> fetch();

}
