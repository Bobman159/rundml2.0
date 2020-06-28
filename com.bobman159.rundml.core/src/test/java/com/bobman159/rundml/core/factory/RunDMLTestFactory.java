package com.bobman159.rundml.core.factory;

import com.bobman159.rundml.core.expressions.IExpressionFactory;
import com.bobman159.rundml.core.sql.types.ISQLClauseFactory;
import com.bobman159.rundml.core.sql.types.ISQLTypesFactory;

public class RunDMLTestFactory implements ISQLTypesFactory, ISQLClauseFactory, IExpressionFactory {

	private static RunDMLTestFactory self;
	
	public static RunDMLTestFactory getInstance() {
		if (self == null) {
			self = new RunDMLTestFactory();
		}
		return self;
	}
}
