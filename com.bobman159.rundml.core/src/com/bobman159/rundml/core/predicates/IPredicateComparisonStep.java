package com.bobman159.rundml.core.predicates;

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
		public IPredicateClauseStep isEqual(Object expr);

		
		/**
		 * Define a greater than (&gt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isGreater(Object expr);
		
		/**
		 * Define a greater than or equal to (&gt;=) comparison for the current 
		 * predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isGreaterOrEqual(Object expr);
		
		/**
		 * Define a less than (&lt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder 
		 */
		public IPredicateClauseStep isLess(Object expr);
		
		/**
		 * Define a equals (&gt;=) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isLessOrEqual(Object expr);
		
		/**
		 * Define a not ("!") comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isNot(Object expr);
		
		/**
		 * Define a not equals (&lt;&gt;) comparison for the current predicate
		 * @param expr expression on right of comparison
		 * @return predicate builder
		 */
		public IPredicateClauseStep isNotEqual(Object expr);

}
