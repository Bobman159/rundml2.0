package com.bobman159.rundml.core.predicates;

import com.bobman159.rundml.core.sql.types.ExprTypeHelper;
import com.bobman159.rundml.core.sql.types.ISQLType;

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
		 * @return the predicate as a <Code>String</code>
		 */
		public String getPredicate() {
			return this.operand;
		}
	}
	private ISQLType exprLhs;
	private PredOperand predicate = PredOperand.WHERE;

	/**
	 * Define an SQL WHERE, AND or OR clause
	 * 
	 * @param predicate type of predicate
	 * @param lhs expression for the predicated
	 * 
	 */
	public PredicateClause(PredOperand predicate, Object lhs) {
		this.predicate = predicate;
		exprLhs = ExprTypeHelper.convertExpression(lhs);
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @see com.bobman159.rundml.core.predicates.IPredicate#serialize()
	 */
//	@Override
//	public String serialize() {
//		String pred = "";
//		return pred + predicate.getPredicate() + " " + exprLhs.serialize() + " ";
//	}

}
