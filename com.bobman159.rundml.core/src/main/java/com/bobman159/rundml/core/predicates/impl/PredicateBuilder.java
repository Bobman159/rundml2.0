package com.bobman159.rundml.core.predicates.impl;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.predicates.IPredicateClauseStep;
import com.bobman159.rundml.core.predicates.IPredicateComparisonStep;
import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.sql.conditions.SQLCondition;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Builder class for generating SQL predicate conditions.
 *
 */
public class PredicateBuilder {

	/**
	 * Define the build predicate step, the last step.
	 *
	 */
	public interface PredBuildStep {
		/**
		 * Finalize the current predicate clause(s) being generated
		 * @return the generated predicate
		 */
		public PredicatesList build();
	}
	
	/**
	 * Create an SQL WHERE clause predicate
	 * @param leftPred expression for the WHERE
	 * @return predicate builder next step
	 */
	public static IPredicateComparisonStep where(ISQLType leftPred) {
		return new PredicateSteps(PredOperand.WHERE,leftPred);
	}
	
	/**
	 * Create an SQL WHERE clause predicate
	 * @param leftPred expression for the WHERE
	 * @return predicate builder next step
	 */
	public static IPredicateComparisonStep where(Number leftPred) {
		return new PredicateSteps(PredOperand.WHERE,Expression.constant(leftPred));
	}
	
	/**
	 * Create an SQL WHERE clause predicate
	 * @param leftPred expression for the WHERE
	 * @return predicate builder next step
	 */
	public static IPredicateComparisonStep where(String leftPred) {
		return new PredicateSteps(PredOperand.WHERE,Expression.constant(leftPred));
	}
	
	/**
	 * Create an SQL HAVING clause predicate
	 * @param expr expression for the HAVING
	 * @return predicate builder next step
	 */
	public static IPredicateComparisonStep having(ISQLType expr) {
		return new PredicateSteps(PredOperand.HAVING,expr);
	}
	
	/**
	 * Create an SQL HAVING clause predicate
	 * @param leftPred expression for the HAVING
	 * @return predicate builder next step
	 */
	public static IPredicateComparisonStep having(Number leftPred) {
		return new PredicateSteps(PredOperand.HAVING,Expression.constant(leftPred));
	}
	
	/**
	 * Create an SQL HAVING clause predicate
	 * @param leftPred expression for the HAVING
	 * @return predicate builder next step
	 */
	public static IPredicateComparisonStep having(String leftPred) {
		return new PredicateSteps(PredOperand.HAVING,Expression.constant(leftPred));
	}
	
