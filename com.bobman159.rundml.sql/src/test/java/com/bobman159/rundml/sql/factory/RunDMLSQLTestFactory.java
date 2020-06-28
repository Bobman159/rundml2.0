package com.bobman159.rundml.sql.factory;

import com.bobman159.rundml.core.expressions.IExpressionFactory;
import com.bobman159.rundml.core.sql.types.ISQLClauseFactory;
import com.bobman159.rundml.core.sql.types.ISQLTypesFactory;

public class RunDMLSQLTestFactory implements IExpressionFactory, ISQLClauseFactory, ISQLTypesFactory {

	private static RunDMLSQLTestFactory self;
	
	public static RunDMLSQLTestFactory getInstance() {
		if (self == null) {
			self = new RunDMLSQLTestFactory();
		}
		return self;
	}
}
