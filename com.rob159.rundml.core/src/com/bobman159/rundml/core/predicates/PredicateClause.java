package com.bobman159.rundml.core.predicates;

import com.bobman159.rundml.core.exprtypes.ExprTypeHelper;
import com.bobman159.rundml.core.exprtypes.IExpression;

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
		AND("AND"),
		HAVING("HAVING"),
		OR("OR"),
		WHERE("WHERE");
		
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
	 * @see com.bobman159.rundml.core.predicates.IPredicate#serialize()
	 */
	@Override
	public String serialize() {
		String pred = "";
		return pred + predicate.getPredicate() + " " + exprLhs.serialize() + " ";
	}

}