	/**
	 * PredicateBuilder clause builder class
	 *
	 */
	public static class PredicateSteps implements IPredicateClauseStep,
												  IPredicateComparisonStep,
												  PredBuildStep {
		
		/* Could define incomplete PredicateClause and SQLCondition instancances, but
		 * this would necessitate 2 different objects being created for each 1x for the 
		 * partial definition, 2nd time for completion.  SO, I chose to keep track 
		 * of the fields in the builder to avoid unnecessary object creation.
		 * 
		 * ASSUME: There is only one clause being created at any one time.
		 */

		PredicatesList predList = null;
		
		/**
		 * Define an SQL predicate builder and the initial WHERE or HAVING clause
		 * @param predOp predicate operand WHERE or HAVING
		 * @param predExpr expression for the SQL WHERE clause
		 */
		public PredicateSteps(PredOperand predOp,ISQLType predExpr) {

			predList = new PredicatesList();
			predList.addPredicate(PredicateClause.createPredicate(predOp, SQLCondition.createSQLCondition(predExpr)));

		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateClauseStep#and(Object) 
		 */
		@Override
		public IPredicateComparisonStep and(ISQLType expr) {
			
			predList.addPredicate(PredicateClause.createPredicate(PredOperand.AND,expr));
			return this;
		}
		
		/**
		 * Create an SQL AND clause predicate
		 * @param leftPred expression for the AND
		 * @return predicate builder next step
		 */
		@Override
		public IPredicateComparisonStep and(Number leftPred) {
			predList.addPredicate(PredicateClause.createPredicate(PredOperand.AND,
					Expression.constant(leftPred)));
			return this;
		}
		
		/**
		 * Create an SQL OR clause predicate
		 * @param leftPred expression for the OR
		 * @return predicate builder next step
		 */
		@Override
		public IPredicateComparisonStep and(String leftPred) {

				predList.addPredicate(PredicateClause.createPredicate(PredOperand.OR,
									  Expression.constant(leftPred)));
				return this;		
		}
		
		/**
		 * Create an SQL OR clause predicate
		 * @param leftPred expression for the OR
		 * @return predicate builder next step
		 */
		@Override
		public IPredicateComparisonStep or(Number leftPred) {
			predList.addPredicate(PredicateClause.createPredicate(PredOperand.OR,Expression.constant(leftPred)));
			return this;
		}
		
		/**
		 * Create an SQL OR clause predicate
		 * @param leftPred expression for the OR
		 * @return predicate builder next step
		 */
		@Override
		public IPredicateComparisonStep or(String leftPred) {
			predList.addPredicate(PredicateClause.createPredicate(PredOperand.OR,Expression.constant(leftPred)));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateClauseStep
		 */
		@Override
		public IPredicateComparisonStep or(ISQLType expr) {
			predList.addPredicate(PredicateClause.createPredicate(PredOperand.OR,expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isEqual(Number expr) {

			addCompletePredicate(predList.getLastPredicate(),Op.EQ,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isEqual(String expr) {
			
			addCompletePredicate(predList.getLastPredicate(),Op.EQ,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isEqual(ISQLType expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.EQ,expr);
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isGreater(Object)
		 */
		@Override
		public IPredicateClauseStep isGreater(Number expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.GT,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isGreater(Object)
		 */
		@Override
		public IPredicateClauseStep isGreater(String expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.GT,Expression.constant(expr));
			return this;
		}	

		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isGreater(Object)
		 */
		@Override
		public IPredicateClauseStep isGreater(ISQLType expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.GT,expr);
			return this;
		}

		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isGreaterOrEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isGreaterOrEqual(Number expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.GTE,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isGreaterOrEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isGreaterOrEqual(String expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.GTE,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isGreaterOrEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isGreaterOrEqual(ISQLType expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.GTE,expr);
			return this;
		}

		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isLess(Object)
		 */
		@Override
		public IPredicateClauseStep isLess(Number expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.LT,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isLess(Object)
		 */
		@Override
		public IPredicateClauseStep isLess(String expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.LT,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isLess(Object)
		 */
		@Override
		public IPredicateClauseStep isLess(ISQLType expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.LT,expr);
			return this;
		}

		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isLessOrEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isLessOrEqual(Number expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.LTE,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isLessOrEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isLessOrEqual(String expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.LTE,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isLessOrEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isLessOrEqual(ISQLType expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.LTE,expr);
			return this;
		}

		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isNot(Object)
		 */
		@Override
		public IPredicateClauseStep isNot(Number expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.NOT,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isNot(Object)
		 */
		@Override
		public IPredicateClauseStep isNot(String expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.NOT,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isNot(Object)
		 */
		@Override
		public IPredicateClauseStep isNot(ISQLType expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.NOT,expr);
			return this;
		}

		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isNotEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isNotEqual(Number expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.NOTEQ,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isNotEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isNotEqual(String expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.NOTEQ,Expression.constant(expr));
			return this;
		}
		
		/**
		 * @see com.bobman159.rundml.core.predicates.IPredicateComparisonStep#isNotEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isNotEqual(ISQLType expr) {
			addCompletePredicate(predList.getLastPredicate(),Op.NOTEQ,expr);
			return this;
		}
		
		/**
		 * @see PredBuildStep#build()
		 */		
		@Override 
		public PredicatesList build() {

			return predList;
		}
		
		/*
		 * Creates a complete predicate clause 
		 */
		private void addCompletePredicate(IPredicate predClause,Op operator,ISQLType predRight) {
			
			ISQLCondition fullCond = SQLCondition.createSQLCondition(predClause.getCondition(), 
					operator, predRight);
			IPredicate wkPred = predList.getLastPredicate();
			wkPred.setCondition(fullCond);
		}
		
	}

}
