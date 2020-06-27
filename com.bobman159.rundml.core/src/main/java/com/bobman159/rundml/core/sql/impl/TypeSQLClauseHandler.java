package com.bobman159.rundml.core.sql.impl;

import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.sql.AbstractSQLClauseHandler;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;
import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.sql.types.impl.NumericType;
import com.bobman159.rundml.core.sql.types.impl.StringType;
import com.bobman159.rundml.core.sql.types.impl.Table;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * Concrete handler for creation of SQL clause text of SQL types.
 * 
 */
public class TypeSQLClauseHandler extends AbstractSQLClauseHandler {

	/**
	 * Create type SQL clause handler 
	 * @param nextHandler the next SQL clause handler
	 */
	public TypeSQLClauseHandler(AbstractSQLClauseHandler nextHandler) {
		super(nextHandler);
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.AbstractSQLClauseHandler#toSQLClause(com.bobman159.rundml.core.sql.types.ISQLType)
	 */
	@Override
	public String toSQLClause(ISQLType typeNode) throws RunDMLException {
		
		if ((SQLType.EXPRESSION.equals(typeNode.getType())) ||
			(SQLType.PREDICATE.equals(typeNode.getType()))) {
			setHandled(false);
			return "";
		}
		
		String clause = "";
		if (SQLType.COLUMN.equals(typeNode.getType())) {
			clause = ((Column) typeNode).getColumnName();
		} else if (SQLType.PARM_MARKER.equals(typeNode.getType())) {
			clause = "?";
		} else if (SQLType.NUMERIC.equals(typeNode.getType())) {
			clause = ((NumericType) typeNode).toString();
		} else if (SQLType.STRING.equals(typeNode.getType())) {
			clause = ((StringType) typeNode).toString();
		} else if (SQLType.TABLE.equals(typeNode.getType())) {
			clause = ((Table) typeNode).tableName();
		} else {
			UnsupportedOperationException ex = new UnsupportedOperationException("SQL Type: " + typeNode.getType() + " was not found for clause creation");
			throw RunDMLException.createRunDMLException(ex, RunDMLException.SQL_MODEL_BUILD, (Object) null);

		}
		
		setHandled(true);
		return CoreUtils.normalizeString(clause);
	}

}
