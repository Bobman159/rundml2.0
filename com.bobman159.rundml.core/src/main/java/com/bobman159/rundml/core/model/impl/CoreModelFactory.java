package com.bobman159.rundml.core.model.impl;

import com.bobman159.rundml.core.expressions.IExpressionList;
import com.bobman159.rundml.core.expressions.impl.ExpressionList;
import com.bobman159.rundml.core.mapping.exceptions.NoTableRowClassFieldException;
import com.bobman159.rundml.core.model.ICoreModelFactory;
import com.bobman159.rundml.core.model.mapping.FieldMap;
import com.bobman159.rundml.core.model.mapping.FieldMapDefinition;
import com.bobman159.rundml.core.model.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.model.mapping.IFieldMapDefinition;
import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.predicates.IPredicatesList;
import com.bobman159.rundml.core.predicates.impl.PredOperand;
import com.bobman159.rundml.core.predicates.impl.PredicateClause;
import com.bobman159.rundml.core.predicates.impl.PredicatesList;
import com.bobman159.rundml.core.sql.IOrderByList;
import com.bobman159.rundml.core.sql.impl.OrderByList;
import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.sql.conditions.SQLCondition;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Factory class for RunDML core model classes
 */
public class CoreModelFactory implements ICoreModelFactory {

	private static ICoreModelFactory self;

	/**
	 * Creates a field map definition for a given java table row class
	 * @param tableRowClass the table row class to create a field map 
	 * @return the field map created
	 */
	@Override
	public FieldMap createFieldMap(Class<?> tableRowClass) throws NoTableRowClassFieldException {
		return new FieldMap(tableRowClass);
	}
	
	/**
	 * Creates a field map definition entry for a java table row class
	 * @param columnName the column name to be be mapped
	 * @return the field map definition entry.
	 */
	@Override
	public IFieldMapDefinition createFieldMapDefinition(String columnName, String classField) {
		return new FieldMapDefinition(columnName,classField);
	}
	
	/**
	 * @see com.bobman159.rundml.core.model.ICoreModelFactory#createFieldMapDefinitionList()
	 */
	@Override
	public FieldMapDefinitionList createFieldMapDefinitionList() {
		return new FieldMapDefinitionList();
	}
	
	/**
	 * 
	 * @return a concrete instance of ICoreModelFactory
	 */
	public static ICoreModelFactory getInstance() {
		if (self == null) {
			self = new CoreModelFactory();
		}
		return self;
	}
		
	/**
	 * 
	 * @return a SQL ORDER BY clause list
	 */
	@Override
	public IOrderByList createOrderByList() {
		return new OrderByList();
	}
	
	/**
	 * @return a SQL predicate (WHERE AND OR clauses) list
	 */
	@Override
	public IPredicatesList createPredicateList() {
		return new PredicatesList();
	}
	
	/**
	 * Define a complete SQL WHERE, AND or OR clause
	 * 
	 * @param predClause type of predicate
	 * @param condition the SQL condition for the predicate
	 * 
	 */
	@Override
	public IPredicate createPredicate(PredOperand predClause,ISQLCondition condition) {
		return new PredicateClause(predClause,condition);
	}
	
	/**
	 * Defines a partial SQL WHERE, AND or OR clause with a partial SQL Condition
	 * @param predOperand type of clause being defined
	 * @param leftPred the left side of the SQL predicate
	 */
	@Override
	public IPredicate createPredicate(PredOperand predOperand, ISQLType leftPred) {
		SQLCondition partCond = SQLCondition.createSQLCondition(leftPred);
		return new PredicateClause(predOperand,partCond);
	}
	
	/**
	 * @return a SQL predicate (WHERE AND OR clauses) list
	 */
	@Override
	public IExpressionList createExpressionList() {
		return new ExpressionList();
	}
	


}
