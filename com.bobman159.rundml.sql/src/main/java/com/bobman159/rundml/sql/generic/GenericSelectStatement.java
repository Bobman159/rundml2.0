package com.bobman159.rundml.sql.generic;
/**
 * Definition(s) for SQL SELECT statement
 * Different DBMS's (SQL dialects) should sub class this class to obtain it's functionality.
 *
 */

import java.util.List;

import com.bobman159.rundml.common.model.ISelectStatement;
import com.bobman159.rundml.common.serialize.AbstractStatementSerializer;
import com.bobman159.rundml.common.serialize.SQLStatementSerializerService;
import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.expressions.IExpressionList;
import com.bobman159.rundml.core.mapping.exceptions.NoTableRowClassFieldException;
import com.bobman159.rundml.core.model.ISQLStatement;
import com.bobman159.rundml.core.model.impl.CoreModelFactory;
import com.bobman159.rundml.core.model.mapping.FieldMap;
import com.bobman159.rundml.core.model.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.model.mapping.IFieldMapDefinition;
import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.predicates.IPredicatesList;
import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.IOrderByList;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;
import com.bobman159.rundml.core.sql.types.impl.Table;


/** Base Model class for SELECT SQL statements. 
 * Supports SELECT syntax common to SELECTs. 
 * 
 */
public class GenericSelectStatement implements ISelectStatement {
	
	private boolean allClause = false;
	private boolean distinctClause = false;
	private IExpressionList selectExpressions = null;
	private Table fromTable = null;
	private IPredicatesList wherePredicates = null;
	private IPredicatesList havingPredicates = null;
	private IOrderByList orderByList = null;
	private IExpressionList groupByExpressions = null;

	public GenericSelectStatement() {
		selectExpressions = CoreModelFactory.getInstance().createExpressionList();
	}
	
	@Override
	public void setAll() {
		allClause = true;
	}

	@Override
	public boolean hasAll() {
		return allClause;
	}

	@Override
	public void setDistinct() {
		distinctClause = true;
	}

	@Override
	public boolean hasDistinct() {
		return distinctClause;
	}

	@Override
	public void addSelectExpressions(Class<?> clazz) throws RunDMLException {
		
		FieldMap fieldMap = FieldMap.findFieldMap(clazz);
		if (fieldMap == null) {
			try {
				fieldMap = CoreModelFactory.getInstance().createFieldMap(clazz);
			} catch (NoTableRowClassFieldException e) {
				throw RunDMLException.createRunDMLException(e, RunDMLException.SQL_MODEL_BUILD);
			}
		}
		FieldMapDefinitionList defsList = fieldMap.getFieldDefinitions();
		Object[] fieldDefs = defsList.getFieldDefinitions().toArray();
		ISQLType col = null;
		for (Object fieldDef : fieldDefs) {
			if (fieldDef instanceof IFieldMapDefinition) {
				IFieldMapDefinition wkFieldDef = (IFieldMapDefinition) fieldDef;
				col = SQLTypeFactory.getInstance().column(wkFieldDef.getColumnName());
			}
			selectExpressions.addExpression(col);
		}
		
	}

	@Override
	public void addSelectExpression(String... columns) {
		for (String column : columns) {
			ISQLType newColumn = SQLTypeFactory.getInstance().column(column);
			selectExpressions.addExpression(newColumn);
		}	
	}

	@Override
	public List<ISQLType> getSelectExpressions() {
		return selectExpressions.getExpressions();
	}
	
	
	/**
	 * Add a SELECT value to the select h2Model
	 * @param selectExpr the value to be added
	 */
	@Override
	public void addSelectExpression(ISQLType selectExpr) {
		selectExpressions.addExpression(selectExpr);
	}
	
	/**
	 * Add a list of SELECT value, value, value... to the select h2Model
	 * @param selectExprs the list of values to be added
	 */
	public void addSelectExpression(ISQLType... selectExprs) {
		selectExpressions.addExpressions(selectExprs);
	}

	@Override
	public void addFrom(Table tableName) {
		fromTable = tableName;
	}

	@Override
	public Table getFrom() {
		return fromTable;
	}

	@Override
	public void setWhere(IPredicatesList predList) {
		wherePredicates = predList;
	}

	@Override
	public List<IPredicate> getWhere() {
		List<IPredicate> wherePreds = null;
		if (wherePredicates != null) {
			wherePreds = wherePredicates.getPredicates();
		}
		return wherePreds;
	}

	@Override
	public void setHaving(IPredicatesList havingList) {
		havingPredicates = havingList;
	}

	@Override
	public List<IPredicate> getHavingPredicates() {
		List<IPredicate> havingPreds = null;
		if (havingPredicates != null) {
			havingPreds = havingPredicates.getPredicates();
		}
		return havingPreds;
	}
	
	/**
	 * Add a list of SELECT value, value, value... to the select h2Model
	 * @param orderByEntries the list of values to be added
	 */
	@Override
	public void addOrderByEntry(IOrderByEntry... orderByEntries) {
		if (orderByList == null) {
			orderByList = 	CoreModelFactory.getInstance().createOrderByList();
		}
		orderByList.addOrderByClause(orderByEntries);
		
	}

	@Override
	public List<IOrderByEntry> getOrderBys() {
		List<IOrderByEntry> orderBys = null;
		
		if (orderByList != null) {
			orderBys = orderByList.getOrderBys();
		}
		return orderBys;
	}

	@Override
	public void addGroupByExpression(ISQLType... groupByValues) {
		
		if (groupByExpressions == null) {
			groupByExpressions = CoreModelFactory.getInstance().createExpressionList();
		}
		groupByExpressions.addExpressions(groupByValues);
	}

	@Override
	public List<ISQLType> getGroupByValues() {
		List<ISQLType> groupBys = null;
		if (groupByExpressions != null) {
			groupBys = groupByExpressions.getExpressions();
		}
		return groupBys;
	}

	@Override
	public ISQLStatement.Statement getStatement() {
		return ISQLStatement.Statement.SELECT;
	}
	
	@Override
	public String getDialect() {
		return AbstractStatementSerializer.GENERIC_SELECT;
	}

	@Override
	public String toSQL() {
		String sql = "";
		sql = SQLStatementSerializerService.getInstance().serializeSelect(this);
		return sql;
	}

}
