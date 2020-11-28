package com.bobman159.rundml.sql.h2;

import com.bobman159.rundml.common.serialize.AbstractStatementSerializer;
import com.bobman159.rundml.common.serialize.SQLStatementSerializerService;
import com.bobman159.rundml.core.model.ISQLStatement;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;
import com.bobman159.rundml.sql.generic.GenericSelectStatement;

/**
 * Definition for H2 SELECT statement clauses
 */
public class H2SelectStatement extends GenericSelectStatement implements IH2SelectStatement {

	private ISQLType topDef;
	private ISQLType limitDef;
	private ISQLType offsetDef;
	
	@Override
	public void addTop(int topNumber) {
		topDef = (ISQLType) SQLTypeFactory.getInstance().constant(topNumber);
	}

	@Override
	public void addTop(ISQLType topExpr) {
		topDef = topExpr;
	}
	
	@Override
	public ISQLType getTop() {
		return topDef;
	}

	@Override
	public void addLimit(ISQLType limitTerm) {
		limitDef = limitTerm;
	}
	
	@Override
	public ISQLType getLimit() {
		return limitDef;
	}

	@Override
	public void addOffset(ISQLType offset) {
		offsetDef = offset;
	}

	@Override
	public ISQLType getOffset() {
		return offsetDef;
	}
	
	@Override 
	public String getDialect() {
		return AbstractStatementSerializer.H2_SELECT;
	}
	
	@Override
	public String toSQL() {
		String sql = "";
		sql = SQLStatementSerializerService.getInstance().serializeSelect(this);
		return sql;
	}
	
	@Override
	public ISQLStatement.Statement getStatement() {
		return ISQLStatement.Statement.SELECT;
	}

}
