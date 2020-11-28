package com.bobman159.rundml.sql.mysql;

import com.bobman159.rundml.common.serialize.SQLStatementSerializerService;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;
import com.bobman159.rundml.sql.generic.GenericSelectStatement;
/**
 * Definition for MySQL SELECT statment clauses.
 *
 */
public class MySQLSelectStatement extends GenericSelectStatement implements IMySQLSelectStatement {

	private ISQLType offset;
	private ISQLType limit;
	private boolean smallResult = false;
	private boolean  bigResult = false;
	private boolean  bufferResult = false;
	
	@Override
	public void addOffset(int offSet) {
		offset = (ISQLType) SQLTypeFactory.getInstance().constant(offSet);
	}

	@Override
	public void addOffset(ISQLType offSet) {
		this.offset = offSet;
	}

	@Override
	public ISQLType getOffset() {
		return offset;
	}

	@Override 
	public void addLimit(int limitTerm) {
		this.limit = (ISQLType) SQLTypeFactory.getInstance().constant(limitTerm);
	}
	
	@Override
	public void addLimit(ISQLType limitTerm) {
		this.limit = limitTerm;
	}

	@Override
	public ISQLType getLimit() {
		return limit;
	}

	@Override
	public void setSmallResult() {
		smallResult = true;
	}

	@Override
	public boolean hasSmallResult() {
		return smallResult;
	}

	@Override
	public void setBigResult() {
		bigResult = true;
	}

	@Override
	public boolean hasBigResult() {
		return bigResult;
	}

	@Override
	public void setBufferResult() {
		bufferResult = true;
	}

	@Override
	public boolean hasBufferResult() {
		return bufferResult;
	}
	
	@Override
	public String toSQL() {
		String sql = "";
		sql = SQLStatementSerializerService.getInstance().serializeSelect(this);
		return sql;
	}

}
