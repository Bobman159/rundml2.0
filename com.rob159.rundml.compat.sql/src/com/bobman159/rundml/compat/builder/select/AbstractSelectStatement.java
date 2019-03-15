package com.bobman159.rundml.compat.builder.select;

import java.util.List;

import com.bobman159.rundml.compat.builder.select.SelectClauses.SelectClause;
import com.bobman159.rundml.compat.model.SQLStatementModel;
import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.util.RunDMLUtils;

/**
 * The base class for SQL SELECT statement building.  This class should be
 * sub-classed by any class for the purpose of creating a new DBMS dialect/version
 * of the SELECT statement.
 * @param <T>
 *
 * @param <T> - Concrete class type
 */
//public abstract class AbstractSelectStatement
//		<S, T extends AbstractSelectStatement<S,T>> 
//		implements IFetchableResults<T> 
//public abstract class AbstractSelectStatement<T extends AbstractSelectStatement>
@SuppressWarnings("rawtypes")
public abstract class AbstractSelectStatement<AS extends AbstractSelectStatement>
//		<T,C extends AbstractSelectStatement<T,C>>
//		<T extends AbstractSelectStatement>


//<T extends SelectListStep> 
//		extends SelectListStep 
{
	protected SQLStatementModel model;
	@SuppressWarnings("unchecked")
	AS self = (AS) this;

																		

//	protected SQLStatementModel model;
	
	public SQLStatementModel getModel() {
		return model;
	}
	
	@SuppressWarnings("unchecked")
	public AS distinct() {
		model.addClause(SelectClause.DISTINCT);
		System.out.println("AnsiSelect distinct");
		return (AS) this;
	}
	
	@SuppressWarnings("unchecked")
	public AS all() {
		model.addClause(SelectClause.ALL);
		System.out.println("AnsiSelect all");
		return (AS) this;
	}
	
	@SuppressWarnings("unchecked")
	public AS selectExpression() {
		System.out.println("SelectList selectExpression");
		return (AS) this;
	}
	
	@SuppressWarnings("unchecked")
	public AS from(String schema, String tbName) {
		model.addClause(SelectClause.FROM,RunDMLUtils.qualifiedTbName(schema, tbName));
		System.out.println("CompatSelect from");
		return (AS) this;
	}
	
	@SuppressWarnings("unchecked")
	public AS where(Predicate pred) {
		model.addClause(SelectClause.WHERE,pred);
		System.out.println("SelectWhere where method");
		return (AS) this;
	}
	
	@SuppressWarnings("unchecked")
	public AS orderBy(IExpression orderByExpr) {
		model.addClause(SelectClause.ORDERBY,orderByExpr);
		System.out.println("SelectWhere orderBy method");
		return (AS)this;		
	}
	
	@SuppressWarnings("unchecked")
	public AS groupBy(IExpression groupByExpr) {
		model.addClause(SelectClause.GROUPBY,groupByExpr);
		System.out.println("SelectWhere groupBy method");
		return (AS) this;
	}
		
	@SuppressWarnings("unchecked")
	public AS having(IExpression havingExpr) {
		model.addClause(SelectClause.HAVING,havingExpr);
		System.out.println("SelectWhere having method");
		return (AS) this;
	}

	public abstract List<String> fetch();
	
}
