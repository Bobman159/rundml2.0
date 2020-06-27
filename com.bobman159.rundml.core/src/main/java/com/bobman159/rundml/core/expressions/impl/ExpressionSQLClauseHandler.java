package com.bobman159.rundml.core.expressions.impl;

import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.model.SQLModelNode;
import com.bobman159.rundml.core.sql.AbstractSQLClauseHandler;
import com.bobman159.rundml.core.sql.impl.SQLClauseClient;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * Concrete handler for creation of SQL clause text for SQL expressions.
 *
 */
public class ExpressionSQLClauseHandler extends AbstractSQLClauseHandler {	

	/**
	 * A SQL clause handler for expressions and add a new handler 
	 * @param handler the next SQL clause handler
	 */
	public ExpressionSQLClauseHandler(AbstractSQLClauseHandler handler) {
		super(handler);
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.AbstractSQLClauseHandler#toSQLClause(SQLModelNode)
	 */
	@Override
	public String toSQLClause(ISQLType typeNode) {
		
		String sqlClause = "";
		
		if (!(SQLType.EXPRESSION.equals(typeNode.getType()))) {
			setHandled(false);
		} else {
			setHandled(true);
			sqlClause = exprtoSQLText((IExpressionNode) typeNode);
		}
		
		return sqlClause;
	}
	
	/*
	 * Serialize an expresion to an SQL clause in text form 
	 * @param typeNode the expression to create a SQL clause for
	 * @return the clause in SQL text
	 */
	private String exprtoSQLText(IExpressionNode typeNode) {
		
		SQLType nodeType = typeNode.getType();
		StringBuilder sqlExpr = new StringBuilder();
		
		if ((SQLType.NUMERIC.equals(nodeType)) ||
			(SQLType.STRING.equals(nodeType)) ||
			(SQLType.COLUMN.equals(nodeType)) ||
			(SQLType.PARM_MARKER.equals(nodeType))) {
				sqlExpr.append(SQLClauseClient.getInstance().toSQLClause(typeNode));
		} else if (SQLType.EXPRESSION.equals(nodeType)) {
				sqlExpr.append(serializeIExpressionNode(typeNode));
		}
		
		return CoreUtils.normalizeString(sqlExpr.toString());
	}

	/*
	 * Serialize an expression node (math or string) expression into a string SQL expression clause
	 * @param exprNode the node to serialize
	 * @return the expression node as an SQL expression string
	 */
	private String serializeIExpressionNode(IExpressionNode exprNode) {

		StringBuilder sql = new StringBuilder();
				
		if (exprNode.getLeftExpr() instanceof IExpressionNode) {
			sql.append(serializeIExpressionNode((IExpressionNode) exprNode.getLeftExpr())).append(" ");
			if (exprNode.getOperator() != null) {
				sql.append(exprNode.getOperator().opToString()).append(" ");
			}
		} else {
			ISQLType exprValue = exprNode.getLeftExpr();
			sql.append(SQLClauseClient.getInstance().toSQLClause(exprValue)).append(" ");
			if (exprNode.getOperator() != null) {
				sql.append(exprNode.getOperator().opToString()).append(" ");
			}
		}
		
		if (exprNode.getRightExpr() instanceof IExpressionNode) {
			sql.append(serializeIExpressionNode((IExpressionNode) exprNode.getRightExpr())).append(" ");
		} else {
			ISQLType exprValue = exprNode.getRightExpr();
			if (exprValue != null) {
				sql.append(SQLClauseClient.getInstance().toSQLClause(exprValue)).append(" ");
			}
		}
		
		return CoreUtils.normalizeString(sql.toString());
	}

}
