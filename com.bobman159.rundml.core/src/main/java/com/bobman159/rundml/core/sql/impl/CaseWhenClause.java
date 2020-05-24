package com.bobman159.rundml.core.sql.impl;

import java.util.stream.Stream;

import com.bobman159.rundml.core.sql.ICaseStatement;
import com.bobman159.rundml.core.sql.ICaseWhenConditions;
import com.bobman159.rundml.core.sql.ISQLClause;
import com.bobman159.rundml.core.sql.types.ISQLType;

public class CaseWhenClause implements ICaseStatement {

	@Override
	public ISQLClause getType() {
		return null;
	}

	@Override
	public ISQLType getCaseExpr() {
		return null;
	}

	@Override
	public Stream<ICaseWhenConditions> getWhenConditions() {
		return null;
	}

	@Override
	public boolean hasElse() {
		return false;
	}

	@Override
	public boolean isCaseWhen() {
		return false;
	}

	@Override
	public ISQLType getElse() {
		return null;
	}

}
