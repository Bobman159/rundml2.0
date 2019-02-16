package net.bobs.own.db.rundml.sql.builders.predicates;

public interface IPredicateComparisonStep {

	/**
	 * Define the build predicate comparison steps (isEqual,isLess,isLessOrEqual)
	 * the second step
	 *
	 */

		/**
		 * Define a equals ("=") comparison for the current predicate
		 * @param expr - expression on right of comparison
		 * @return - predicate builder
		 */
		public IPredicateClauseStep isEqual(Object expr);

		
		/**
		 * Define a greater than (">") comparison for the current predicate
		 * @param expr - expression on right of comparison
		 * @return - predicate builder
		 */
		public IPredicateClauseStep isGreater(Object expr);
		
		/**
		 * Define a greater than or equal to (">=") comparison for the current 
		 * predicate
		 * @param expr - expression on right of comparison
		 * @return - predicate builder
		 */
		public IPredicateClauseStep isGreaterOrEqual(Object expr);
		
		/**
		 * Define a less than ("<") comparison for the current predicate
		 * @param expr - expression on right of comparison
		 * @return - predicate builder 
		 */
		public IPredicateClauseStep isLess(Object expr);
		
		/**
		 * Define a equals ("<=") comparison for the current predicate
		 * @param expr - expression on right of comparison
		 * @return - predicate builder
		 */
		public IPredicateClauseStep isLessOrEqual(Object expr);
		
		/**
		 * Define a not ("!") comparison for the current predicate
		 * @param exprRhs - expression on right of comparison
		 * @return - predicate builder
		 */
		public IPredicateClauseStep isNot(Object expr);
		
		/**
		 * Define a not equals ("<>") comparison for the current predicate
		 * @param exprRhs - expression on right of comparison
		 * @return - predicate builder
		 */
		public IPredicateClauseStep isNotEqual(Object expr);

}
