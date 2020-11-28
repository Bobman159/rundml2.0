package com.bobman159.rundml.common.model;

import com.bobman159.rundml.core.expressions.IExpressionList;
import com.bobman159.rundml.core.mapping.exceptions.NoTableRowClassFieldException;
import com.bobman159.rundml.core.model.mapping.FieldMap;
import com.bobman159.rundml.core.model.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.model.mapping.IFieldMapDefinition;
import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.predicates.IPredicatesList;
import com.bobman159.rundml.core.predicates.impl.PredOperand;
import com.bobman159.rundml.core.sql.IOrderByList;
import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Interface for Factory methods to create RunDML model classes *
 */
public interface ICoreModelFactory {

	/**
	 * 
	 * @return a SQL ORDER BY list of clauses model
	 */
	public IOrderByList createOrderByList();
	
	/**
	 * 
	 * @return a SQL predicate (WHERE AND OR clause(s) list
	 */
	public IPredicatesList createPredicateList();
	
	/**
	 * Define a complete SQL WHERE, AND or OR clause
	 * 
	 * @param predClause type of predicate
	 * @param condition the SQL condition for the predicate
	 * @return the predicate model definition
	 * 
	 */
	public IPredicate createPredicate(PredOperand predClause,ISQLCondition condition);
	
	/**
	 * Defines a partial SQL WHERE, AND or OR clause with a partial SQL Condition
	 * @param predOperand type of clause being defined
	 * @param leftPred the left side of the SQL predicate
	 * @return the predicate model definition
	 */
	public IPredicate createPredicate(PredOperand predOperand, ISQLType leftPred);
	
	/**
	 * 
	 * @return a SQL expression(s) list
	 */
	public IExpressionList createExpressionList();
	
	/**
	 * Creates a field map definition for a given java table row class
	 * @param tableRowClass the table row class to create a field map 
	 * @return the field map definition
	 * @throws NoTableRowClassFieldException when the clas name was not found
	 */
	public FieldMap createFieldMap(Class<?> tableRowClass) throws NoTableRowClassFieldException;
	
	
	/**
	 * Creates a field map definition entry for a java table row class
	 * @param columnName the column name to be be mapped
	 * @param classField the class field name to be mapped
	 * @return the field map definition entry.
	 */
	public IFieldMapDefinition createFieldMapDefinition(String columnName, String classField);

	/**
	 * Create a list for Field Map definitions.
	 * @return the field map definition list
	 */
	public FieldMapDefinitionList createFieldMapDefinitionList();

}
