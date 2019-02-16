package net.bobs.own.db.rundml.sql.predicates;

import net.bobs.own.db.rundml.sql.expression.types.ExprTypeHelper;
import net.bobs.own.db.rundml.sql.expression.types.IExpression;

/**
 * Represents a predicate WHERE, AND, OR clause in an SQL statement.
 *
 */
public class PredicateClause implements IPredicate {

	/**
	 * Enumeration for predicates supported by the builder
	 *
	 */
	public enum PredOperand {
		WHERE(" WHERE "),
		AND( " AND "),
		OR(" OR ");
		
		private String operand;
		
		private PredOperand(String operand) {
			this.operand = operand;
		}
		
		/**
		 * Returns the string representation for the predicate
		 * @return - the predicate as a <Code>String</code>
		 */
		public String getPredicate() {
			return this.operand;
		}
	}
	private IExpression exprLhs;
	private PredOperand predicate = PredOperand.WHERE;

	/**
	 * Define an SQL WHERE, AND or OR clause
	 * 
	 * @param predicate - type of predicate
	 * @param lhs - expression for the predicated
	 * 
	 */
	public PredicateClause(PredOperand predicate, Object lhs) {
		this.predicate = predicate;
		exprLhs = ExprTypeHelper.convertExpression(lhs);
	}


	/**
	 * @see net.bobs.own.db.rundml.sql.predicates.IPredicate#serialize()
	 */
	@Override
	public String serialize() {
		String pred = "";
		return pred + predicate.getPredicate() + exprLhs.serialize();
	}

}
