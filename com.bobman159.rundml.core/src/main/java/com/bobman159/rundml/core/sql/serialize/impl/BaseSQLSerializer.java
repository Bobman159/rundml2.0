package com.bobman159.rundml.core.sql.serialize.impl;

import java.util.List;
import java.util.stream.Stream;

import com.bobman159.rundml.core.expressions.IExpressionList;
import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.predicates.IPredicatesList;
import com.bobman159.rundml.core.sql.ICaseClause;
import com.bobman159.rundml.core.sql.ICaseWhenThen;
import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.sql.types.impl.NumericType;
import com.bobman159.rundml.core.sql.types.impl.ParmMarker;
import com.bobman159.rundml.core.sql.types.impl.StringType;
import com.bobman159.rundml.core.sql.types.impl.Table;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * Serialize Java objects into SQL text clauses.
 *
 */
class BaseSQLSerializer {


	protected final String serialize(ISQLType sqlType) {
		String sql = "";
		switch (sqlType.getType()) {
			case COLUMN:
				sql = serialize((Column) sqlType);
				break;
			case NUMERIC:
				sql = serialize((NumericType) sqlType);
				break;
			case PARM_MARKER:
				sql = serialize((ParmMarker) sqlType);
				break;
			case STRING:
				sql = serialize((StringType) sqlType);
				break;
			case TABLE:
				sql = serialize((Table) sqlType);
				break;
			case EXPRESSION:
				sql = serialize((IExpressionNode) sqlType);
				break;
			case CASE:
				sql = serialize((ICaseClause) sqlType);
				break;
			case CONDITION:
				sql = serialize((ISQLCondition) sqlType);
				break;
			case PREDICATE:
				sql = serialize((ISQLCondition) sqlType);
				break;
		}
		
		return CoreUtils.normalizeString(sql);
	}
	
	/**
	 * Serialize an SQL column
	 * @param column an SQL column type
	 * @return an SQL column as text
	 */
	public final String serialize(Column column) {
		
		String sql = "";
		sql = column.getColumnName();	
		return CoreUtils.normalizeString(sql);
		
	}

	/**
	 * Serialize an SQL parameter marker
	 * @param parmMarker an SQL parameter marker type
	 * @return a parameter marker character as text 
	 */
	public final String serialize(ParmMarker parmMarker) {	//NOSONAR
		
		String sql = "";
		sql = "?";
		return CoreUtils.normalizeString(sql);
	
	}

	/**
	 * Serialize an SQL numeric type
	 * @param number an SQL parameter marker type
	 * @return the numeric type as text
	 */
	public final String serialize(NumericType number) {
		
		String sql = "";
		sql = number.getNumber().toString();
		return CoreUtils.normalizeString(sql);
	
	}

	/**
	 * Serialize an SQL numeric type
	 * @param stringValue an SQL parameter marker type
	 * @return the numeric type as text
	 */
	public final String serialize(StringType stringValue) {
		
		String sql = "";
		sql = stringValue.getConstant();
		return CoreUtils.normalizeString(sql);
	
	}
	
	/**
	 * Serialize an SQL numeric type
	 * @param tableName an SQL parameter marker type
	 * @return the parameter marker as text
	 */
	public final String serialize(Table tableName) {
		
		String sql = "";
		sql = tableName.tableName();
		return CoreUtils.normalizeString(sql);
	
	}
	
	/**
	 * Serialize an expression node (math or string) expression into a string SQL expression clause
	 * @param exprNode the node to serialize
	 * @return the expression node as an SQL expression string
	 */
	public final String serialize(IExpressionNode exprNode) {

		StringBuilder sql = new StringBuilder();
				
		if (exprNode.getLeftExpr() instanceof IExpressionNode) {
			sql.append(serialize((IExpressionNode) exprNode.getLeftExpr())).append(" ");
			if (exprNode.getOperator() != null) {
				sql.append(exprNode.getOperator().opToString()).append(" ");
			}
		} else {
			ISQLType exprValue = exprNode.getLeftExpr();
			sql.append(serialize(exprValue)).append(" ");
			if (exprNode.getOperator() != null) {
				sql.append(exprNode.getOperator().opToString()).append(" ");
			}
		}
		
		if (exprNode.getRightExpr() instanceof IExpressionNode) {
			sql.append(serialize((IExpressionNode) exprNode.getRightExpr())).append(" ");
		} else {
			ISQLType exprValue = exprNode.getRightExpr();
			if (exprValue != null) {
				sql.append(serialize(exprValue)).append(" ");
			}
		}
		
		return CoreUtils.normalizeString(sql.toString());
	}
	
