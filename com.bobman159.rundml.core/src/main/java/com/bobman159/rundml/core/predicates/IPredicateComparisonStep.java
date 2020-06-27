package com.bobman159.rundml.core.predicates;

import com.bobman159.rundml.core.sql.types.ISQLType;

public interface IPredicateComparisonStep {

	/**
	 * Define the build predicate comparison steps (isEqual,isLess,isLessOrEqual)
	 * the second step
	 *
	 */
		
		/**
		 * Define a equals ("=") comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isEqual(Number expr);
		
		/**
		 * Define a equals ("=") comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isEqual(String expr);

		/**
		 * Define a equals ("=") comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isEqual(ISQLType expr);
		
		/**
		 * Define a greater than (&gt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isGreater(Number expr);
		
		/**
		 * Define a greater than (&gt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isGreater(String expr);

		/**
		 * Define a greater than (&gt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isGreater(ISQLType expr);
				
		/**
		 * Define a greater than or equal to (&gt;=) comparison for the current 
		 * predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isGreaterOrEqual(Number expr);
		
		/**
		 * Define a greater than or equal to (&gt;=) comparison for the current 
		 * predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isGreaterOrEqual(String expr);
		
		/**
		 * Define a greater than or equal to (&gt;=) comparison for the current 
		 * predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isGreaterOrEqual(ISQLType expr);

		/**
		 * Define a less than (&lt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder 
		 */
		public IPredicateClauseStep isLess(Number expr);
		
		/**
		 * Define a less than (&lt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder 
		 */
		public IPredicateClauseStep isLess(String expr);
		
		/**
		 * Define a less than (&lt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder 
		 */
		public IPredicateClauseStep isLess(ISQLType expr);
		
		/**
		 * Define a equals (&gt;=) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isLessOrEqual(Number expr);
		
		/**
		 * Define a equals (&gt;=) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isLessOrEqual(String expr);
		
		/**
		 * Define a equals (&gt;=) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isLessOrEqual(ISQLType expr);
		
		
		/**
		 * Define a not ("!") comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isNot(Number expr);
		
		/**
		 * Define a not ("!") comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isNot(String expr);
		
		/**
		 * Define a not ("!") comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isNot(ISQLType expr);
		
		
		/**
		 * Define a not equals (&lt;&gt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isNotEqual(Number expr);
		
		/**
		 * Define a not equals (&lt;&gt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isNotEqual(String expr);
		
		/**
		 * Define a not equals (&lt;&gt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isNotEqual(ISQLType expr);

}
