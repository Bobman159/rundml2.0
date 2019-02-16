package net.bobs.own.db.rundml.sql.predicates;

import net.bobs.own.db.rundml.sql.condition.Op;
import net.bobs.own.db.rundml.sql.expression.types.ExprTypeHelper;
import net.bobs.own.db.rundml.sql.expression.types.IExpression;

/**
 * Represents the comparison (<, > etc) clause for an SQL predicate 
 * WHERE, AND or OR clause.
 *
 */
public class PredicateComparison implements IPredicate {
	
	private IExpression exprRhs;
	private Op compareOp;

	/**
	 * Define a predicate comparison clause
	 * @param compareOp - the comparison operator (<, >, etc)
	 * @param expr - the right side expression of the comparison.
	 * 
	 * @see net.bobs.own.db.rundml.sql.condition.Op
	 */
	public PredicateComparison(Op compareOp, Object expr) {
		exprRhs = ExprTypeHelper.convertExpression(expr);
		this.compareOp = compareOp;
	}

	@Override
	public String serialize() {
		return compareOp.getOperator() + exprRhs.serialize();
	}
	
	

}