	/**
	 * Serialize a list of SQL expressions as comma separated text 
	 * "expr1,expr2,expr3..."
	 * @param exprList a a list of SQL expressions as a <code>IExpressionList</code> 
	 * @return comma separated text string
	 */
	public final String serialize(IExpressionList exprList) {
	
		StringBuilder csvString = new StringBuilder();
		List<ISQLType> csvIterator = exprList.getExpressions();
		
		for (ISQLType exprBase : csvIterator) {
			csvString.append(serialize(exprBase));
			if (csvIterator.indexOf(exprBase) + 1 < csvIterator.size()) {
				csvString.append(",");
			}
		}
		
		return CoreUtils.normalizeString(csvString.toString());
	}
	
	/**
	 * Serialize an SQL predicate (WHERE, AND, OR) into a string SQL clause 
	 * @param predNode the predicate clause to serialize
	 * @return the predicate node as a SQL clause
	 */
	public final String serialize(IPredicate predNode) {
		
		StringBuilder sql = new StringBuilder();
		
		switch (predNode.getPredicateOperation()) {
			case WHERE:
				sql.append(" WHERE").append(" ");
				break;
			case AND:
				sql.append(" AND").append(" ");
				break;
			case OR:
				sql.append(" OR").append(" ");
				break;
			case HAVING:
				sql.append(" HAVING").append(" ");
				break;
		}
			
		ISQLCondition cond = predNode.getCondition();
		sql.append(serialize(cond.getLeftCondition())).append(" ");
		sql.append(cond.getOperator().opToString()).append(" ");
		sql.append(serialize(cond.getRightCondition())).append(" ");
		
		return CoreUtils.normalizeString(sql.toString());
	}

	/**
	 * Serialize a list of predicates into an SQL predicate string
	 * @param predList list of predicate clauses
	 * @return the predicates list as an SQL predicate string
	 */
	public final String serialize(IPredicatesList predList) {
		
		StringBuilder sql = new StringBuilder();
		for (IPredicate pred : predList.getPredicates()) {
			sql.append(serialize(pred)).append(" ");
		}
		
		return CoreUtils.normalizeString(sql.toString());
	}
	
	/**
	 * Serialize an SQL Condition into an SQL condition string
	 * @param sqlCond a SQL condition 
	 * @return the SQL condition as an SQL predicate string
	 */
	public final String serialize(ISQLCondition sqlCond) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(serialize(sqlCond.getLeftCondition())).append(" ");
		sql.append(sqlCond.getOperator().opToString()).append(" ");
		sql.append(serialize(sqlCond.getRightCondition())).append(" ");

		return CoreUtils.normalizeString(sql.toString());
	}
	
	/**
	 * Serialize a CASE statement into a string SQL clause
	 * @param caseStmt the CASE statement
	 * @return an ICaseClause node as a CASE clause string 
	 */
	public final String serialize(ICaseClause caseStmt) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("case").append(" ");
		if (caseStmt.getCaseExpr() != null) {
			sql.append(serialize(caseStmt.getCaseExpr())).append(" ");
		}
		
		sql.append(serialize(caseStmt.getWhenThenConditions())).append(" ");
		if (caseStmt.hasElse()) {
			sql.append("else ").append(serialize(caseStmt.getElse())).append(" ");
		}
		sql.append("end");
		return CoreUtils.normalizeString(sql.toString());
	}
	
	/**
	 * Serialize CASE statement list of WHEN value THEN value into a string SQL clause
	 * @param streamWhenThen the <code>Stream</code> of WHEN THEN conditions
	 * @return the list as a SQL string
	 */
	public final String serialize(Stream<ICaseWhenThen> streamWhenThen) {
		StringBuilder sql = new StringBuilder();
		
		streamWhenThen.forEach(whenThenCond -> {
			if (whenThenCond.isWhenValueSQLCondition()) {
				sql.append("when").append(" ").append(serialize(whenThenCond.getWhenCondition())).append(" ");
			} else if (whenThenCond.isWhenValueSQLType()) {
				sql.append("when").append(" ").append(serialize(whenThenCond.getWhenType())).append(" ");
			}
			sql.append("then").append(" ").append(serialize(whenThenCond.getThenCondition())).append(" ");
		});
		return CoreUtils.normalizeString(sql.toString());
	}

}
