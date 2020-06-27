package com.bobman159.rundml.core.predicates.impl;

import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.sql.AbstractSQLClauseHandler;
import com.bobman159.rundml.core.sql.impl.SQLClauseClient;
import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * A SQL clause handler for predicates.
 *
 */
public class PredicateSQLClauseHandler extends AbstractSQLClauseHandler {

	public PredicateSQLClauseHandler(AbstractSQLClauseHandler handler) {
		super(handler);
	}

	@Override
	public String toSQLClause(ISQLType typeNode) {
		
		String sqlClause = "";
		
		if (!(SQLType.PREDICATE.equals(typeNode.getType()))) {
			setHandled(false);
		} else {
			setHandled(true);
			sqlClause = predtoSQLText((IPredicate) typeNode);
		}
		
		return sqlClause;
	}
	
	private String predtoSQLText(IPredicate predNode) {
	
		SQLType nodeType = predNode.getType();
		StringBuilder sqlPred = new StringBuilder();
		
		if ((SQLType.NUMERIC.equals(nodeType)) ||
			(SQLType.STRING.equals(nodeType)) ||
			(SQLType.COLUMN.equals(nodeType)) ||
			(SQLType.PARM_MARKER.equals(nodeType)) ||
			(SQLType.EXPRESSION).equals(nodeType)) {
				sqlPred.append(SQLClauseClient.getInstance().toSQLClause(predNode));
		} else if (SQLType.PREDICATE.equals(nodeType)) {
				sqlPred.append(serializePredicate(predNode));
		}
		
		return CoreUtils.normalizeString(sqlPred.toString());		
		
	}

	/*
	 * Serialize an SQL predicate (WHERE, AND, OR) into a string SQL clause 
	 * @param predNode the predicate clause to serialize
	 * @return the predicate node as a SQL clause
	 */
	private String serializePredicate(IPredicate predNode) {
		
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
		sql.append(SQLClauseClient.getInstance().toSQLClause(cond.getLeftCondition())).append(" ");
		sql.append(cond.getOperator().opToString()).append(" ");
		sql.append(SQLClauseClient.getInstance().toSQLClause(cond.getRightCondition())).append(" ");
		
		return CoreUtils.normalizeString(sql.toString());
	}

}
