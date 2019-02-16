package net.bobs.own.db.rundml.sql.builders.predicates;

import net.bobs.own.db.rundml.sql.condition.Op;
import net.bobs.own.db.rundml.sql.predicates.IPredicate;
import net.bobs.own.db.rundml.sql.predicates.PredicateClause;
import net.bobs.own.db.rundml.sql.predicates.PredicateClause.PredOperand;
import net.bobs.own.db.rundml.sql.predicates.PredicateComparison;

/**
 * Factory class for generating SQL predicate conditions.
 *
 */
public class Predicate implements IPredicate {

	private PredicatesList predList;
	
	/**
	 * Define all the current predicate conditions
	 * 
	 * @param predList - current defined predicate list
	 */
	public Predicate(PredicatesList predList) {
		this.predList = predList;
	}
	
	/**
	 * @see net.bobs.own.db.rundbml.sql.predicates.IPredicate#serialize
	 */
	@Override
	public String serialize() {
		return predList.serialize();
	}
	
	/**
	 * Define the build predicate step, the last step.
	 *
	 */
	public interface PredBuildStep {
		/**
		 * Finalize the current predicate clause(s) being generated
		 * @return - the generated predicate
		 */
		public Predicate build();
	}
	
	/**
	 * Create an SQL WHERE clause predicate
	 * @param expr - expression for the WHERE
	 * @return - predicate builder next step
	 */
	public static IPredicateComparisonStep where(Object expr) {
		return new PredicateSteps(expr);
	}
	
	/**
	 * Predicate clause builder class
	 *
	 * @param <T>
	 */
	public static class PredicateSteps implements //PredWhereStep,
												  IPredicateClauseStep,
												  IPredicateComparisonStep,
												  PredBuildStep {
		
		private PredicatesList predList = null;
		
		/**
		 * Define an SQL predicate builder and the initial WHERE clause 
		 * @param whereExpr - expression for the SQL WHERE clause
		 */
		public PredicateSteps(Object whereExpr) {
			predList = new PredicatesList();
			predList.addPredicate(new PredicateClause(PredOperand.WHERE,whereExpr));
			
		}
		
		/**
		 * @see net.bobs.own.db.rundml.sql.builders.predicates.IPredicateClauseStep#and(Object) 
		 */
		@Override
		public IPredicateComparisonStep and(Object expr) {
			PredicateClause clause = new PredicateClause(PredOperand.AND,expr);
			predList.addPredicate(clause);
			return this;
		}
		
		/**
		 * @see net.bobs.own.db.rundml.sql.builders.predicates.IPredicateClauseStep
		 */
		@Override
		public IPredicateComparisonStep or(Object expr) {
			PredicateClause clause = new PredicateClause(PredOperand.OR,expr);
			predList.addPredicate(clause);
			return this;
		}
		
		/**
		 * @see net.bobs.own.db.rundml.sql.builders.predicatesIPredicateComparisonStep#isEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isEqual(Object expr) {
			PredicateComparison compare = new PredicateComparison(Op.EQ,expr);
			predList.addPredicate(compare);
			return this;
		}
		
		/**
		 * @see net.bobs.own.db.rundml.sql.builders.predicatesIPredicateComparisonStep#isGreater(Object)
		 */
		@Override
		public IPredicateClauseStep isGreater(Object expr) {
			PredicateComparison compare = new PredicateComparison(Op.GT,expr);
			predList.addPredicate(compare);			
			return this;
		}

		/**
		 * @see net.bobs.own.db.rundml.sql.builders.predicatesIPredicateComparisonStep#isGreaterOrEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isGreaterOrEqual(Object expr) {
			PredicateComparison compare = new PredicateComparison(Op.GTE,expr);
			predList.addPredicate(compare);			
			return this;
		}

		/**
		 * @see net.bobs.own.db.rundml.sql.builders.predicatesIPredicateComparisonStep#isLess(Object)
		 */
		@Override
		public IPredicateClauseStep isLess(Object expr) {
			PredicateComparison compare = new PredicateComparison(Op.LT,expr);
			predList.addPredicate(compare);						
			return this;
		}

		/**
		 * @see net.bobs.own.db.rundml.sql.builders.predicatesIPredicateComparisonStep#isLessOrEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isLessOrEqual(Object expr) {
			PredicateComparison compare = new PredicateComparison(Op.LTE,expr);
			predList.addPredicate(compare);						
			return this;
		}

		/**
		 * @see net.bobs.own.db.rundml.sql.builders.predicatesIPredicateComparisonStep#isNot(Object)
		 */
		@Override
		public IPredicateClauseStep isNot(Object expr) {
			PredicateComparison compare = new PredicateComparison(Op.NOT,expr);
			predList.addPredicate(compare);									
			return this;
		}

		/**
		 * @see net.bobs.own.db.rundml.sql.builders.predicatesIPredicateComparisonStepp#isNotEqual(Object)
		 */
		@Override
		public IPredicateClauseStep isNotEqual(Object expr) {
			PredicateComparison compare = new PredicateComparison(Op.NOTEQ,expr);
			predList.addPredicate(compare);									
			return this;
		}
		
		/**
		 * @see PredBuildStep#build()
		 */		
		@Override 
		public Predicate build() {
			return new Predicate(predList);
		}
		
	}

}
